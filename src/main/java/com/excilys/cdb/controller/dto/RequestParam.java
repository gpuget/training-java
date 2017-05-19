package com.excilys.cdb.controller.dto;

public class RequestParam {
    private String numberPage;
    private String maxPerPage;
    private String search;

    /**
     * @return the numberPage
     */
    public String getNumberPage() {
        return numberPage;
    }

    /**
     * @param numberPage the numberPage to set
     */
    public void setNumberPage(String numberPage) {
        this.numberPage = numberPage;
    }

    /**
     * @return the maxPerPage
     */
    public String getMaxPerPage() {
        return maxPerPage;
    }

    /**
     * @param maxPerPage the maxPerPage to set
     */
    public void setMaxPerPage(String maxPerPage) {
        this.maxPerPage = maxPerPage;
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
}
