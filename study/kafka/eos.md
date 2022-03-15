* exactly once semantics
    * producer 에서 생성하는 중복 메시지 방지
    * 중복 메시지로 인한 중복 처리 방지
    * Transaction 기능을 사용
        * 하나의 트랜잭션 내의 모든 메시지가 모두 write 되었는지 또는 전혀 write 되지 않았는지 확인 (Atomic Message)
