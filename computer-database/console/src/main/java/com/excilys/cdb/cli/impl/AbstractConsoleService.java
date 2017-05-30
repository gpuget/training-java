package com.excilys.cdb.cli.impl;

import java.util.Scanner;

import com.excilys.cdb.cli.ConsoleService;

public abstract class AbstractConsoleService implements ConsoleService {
    Scanner scanner = new Scanner(System.in);

    @Override
    protected void finalize() throws Throwable {
        scanner.close();
    }
}
