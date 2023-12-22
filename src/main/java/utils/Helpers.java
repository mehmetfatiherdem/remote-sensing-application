package main.java.utils;

public class Helpers {
    public static StringBuilder ByteToStr(byte[] a) {
        if (a == null) return null;

        StringBuilder ret = new StringBuilder();

        int i = 0;
        while(a[i] != 0){
            ret.append((char)a[i++]);
        }

        return ret;
    }
}
