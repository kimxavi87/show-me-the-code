package com.company.toby.doubledispatch;

import java.util.Arrays;
import java.util.List;

public class Dispatch {
    interface Sns {
        void post(Text text);
        void post(Picture picture);
    }

    static class Facebook implements Sns {

        @Override
        public void post(Text text) {
            System.out.println(this.getClass().getSimpleName() + " " + text.getClass().getSimpleName());

        }

        @Override
        public void post(Picture picture) {
            System.out.println(this.getClass().getSimpleName() + " " + picture.getClass().getSimpleName());
        }
    }

    static class Twitter implements Sns {

        @Override
        public void post(Text text) {
            System.out.println(this.getClass().getSimpleName() + " " + text.getClass().getSimpleName());
        }

        @Override
        public void post(Picture picture) {
            System.out.println(this.getClass().getSimpleName() + " " + picture.getClass().getSimpleName());
        }
    }

    interface Post {
        void postOn(Sns sns);
    }

    static class Text implements Post {
        @Override
        public void postOn(Sns sns) {
            sns.post(this);
        }
    }

    static class Picture implements Post {
        @Override
        public void postOn(Sns sns) {
            sns.post(this);
        }
    }

    public static void main(String[] args) {
        List<Sns> snsList = Arrays.asList(new Twitter(), new Facebook());
        List<Post> posts = Arrays.asList(new Text(), new Picture());

        for (Sns sns : snsList) {
            for (Post post : posts) {
                post.postOn(sns);
            }
        }
    }
}
