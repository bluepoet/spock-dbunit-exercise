package net.bluepoet.exercise

import be.janbols.spock.extension.dbunit.DbUnit
import groovy.sql.Sql
import spock.lang.Specification

import org.apache.tomcat.jdbc.pool.DataSource


/**
 * Created by bluepoet on 2017. 1. 15..
 */
class DBFieldInitializeErrorTest extends Specification {
    DataSource dataSource

    @DbUnit
    def content = {
        User(id: 1, name: 'bluepoet', age: 20, 'phone_number': '010-0000-0001')
        User(id: 2, name: 'tester', age: 10, 'phone_number': '010-0000-0002')

        Other_User(id: 1, name: 'bluepoet', age: 20)
        Other_User(id: 2, name: 'tester', age: 10, 'phone_number': '010-0000-0002')
    }

    def setup() {
        dataSource = new DataSource()
        dataSource.driverClassName = 'org.h2.Driver'
        dataSource.url = 'jdbc:h2:mem:'
        dataSource.username = 'sa'
        dataSource.password = ''
        new Sql(dataSource).execute("CREATE TABLE User(id INT PRIMARY KEY, name VARCHAR(255), age INT, phone_number VARCHAR(20))")
        new Sql(dataSource).execute("CREATE TABLE Other_User(id INT PRIMARY KEY, name VARCHAR(255), age INT, phone_number VARCHAR(20))")
    }

    def "모든 필드에 값을 넣은 경우, 모든 필드의 값이 정상적으로 조회된다."() {
        when:
        def result = new Sql(dataSource).rows("select * from User")

        then:
        result == [
                [ID: 1, NAME: 'bluepoet', AGE: 20, PHONE_NUMBER: '010-0000-0001'],
                [ID: 2, NAME: 'tester',AGE: 10, PHONE_NUMBER: '010-0000-0002']
        ]
    }

    def "모든 필드에 값을 넣지 않은 경우, 값을 넣지 않은 필드의 값은 조회되지 않는다."() {
        when:
        def result = new Sql(dataSource).rows("select * from Other_User")

        then:
        result == [
                [ID: 1, NAME: 'bluepoet',AGE: 20, PHONE_NUMBER: null],
                [ID: 2,  NAME: 'tester',AGE: 10, PHONE_NUMBER: null]
        ]
    }
}