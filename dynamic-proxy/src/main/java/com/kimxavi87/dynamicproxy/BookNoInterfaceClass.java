package com.kimxavi87.dynamicproxy;

public class BookNoInterfaceClass {
    public void rent(Book book) {
        System.out.println("rent : " + book.getTitle());
    }

    public void ret(Book book) {
        System.out.println("ret : " + book.getTitle());
    }

}
