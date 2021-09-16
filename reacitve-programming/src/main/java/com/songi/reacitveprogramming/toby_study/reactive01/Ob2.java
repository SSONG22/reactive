package com.songi.reacitveprogramming.toby_study.reactive01;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ob2 {
    static class IntObservable extends Observable implements Runnable{

        @Override
        public void run() {
            for(int i=0; i<=10; i++) {
                setChanged();
                notifyObservers(i);    // push
                // int i = it.next();  // pull
            }
        }
    }

    // DATA method(void) <-> void method(DATA)
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        Observer ob = (o, arg) -> System.out.println(Thread.currentThread().getName()+" "+arg);

        IntObservable io = new IntObservable();
        io.addObserver(ob);

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(io);

        System.out.println(Thread.currentThread().getName()+" EXIT");
        es.shutdown();
    }
}
