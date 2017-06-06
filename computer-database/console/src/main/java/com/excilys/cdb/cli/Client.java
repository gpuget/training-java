package com.excilys.cdb.cli;

import java.util.List;
import java.util.Scanner;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.cli.impl.CompanyConsole;
import com.excilys.cdb.cli.impl.ComputerConsole;
import com.excilys.cdb.config.ConsoleConfig;
import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.model.dto.ComputerDTO;

public class Client {

    private static ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(
            ConsoleConfig.class);

   private static ComputerConsole computerConsole = context.getBean(ComputerConsole.class);
    
   private static CompanyConsole companyConsole = context.getBean(CompanyConsole.class);
    
    private static void menu(){
        System.out.println("List Computers : write 1");
        System.out.println("List companies : write 2");
        System.out.println("Show computer details : write 3");
        System.out.println("Show company details : write 4");
        System.out.println("Add a computer : write 5");
        System.out.println("Add a company : write 6");
        System.out.println("Update a computer : write 7");
        System.out.println("Delete a computer : write 8");
        System.out.println("Delete a company : write 9");
        System.out.println("To leave : write 9");
        }
    private static ComputerDTO createComputerDto(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Computer name : (not null) ");
        String name = scanner.next();
        System.out.print("shoose Company id : ");
        companyConsole.display();
        long companyId = scanner.nextLong();
        System.out.print("Introduced (yyyy-MM-dd): ");
        String introduced = scanner.next();
        System.out.print("Discontinued (yyyy-MM-dd): ");
        String discontinued = scanner.next();

        ComputerDTO computerDto = new ComputerDTO();
        computerDto.setName(name);
        computerDto.setCompanyId(companyId);
        computerDto.setDiscontinued(discontinued);
        computerDto.setIntroduced(introduced);
        return computerDto;
    }
    private static CompanyDTO createCompanyDto(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Company name : (not null) ");
        String name = scanner.next();
        
        CompanyDTO companyDto = new CompanyDTO();
        companyDto.setName(name);
        
        return companyDto;
    }
    
    
    public static void main(String[] args) {
        
        int menu = 0;
        boolean arret = false;
       
        menu();
        Scanner sc = new Scanner(System.in);
        Scanner inputscanner = new Scanner(System.in);
        String input = inputscanner.next();
        menu = Integer.valueOf(input);
        
        while (!arret) {
            switch (menu) {
            case 1:
                System.out.println("List Computers");
                List<ComputerDTO> computers = computerConsole.display();
                for (ComputerDTO computer : computers) {
                    System.out.println(computer);
                }
                input = inputscanner.next();
                menu = Integer.valueOf(input);
                break;
                
            case 2:
                System.out.println("List Company");
                List<CompanyDTO> companys = companyConsole.display();
                for (CompanyDTO company : companys) {
                    System.out.println(company);
                }
                input = inputscanner.next();
                menu = Integer.valueOf(input);
                break;

            case 3:
                System.out.println("Show computer details");
                System.out.println("Computer id");
                String idFind =  inputscanner.next();
                try{
                    Long.parseLong(idFind);
                    ComputerDTO computerDTO = computerConsole.findById(idFind);
                    if(computerDTO.getId()!=0){
                        System.out.println(computerDTO);
                    }
                    else{
                        System.out.println("computer don't exist");
                    }
                }catch( NumberFormatException e){
                    System.out.println("Id Not Correct.");
                    menu();
                }
                input = inputscanner.next();
                menu = Integer.valueOf(input);
                break;
            
            case 4:
                System.out.println("Show company details");
                System.out.println("Company id");
                String idFindCompany =  inputscanner.next();
                try{
                    Long.parseLong(idFindCompany);
                    CompanyDTO companyDTO = companyConsole.findById(idFindCompany);
                    if(companyDTO.getId()!=0){
                        System.out.println(companyDTO);
                    }
                    else{
                        System.out.println("computer don't exist");
                    }
                }catch( NumberFormatException e){
                    System.out.println("Id Not Correct.");
                    menu();
                }
                input = inputscanner.next();
                menu = Integer.valueOf(input);
                break;
            case 5:
                System.out.println("---Add Computer---");
                ComputerDTO computerDTO = createComputerDto();
                computerConsole.add(computerDTO);
                input = inputscanner.next();
                menu = Integer.valueOf(input);
                break;
                
            case 6:
                System.out.println("---Add company---");
                CompanyDTO companyDTO = createCompanyDto();
                companyConsole.add(companyDTO);
                input = inputscanner.next();
                menu = Integer.valueOf(input);
                break;
                
            case 7:
                System.out.println("---Update a computer---");
                //TO DO
                input = inputscanner.next();
                menu = Integer.valueOf(input);
                break;
                
            case 8:
                System.out.println("---Delete a computer---");
                System.out.print("Computer id :");
                String idDelete = inputscanner.next();
                try{
                    Long.parseLong(idDelete);
                    computerConsole.delete(idDelete);
                    System.out.println("computer has been deleted");
                }catch( NumberFormatException e){
                    System.out.println("Id Not Correct.");
                    System.out.println("computer has not been deleted");
                    System.out.println();
                    menu();
                }
                input = inputscanner.next();
                menu = Integer.valueOf(input);
                break;
                
            
            case 9:
                System.out.println("---Delete a company---");
                System.out.print("Company id :");
                String idDeleteCompany = inputscanner.next();
                try{
                    Long.parseLong(idDeleteCompany);
                    companyConsole.delete(idDeleteCompany);
                    System.out.println("the company has been deleted");
                }catch( NumberFormatException e){
                    System.out.println("Id Not Correct.");
                    System.out.println("the company has not been deleted");
                    System.out.println();
                    menu();
                }
                input = inputscanner.next();
                menu = Integer.valueOf(input);
                break;
                
            case 10:
                arret = true;
                break;
                
            default:
                System.out.println("Make a choice between 1 to 7");
                break;
            }

        }

    }
}
