## 간단한 spock-dbunit CRUD 예제

SpockDBUnitTest.groovy에 spock-dbunit library를 이용해서 

User테이블을 대상으로 초간단 CRUD예제를 만들어봤습니다.

기존 spring-test-dbunit을 사용할 때는, DB와 연관된 부분을 XML파일로 

따로 만들어야 하는 불편함이 있었지만, spock-dbunit은 직접 쿼리를 날릴 수 있어 편리합니다.

spock-dbunit의 테스트코드는 [spock-dbunit testcode](https://github.com/janbols/spock-dbunit/tree/master/src/test/groovy/be/janbols/spock/extension.dbunit)에서 확인하실 수 있습니다.