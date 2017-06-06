package com.excilys.cdb.model;

import java.util.List;

public class Page<T> {
    private List<T> objects;
    private int number;
    private int maxPerPage;
    private int total;

    /**
     * Constructor.
     */
    public Page() {
    }

    /**
     * Constructor.
     *
     * @param number number of page
     * @param list list of objects
     */
    public Page(int number, int maxPerPage, List<T> list) {
        objects = list;
        this.maxPerPage = maxPerPage;
        this.number = number;
    }

    @Override
    public String toString() {
        String res = "Page " + number + " : ";

        res = res + objects;

        return res;
    }

    /**
     * Returns the last page number.
     *
     * @return last page number
     */
    public int getLastNumber() {
        return 1 + (total / maxPerPage);
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMaxPerPage() {
        return maxPerPage;
    }

    public void setMaxPerPage(int maxPerPage) {
        this.maxPerPage = maxPerPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
