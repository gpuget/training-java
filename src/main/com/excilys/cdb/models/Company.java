package com.excilys.cdb.models;

public class Company{
	/*============*/
	/* ATTRIBUTES */
	/*============*/
	private Long id;
	private String name;
	
	/*==============*/
	/* CONSTRUCTORS */
	/*==============*/
	
	private Company(String name) {
		this.name = name;
	}
	
	/*===========*/
	/* OVERRIDES */
	/*===========*/
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Company) {
			Company c = (Company) obj;
			
			return this.name.equals(c.name);
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "Company :"+this.name;
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
	
	/*=========*/
	/* BUILDER */
	/*=========*/
	public static class Builder {
		private Company company;
		
		public Builder(String name) {
			this.company = new Company(name);
		}
		
		public Company build() {
			return company;
		}
		
		public Builder id(Long id) {
			company.setId(id);
			
			return this;
		}
	}
}
