package com.lyn.cli.example;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

public class Login implements Callable<Integer> {
    @Option(names = {"-u", "--user"}, description = "User name")
    String user;

    @Option(names = {"-p", "--password"},arity = "0..1", description = "Passphrase", interactive = true)
    String password;

    @Option(names = {"-w", "--word"}, arity = "0..1", description = "Word",interactive = true)
    String word;

    public Integer call() throws Exception {
        System.out.println("password = " + password);
        System.out.println("word = " + word);
        return 0;
    }

    public static void main(String[] args) {
        new CommandLine(new Login()).execute("-u", "user123", "-p","-w");
    }
}
