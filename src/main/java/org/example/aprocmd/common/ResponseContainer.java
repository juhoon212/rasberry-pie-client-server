package org.example.aprocmd.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.aprocmd.exception.FieldErrorDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResponseContainer<T> {

    private T payload;
    private boolean success;
    private String message;
    private List<FieldErrorDetail> details;
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
        this.message = t.getMessage();
    }

    public void setError(List<FieldErrorDetail> details) {
        this.success = false;
        this.details = details;
    }
    @Getter
    @Setter
    @ToString
    public static class PageInfo {
        private Long totalCount;
        private int totalPages;
        private int pageNo;
        private Integer size;
    }

}
