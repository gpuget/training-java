package model;

public class Company{
	private long id = 0l;
	private String name = null;
	
	public Company() {
		super();
	}
	
	public Company(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
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
}
