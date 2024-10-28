package ru.nsu.fitkulin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

/**
 * class for hash table.
 *
 * @param <K> key
 * @param <V> value
 */
public class HashTable<K, V> implements Iterable<Entry<K, V>> {

    private ArrayList<ArrayList<Entry<K, V>>> table;
    private int size;
    private int capacity;

    /**
     * constructor.
     */
    public HashTable() {
        this.capacity = 16;
        this.table = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            table.add(new ArrayList<>());
        }
        this.size = 0;
    }

    public int hashFunction(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    private void resize() {
        ArrayList<ArrayList<Entry<K, V>>> oldValues = table;
        capacity *= 2;
        table = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            table.add(new ArrayList<>());
        }

        size = 0;
        for (ArrayList<Entry<K, V>> bucket : oldValues) {
            for (Entry<K, V> entry : bucket) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void put(K key, V value) {
        int index = hashFunction(key);
        ArrayList<Entry<K, V>> bucket = table.get(index);

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }

        bucket.add(new SimpleEntry<>(key, value));
        size++;

        if (size >= capacity) {
            resize();
        }
    }

    public V get(K key) {
        int index = hashFunction(key);
        ArrayList<Entry<K, V>> bucket = table.get(index);

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void update(K key, V value) {
        put(key, value);
    }

    public boolean isContains(K key) {
        return (get(key) != null);
    }

    public void removeKey(K key) {
        int index = hashFunction(key);
        ArrayList<Entry<K, V>> bucket = table.get(index);

        for (int i = 0; i < bucket.size(); i++) {
            Entry<K, V> entry = bucket.get(i);
            if (entry.getKey().equals(key)) {
                bucket.remove(i);
                size--;
                return ;
            }
        }
        throw new IllegalArgumentException("Key not found: " + key);
    }

    @Override
    public boolean equals(Object o) {
        return false; //////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");


        for (ArrayList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
                sb.append(" ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            private int currentBucket = 0;
            private int currentEntry = 0;

            @Override
            public boolean hasNext() {
                while (currentBucket < table.size()) {
                    if (currentEntry < table.get(currentBucket).size()) {
                        return true;
                    }
                    currentBucket++;
                    currentEntry = 0;
                }
                return false;
            }

            @Override
            public Entry<K, V> next() {
                Entry<K, V> entry = table.get(currentBucket).get(currentEntry);
                currentEntry++;
                return entry;
            }
        };
    }
    public int getSize() {
        return size;
    }
}