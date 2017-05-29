package com.excilys.cdb.cli;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.DataConfig;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.auth.User;
import com.excilys.cdb.model.auth.UserRole;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.persistence.UserDAO;

public class Main {
    ApplicationContext context = new AnnotationConfigApplicationContext(DataConfig.class);
    CompanyDAO companyDao = context.getBean(CompanyDAO.class);
    ComputerDAO computerDao = context.getBean(ComputerDAO.class);
    UserDAO userDao = context.getBean(UserDAO.class);

    Scanner scanner = new Scanner(System.in);

    @Override
    protected void finalize() throws Throwable {
        scanner.close();
    }

    public static void menu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1 : Computers list");
        System.out.println("2 : Companies list");
        System.out.println("3 : Users list");
        System.out.println("4 : Add Computer");
        System.out.println("5 : Delete Computer");
        System.out.println("6 : Update Computer");
        System.out.println("7 : Add Company");
        System.out.println("8 : Delete Company");
        System.out.println("9 : Update Company");
        System.out.println("10 : Add User");
        System.out.println("11 : Delete User");
        System.out.println("\n0 : Quit");
    }

    private void displayComputers() {
        for (Computer cpu : computerDao.findAll()) {
            System.out.println(cpu);
        }
    }

    private void displayCompanies() {
        for (Company com : companyDao.findAll()) {
            System.out.println(com);
        }
    }

    private void displayUsers() {
        for (User usr : userDao.findAll()) {
            System.out.println(usr);
        }
    }

    private void addComputer() {
        System.out.print("Computer name : ");
        String name = scanner.nextLine();
        displayCompanies();
        System.out.print("Company id : ");
        long companyId = scanner.nextLong();
        System.out.print("Introduced (yyyy MM dd): ");
        LocalDate i = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        System.out.print("Discontinued (yyyy MM dd): ");
        LocalDate d = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());

        Computer computer = new Computer.Builder().name(name)
                .manufacturer(new Company.Builder().id(companyId).build()).introduced(i)
                .discontinued(d).build();

        computerDao.create(computer);
    }

    private void deleteComputer() {
        displayComputers();
        System.out.print("Computer id :");
        long id = scanner.nextLong();

        computerDao.delete(id);
    }

    private void updateComputer() {
        displayComputers();
        System.out.print("Computer id : ");
        long id = scanner.nextLong();
        System.out.print("Computer name : ");
        String name = scanner.nextLine();
        displayCompanies();
        System.out.print("Company id : ");
        long companyId = scanner.nextLong();
        System.out.print("Introduced (yyyy MM dd): ");
        LocalDate i = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        System.out.print("Discontinued (yyyy MM dd): ");
        LocalDate d = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());

        Computer computer = new Computer.Builder().id(id).name(name)
                .manufacturer(new Company.Builder().id(companyId).build()).introduced(i)
                .discontinued(d).build();

        computerDao.update(computer);
    }

    private void addCompany() {
        System.out.print("Company name : ");
        String name = scanner.nextLine();

        companyDao.create(new Company.Builder().name(name).build());
    }

    private void deleteCompany() {
        displayCompanies();
        System.out.print("Company id :");
        long id = scanner.nextLong();

        companyDao.delete(id);
    }

    private void updateCompany() {
        System.out.print("Company id : ");
        long id = scanner.nextLong();
        System.out.print("Company name : ");
        String name = scanner.nextLine();

        companyDao.create(new Company.Builder().id(id).name(name).build());
    }

    private void addRoles(User user) {
        Set<UserRole> res = new HashSet<>();

        System.out.print("Roles : ");
        String roles = scanner.nextLine();

        for (String s : roles.split(" ")) {
            UserRole ur = new UserRole(user, s);
            res.add(ur);
            userDao.create(ur);
        }
    }

    private void addUser() {
        System.out.print("Name : ");
        String name = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();
        User user = new User(name, password, true);

        userDao.create(user);
        addRoles(user);
    }

    private void deleteUser() {
        displayUsers();
        System.out.print("Name : ");
        String name = scanner.nextLine();
        userDao.delete(name);
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
                    System.out.println("Application closed...");
                    sc.close();
                    System.exit(0);
                }
                break;

                case 1 : main.displayComputers();
                break;

                case 2 : main.displayCompanies();
                break;

                case 3 : main.displayUsers();
                break;

                case 4 : main.addComputer();
                break;

                case 5 : main.deleteComputer();
                break;

                case 6 : main.updateComputer();
                break;

                case 7 : main.addCompany();
                break;

                case 8 : main.deleteCompany();
                break;

                case 9 : main.updateCompany();
                break;

                case 10 : main.addUser();
                break;

                case 11 : main.deleteUser();
                break;
            }
        } while (true);
    }
}
