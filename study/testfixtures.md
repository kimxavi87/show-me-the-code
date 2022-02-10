테스트에 필요한 공통 유틸객체 같은 것을 공유하면 좋을 때가 있다
이럴 때 gradle testFixtures plugin을 활용해서 테스트에서 활용해보자

1. 공통 build.gradle 수정
apply plugin: "java-test-fixtures"
testFixtures plugin
gradle 설정에 넣어준다

2. 사용할 build.gradle 수정
testImplementation(testFixtures(project(":project")))

3. 해당하는 프로젝트에서 testFixtures 만들기
gradle 빌드하면 intellij Project Structure 에 main, test 처럼 testFixtures 가 생긴다
그러면 디렉토리를 만들어주고 거기에 공통유틸들 넣어주면 됨

출처)
https://kyucumber.github.io/posts/spring/gradle-test-fixtures-plugins

처음 확인한 곳은
org.springframework.transaction.testfixture.CallCountingTransactionManager