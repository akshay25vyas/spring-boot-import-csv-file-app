//package com.pixeltrice.springbootimportcsvfileapp;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Scope;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.util.Map;
//
//@Component
//@Scope(value = "singleton")
//@Slf4j
//public class GenericError {
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    private static final String STATIC_ERROR_CONFIG_JSON = "static/errorConfig.json";
//    private static Map<String, GenericResponse> errorMap = null;
//
//    private static final ObjectMapper mapper = new ObjectMapper();
//
//    public static GenericResponse<?> getError(Exception genericException) {
//        if (genericException == null) {
//            return null;
//        }
//        if (errorMap != null) {
//            GenericResponse<?> errorResponse = errorMap.get(genericException
//                    .getMessage());
//            return errorResponse;
//        } else {
//            return null;
//        }
//    }
//
//    public static GenericResponse<?> getError(String errorString) {
//
//        if (errorMap != null) {
//            return errorMap.get(errorString);
//        } else {
//            return null;
//        }
//    }
//
//    @PostConstruct
//    public void initialize() {
//        Resource resource = applicationContext.getResource("classpath:"+ STATIC_ERROR_CONFIG_JSON);
//        TypeReference<Map<String, GenericResponse>> type = new TypeReference<Map<String, GenericResponse>>() {
//        };
//        try {
//            errorMap = mapper.readValue(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8), type);
//            System.out.println("errorMap " + errorMap);
//        } catch (Exception e) {
//            log.error("Error file loading failed: ", e);
//        }
//    }
//}
