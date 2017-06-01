package com.excilys.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Override
    public int hashCode() {
        int hash = 2;

        hash = hash * 7 + Long.hashCode(id);
        hash = hash * 7 + name.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Company) {
            Company company = (Company) obj;

            return (this.id == company.id) && this.name.equals(company.name);
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

    public static class Builder {
        private Company company;

        /**
         * Company builder.
         */
        public Builder() {
            company = new Company();
        }

        /**
         * Performs the construction.
         *
         * @return new company
         */
        public Company build() {
            return company;
        }

        /**
         * Adds a name to the company.
         *
         * @param name name of the company
         * @return builder current instance
         */
        public Builder name(String name) {
            company.setName(name);

            return this;
        }

        /**
         * Adds an identifier to the company.
         *
         * @param id identifier
         * @return builder current instance
         */
        public Builder id(long id) {
            company.setId(id);

            return this;
        }
    }
}
