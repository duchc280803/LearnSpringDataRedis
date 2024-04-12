package com.example.learnspringdataredis.param;

import java.io.Serializable;

public record PagingParam(Integer pageNumber, Integer pageSize) implements Serializable {

    /**
     * Constructor to create default values for pageNumber and pageSize
     * @param pageNumber
     * @param pageSize
     */
    public PagingParam(Integer pageNumber, Integer pageSize) {
        this.pageNumber = (pageNumber != null) ? pageNumber : 0;
        this.pageSize = (pageSize != null) ? pageSize : 10;
    }
}

