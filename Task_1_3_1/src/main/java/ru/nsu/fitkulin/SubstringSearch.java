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
     * method for finding a substring using the KMP algorithm.
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
            byte[] helper1 = new byte[arrTarget.length - 1];
            Arrays.fill(helper1, (byte) 0b10101010);
            byte[] helper2 = new byte[arrTarget.length + 1];

            while ((bytesRead = inputStream.read(helper2)) != -1) {
                System.arraycopy(helper1, 0, buffer, 0, helper1.length);
                System.arraycopy(helper2, 0, buffer, helper1.length, helper2.length);
                for (int i = 0; i < bytesRead; i++) {
                    if (getOctetNumber(buffer[i]) != 0) {
                        boolean correct = true;
                        currPos++;
                        for (int j = 0; j < arrTarget.length; j++) {
                            if (arrTarget[j] != buffer[i + j]) {
                                correct = false;
                                break;
                            }
                        }
                        if (correct) {
                            res.add(currPos);
                        }
                    }
                }
                helper1 = Arrays.copyOfRange(buffer,
                        buffer.length - (arrTarget.length - 1), buffer.length);

            }
            return res;
        }
    }

    /**
     * method for knowing how much bytes will be next in UTF-8.
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
