package com.excilys.cdb.model.dto;

import java.io.Serializable;

public class ComputerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String companyId;
    private String companyName;
    private String introduced;
    private String discontinued;

    /**
     * Constructor.
     */
    public ComputerDTO() {
        id = "";
        name = "";
        companyId = "";
        companyName = "";
        introduced = "";
        discontinued = "";
    }

    @Override
    public int hashCode() {
        int hash = 6;

        hash = hash * 11 + id.hashCode();
        hash = hash * 11 + name.hashCode();
        hash = hash * 11 + companyId.hashCode();
        hash = hash * 11 + companyName.hashCode();
        hash = hash * 11 + introduced.hashCode();
        hash = hash * 11 + discontinued.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ComputerDTO) {
            ComputerDTO computerDto = (ComputerDTO) obj;

            return this.id.equals(computerDto.id) && this.name.equals(computerDto.name)
                    && this.companyId.equals(computerDto.companyId)
                    && this.companyName.equals(computerDto.companyName)
                    && this.introduced.equals(computerDto.introduced)
                    && this.discontinued.equals(computerDto.discontinued);
        }

        return false;
    }

    @Override
    public String toString() {
        String res;

        res = "Computer (" + id + ") " + name;
        if (companyName != null) {
            res = res + " [" + companyName + "]";
        }

        if (introduced != null && !introduced.equals("-")) {
            res = res + " from " + introduced;
        }

        if (discontinued != null && !discontinued.equals("-")) {
            res = res + " to " + discontinued;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIntroduced() {
        return introduced;
    }

    /**
     * Sets the date of introduction.
     *
     * @param introduced date of introduction - '-' if equals "null"
     */
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

    /**
     * Sets the date of discontinue.
     *
     * @param discontinued date of discontinue - '-' if equals "null"
     */
    public void setDiscontinued(String discontinued) {
        if (discontinued.equals("null")) {
            this.discontinued = "-";
        } else {
            this.discontinued = discontinued;
        }
    }
}
