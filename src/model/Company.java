package model;

import java.io.Serializable;

public class Company implements Serializable{	
	private static final transient long serialVersionUID = 1L;
	private String name;
	
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
		return "Company "+this.name;
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
}
