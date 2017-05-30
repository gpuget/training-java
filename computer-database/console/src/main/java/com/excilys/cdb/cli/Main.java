package com.excilys.cdb.cli;

import java.util.Scanner;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.cli.impl.ConsoleCompanyService;
import com.excilys.cdb.cli.impl.ConsoleComputerService;
import com.excilys.cdb.cli.impl.ConsoleUserService;
import com.excilys.cdb.config.ConsoleConfig;

public class Main {
    /**
     * Display menu.
     */
    public static void menu() {
        System.out.print("\n--- MENU ---"
                + "\n1 : Companies list\n2 : Add company\n3 : Delete company\n4 : Update company\n"
                + "\n5 : Computers list\n6 : Add computer\n7 : Delete computer\n8 : Update computer\n"
                + "\n9 : Users list\n10 : Add User\n11 : Delete User\n");
        System.out.println("\n0 : Quit");
    }
    /**
     * Main.
     *
     * @param args args
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(
                ConsoleConfig.class);

        ConsoleCompanyService companyService = context.getBean(ConsoleCompanyService.class);
        ConsoleComputerService computerService = context.getBean(ConsoleComputerService.class);
        ConsoleUserService userService = context.getBean(ConsoleUserService.class);

        int i;
        Scanner sc = new Scanner(System.in);

        do {
            menu();
            try {
                i = sc.nextInt();
            } catch (Exception e) {
                i = 0;
            }

            switch (i) {
                default :
                case 0 :
                    System.out.println("Application closed...");
                    sc.close();
                    context.close();
                    System.exit(0);
                break;

                case 1 : companyService.display();
                break;

                case 2 : companyService.add();
                break;

                case 3 : companyService.delete();
                break;

                case 4 : companyService.update();
                break;

                case 5 : computerService.display();
                break;

                case 6 : computerService.add();
                break;

                case 7 : computerService.delete();
                break;

                case 8 : computerService.update();
                break;

                case 9 : userService.display();
                break;

                case 10 : userService.add();
                break;

                case 11 : userService.delete();
                break;
            }
        } while (true);
    }
}
