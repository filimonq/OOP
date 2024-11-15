package ru.nsu.fitkulin;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

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
    private int modificationCount;

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
        throw new IllegalArgumentException("Key not found: " + key);
    }

    public void update(K key, V value) {
        if (isContains(key)) {
            put(key, value);
        } else {
            throw new IllegalArgumentException("Key not found: " + key);
        }

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
        if (this == o) {
            return true;
        }

        if (!(o instanceof HashTable<?, ?>)) {
            return false;
        }

        HashTable<K, V> other = (HashTable<K, V>) o;
        if (this.size != other.size) {
            return false;
        }

        for (ArrayList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                K key = entry.getKey();
                V value = entry.getValue();

                if (!other.isContains(key) || !Objects.equals(other.get(key), value)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (ArrayList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                K key = entry.getKey();
                V value = entry.getValue();

                result = 31 * result + (key == null ? 0 : key.hashCode());
                result = 31 * result + (value == null ? 0 : value.hashCode());
            }
        }
        return result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (ArrayList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
                sb.append(", ");
            }
        }
        sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        final int expectedModificationCount = modificationCount;

        return new Iterator<Entry<K, V>>() {
            private int currentBucket = 0;
            private int currentEntry = 0;

            @Override
            public boolean hasNext() {
                checkForConcurrentModification();
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
                checkForConcurrentModification();
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements to iterate.");
                }
                Entry<K, V> entry = table.get(currentBucket).get(currentEntry);
                currentEntry++;
                return entry;
            }

            private void checkForConcurrentModification() {
                if (expectedModificationCount != modificationCount) {
                    throw new ConcurrentModificationException("Collection was modified");
                }
            }
        };
    }
    public int getSize() {
        return size;
    }
}