package com.kgalligan.justdbextract.shared;
import java.util.List;

public class TypeHelper
{
    public static String[] stringArray(int size){
        return new String[size];
    }

    public static void replaceStringAtIndex(String[] arr, String s, int index){
        arr[index] = s;
    }

    public static String stringAtIndex(String[] arr, int index){
        return arr[index];
    }

    public static Object[] listToArray(List<Object> asdf){
        return asdf.toArray();

    }
}
