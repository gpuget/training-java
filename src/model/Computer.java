package model;

import java.io.Serializable;
import java.sql.Date;

public class Computer implements Serializable{
	private long id;
	private String name;
	private Company manufacturer;
	private Date introduced;
	private Date discontinued;
	
	public Computer() {
		
	}
	
	public Computer(long id, String name, Company manufacturer, Date... duration) {
		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
		
		switch(duration.length) {		
			case 2: this.discontinued = duration[1];
			case 1: this.introduced = duration[0];
			break;
			
			default: return;
		}
	}
	
	@Override
	public int hashCode() {
		int res;
		
		res = name.hashCode() + manufacturer.hashCode();
		
		return res;
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
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the manufacturer
	 */
	public Company getManufacturer() {
		return manufacturer;
	}
	
	/**
	 * @param manufacter the manufacturer to set
	 */
	public void setManufacturer(Company manufacter) {
		this.manufacturer = manufacter;
	}
	
	/**
	 * @return the introduced
	 */
	public Date getIntroduced() {
		return introduced;
	}
	
	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}
	
	/**
	 * @return the discontinued
	 */
	public Date getDiscontinued() {
		return discontinued;
	}
	
	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}
}
