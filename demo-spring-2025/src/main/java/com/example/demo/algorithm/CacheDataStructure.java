package com.example.demo.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * from <a href="https://zhuanlan.zhihu.com/p/420163076">大厂爱考的 LRU 与 LFU，进来学会了再走</a>
 *
 * @author Lawrence Peng
 */
public class CacheDataStructure {


    /**
     * 1 LRU
     * LRU（Least Recently Used，最近最少使用）算法，其核心思想为：最近被访问过的数据，将来被再次访问的概率会更高。当缓存容量已满，则删除最近最少使用的数据，因为它再次被访问的概率更低。
     *
     * 本文 LRU 的实现使用的数据结构是哈希表和双向链表，哈希表用于存储数据，双向链表根据最近访问的顺序管理数据，使最近访问的数据始终在链表头部，最近最少使用的总是在链表尾部等待删除。另外，双向链表也方便添加和删除节点。
     *
     * LRU 工作过程：
     *
     * 每次的 get 或 put 操作都是一次访问，会触发 setFirst 方法将数据置于链表头部。
     * 当 put 操作使缓存大小超出容量时，del 方法会删除链表尾部最近最少使用的元素。
     */
    public static class LRUCache {
        private int sz;
        private final int cap;
        private final Map<Integer, DoublyLinkedList.Node> cache;
        private final DoublyLinkedList list;

        public LRUCache(int capacity) {
            cap = capacity;
            cache = new HashMap<>();
            list = new DoublyLinkedList();
        }

        public int get(int key) {
            if (!cache.containsKey(key)) {
                return -1;
            }
            DoublyLinkedList.Node node = cache.get(key);
            return list.setFirst(node);
        }

        public void put(int key, int value) {
            DoublyLinkedList.Node node = cache.get(key);
            if (node == null) {
                node = new DoublyLinkedList.Node(key, value);
                cache.put(key, node);
                sz++;
            }
            node.update(value);
            list.setFirst(node);
            if (sz > cap) {
                del();
            }
        }

        private void del() {
            cache.remove(list.deleteLast());
            sz--;
        }
    }

    public static class DoublyLinkedList {
        public static class Node {
            private int key;
            private int val;
            private Node next;
            private Node last;

            public Node() {
            }

            public Node(int k, int v) {
                key = k;
                val = v;
            }

            public void update(int v) {
                val = v;
            }
        }

        private Node head;
        private Node tail;

        public DoublyLinkedList() {
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.last = head;
        }

        // 将节点移动到头部并返回节点值
        public int setFirst(Node node) {
            // 如果节点已在链表中
            if (node.last != null && node.next != null) {
                node.last.next = node.next;
                node.next.last = node.last;
            }
            node.next = head.next;
            node.next.last = node;
            node.last = head;
            head.next = node;
            return node.val;
        }

        // 删除尾部最近最少使用的节点并返回键
        public int deleteLast() {
            Node del = tail.last;
            del.last.next = tail;
            tail.last = del.last;
            return del.key;
        }
    }


    /**
     * 2 LFU
     * LFU（Least Frequently Used，最少使用）算法，其核心思想为：当缓存容量已满，删除最少使用（使用次数最少）的数据，若这样的数据存在多个，删除其中的最近最少使用。
     *
     * 注意：
     *
     * LFU虽然使用次数最少，但可能最近使用了。
     * LRU虽然最近最少使用，但可能使用次数很多。
     * 本文 LFU 的实现使用两个哈希表，一个记录 key 到 node 的映射，node 是双向链表的一个节点，组合了 key、value、frequency 等字段；另一个记录 frequency 到双向链表的映射，这个 frequency 是对应双向链表中所有节点的使用次数。
     *
     * LFU 工作过程：
     *
     * 调用 put 时，如果 key 不存在，我们检查容量，如果容量为0，拒绝 put；如果容量不足，删除最少使用节点中的最近最少使用（最近最少使用的实现同 LRU）；然后新增 node，并将其添加到 frequency 为 1 的双向链表头部，更新两个哈希表，递增 size，minFrequency 置 1。
     * 调用put 时，如果 key 存在，更新 value，再更新节点。
     * 调用 get 时，更新节点，返回 value。
     * 每次访问（put或get）都要更新节点，即更新使用次数，从旧链表中删除，添加到新链表头部，更新全局最少使用次数。
     * LFU cache 和双向链表的实现如下：
     */

    class LFUCache {
        private int capacity;
        private int size;
        private int minFrequency;
        private Map<Integer, DoublyLinkedList2.Node> cache;
        private Map<Integer, DoublyLinkedList2> frequencyToList;

        public LFUCache(int capacity) {
            this.capacity = capacity;
            cache = new HashMap<>();
            frequencyToList = new HashMap<>();
            frequencyToList.put(1, new DoublyLinkedList2());
        }

        // 更新节点并返回节点值
        public int get(int key) {
            if (!cache.containsKey(key)) {
                return -1;
            }
            DoublyLinkedList2.Node node = cache.get(key);
            updateNode(node);
            return node.getVal();
        }

        // 1 若键不存在，则新增节点，若容量不足，还需删除最少使用的节点中的最近最少使用
        // 新增节点存至 frequency 为1的链表头部，存入哈希表，更新 size 和 minFrequency
        // 2 若键存在，则更新节点值，并更新节点
        public void put(int key, int value) {
            if (capacity == 0) {
                return;
            }
            if (!cache.containsKey(key)) {
                if (size == capacity) {
                    cache.remove(frequencyToList.get(minFrequency).deleteLast());
                    size--;
                }
                DoublyLinkedList2 list = frequencyToList.get(1);
                DoublyLinkedList2.Node node = list.new Node(key, value);
                list.addFirst(node);
                cache.put(key, node);
                size++;
                minFrequency = 1;
                return;
            }
            DoublyLinkedList2.Node node = cache.get(key);
            node.updateVal(value);
            updateNode(node);
        }

        // 每次 get put 访问都需要更新节点
        // 1 更新使用频率
        // 2 从旧链表中移除
        // 3 添加到新链表头部
        // 4 更新最少使用频率
        private void updateNode(DoublyLinkedList2.Node node) {
            int oldFreq = node.updateFreq();
            DoublyLinkedList2 oldList = frequencyToList.get(oldFreq);
            oldList.deleteNode(node);
            DoublyLinkedList2 newList = frequencyToList.get(node.getFreq());
            if (newList == null) {
                newList = new DoublyLinkedList2();
                frequencyToList.put(node.getFreq(), newList);
            }
            newList.addFirst(node);
            if (oldList.isEmpty() && minFrequency == oldFreq) {
                minFrequency++;
            }
        }
    }

    class DoublyLinkedList2 {
        class Node {
            private int key;
            private int val;
            private int frequency;
            private Node last;
            private Node next;

            public Node() {}

            public Node(int k, int v) {
                key = k;
                val = v;
                frequency = 1;
            }

            public int getVal() {
                return val;
            }

            public void updateVal(int v) {
                val = v;
            }

            public int getFreq() {
                return frequency;
            }

            public int updateFreq() {
                return frequency++;
            }
        }

        private Node head;
        private Node tail;

        public DoublyLinkedList2() {
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.last = head;
        }

        public void deleteNode(Node node) {
            node.last.next = node.next;
            node.next.last = node.last;
        }

        public int deleteLast() {
            Node del = tail.last;
            tail.last = del.last;
            tail.last.next = tail;
            return del.key;
        }

        public void addFirst(Node node) {
            node.next = head.next;
            node.next.last = node;
            node.last = head;
            head.next = node;
        }

        public boolean isEmpty() {
            return head.next == tail;
        }
    }
}
