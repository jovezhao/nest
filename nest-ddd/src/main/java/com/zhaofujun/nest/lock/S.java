package com.zhaofujun.nest.lock;

public class S {
    int value;

    public  S(int value) {
        this.value = value;
    }

    public synchronized void p() {
        value--;
        if (value < 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void v() {
        value++;
        if (value <= 0)
            this.notify();
    }
}
