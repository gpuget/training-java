package com.excilys.cdb.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

@Component
public class RequestParameters {
    public static final String SEARCH_REGEX = "[\\w\\+\\-\\ \\.]*";
    public static final String COLUMN_REGEX = "id|name|introduced|discontinued|companyName";

    @Min(1)
    private int page;

    @Min(10)
    private int max;

    @Pattern(regexp = SEARCH_REGEX)
    private String search;
    
    @Pattern(regexp = COLUMN_REGEX)
    private String column;
    
    @Min(0)
    private int order;

    @Override
    public String toString() {
        return "RequestParameters [page=" + page + ", max=" + max + ", search=" + search + "]";
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the search
     */
    public String getSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * @return the column
     */
    public String getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * @return the order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(int order) {
        this.order = order;
    }
}
