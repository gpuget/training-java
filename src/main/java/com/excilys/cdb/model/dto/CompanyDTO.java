package com.excilys.cdb.model.dto;

import java.io.Serializable;

public class CompanyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    /**
     * Constructor.
     */
    public CompanyDTO() {
        id = "";
        name = "";
    }

    @Override
    public int hashCode() {
        int hash = 2;

        hash = hash * 10 + id.hashCode();
        hash = hash * 10 + name.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof CompanyDTO) {
            CompanyDTO companyDto = (CompanyDTO) obj;

            return this.id.equals(companyDto.id) && this.name.equals(companyDto.name);
        }

        return false;
    }

    @Override
    public String toString() {
        return "Company (" + id + ") " + name;
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
