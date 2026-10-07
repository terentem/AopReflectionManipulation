package org.example.aopPoligon;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.bankService.model.Account;
import org.example.bankService.model.Bank;
import org.example.reflectionApiPoligon.ReflectionApiMethods;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Aspect
public class BankAspect {
    public BankAspect() {
        System.out.println("BankAspect constructor");
    }

    @Before("execution(* org.example.bankService.model.Bank.createAccount(..))")
    public void validateCreateAccount(JoinPoint joinPoint) {

        Long taxId = (Long) joinPoint.getArgs()[0];

        if (taxId == null) {
            throw new IllegalArgumentException("taxId cannot be null");
        }

        if (taxId <= 0) {
            throw new IllegalArgumentException("taxId must be positive");
        }
    }

    @Around("execution(* org.example.bankService.model.Bank.createAccount(..))")
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
            Account newAccount = new Account(id,taxId, true,1000.00);
            Field fieldAccountsFromBank=ReflectionApiMethods.getFieldObject(Bank.class, "accounts");
            fieldAccountsFromBank.setAccessible(true);
            List<Account> listAccounts=((List<Account>) ReflectionApiMethods.getFieldValue(Bank.class,newBank,fieldAccountsFromBank));
            System.out.println("In bank list of accounts "+listAccounts.size()+" accounts before spooky creation.");
            listAccounts.add(newAccount);
            System.out.println("created spooky account = "+listAccounts.getLast());
            List<Account> accountListFromMethod=(List<Account>)ReflectionApiMethods.invokeMethod(Bank.class,"getAccounts", newBank);
            System.out.println("Number of accounts after spooky creation within @Around ="+accountListFromMethod.size());
            //return newAccount;
        }

        return joinPoint.proceed();
    }
}
