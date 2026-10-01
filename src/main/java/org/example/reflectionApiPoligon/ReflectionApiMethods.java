package org.example.reflectionApiPoligon;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionApiMethods {

    public static int getFieldModifier(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        int modifier = clazz.getDeclaredField(fieldName).getModifiers();
        System.out.println("intModifier=" + modifier);
        return modifier;
    }

    public static Field[] getFields(Class<?> clazz) throws NoSuchFieldException {
        Field[] fields = clazz.getDeclaredFields();
        System.out.println("fields=" + fields[0].getName() + "," + fields[1].getName() + "," + fields[2].getName());
        return fields;
    }

    public static Object getFieldValue(Class<?> clazz, Object newClass, Field field) throws NoSuchFieldException, IllegalAccessException {
        Object fieldValue = field.get(newClass);
        System.out.println("fieldValue=" + fieldValue);
        return fieldValue;
    }

    public static boolean isFieldPrivate(int modifierIntValue) {
        boolean isPrivate = Modifier.isPrivate(modifierIntValue);
        System.out.println(isPrivate);
        return isPrivate;
    }

    public static Field getFieldObject(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        return clazz.getDeclaredField(fieldName);
    }

    public static Object invokeMethod(Class<?> clazz, String methodName, Object newClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method=clazz.getDeclaredMethod(methodName);
        method.setAccessible(true);
        Object result = method.invoke(newClass);
        return result;
    }
}
