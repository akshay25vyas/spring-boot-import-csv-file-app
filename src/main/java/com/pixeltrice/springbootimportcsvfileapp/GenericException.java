//package com.pixeltrice.springbootimportcsvfileapp;
//
//import lombok.Data;
//
//@Data
//public class GenericException extends RuntimeException {
//
//    private static final long serialVersionUID = 1L;
//
//    private GenericResponse response;
//
//    public GenericException() {
//        super();
//        response = GenericError.getError(GenericExceptionList.GENERIC_ERROR.name());
//    }
//
//    public GenericException(String message, String varName) {
//        super(message);
//
//        GenericResponse temp = GenericError.getError(message);
//        response = new GenericResponse(temp != null ? temp : GenericError.getError(GenericExceptionList.GENERIC_ERROR.name()));
//
//        if (varName != null && temp != null) {
//            String msg = varName + " " + response.getMessage();
//            response.setMessage(msg);
//        }
//    }
//
//    public GenericException(String message, Throwable cause) {
//        super(message, cause);
//        response = GenericError.getError(message);
//        if(response == null) {
//            response = new GenericResponse(GenericError.getError(GenericExceptionList.GENERIC_ERROR.name()));
//            response.setMessage(cause.getMessage());
//        }
//    }
//
//    public GenericException(String message) {
//        super(message);
//        GenericResponse temp = GenericError.getError(message);
//        response = temp != null ? temp : GenericError.getError(GenericExceptionList.GENERIC_ERROR.name());
//    }
//
//    public GenericException(Throwable cause) {
//        super(cause);
//        response = new GenericResponse(GenericError.getError(GenericExceptionList.GENERIC_ERROR.name()));
//        response.setMessage(cause.getMessage());
//    }
//}
