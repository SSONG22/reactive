package com.songi.reacitveprogramming.toby_study.reactive01;


import java.util.Iterator;

public class Ob {
    // FRP : Functional Reactive Programming
    public static void main(String[] args) {
        // iterable 만들기
        Iterable<Integer> iter = () ->
                new Iterator<>() {
                    int i = 0;
                    final static int MAX = 10;

                    public boolean hasNext() {
                        return i < MAX;
                    }

                    public Integer next() {
                        return ++i;
                    }
                };
        for (Integer i : iter) {
            System.out.println(i);
        }
        for(Iterator<Integer> it = iter.iterator(); it.hasNext();){
            System.out.println(it.next());
        }

    }

}
