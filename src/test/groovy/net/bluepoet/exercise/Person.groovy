package net.bluepoet.exercise

/**
 * Created by bluepoet on 2016. 12. 11..
 */
class Person {
    int age
    String name

    String getLastName() {
        name.split(' ')[1]
    }
}
