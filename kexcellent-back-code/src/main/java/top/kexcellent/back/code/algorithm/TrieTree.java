/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

/**
 * 前缀树
 *     []
 *  [] [] [] [] [] ... 26
 * [][][].. [][][]..  26*26
 * @author kanglele
 * @version $Id: Trie, v 0.1 2024/11/6 11:34 kanglele Exp $
 */
public class TrieTree {
    //记录该字母的下一位所有可能的字母坐标
    private TrieTree[] children;
    //该字母是否为最后一个字母
    private boolean isEnd;
    public TrieTree() {
        //初始化26个字母
        children = new TrieTree[26];
        //默认为不是最后一个字母
        isEnd = false;
    }
    public void insert(String word) {
        //得到字典树根节点
        TrieTree node = this;
        //去遍历待插入单词的字符集合
        for (char c : word.toCharArray()) {
            //得到该字符在数组中的坐标
            int index = c - 'a';
            //如果正在遍历的该字母节点的数组坐标中没有记录，就新建一个字母节点在字典树中
            if(node.children[index] == null){
                node.children[index] = new TrieTree();
            }
            //每一次生成字母都移动指针到下一个字母节点
            node = node.children[index];
        }
        //最后一个字母节点设置为最后一个字母
        node.isEnd = true;
    }
    public boolean search(String word) {
        //返回检索到的最后一个字母节点
        TrieTree node = searchPrefix(word);
        //只有当该单词在字典树中存在并且最后一个字母节点为最后一个字母，才返回true
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        //只要前缀匹配存在于字典树中就返回true
        return searchPrefix(prefix) != null;
    }
    //前缀搜索 还是 全文搜索都是调用此方法，区别在于前缀搜索只要前缀匹配就返回true，全文搜索则要匹配到最后一个字母才返回true，所以这里返回的是最后一个字母节点
    public TrieTree searchPrefix(String word){
        //得到字典树根节点
        TrieTree node = this;
        //开始验证字符串在字典树中是否存在
        for (char c : word.toCharArray()) {
            //得到该字符在数组中的坐标
            int index = c - 'a';
            if(node.children[index] == null){
                //如果在此过程中没有找到待搜索的其中一个字母节点，就返回null，代表该字母不存在于字典树中
                return null;
            }
            //如果该字符在数组中存在，就移动指针到下一个字母节点，直至到达最后一个待搜索的最后一个字母节点
            node = node.children[index];
        }
        //没有问题，那就是到达了最后一个待搜索的最后一个字母节点，返回该节点(节点可能是最后一个字母节点也可能不是)
        return node;
    }
}
