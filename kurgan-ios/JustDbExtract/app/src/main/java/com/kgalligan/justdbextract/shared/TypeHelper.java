package com.kgalligan.justdbextract.shared;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TypeHelper
{
    public static String[] stringArray(int size){
        return new String[size];
    }

    public static void replaceStringAtIndex(String[] arr, String s, int index){
        arr[index] = s;
    }

    public static void replaceObjectAtIndex(Object[] arr, Object o, int index){
        arr[index] = o;
    }

    public static Object[] createObjectArray(int length){
        return new Object[length];
    }

    public static String stringAtIndex(String[] arr, int index){
        return arr[index];
    }

    public static Object[] listToArray(List<Object> asdf){
        return asdf.toArray();

    }

    public static byte[] justTestingToUtf8(String s)
    {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    public static String justTestingFromUtf8(byte[] b)
    {
        return new String(b, StandardCharsets.UTF_8);
    }
}
