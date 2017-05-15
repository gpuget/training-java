package com.excilys.cdb.model;

import java.util.List;

public class Page<T> {
    private List<T> objects;
    private int number;
    private int maxPerPage;

    /**
     * Constructor.
     *
     * @param number number of page
     * @param list list of objects
     */
    public Page(int number, List<T> list) {
        objects = list;
        maxPerPage = list.size();
        this.number = number;
    }

    @Override
    public String toString() {
        String res = "Page " + number + " : ";

        res = res + objects;

        return res;
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
}
