package org.example;


import org.example.aopPoligon.BankAspect;
import org.example.bankService.Bank;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        System.out.println("MAIN");
        System.out.println(
                BankAspect.class.getDeclaredMethod("aspectOf")
        );

        System.out.println(
                BankAspect.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
        );
        Bank bank = new Bank();
        bank.createAccount(1111L);
        System.out.println("There are "+bank.getAccounts().size()+" accounts in Bank.");
    }
}
