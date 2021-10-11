package com.kimxavi87.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AppMain {
    public static void main(String[] args) {
        BookService bookService = (BookService) Proxy.newProxyInstance(BookService.class.getClassLoader(), new Class[]{BookService.class}, new InvocationHandler() {
            BookService bookService = new BookDefaultService();

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equalsIgnoreCase("rent")) {
                    System.out.println("logging a");
                    Object invoke = method.invoke(bookService, args);
                    System.out.println("logging b");
                    return invoke;
                }
                return method.invoke(bookService, args);
            }
        });
        Book book = new Book();
        book.setTitle("Hello Proxy!");
        bookService.rent(book);
        bookService.ret(book);
    }
}
