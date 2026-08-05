package Problems.Core;

import java.util.HashMap;
import java.util.Map;

public class Practise {

    static class Student {
        int age;

        Student(int age) {
            this.age = age;
        }

        @Override
        public int hashCode() {
            return age;
        }

        @Override
        public boolean equals(Object o) {
            return age == ((Student)o).age;
        }
    }
    public static void main(String[] args) {

        // What is the output of this code?
        int i = 5;
        System.out.println("Question 1: " + i++ + ++i);

        // Q2. Explain why this code doesn't work?
        byte b = 127;
        b++;
        System.out.println("Question 2: " + b);
        // Reason: byte is a 8-bit signed integer, so the maximum value is 127.
        // When we increment 127, it overflows to -128.

        // Q3.
        char c = 'A';
        c++;
        System.out.println(c); // B

        System.out.println(10 + 20 + "Java");
        System.out.println("Java" + 10 + 20);

        int x = 5;

        System.out.println(x = x++);




        Student s = new Student(20);

        Map<Student, String> map = new HashMap<>();

        map.put(s, "Java");

        s.age = 30;

        System.out.println(map.get(s));
    }
}
