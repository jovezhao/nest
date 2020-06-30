package com.zhaofujun.nest.test;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> "hello")
                .thenApply(i -> i + "world")
                .thenApply(String::toUpperCase)
                .thenCombine(CompletableFuture.completedFuture("Java"),
                        (x, y) -> x +"-"+ y)
                .thenAccept(System.out::println);

    }
}
