package com.excilys.cdb.models;

public class Company {
    private Long id;
    private String name;

    /**
     * Constructor with name.
     * @param name name of the company
     */
    private Company(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Company) {
            Company c = (Company) obj;

            return this.name.equals(c.name);
        }

        return false;
    }

    @Override
    public String toString() {
        return "Company :" + this.name;
    }

    /**
     * Gets the identifier.
     * @return identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the identifier.
     * @param id identifier
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the company.
     * @return name of the company
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     * @param name name of the company
     */
    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {
        private Company company;

        /**
         * Company builder.
         * @param name name of the company created
         */
        public Builder(String name) {
            this.company = new Company(name);
        }

        /**
         * Performs the construction.
         * @return new company
         */
        public Company build() {
            return company;
        }

        /**
         * Adds an identifier to the company.
         * @param id identifier
         * @return builder current instance
         */
        public Builder id(Long id) {
            company.setId(id);

            return this;
        }
    }
}
