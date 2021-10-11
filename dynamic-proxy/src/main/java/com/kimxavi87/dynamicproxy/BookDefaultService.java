package com.kimxavi87.dynamicproxy;

public class BookDefaultService implements BookService {

    public void rent(Book book) {
        System.out.println("rent : " + book.getTitle());
    }

    public void ret(Book book) {
        System.out.println("ret : " + book.getTitle());
    }
}
