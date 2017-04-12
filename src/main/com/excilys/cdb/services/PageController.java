package com.excilys.cdb.services;

import com.excilys.cdb.models.Page;
import com.excilys.cdb.persistences.CompanyDAO;
import com.excilys.cdb.persistences.ComputerDAO;

public class PageController {
	private Page page = null;
	private ComputerDAO computerDao = null;
	private CompanyDAO companyDao = null;
	private static PageController instance = null;
	
	private PageController() {
		this.computerDao = ComputerDAO.getInstance();
		this.companyDao = CompanyDAO.getInstance();
	}
	
	public static PageController getInstance() {
		if(instance == null) instance = new PageController();
		return instance;
	}
	
	public void createPage() {
		this.page = new Page();
		this.page.setCompanies(companyDao.findAll());
		this.update();
	}
	
	public int nextPage() {
		this.page.next();
		this.update();
		
		return this.page.getNumber();
	}
	
	public int previousPage() {
		this.page.previous();
		this.update();
		
		return this.page.getNumber();
	}
	
	public void update() {
		this.page.setComputers(computerDao.findAll(Page.MAX_PER_PAGE, (this.page.getNumber()-1)*Page.MAX_PER_PAGE));
	}

	public Page getPage() {
		return page;
	}
}
