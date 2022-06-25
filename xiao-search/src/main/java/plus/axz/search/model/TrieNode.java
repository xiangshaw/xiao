package plus.axz.search.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoxiang
 * @date 2022年06月25日
 * @particulars
 * 工具类TrieNode
 * Trie树：又称单词查找树，Trie树，是一种树形结构，是一种哈希树的变种。
 * 典型应用是用于统计，排序和保存大量的字符串（但不仅限于字符串），所以经常被搜索引擎系统用于文本词频统计。
 * 它的优点是：利用字符串的公共前缀来减少查询时间，最大限度地减少无谓的字符串比较，查询效率比哈希树高。
 *
 * - 根节点不包含字符，除根节点外的每一个子节点都包含一个字符。
 * - 从根节点到某一节点，路径上经过的字符连接起来，就是该节点对应的字符串。
 * - 每个单词的公共前缀作为一个字符节点保存。
 */
public class TrieNode {
    public char var;
    public boolean isWord;
    public Map<Character,TrieNode> children = new HashMap<>();
    public boolean containLongTail = false;
    public TrieNode(){}
    public TrieNode(char c){
        TrieNode node = new TrieNode();
        node.var = c;
    }
}
