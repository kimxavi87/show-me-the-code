package com.kimxavi87.normal.file;

import org.junit.jupiter.api.Test;

import java.io.File;

public class FileTests {

    @Test
    public void getFileName() {
        String path = "/abc/sss/filename.jpg";
        File file = new File(path);
        System.out.println(file.getName());
    }
}
