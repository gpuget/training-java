package model;

import java.util.List;
import java.util.ArrayList;

public class Page {
	private List<Computer> computers;
	private int number;

	public Page() {
		this.computers = new ArrayList<>();
		this.number = 0;
	}
	
	public Page(List<Computer> computers, int number) {
		this.computers = computers;
		this.number = number;
	}
	
	@Override
	public String toString() {
		String res = "Page "+number+" :\n";
		
		for(Computer cpu : computers) {
			res = res + cpu +'\n';
		}
		
		return res;
	}
	
	public void nextPage() {
		this.number++;
	}
	
	public void previousPage() {
		if(this.number>0) this.number--;
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
