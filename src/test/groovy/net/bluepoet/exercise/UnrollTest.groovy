package net.bluepoet.exercise

import spock.lang.Specification
import spock.lang.Unroll


/**
 * Created by bluepoet on 2016. 12. 11..
 */
class UnrollTest extends Specification {
    @Unroll
    def "maximum of #a and #b is #c"() {
        expect:
        Math.max(a, b) == c

        where:
        a | b || c
        1 | 3 || 3
        7 | 0 || 4
    }

    @Unroll
    def "#lastName"() {
        given:
        def person = new Person(age: 14, name: 'blue poet')

        expect:
        person.getLastName() == lastName

        where:
        lastName = 'blue'
    }
}