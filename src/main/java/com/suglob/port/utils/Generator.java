package com.suglob.port.utils;


import java.util.Random;

public class Generator {
    private static Random random=new Random();

    private static long count=1;


    public static long generateNextLong(){
        return count++;
    }

    public static int generateRandomInt(int i){
        return random.nextInt(i);
    }
}
