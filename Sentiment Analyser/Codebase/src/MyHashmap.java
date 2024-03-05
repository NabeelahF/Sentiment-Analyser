class MyHashMap<K, V> {
    private Entry<K, V>[] table;
    private int capacity = 16; // Initial capacity of the HashMap
    private int size = 0; // Number of elements in the HashMap

    public MyHashMap() {
        table = new Entry[capacity];
    }

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public void put(K key, V value) {
        int hash = key.hashCode();
        int index = Math.abs(hash) % capacity;

        Entry<K, V> entry = new Entry<>(key, value, null);

        if (table[index] == null) {
            table[index] = entry;
        } else {
            Entry<K, V> current = table[index];
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            current.next = entry;
        }
        size++;
    }

    public V get(K key) {
        int hash = key.hashCode();
        int index = Math.abs(hash) % capacity;

        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return value != null ? value : defaultValue;
    }

    public int size() {
        return size;
    }
}
