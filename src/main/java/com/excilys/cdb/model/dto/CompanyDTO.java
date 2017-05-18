package com.excilys.cdb.model.dto;

import java.io.Serializable;

public class CompanyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;

    /**
     * Constructor.
     */
    public CompanyDTO() {
        name = "";
    }

    /**
     * Constructor.
     */
    public CompanyDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 2;

        hash = hash * 10 + Long.hashCode(id);
        hash = hash * 10 + name.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof CompanyDTO) {
            CompanyDTO companyDto = (CompanyDTO) obj;

            return this.id == companyDto.id && this.name.equals(companyDto.name);
        }

        return false;
    }

    @Override
    public String toString() {
        return "Company (" + id + ") " + name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
