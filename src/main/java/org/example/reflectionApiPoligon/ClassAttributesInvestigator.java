package org.example.reflectionApiPoligon;

import org.example.bankService.Account;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClassAttributesInvestigator {
    public static void investigate() throws NoSuchFieldException, IllegalAccessException {
        Account account = new Account(12345L, 98765L, 100.00);
        Field[] accountFields = ReflectionApiMethods.getFields(Account.class);
        int intModifierValue = ReflectionApiMethods.getFieldModifier(Account.class, accountFields[2].getName());
        boolean isPrivate = ReflectionApiMethods.isFieldPrivate(intModifierValue);
        Field field = ReflectionApiMethods.getFieldObject(Account.class, accountFields[2].getName());
        System.out.println("can access account before setAccessible= " + field.canAccess(account));
        field.setAccessible(true);
        System.out.println("can access account after setAccessible = " + field.canAccess(account));
        ReflectionApiMethods.getFieldValue(Account.class, account, field);
    }
}

