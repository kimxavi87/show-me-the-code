* nested transaction

MySQL innoDB 에서는 지원 안함
대신 SAVEPOINTS 라는 것을 지원
보통 SQL SERVER는 트랜잭션을 하나의 큰 단위로만 인식한다

* PROPAGATION_REQUIRES_NEW

항상 독립적인 트랜잭션을 사용하며, 기존 외부 트랜잭션에는 절대 참여하지 않음