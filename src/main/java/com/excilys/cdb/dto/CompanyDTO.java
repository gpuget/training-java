package com.excilys.cdb.dto;

import java.io.Serializable;

public class CompanyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    /**
     * Constructor.
     */
    public CompanyDTO() {
        this.id = "";
        this.name = "";
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CompanyDTO) {
            CompanyDTO c = (CompanyDTO) obj;

            return this.name.equals(c.name);
        }

        return false;
    }

    @Override
    public String toString() {
        return "Company :" + this.name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
