package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * @author: yansu
 * @date: 2020/9/11
 */
public class FunctionalServiceTester {
    private final <T> int testTaskQPS(Runnable task) {
        int count = 0;
        long start = System.currentTimeMillis();
        long end = 1000;
        while (System.currentTimeMillis() - start <= end) {
            try {
                task.run();
            } catch (Exception e) {

            }
            count++;
        }
        return count;
    }

    //Function::apply, compose, andThen
    private void functionTest() {
        Function<Integer, Integer> add = i -> i + 1;
        Integer res = add.apply(5);
        Function<Integer, Integer> multiply = i -> i * i;
        System.out.println(res);
        //thus we can pass different actions to a method, adding flexibility to the mehtod
        Integer res2 = add.apply(multiply.apply(2));
        System.out.println(res2);
        //if two methods needs four or more separate actions, but each needs them in different order
        //we can use compose and andThen
        Integer res3 = add.andThen(multiply).apply(2);
        Integer res4 = add.compose(multiply).apply(2);
        System.out.println("res4 == res 2 ? " + res2.equals(res4));
        System.out.println(res4);
    }

    //@FunctionalInterface
    private void functionalInterfaceTest() {
        Hello hello = param -> param + " world";
        System.out.println(hello.msg("hello"));
    }

    public static void main(String[] args) {

    }
}
@FunctionalInterface
interface Hello {
    //only one abstract method allowed
    String msg(String info);
    //can have public methods of Object class
    @Override
    boolean equals(Object object);
    //can have more than one static methods
    static void call(String message) {
        System.out.println(message);
    }
}
class ComparatorTest {
    private int a;
    private int b;

    public ComparatorTest(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public ComparatorTest setA(int a) {
        this.a = a;
        return this;
    }

    public int getB() {
        return b;
    }

    public ComparatorTest setB(int b) {
        this.b = b;
        return this;
    }

    @Override
    public String toString() {
        return "\n" + a + " : " + b;
    }

    public static void main(String[] args) {
        List<ComparatorTest> list = new ArrayList() {{
            add(new ComparatorTest(1, 1));
            add(new ComparatorTest(1, 2));
            add(new ComparatorTest(2, 3));
            add(null);
            add(new ComparatorTest(2, 1));
            add(new ComparatorTest(3, 4));
            add(new ComparatorTest(3, 1));
        }};

        // 按b属性倒序，再按a属性倒序排列，null放最前面
        // 相当于SQL: sort by b desc, a desc
        list.sort(Comparator.nullsFirst(Comparator
            .comparing(ComparatorTest::getB)
            .reversed()
            .thenComparing(Comparator
                .comparing(ComparatorTest::getA)
                .reversed())));
        System.out.println(list);
    }
}