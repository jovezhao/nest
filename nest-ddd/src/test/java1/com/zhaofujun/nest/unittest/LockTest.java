package com.zhaofujun.nest.unittest;

import com.zhaofujun.nest.lock.DefaultLockProvider;
import com.zhaofujun.nest.lock.LockProvider;
import com.zhaofujun.nest.utils.LockUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LockTest {

   public static int[] i = {0};

    public static void main(String[] args) {
        Thread th1 = new Thread(new RunClass(),"th1");
        Thread th2 = new Thread(new RunClass(),"th2");

        Thread th3 = new Thread(new RunClass(),"th3");
        Thread th4 = new Thread(new RunClass(),"th4");
        th1.start();
        th2.start();
        th3.start();
        th4.start();
    }

}
class RunClass implements Runnable {
    @Override
    public void run() {
        for (int j = 0; j < 100; j++) {
            LockUtils.runByLock("aaaa",()->{
                LockTest.i[0]++;
                System.out.println(Thread.currentThread().getName() + ": " + LockTest.i[0]);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}