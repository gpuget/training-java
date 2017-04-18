package com.excilys.cdb.models;

import java.util.List;
import java.util.ArrayList;

public class Page<T> {
    public static final int MAX_PER_PAGE = 20;

    private List<T> objects;
    private int number;

    /**
     * Constructor.
     */
    public Page() {
        this.objects = new ArrayList<>();
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

}
