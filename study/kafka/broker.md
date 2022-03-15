

* Page Cache, Flush
    * 파티션은 Log segment 파일로 구성
        * 기본값 1GB 마다 새로운 segment 구성
    * 브로커는 성능을 위해 OS page cache 에 write 
    * 브로커는 수신된 데이터를 아무 핸들링하지 않고, 그대로 write 한다
        * Zero-Copy
    * Flush
        * Broker가 완전히 종료되면 Flush
        * OS 가 자체적으로 실행하는 Background Flusher Thread 실행
    * flush 가 되기 전에 브로커에 장애가 나면?
        * 리플리케이션이 없으면, 해당 데이터가 손실된다
        * 리플리케이션에 복제본이 있다면, 온라인 상태가 되면 복구된다 
    
* Zero-Copy
    * User space (java hep) 에 복사되지 않고,
    * CPU 개입 없이 네트워크 버퍼에 있는 데이터를 OS Cache로 그대로 전송하는 기능
    * Brokder heap 메모리를 절약하고, CPU 부담을 줄여준다
    * 구현 방법
        * 리눅스 sendfile 시스템콜로 구현됨
            * read 와 send 가 합쳐진 메소드
            * DMA 엔진이 디스크에서 파일을 읽어 커널의 주소공간인 Read Buffer로 복사
            * 커널 모드에서 유저모드로 컨텍스트 스위칭 없이 Socket Buffer로 복사한다 
            * Socket Buffer 에 복사된 데이터를 DMA 엔진을 통해 Nic Buffe 로 복사한다
            * 데이터가 전송됨 
        * java nio transferTo(), transferFrom()
            * 내부에서 senfile 시스템콜 호출

* Log File

* Commit Log
  * 설명
    * 추가만 가능하고 변경 불가능한 데이터 스트럭처
    * 데이터는 항상 로그 끝에 추가되고 변경되지 않음
  * offset
  * log-end-offset
  * current-offset

* Partition

* Segment

https://soft.plusblog.co.kr/7


