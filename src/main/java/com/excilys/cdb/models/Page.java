package com.excilys.cdb.models;

import java.util.List;

public class Page<T> {
    private List<T> objects;
    private int number;
    public int maxPerPage;

    /**
     * Constructor.
     * @param list list of objects
     */
    public Page(List<T> list) {
        this.objects = list;
        this.maxPerPage = list.size();
        this.number = 1;
    }

    @Override
    public String toString() {
        String res = "Page " + number + " :\n";

        for (T obj : objects) {
            res = res + obj + '\n';
        }

        return res;
    }

    /**
     * Increments the number of the page.
     */
    public void next() {
        this.number++;
    }

    /**
     * Decrements the number of the page.
     */
    public void previous() {
        if (this.number > 1) {
            this.number--;
        }
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
        if (number > 0) {
            this.number = number;
        }
    }
}
