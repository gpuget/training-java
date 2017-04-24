package com.excilys.cdb.models;

import java.util.List;

public class Page<T> {
    private List<T> objects;
    private int number;
    private int maxPerPage;

    /**
     * Constructor.
     * @param list list of objects
     */
    public Page(int number, List<T> list) {
        this.objects = list;
        this.maxPerPage = list.size();
        this.number = number;
    }

    @Override
    public String toString() {
        String res = "Page " + number + " :\n";

        for (T obj : objects) {
            res = res + obj + '\n';
        }

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
    
    public void setNumber(int number){
        this.number = number;
    }

    public int getMaxPerPage() {
        return maxPerPage;
    }

    public void setMaxPerPage(int maxPerPage) {
        this.maxPerPage = maxPerPage;
    }
}
