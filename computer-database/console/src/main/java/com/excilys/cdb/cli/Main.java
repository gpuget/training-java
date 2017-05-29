package com.excilys.cdb.cli;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.DataConfig;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.auth.User;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.persistence.UserDAO;

public class Main {
    ApplicationContext context = new AnnotationConfigApplicationContext(DataConfig.class);
    CompanyDAO companyDao = context.getBean(CompanyDAO.class);
    ComputerDAO computerDao = context.getBean(ComputerDAO.class);
    UserDAO userDao = context.getBean(UserDAO.class);

    public static void menu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1 : Computers list");
        System.out.println("2 : Companies list");
        System.out.println("3 : Users list");
        System.out.println("\n0 : Exit");
    }
    
    private void displayComputers() {
        for (Computer cpu : computerDao.findAll()) {
            System.out.println(cpu);
        }
    }
    
    private void displayCompanies() {
        for (Computer cpu : computerDao.findAll()) {
            System.out.println(cpu);
        }
    }
    
    private void displayUsers() {
        for (User usr : userDao.findAll()) {
            System.out.println(usr);
        }
    }

    public static void main(String[] args) {
        int i;
        Scanner sc = new Scanner(System.in);
        Main main = new Main();
        
        do {
            menu();
            i = sc.nextInt();
            
            switch (i) {
                case 0 : {
                    sc.close();
                    System.exit(0);
                }
                break;
                
                case 1 : {
                    main.displayComputers();
                }
                break;
                
                case 2 : {
                    main.displayCompanies();
                }
                break;
                
                case 3 : {
                    main.displayUsers();
                }
                break;
            }
        } while (true);
    }
}
