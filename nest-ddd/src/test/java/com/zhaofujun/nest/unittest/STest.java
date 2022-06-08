package com.zhaofujun.nest.unittest;

import com.zhaofujun.nest.lock.S;

import java.io.IOException;

public class STest {

    public static void main(String[] args) throws IOException {
        S s1 = new S(0);
        S s2 = new S(0);
        S s3 = new S(0);
        Thread P1 = new Thread(new P1(s1));
        Thread P2 = new Thread(new P2(s2));
        Thread P3 = new Thread(new P3(s1, s2, s3));
        Thread P4 = new Thread(new P4(s3));

        P3.start();
        P4.start();
        P1.start();
        P2.start();

        System.in.read();
    }
}

class P1 implements Runnable {

    S s1;

    public P1(S s1) {
        this.s1 = s1;
    }

    @Override
    public void run() {
        System.out.println("P1 start");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("P1 end");

        s1.v();
    }
}

class P2 implements Runnable {

    S s2;

    public P2(S s2) {
        this.s2 = s2;
    }

    @Override
    public void run() {
        System.out.println("P2 start");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("P2 end");

        s2.v();
    }
}

class P3 implements Runnable {

    S s1;
    S s2;
    S s3;

    public P3(S s1, S s2, S s3) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
    }

    @Override
    public void run() {
        s1.p();
        s2.p();
        System.out.println("P3 start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("P3 end");
        s3.v();
    }
}


class P4 implements Runnable {

    S s3;

    public P4(S s3) {
        this.s3 = s3;
    }

    @Override
    public void run() {
        s3.p();
        System.out.println("P4 start");
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("P4 end");
    }
}
