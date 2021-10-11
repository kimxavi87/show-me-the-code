package com.kimxavi87.dynamicproxy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static net.bytebuddy.matcher.ElementMatchers.named;

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
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AppMain appMain = new AppMain();
        // todo: dynamic proxy with interface

//        BookService bookService = appMain.javaDynamicProxy();
//        Book book = new Book();
//        book.setTitle("Hello Proxy!");
//        bookService.rent(book);
//        bookService.ret(book);

        // todo : dynamic proxy with class
        // todo : cglib
//        BookNoInterfaceClass bookNoInterfaceClass = appMain.cglibDynamicProxy();

//        Book book = new Book();
//        book.setTitle("Hello Proxy!");
//        bookNoInterfaceClass.rent(book);
//        bookNoInterfaceClass.ret(book);


        // todo : bytebuddy
        BookNoInterfaceClass bookNoInterfaceClass = appMain.byteBuddyDynamicProxy();
        Book book = new Book();
        book.setTitle("Hello Proxy!");
        bookNoInterfaceClass.rent(book);
        bookNoInterfaceClass.ret(book);

    }

    private BookNoInterfaceClass byteBuddyDynamicProxy() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends BookNoInterfaceClass> loaded = new ByteBuddy().subclass(BookNoInterfaceClass.class)
                .method(named("rent")).intercept(InvocationHandlerAdapter.of(new InvocationHandler() {
                    BookNoInterfaceClass bookNoInterfaceClass = new BookNoInterfaceClass();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("logging bytebuddy a");
                        Object invoke = method.invoke(bookNoInterfaceClass, args);
                        System.out.println("logging bytebuddy b");
                        return invoke;
                    }
                }))
                .make()
                .load(BookService.class.getClassLoader())
                .getLoaded();

        return loaded.getConstructor().newInstance();
    }

    private BookNoInterfaceClass cglibDynamicProxy() {
        MethodInterceptor methodInterceptor = new MethodInterceptor() {
            BookNoInterfaceClass bookService = new BookNoInterfaceClass();
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

        return (BookNoInterfaceClass) Enhancer.create(BookNoInterfaceClass.class, methodInterceptor);
    }
}
