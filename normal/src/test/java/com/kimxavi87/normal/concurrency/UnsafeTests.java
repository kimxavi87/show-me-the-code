package com.kimxavi87.normal.concurrency;

import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;

public class UnsafeTests {

    @Test
    public void getUnsafe() throws ReflectiveOperationException {
        Unsafe unsafe = getUnsafeForTest();
    }

    @Test
    public void allocateInstance() throws ReflectiveOperationException {
        Unsafe unsafe = getUnsafeForTest();

        Customer customer = new Customer();
        System.out.println(customer.getId());

        // 생성자 호출이 되지 않음
        Customer allocateInstance = (Customer) unsafe.allocateInstance(Customer.class);
        System.out.println(allocateInstance.getId());
    }

    @Test
    public void alternatePrivateField() throws ReflectiveOperationException {
        Unsafe unsafe = getUnsafeForTest();
        Customer customer = new Customer();
        Field id = customer.getClass().getDeclaredField("id");

        // private 값 변경
        unsafe.putInt(customer, unsafe.objectFieldOffset(id), 3);
        System.out.println(customer.getId());
    }

    public static class Customer {
        private long id;

        public Customer() {
            this.id = 5;
        }

        public long getId() {
            return id;
        }
    }

    @Test
    public void throwException() throws ReflectiveOperationException {
        Unsafe unsafe = getUnsafeForTest();

        // checked exception 이지만 try-catch 나 메서드에 표시를 안 해도 된다
        // 컴파일러가 일반 java 코드와 같은 방식으로 잡지 않는다
        // 예외가 던져지긴 던져짐
        unsafe.throwException(new IOException());
    }

    @Test
    public void offHeapMemory() throws ReflectiveOperationException {
        // gc 프로세스와 jvm에 제어되지 않는 off-heap 인 특수 메모리 영역
        // 수동으로 관리되어야 한다

        Unsafe unsafe = getUnsafeForTest();
        final int BYTE = 1;
        // 큰 사이즈
        long size = (long) Integer.MAX_VALUE * 2;

        // 메모리 할당
        long address = unsafe.allocateMemory(size * BYTE);

        unsafe.putByte(address, (byte) 5);
        byte firstByte = unsafe.getByte(address);

        System.out.println(firstByte);

        unsafe.putByte(address + 1, (byte) 6);
        byte secondByte = unsafe.getByte(address + 1);
        System.out.println(secondByte);

        // 메모리 해제 필수
        unsafe.freeMemory(address);
    }

    @Test
    public void compareAndSwap() throws Exception {
        CasCounter casCounter = new CasCounter();
        casCounter.increment();
    }

    static class CasCounter {
        private Unsafe unsafe;
        private volatile long counter = 0;
        private long offset;

        private Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        }

        public CasCounter() throws Exception {
            unsafe = getUnsafe();

            // 해당 필드의 offset을 저장해둠
            offset = unsafe.objectFieldOffset(CasCounter.class.getDeclaredField("counter"));
        }

        public void increment() {
            long before = counter;
            // 성공할 때 까지 시행
            // blocking 이 없음
            while (!unsafe.compareAndSwapLong(this, offset, before, before + 1)) {
                before = counter;
            }
        }

        public long getCounter() {
            return counter;
        }
    }

    @Test
    public void parkAndUnPark() throws ReflectiveOperationException {
        // JVM이 컨텍스트 스위칭을 할 때 사용되는 메서드
        Unsafe unsafe = getUnsafeForTest();

        // 쓰레드가 어떤 액션이 끝나기를 기다릴 때
        // park 메서드를 통해서 쓰레드를 블로킹할 수 있음
        // unsafe.park()
        // Object.wait() 과 비슷함
        // 그러나 이것은 native OS 코드를 호출 하므로 특정 아키텍쳐에서 성능상 이점을 얻는다
        // 쓰레드가 블락 됐다가 다시 실행되어야 할 때, JVM은 unpark() 를 사용한다
    }

    private Unsafe getUnsafeForTest() throws ReflectiveOperationException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe) f.get(null);
    }
}
