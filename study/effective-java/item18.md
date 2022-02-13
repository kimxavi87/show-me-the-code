상속보다는 컴포지션을 사용하라
=============

* 상속은 캡슐화를 깨뜨린다
  * 안전한 경우
    * 상위 클래스와 하위 클래스가 모두 개발자 통제하에 같은 패키지
    * 확장할 목적으로 설계되었고, 문서화도 잘 된 경우  

* 대책 : 컴포지션을 사용

* 전달과 전달 메서드
  * Wrapper 클래스
  * 데코레이터 패턴
  * 위임(delegation) 

* SELF 문제

https://stackoverflow.com/questions/28254116/wrapper-classes-are-not-suited-for-callback-frameworks