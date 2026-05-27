package com.ticket.helper;

public class CommonHelper {
    public static void addDelay() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
