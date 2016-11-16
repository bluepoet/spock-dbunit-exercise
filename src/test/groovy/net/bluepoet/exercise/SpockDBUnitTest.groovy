package net.bluepoet.exercise

import be.janbols.spock.extension.dbunit.DbUnit
import groovy.sql.Sql
import org.apache.tomcat.jdbc.pool.DataSource
import spock.lang.Specification

/**
 * Created by bluepoet on 2016. 11. 17..
 */
class SpockDBUnitTest extends Specification {

    DataSource dataSource

    @DbUnit
    def content = {
        User(id: 1, name: 'bluepoet', age: 20, 'phone_number': '010-0000-0001')
        User(id: 2, name: 'tester', age: 10, 'phone_number': '010-0000-0002')
    }

    def setup() {
        dataSource = new DataSource()
        dataSource.driverClassName = 'org.h2.Driver'
        dataSource.url = 'jdbc:h2:mem:'
        dataSource.username = 'sa'
        dataSource.password = ''
        new Sql(dataSource).execute("CREATE TABLE User(id INT PRIMARY KEY, name VARCHAR(255), age INT, phone_number VARCHAR(20))")
    }

    def cleanup() {
        new Sql(dataSource).execute("drop table User")
    }

    def "총 유저수가 몇명인지 테스트한다."() {
        when:
        def result = new Sql(dataSource).firstRow("select count(*) as cnt from User")

        then:
        result.cnt == 2
    }

    def "특정 유저정보를 가져와 확인한다."() {
        expect:
        new Sql(dataSource).firstRow("select * from User where id = " + id) == result

        where:
        id | result
        1  | [ID: 1, NAME: 'bluepoet', AGE: 20, PHONE_NUMBER: '010-0000-0001']
        2  | [ID: 2, NAME: 'tester', AGE: 10, PHONE_NUMBER: '010-0000-0002']
    }

    def "특정 유저정보를 수정하고 결과를 확인한다."() {
        when:
        new Sql(dataSource).executeUpdate("update User set age=12, phone_number='010-1111-2222' where id=1")
        def result = new Sql(dataSource).firstRow("select * from User where id=1")

        then:
        result.age == 12
        result.phone_number == '010-1111-2222'
    }

    def "특정 유저를 삭제하고 총 유저수를 확인한다."() {
        when:
        new Sql(dataSource).executeUpdate("delete from User where id=2")
        def result = new Sql(dataSource).firstRow("select count(*) as cnt from User")

        then:
        result.cnt == 1
    }
}
