package com.excilys.cdb.dto;

import java.io.Serializable;

public class ComputerDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*============*/
	/* ATTRIBUTES */
	/*============*/
	private String id;
	private String name;
	private CompanyDTO manufacturer;
	private String introduced;
	private String discontinued;
	
	/*===========*/
	/* OVERRIDES */
	/*===========*/
	@Override
	public int hashCode() {
		return name.hashCode() + manufacturer.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ComputerDTO) {
			ComputerDTO c = (ComputerDTO) obj;
			
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
		this.introduced = introduced;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
}
