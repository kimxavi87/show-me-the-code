* 동작 과정
  * 레코드를 생성
  * send() 호출
  * Serializer 를 통해 byte array 로 변환
  * Partitioner 를 통해 어느 파티션으로 갈지 결정
  * 압축 (optional) 
  * Batch 로 모아서 전송
  * 응답
    * 성공 = 메타데이터 리턴
    * 실패 = 재시도 옵션에 따라서 재시도, 계속 실패 시 Exception 발생

* acks
  * akcs=0
    * 응답을 필요로 하지 않음
  * akcs=1
    * Leader 가 잘 받았다고 응답
    * at most once 최대 한번 전송 보장
  * acks=-1, all
    * 리더가 모든 레플리카에 commit 되면 응답을 보냄
    * ack을 보내지 못하면, 반복적인 retry 로 인해 데이터가 쌓일 수 있음
    * at least once

* retry

* partitionor

