* Generic Type Erasure 란
    * Element 타입을 컴파일 타임에만 검사하고, Runtime 에는 알 수 없음
    * 컴파일 타임에만 타입을 정의하고 런타임에는 타입을 제거
    * 소거
        * unbounded type (<?>, <T>) 은 Object로 변환
        * bound type (E extends Comparable) 의 경우 Comparable로
        * 타입 안정성 보존을 위해 필요하다면 타입 캐스팅을 넣음
        * Generic 타입에서 다형성 보존을 위해 bridge method를 넣음

~~~java

// bridge method 생성 전
public class MyComparator implements Comparator {
   public int compare(Integer a, Integer b) {
   }
}

// 생성 후
public class MyComparator implements Comparator<Integer> {
   public int compare(Integer a, Integer b) {
   }

   // bridge method
   public int compare(Object a, Object b) {
      return compare((Integer)a, (Integer)b);
   }
}

~~~


https://devlog-wjdrbs96.tistory.com/263
