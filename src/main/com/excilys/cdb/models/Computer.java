package com.excilys.cdb.models;

import java.time.LocalDate;

public class Computer{
	/*============*/
	/* ATTRIBUTES */
	/*============*/
	private Long id;
	private String name;
	private Company manufacturer;
	private LocalDate introduced;
	private LocalDate discontinued;

	/*==============*/
	/* CONSTRUCTORS */
	/*==============*/
	private Computer(String name) {
		this.name = name;
	}

	/*===========*/
	/* OVERRIDES */
	/*===========*/
	@Override
	public int hashCode() {
		return name.hashCode() + manufacturer.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Computer) {
			Computer c = (Computer) obj;
			
			return (this.manufacturer.equals(c.manufacturer) && this.name.equals(c.name));
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		String res;
		
		res = "Computer : "+this.name+" ("+this.manufacturer.getName()+") "+this.introduced;
		if(this.discontinued!=null) res = res +" to "+this.discontinued;
		
		return res;
	}

	/*===================*/
	/* GETTERS / SETTERS */
	/*===================*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	
	/*=========*/
	/* BUILDER */
	/*=========*/
	public static class Builder {
		private Computer computer;
		
		public Builder(String name) {
			this.computer = new Computer(name);
		}
		
		public Computer build() {
			return computer;
		}
		
		public Builder id(Long id) {
			computer.setId(id);
			
			return this;
		}
		
		public Builder manufacturer(Company company) {
			computer.setManufacturer(company);
			
			return this;
		}
		
		public Builder introduced(LocalDate date) {
			computer.setIntroduced(date);
			
			return this;
		}
		
		public Builder discontinued(LocalDate date) {
			computer.setDiscontinued(date);
			
			return this;
		}
	}
}
