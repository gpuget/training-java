package com.excilys.cdb.cli.impl;

import java.util.Scanner;

import com.excilys.cdb.cli.Console;

public abstract class AbstractConsole implements Console {
    Scanner scanner = new Scanner(System.in);

    @Override
    protected void finalize() throws Throwable {
        scanner.close();
    }
}
