package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.Map.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HashTableTest {
    private HashTable<String, Integer> hashTable;

    @BeforeEach
    public void setUp() {
        hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);
    }

    @Test
    public void testHashTableOperations() {

        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertEquals(3, hashTable.get("three"));

        hashTable.update("one", 5);
        assertEquals(5, hashTable.get("one"));

        assertTrue(hashTable.isContains("one"));

        hashTable.removeKey("two");
        assertEquals(2, hashTable.getSize());
    }

    @Test
    public void testEmptyHashTable() {
        hashTable.removeKey("one");
        hashTable.removeKey("two");
        hashTable.removeKey("three");
        assertEquals(0, hashTable.getSize());
    }

    @Test
    public void testIterator() {
        Iterator<Entry<String, Integer>> iterator = hashTable.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            iterator.next();
        }
        assertEquals(3, count);
    }

    @Test
    public void testToString() {
        String expected = "[one=1 two=2 three=3 ]";
        assertEquals(expected, hashTable.toString());
    }
}