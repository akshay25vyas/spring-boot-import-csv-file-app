package com.pixeltrice.springbootimportcsvfileapp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {
    @Autowired
    DeveloperTutorialRepository repository;

    @Autowired
    UserStocksRepository stocksRepository;

    public void save(MultipartFile file) {
        try {
            List<DeveloperTutorial> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
            repository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
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

//    List<UserStocks> userStocks = stockRepository.findAllStocksByUserId(user.getUserId());
//    response.setStocks(userStocks);

        return response;
    }

    public boolean addUserInvestment(String email, List<StockInvestmentRequest> stocks) {

        DeveloperTutorial user = repository.findUserByEmail(email);
//        User user = new User();

        user.setEmail(email);

//        int incentive = user.getIncentive();
        int incentive = 0;

        for (StockInvestmentRequest stock : stocks) {
            incentive += stock.getIncentive();
        }
        user.setIncentive(incentive);

        addUserStocksInInvestment(user.getUserid(), stocks);

//    log.info("Incentive: " + incentive);

        return true;
    }

    private void addUserStocksInInvestment(int userId, List<StockInvestmentRequest> stocks) {
        for (StockInvestmentRequest stock : stocks) {
            UserStocks userStocks = stocksRepository.findUserStocksFromUserIdAndStockId(userId, stock.getStockId());
            if (userStocks == null) {
                userStocks = new UserStocks();
            }
//      UserStocks userStocks = new UserStocks();
            userStocks.setUserid(userId);
            userStocks.setQuantity(userStocks.getQuantity() + stock.getQuantity());
            userStocks.setTotalPurchasePrice(userStocks.getTotalPurchasePrice() + stock.getPrice() * stock.getQuantity());
            // Save userStocks record
//      log.info(userStocks.toString());
        }
    }

}

