package org.example.aopPoligon;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.bankService.Account;
import org.example.bankService.Bank;
import org.example.reflectionApiPoligon.ReflectionApiMethods;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Aspect
public class BankAspect {
    public BankAspect() {
        System.out.println("BankAspect constructor");
    }

    @Around("execution(* org.example.bankService.Bank.createAccount(..))")
    public Object aroundCreate(ProceedingJoinPoint joinPoint)
            throws Throwable {
        System.out.println("Aspect works");
        Object[] args = joinPoint.getArgs();

        Long taxId = (Long) args[0];

        System.out.println("Aspect: taxId = " + taxId);
        if (taxId == 1111L) {
            Field fieldFromBank = ReflectionApiMethods.getFieldObject(Bank.class, "idCounter");
            fieldFromBank.setAccessible(true);
            Bank newBank = new Bank();
            Object counter = ReflectionApiMethods.getFieldValue(Bank.class, newBank, fieldFromBank);
            System.out.println("counterFromBank=" + counter);
            AtomicLong atomicCounter = (AtomicLong) fieldFromBank.get(null);
            System.out.println("atomicCounter=" + atomicCounter);
            Long id=atomicCounter.getAndIncrement();
            Account newAccount = new Account(id,taxId, 1000.00);
            Field fieldAccountsFromBank=ReflectionApiMethods.getFieldObject(Bank.class, "accounts");
            fieldAccountsFromBank.setAccessible(true);
            List<Account> listAccounts=((List<Account>) ReflectionApiMethods.getFieldValue(Bank.class,newBank,fieldAccountsFromBank));
            listAccounts.add(newAccount);
            System.out.println("created spooky account = "+listAccounts.getLast());
            return newAccount;
        }

        return joinPoint.proceed();
    }
}
