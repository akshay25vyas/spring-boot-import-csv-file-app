package com.pixeltrice.springbootimportcsvfileapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class CSVService {
    public static final Double ALPHA = 0.5;
    public static final Double Beta = 0.5;
    public static final Integer k = 10;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    DeveloperTutorialRepository repository;

    @Autowired
    UserStocksRepository stocksRepository;

    @Autowired
    StockEsgRepository stockEsgRepository;

    @Autowired
    StockPriceRepository stockPriceRepository;


    public void save(MultipartFile file) {
        if (Objects.equals(file.getOriginalFilename(), "data.csv")) {

            try {
                List<DeveloperTutorial> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
                repository.saveAll(tutorials);
            } catch (IOException e) {
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }
        }

        if (Objects.equals(file.getOriginalFilename(), "stock_esg_data.csv")) {
            try {
                List<StockEsg> stockEsgs = CSVHelper.csvToStockEsgs(file.getInputStream());
                stockEsgRepository.saveAll(stockEsgs);
            } catch (IOException e) {
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }
        }

        if (Objects.equals(file.getOriginalFilename(), "stock_price_data.csv")) {
            try {
                List<StockPrice> stockPrice = CSVHelper.csvToStockPrice(file.getInputStream());
                stockPriceRepository.saveAll(stockPrice);
            } catch (IOException e) {
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }
        }
    }

    public ByteArrayInputStream load() {
        List<DeveloperTutorial> tutorials = repository.findAll();

        ByteArrayInputStream in = CSVHelper.tutorialsToCSV(tutorials);
        return in;
    }

    public List<DeveloperTutorial> getAllTutorials() {
        return repository.findAll();
    }

    public UserProfileResponse getUserProfileFromId(String email) {
        UserProfileResponse response = new UserProfileResponse(email);

        DeveloperTutorial user = repository.findUserByEmail(email);
        response.setUserName(user.getName());

        return response;
    }

    public boolean addUserInvestment(String email, List<StockInvestmentRequest> stocks) {

        DeveloperTutorial user = repository.findUserByEmail(email);

        user.setEmail(email);

        int incentive = 0;

        for (StockInvestmentRequest stock : stocks) {
            incentive += stock.getIncentive();
        }
        user.setIncentive(incentive);

        addUserStocksInInvestment(user.getUserid(), stocks);


        return true;
    }

    private void addUserStocksInInvestment(int userId, List<StockInvestmentRequest> stocks) {
        for (StockInvestmentRequest stock : stocks) {
            UserStocks userStocks = stocksRepository.findUserStocksFromUserIdAndStockId(userId, stock.getStockId());
            if (userStocks == null) {
                userStocks = new UserStocks();
            }

            userStocks.setUserid(userId);
            userStocks.setQuantity(userStocks.getQuantity() + stock.getQuantity());
            userStocks.setTotalPurchasePrice(userStocks.getTotalPurchasePrice() + stock.getPrice() * stock.getQuantity());

        }
    }

    private void getEsgValueByStockName(String stockName) {
        StockEsg stockEsg = stockEsgRepository.findById(stockName).get();
    }

    public List<Company> getTopCompaniesToInvest() {

        PriorityQueue<PairClass> pq = new PriorityQueue<>(100, new PairClassComparator());


        List<StockEsg> stockEsgList = stockEsgRepository.findAll();

        for (StockEsg stockEsg : stockEsgList) {
            String name = stockEsg.getStockname();
            Double esgValue = stockEsg.getEsgvalue();

            Optional<StockPrice> stockPrice = stockPriceRepository.findById(name);
            Double predictionPrice = stockPrice.get().getStockPricePredicted();

            pq.add(new PairClass(name, ALPHA * esgValue + Beta * predictionPrice, esgValue, predictionPrice));

        }

        List<Company> companyList = new ArrayList<>();

        while (!pq.isEmpty()) {

            companyList.add(new Company(pq.peek().name, pq.peek().esgScore, pq.peek().stockPredictedPrice));
            pq.remove();

            if (companyList.size() == k) break;
        }
        pq.clear();
        return companyList;
    }


    class PairClassComparator implements Comparator<PairClass> {

        // Overriding compare()method of Comparator
        // for descending order of cgpa
        public int compare(PairClass s1, PairClass s2) {
            if (s1.loss < s2.loss)
                return 1;
            else if (s1.loss > s2.loss)
                return -1;
            return 0;
        }
    }


    public List<Company> getTopCompaniesToInvestWithLP() throws Exception {
        return callFlaskApiWithParam();
    }


    private List<Company> callFlaskApiWithParam() throws JsonProcessingException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String flaskResourceUrl
                = "http://localhost:5000/evaluate";


        PriorityQueue<PairClass> pq = new PriorityQueue<>(100, new PairClassComparator());

        List<StockEsg> stockEsgList = stockEsgRepository.findAll();

        for (StockEsg stockEsg : stockEsgList) {
            String name = stockEsg.getStockname();
            Double esgValue = stockEsg.getEsgvalue();

            Optional<StockPrice> stockPrice = stockPriceRepository.findById(name);
            Double predictionPrice = stockPrice.get().getStockPricePredicted();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject personJsonObject = new JSONObject();
            personJsonObject.put("esgvalue", esgValue);
            personJsonObject.put("stockpricepredicted", predictionPrice);

            HttpEntity<String> request =
                    new HttpEntity<>(personJsonObject.toString(), headers);

            String personResultAsJsonStr =
                    restTemplate.postForObject(flaskResourceUrl, request, String.class);
            JsonNode root = objectMapper.readTree(personResultAsJsonStr);
            JsonNode alphaReturned = root.path("alpha");
            JsonNode betaReturned = root.path("beta");

            pq.add(new PairClass(name, alphaReturned.asDouble() * esgValue + betaReturned.asDouble() * predictionPrice, esgValue, predictionPrice));

        }

        List<Company> companyList = new ArrayList<>();

        while (!pq.isEmpty()) {

            companyList.add(new Company(pq.peek().name, pq.peek().esgScore, pq.peek().stockPredictedPrice));
            pq.remove();

            if (companyList.size() == k) break;
        }
        pq.clear();
        return companyList;

    }


}


