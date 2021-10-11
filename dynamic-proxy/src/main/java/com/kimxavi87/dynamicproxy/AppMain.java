package com.kimxavi87.dynamicproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AppMain {
    public BookService javaDynamicProxy() {
        BookService bookService = (BookService) Proxy.newProxyInstance(BookService.class.getClassLoader(), new Class[]{BookService.class}, new InvocationHandler() {
            BookService bookService = new BookDefaultService();

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equalsIgnoreCase("rent")) {
                    System.out.println("logging java a");
                    Object invoke = method.invoke(bookService, args);
                    System.out.println("logging java b");
                    return invoke;
                }
                return method.invoke(bookService, args);
            }
        });

        return bookService;
    }
    public static void main(String[] args) {
        AppMain appMain = new AppMain();
        // appMain.javaDynamicProxy();
        // BookService bookService = appMain.cglibDynamicProxy();

        Book book = new Book();
        book.setTitle("Hello Proxy!");
        bookService.rent(book);
        bookService.ret(book);
    }

    private BookService cglibDynamicProxy() {
        MethodInterceptor methodInterceptor = new MethodInterceptor() {
            BookService bookService = new BookDefaultService();
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                if (method.getName().equalsIgnoreCase("rent")) {
                    System.out.println("logging cglib a");
                    Object invoke = method.invoke(bookService, args);
                    System.out.println("logging cglib b");
                    return invoke;
                }
                return method.invoke(bookService, args);
            }
        };

        return (BookService) Enhancer.create(BookService.class, methodInterceptor);
    }
}
