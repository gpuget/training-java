package com.excilys.cdb.models;

import java.time.LocalDate;

public class Computer {
    private long id;
    private String name;
    private Company manufacturer;
    private LocalDate introduced;
    private LocalDate discontinued;

    @Override
    public int hashCode() {
        int hash = 5;
        
        hash = hash * 8 + Long.hashCode(id);
        hash = hash * 8 + name.hashCode();
        hash = hash * 8 + manufacturer.hashCode();
        hash = hash * 8 + introduced.hashCode();
        hash = hash * 8 + discontinued.hashCode();
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Computer) {
            Computer computer = (Computer) obj;

            return (this.id == computer.id) &&
                    this.name.equals(computer.name) &&
                    this.manufacturer.equals(computer.manufacturer) &&
                    this.introduced.equals(computer.introduced) &&
                    this.discontinued.equals(computer.discontinued);
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

    /**
     * Gets the identifier.
     * @return identifier
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the identifier.
     * @param id identifier
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name of the computer.
     * @return name of the computer
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the computer.
     * @param name name of the computer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the manufacturing company.
     * @return manufacturing company
     */
    public Company getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the manufacturing company.
     * @param manufacter manufacturing company
     */
    public void setManufacturer(Company manufacter) {
        this.manufacturer = manufacter;
    }

    /**
     * Gets the date of the first apparition.
     * @return date of the first apparition
     */
    public LocalDate getIntroduced() {
        return introduced;
    }

    /**
     * Sets the date of the first apparition.
     * @param introduced date of the first apparition
     */
    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    /**
     * Gets the date of the last apparition.
     * @return date of the last apparition
     */
    public LocalDate getDiscontinued() {
        return discontinued;
    }

    /**
     * Sets the date of the last apparition.
     * @param discontinued date of the last apparition
     */
    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public static class Builder {
        private Computer computer;

        /**
         * Computer builder.
         * @param name name of the computer
         */
        public Builder() {
            computer = new Computer();
        }

        /**
         * Performs the construction.
         * @return new computer
         */
        public Computer build() {
            return computer;
        }

        /**
         * Adds an identifier to the computer.
         * @param id identifier
         * @return builder current instance
         */
        public Builder id(long id) {
            computer.setId(id);

            return this;
        }
        
        /**
         * Adds a name to the computer.
         * @param name name of the computer
         * @return builder current instance
         */
        public Builder name(String name){
            computer.setName(name);
            
            return this;
        }

        /**
         * Adds a manufacturer to the computer.
         * @param company manufacturer
         * @return builder current instance
         */
        public Builder manufacturer(Company company) {
            computer.setManufacturer(company);

            return this;
        }

        /**
         * Adds a date of the first apparition to the computer.
         * @param date date of the first apparition
         * @return builder current instance
         */
        public Builder introduced(LocalDate date) {
            computer.setIntroduced(date);

            return this;
        }

        /**
         * Adds a date of the last apparition to the computer.
         * @param date date of the last apparition
         * @return builder current instance
         */
        public Builder discontinued(LocalDate date) {
            computer.setDiscontinued(date);

            return this;
        }
    }
}
