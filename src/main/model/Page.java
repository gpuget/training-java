package model;

import java.util.List;
import java.util.ArrayList;

public class Page {
	public static final int MAX_PER_PAGE = 20;
	
	private List<Computer> computers;
	private int number;

	public Page() {
		this.computers = new ArrayList<>();
		this.number = 1;
	}
	
	@Override
	public String toString() {
		String res = "Page "+number+" :\n";
		
		for(Computer cpu : computers) {
			res = res + cpu +'\n';
		}
		
		return res;
	}
	
	public void next() {
		this.number++;
	}
	
	public void previous() {
		if(this.number>1) this.number--;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
