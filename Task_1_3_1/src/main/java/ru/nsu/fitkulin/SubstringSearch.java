package ru.nsu.fitkulin;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
    ArrayList<Integer> find(String filePath) throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        StringBuilder testBuilder = new StringBuilder();

        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(filePath))) {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = stream.read(buffer)) != -1) {
                testBuilder.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
            }
        }
        kmpSearch(testBuilder.toString(), list);
        return list;
    }

    /**
     * Knuth–Morris–Pratt algorithm.
     */
    private void kmpSearch(String text, ArrayList<Integer> list) {
        int[] lps = computeLPSArray(targetString);
        int i = 0; // индекс для текста
        int j = 0; // индекс для паттерна

        while (i < text.length()) {
            if (targetString.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }

            if (j == targetString.length()) {
                list.add(i - j); // совпадение
                j = lps[j - 1];
            } else if (i < text.length() && targetString.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
    }

    /**
     * method for fill lps array.
     */
    private int[] computeLPSArray(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0;
        lps[0] = 0; // lps[0] всегда 0

        for (int i = 1; i < pattern.length(); i++) {
            while (length > 0 && pattern.charAt(i) != pattern.charAt(length)) {
                length = lps[length - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
            } else {
                lps[i] = 0;
            }
        }
        return lps;
    }
}
