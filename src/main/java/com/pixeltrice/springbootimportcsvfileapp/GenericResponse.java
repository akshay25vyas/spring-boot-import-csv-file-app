package com.pixeltrice.springbootimportcsvfileapp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
public class GenericResponse<T> implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private int code;
    private Integer errorCode;
    private String status;
    private String message;
    private T data;

    public GenericResponse(int code, String status, String message) {
        super();
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public GenericResponse(GenericResponse<T> genericResponse) {
        super();
        this.code = genericResponse.code;
        this.status = genericResponse.status;
        this.message = genericResponse.message;
        this.data = genericResponse.data;
        this.errorCode = genericResponse.errorCode;
    }

    public GenericResponse() {
        super();
    }

    public static <T> GenericResponse<T> getSuccessResponse(Class<T> type) {
        return new GenericResponse<T>(200, "success", null);
    }

    public static <T> GenericResponse<T> getFailureResponse(Class<T> type) {
        return new GenericResponse<T>(404, "failure", null);
    }

    public static <T> GenericResponse<T> getFailureResponse(Class<T> type, Integer code) {
        return new GenericResponse<T>(code, "failure", null);
    }

    public static <T> GenericResponse<T> getSuccessResponse(Class<T> type, Integer code, String msg) {
        return new GenericResponse<T>(code, "success", msg);
    }

    public static <T> GenericResponse<T> getFailureResponse(Class<T> type, Integer code, String msg) {
        return new GenericResponse<T>(code, "failure", msg);
    }

    public GenericResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public T getData() {
        return data;
    }
}
