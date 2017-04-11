package service;

import model.Page;
import persistence.ComputerDAO;

public class PageController {
	private Page page = null;
	private ComputerDAO computerDao = null;
	private static PageController instance = null;
	
	private PageController() {
		this.computerDao = ComputerDAO.getInstance();
	}
	
	public static PageController getInstance() {
		if(instance == null) instance = new PageController();
		return instance;
	}
	
	public void createPage() {
		this.page = new Page();
		this.update();
	}
	
	public void nextPage() {
		this.page.next();
		this.update();
	}
	
	public void previousPage() {
		this.page.previous();
		this.update();
	}
	
	public void update() {
		this.page.setComputers(computerDao.findAll(Page.MAX_PER_PAGE, (this.page.getNumber()-1)*Page.MAX_PER_PAGE));
	}

	public Page getPage() {
		return page;
	}
}
