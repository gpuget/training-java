package com.excilys.cdb.dto;

import java.io.Serializable;

public class ComputerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private CompanyDTO manufacturer;
    private String introduced;
    private String discontinued;

    /**
     * Constructor.
     */
    public ComputerDTO() {
        this.id = "";
        this.name = "";
        this.manufacturer = new CompanyDTO();
        this.introduced = "";
        this.discontinued = "";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        
        hash = hash * 11 + id.hashCode();
        hash = hash * 11 + name.hashCode();
        hash = hash * 11 + manufacturer.hashCode();
        hash = hash * 11 + introduced.hashCode();
        hash = hash * 11 + discontinued.hashCode();
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ComputerDTO) {
            ComputerDTO computerDto = (ComputerDTO) obj;

            return this.id.equals(computerDto.id) &&
                    this.name.equals(computerDto.name) &&
                    this.manufacturer.equals(computerDto.manufacturer) &&
                    this.introduced.equals(computerDto.introduced) &&
                    this.discontinued.equals(computerDto.discontinued);
        }

        return false;
    }

    @Override
    public String toString() {
        String res;

        res = "Computer : " + this.name + " (" + this.manufacturer.getName()
                + ") " + this.introduced;
        if (this.discontinued != null) {
            res = res + " to " + this.discontinued;
        }

        return res;
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

    public CompanyDTO getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(CompanyDTO manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        if (introduced.equals("null")) {
            this.introduced = "-";
        } else {
            this.introduced = introduced;
        }
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        if (discontinued.equals("null")) {
            this.discontinued = "-";
        } else {
            this.discontinued = discontinued;
        }
    }
}
