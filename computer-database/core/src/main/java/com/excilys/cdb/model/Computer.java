package com.excilys.cdb.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "computer")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "company_id")
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

            return (this.id == computer.id) && this.name.equals(computer.name)
                    && this.manufacturer.equals(computer.manufacturer)
                    && this.introduced.equals(computer.introduced)
                    && this.discontinued.equals(computer.discontinued);
        }

        return false;
    }

    @Override
    public String toString() {
        String res;

        res = "Computer (" + this.id + ") " + this.name;
        if (this.manufacturer != null) {
            res = res + " [" + this.manufacturer.getName() + "]";
        }

        if (this.introduced != null) {
            res = res + " from " + this.introduced;
        }

        if (this.discontinued != null) {
            res = res + " to " + this.discontinued;
        }

        return res;
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

    public Company getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Company manufacter) {
        this.manufacturer = manufacter;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public static class Builder {
        private Computer computer;

        /**
         * Computer builder.
         */
        public Builder() {
            computer = new Computer();
        }

        /**
         * Performs the construction.
         *
         * @return new computer
         */
        public Computer build() {
            return computer;
        }

        /**
         * Adds an identifier to the computer.
         *
         * @param id identifier
         * @return builder current instance
         */
        public Builder id(long id) {
            computer.setId(id);

            return this;
        }

        /**
         * Adds a name to the computer.
         *
         * @param name name of the computer
         * @return builder current instance
         */
        public Builder name(String name) {
            computer.setName(name);

            return this;
        }

        /**
         * Adds a manufacturer to the computer.
         *
         * @param company manufacturer
         * @return builder current instance
         */
        public Builder manufacturer(Company company) {
            computer.setManufacturer(company);

            return this;
        }

        /**
         * Adds a date of the first apparition to the computer.
         *
         * @param date date of the first apparition
         * @return builder current instance
         */
        public Builder introduced(LocalDate date) {
            computer.setIntroduced(date);

            return this;
        }

        /**
         * Adds a date of the last apparition to the computer.
         *
         * @param date date of the last apparition
         * @return builder current instance
         */
        public Builder discontinued(LocalDate date) {
            computer.setDiscontinued(date);

            return this;
        }
    }
}
