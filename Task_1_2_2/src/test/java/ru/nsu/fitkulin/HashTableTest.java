package ru.nsu.fitkulin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.Map.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HashTableTest {
    private HashTable<String, Integer> hashTable;
    private HashTable<String, Integer> hashTable2;

    @BeforeEach
    public void setUp() {
        hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        hashTable2 = new HashTable<>();
        hashTable2.put("one", 1);
        hashTable2.put("three", 3);
        hashTable2.put("two", 2);
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
        for (var c : hashTable) {
            count++;
        }
        assertEquals(6, count);
    }
    @Test
    public void testResize() {
        hashTable.put("four", 4);
        hashTable.put("five", 5);
        hashTable.put("six", 6);
        hashTable.put("seven", 7);
        hashTable.put("eight", 8);
        hashTable.put("nine", 9);
        hashTable.put("ten", 10);
        hashTable.put("eleven", 11);
        hashTable.put("twelve", 12);
        hashTable.put("ildar", 13);
        hashTable.put("kirill", 14);
        hashTable.put("grigory", 15);
        hashTable.put("bogdan", 16);
        hashTable.put("kolya", 17);
        hashTable.put("nikita", 18);
        hashTable.put("lol", 19);

        hashTable.update("one", 5);
        assertEquals(5, hashTable.get("one"));

        assertTrue(hashTable.isContains("one"));

        hashTable.removeKey("grigory");
        assertEquals(18, hashTable.getSize());

        String expected = "{six=6, one=5, four=4, twelve=12, bogdan=16, lol=19, kirill=14,"
                + " two=2, seven=7, eight=8, kolya=17, five=5, nine=9, ildar=13,"
                + " nikita=18, ten=10, three=3, eleven=11}";
        assertEquals(expected, hashTable.toString());
    }

    @Test
    public void testToString() {
        String expected = "{one=1, two=2, three=3}";
        assertEquals(expected, hashTable.toString());
    }

    @Test
    void testEquals() {
        hashTable.put("one", 3);
        hashTable.put("two", 4);
        hashTable.put("three", 5);

        hashTable2.put("one", 3);
        hashTable2.put("three", 5);
        hashTable2.put("two", 4);

        assertEquals(hashTable, hashTable2);
    }

    @Test
    void testNotEquals() {
        hashTable2.put("four", 4);
        assertNotEquals(hashTable, hashTable2);
    }

}