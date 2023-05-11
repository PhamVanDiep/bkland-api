package com.api.bkland.util;

import java.util.Random;

public class Util {
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public static int calculatePostPrice(Integer priority, Integer period, boolean sell) {
        float heSo = 0.0f;
        int price = 0;
        if (sell) {
            if (priority == 1) {
                price = 50000;
            } else if (priority == 2) {
                price = 100000;
            } else if (priority == 3) {
                price = 150000;
            }
        } else {
            if (priority == 1) {
                price = 20000;
            } else if (priority == 2) {
                price = 40000;
            } else if (priority == 3) {
                price = 60000;
            }
        }
        if (period == 15) {
            heSo = 1.5f;
        } else if (period == 30) {
            heSo = 3;
        } else if (period == 60) {
            heSo = 6;
        }
        return Math.round(heSo * price);
    }
}
