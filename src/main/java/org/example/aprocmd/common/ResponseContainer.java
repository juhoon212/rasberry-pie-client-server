package org.example.aprocmd.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.SQLException;

public class ResponseContainer<T> {

    private T payload;
    private boolean success;
    private String message;
    private ErrDetail detail;
    private PageInfo pageInfo;
    public static<P> ResponseContainer<P> emptyResponse() {
        return new ResponseContainer<P>();
    }
    public void setPayload(T payload) {
        this.payload = payload;
        this.success = true;
    }

    public void setError(Throwable t) {
        this.success = false;
        this.detail = new ErrDetail();
        this.message = t.getMessage();
        Throwable cause;

        cause = t.getCause();
        if(cause != null) {
            while(true) {
                if(cause.getCause()!= null) {
                    cause = cause.getCause();
                } else {
                    break;
                }
            }
            if(cause instanceof SQLException) {
                detail.code = ((SQLException)cause).getErrorCode();
            }
            detail.message = cause.getMessage();
        }
    }
    @Getter
    @Setter
    @ToString
    public class PageInfo {
        private Long totalCount;
        private int totalPages;
        private int pageNo;
        private Integer size;
    }
    @Getter
    @Setter
    @ToString
    public class ErrDetail {
        private int code;
        private String message;
    }
}
