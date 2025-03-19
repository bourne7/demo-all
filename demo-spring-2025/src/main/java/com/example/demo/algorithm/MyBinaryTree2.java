package com.example.demo.algorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

// https://www.cnblogs.com/hapjin/p/5682704.html
public class MyBinaryTree2 {
    private static final Random rand = new Random();//insert left or right

    private static class BinaryNode {
        int ele;
        BinaryNode left;
        BinaryNode right;

        public BinaryNode(int ele) {
            this.ele = ele;
            this.left = this.right = null;
        }
    }

    private BinaryNode root;

    public void buildTree() {
        int[] nodes = {3, 0, 7, 5, 8};
        for (int node : nodes) {
            insert(node);
        }
    }

    public BinaryNode insert(int ele) {
        return root = insert(root, ele);
    }

    private BinaryNode insert(BinaryNode root, int ele) {
        if (root == null)
            return new BinaryNode(ele);
        if (ele % 2 == 0)
//        if (rand.nextInt() % 2 == 0)
            root.left = insert(root.left, ele);
        else
            root.right = insert(root.right, ele);
        return root;
    }

    //求解二叉树的高度
    public int height() {
        return height(root);
    }

    private int height(BinaryNode root) {
        if (root == null)
            return 0;
        int left_height = height(root.left);
        int right_height = height(root.right);
        return 1 + (left_height > right_height ? left_height : right_height);
    }

    //判断二叉树是否为平衡二叉树
    public boolean isBalance() {
        return isBalance(root);
    }

    private boolean isBalance(BinaryNode root) {
        if (root == null)
            return true;
        int left_height = height(root.left);
        int right_height = height(root.right);
        if (Math.abs(left_height - right_height) > 1)
            return false;
        else
            return isBalance(root.left) && isBalance(root.right);
    }

    //print binary tree in level
    public void printTree() {
        if (root == null)
            return;
        printTree(root);
    }

    //按层打印二叉树,每行打印一层
    private void printTree(BinaryNode root) {
        assert root != null;
        Queue<BinaryNode> queue = new LinkedList<>();
        BinaryNode currentNode;
        int current, next;
        current = 1;
        next = 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            currentNode = queue.poll();
            System.out.printf("%-4d", currentNode.ele);
            current--;
            if (currentNode.left != null) {
                queue.offer(currentNode.left);
                next++;
            }
            if (currentNode.right != null) {
                queue.offer(currentNode.right);
                next++;
            }

            if (current == 0) {
                System.out.println();
                current = next;
                next = 0;
            }
        }
    }


    private void printTree2(BinaryNode root) {
        assert root != null;
        Queue<BinaryNode> queue = new LinkedList<>();
        BinaryNode currentNode;
        int singleLevelCount, nextLevelCount;
        singleLevelCount = 1;
        nextLevelCount = 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            currentNode = queue.poll();
            System.out.printf("%-5d", currentNode.ele);
            singleLevelCount--;
            if (currentNode.left != null) {
                queue.offer(currentNode.left);
                nextLevelCount++;
            }
            if (currentNode.right != null) {
                queue.offer(currentNode.right);
                nextLevelCount++;
            }

            // 当一层打印完了以后就打印下一层。
            if (singleLevelCount == 0) {
                System.out.println();
                singleLevelCount = nextLevelCount;
                nextLevelCount = 0;
            }
        }
    }

    public static void main(String[] args) {
        MyBinaryTree2 mbt2 = new MyBinaryTree2();
        mbt2.buildTree();
        mbt2.printTree();
        System.out.println("height: " + mbt2.height());
        System.out.println("balance? " + mbt2.isBalance());
        mbt2.printTree2(mbt2.root);
    }
}