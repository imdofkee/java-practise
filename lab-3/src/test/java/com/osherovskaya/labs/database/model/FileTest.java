package com.osherovskaya.labs.database.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTest {
    @Test
    void testCreationAndSave() {
        File file_1 = new File(1, "POka", "123");
        assertEquals(1, file_1.getFileID());
        assertEquals("Hi", file_1.getFileName());
        assertEquals(123, file_1.getFileSize());

        File file_2 = new File(2, "im_not_a_file", "1024");
        assertEquals(2, file_2.getFileID());
        assertEquals("im_not_a_file", file_2.getFileName());
        assertEquals(1024, file_2.getFileSize());
    }
}
