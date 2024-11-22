package ru.nsu.fitkulin;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;


public class SubstringSearch {

    private final String targetString;

    /**
     * constructor.
     */
    public SubstringSearch(String targetString) {
        this.targetString = targetString;
    }
    /**
     * method for finding a substring.
     */
    ArrayList<Long> find(String filename) throws  IOException {
        ArrayList<Long> res = new ArrayList<>();
        byte[] arrTarget = targetString.getBytes(StandardCharsets.UTF_8);
        int bufferSize = arrTarget.length * 2;
        long currPos = -1;
        int bytesRead;

        try (BufferedInputStream inputStream =
                     new BufferedInputStream(new FileInputStream(filename))) {
            byte[] buffer = new byte[bufferSize];
            byte[] tempBuf1 = new byte[arrTarget.length - 1];
            byte[] tempBuf2 = new byte[arrTarget.length + 1];
            Arrays.fill(tempBuf1, (byte) 0b10101010);

            while ((bytesRead = inputStream.read(tempBuf2)) != -1) {
                System.arraycopy(tempBuf1, 0, buffer, 0, tempBuf1.length);
                System.arraycopy(tempBuf2, 0, buffer, tempBuf1.length, bytesRead);
                for (int i = 0; i < bytesRead; i++) {
                    if (getOctetNumber(buffer[i]) != 0) {
                        boolean flag = true;
                        currPos++;
                        for (int j = 0; j < arrTarget.length; j++) {
                            if (arrTarget[j] != buffer[i + j]) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            res.add(currPos);
                        }
                    }
                }
                tempBuf1 = Arrays.copyOfRange(buffer,
                        buffer.length - (arrTarget.length - 1), buffer.length);
            }
            return res;
        }
    }

    /**
     * determines how many bytes are needed for the next UTF-8 character.
     */
    private static int getOctetNumber(byte curr) {
        if ((curr & 0b10000000) == 0) {
            return 1;
        } else if ((curr & 0b11100000) == 0b11000000) {
            return 2;
        } else if ((curr & 0xF0) == 0b11100000) {
            return 3;
        } else if ((curr & 0b11111000) == 0b11110000) {
            return 4;
        } else {
            return 0;
        }
    }
}
