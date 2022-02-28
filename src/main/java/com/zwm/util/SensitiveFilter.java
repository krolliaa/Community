package com.zwm.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //替换符
    private static final String REPLACEMENT = "***";

    //创建根节点
    TrieNode rootNode = new TrieNode();

    //该注解表示该方法在构造方法后执行
    @PostConstruct
    public void init() {
        try (
                //获取敏感词列表信息
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            //读取敏感词 ---> 字符流
            String keyword;
            while ((keyword = bufferedReader.readLine()) != null) {
                //添加到前缀树当中 ---> 根据敏感词列表初始化前缀树
                this.addKeyword(keyword);
            }
        } catch (Exception e) {
            logger.error("加载敏感词文件失败：" + e.getMessage());
        }
    }

    //将敏感词列表字符添加到前缀树中
    public void addKeyword(String keyword) {
        //每来一个字符串都将其挂在到根节点
        TrieNode tempNode = rootNode;
        //遍历字符串中的字符，将其挂载到前缀树上
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            //判断当前字符是否已经存在在该节点上，如果不存在直接挂载
            TrieNode subNode = tempNode.getSubNode(c);
            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }
            //遍历下一个节点
            tempNode = subNode;
            //如果是最后一个字符则将判断最后一个字符的布尔型设置为true
            if (i == keyword.length() - 1) subNode.setKeywordEnd(true);
        }
    }

    //过滤敏感词
    public String filter(String text) {
        //如果文本是空的，直接返回null
        if (StringUtils.isBlank(text)) return null;
        //获取三个指针
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = begin;
        //存储结果集
        StringBuilder stringBuilder = new StringBuilder();
        //只要 position 不到最后一个位置说明一直要检测
        while (position < text.length()) {
            //获取文本的字符
            Character character = text.charAt(position);
            //判断是不是字符，如果是字符就跳过
            if (isSymbol(character)) {
                //如果是根节点
                if (tempNode == rootNode) {
                    begin++;
                }
                position++;
                continue;
            }
            System.out.println("什么字：" + character);
            //文本字符跟节点的子节点进行比对看有是不是一样的字符
            tempNode = tempNode.getSubNode(character);
            //如果获取得到的结果为空，表示子节点不存在该字符
            if (tempNode == null && begin == position) {
                //将字符添加到结果集中
                stringBuilder.append(character);
                position = ++begin;
                //tempNode回到根节点的位置上
                tempNode = rootNode;
            } else if (tempNode == null && position > begin) {
                //还有一种情况就是：比对到一半但是没有比对完全，没有全部对上，比如赌博，赌它干嘛，只匹配了一个赌字，第二个就不匹配了
                //需要拼接到stringBuilder中
                stringBuilder.append(text.substring(begin, position) + character);
                begin = ++position;
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                //如果获取到的结果不为空并且在前缀树中的位置是最后一个字符，替换字符
                for (int i = 0; i < position - begin + 1; i++) stringBuilder.append("*");
                //进入到下一个非敏感词的范围职位
                begin = ++position;
                tempNode = rootNode;
            } else {
                //最后一种情况就是收到结果不为空但是不是前缀树最后一个节点
                position++;
            }
        }
        //如果最后 begin 还在范围之内，表示从 begin 开始的字符不是组合不成敏感词，直接放到stringBuilder中去
        if (begin < text.length()) stringBuilder.append(text.substring(begin));
        return stringBuilder.toString();
    }

    //判断是否是字符，如果是字符就往前面判断
    public boolean isSymbol(Character character) {
        //当前字符不在A-Za-z并且不是东亚文字就跳过
        return !CharUtils.isAsciiAlphanumeric(character) && (character < 0x2E80 || character > 0x9FFF);
    }

    private class TrieNode {
        //是否为最后一个字符
        private boolean isKeywordEnd = false;
        //子节点集合
        Map<Character, TrieNode> subNodes = new HashMap<>();

        //获取是否为最后一个字符
        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        //设置是否为最后一个字符
        public void setKeywordEnd(boolean isKeywordEnd) {
            this.isKeywordEnd = isKeywordEnd;
        }

        //添加子节点
        public void addSubNode(Character character, TrieNode subNode) {
            subNodes.put(character, subNode);
        }

        //获取子节点
        public TrieNode getSubNode(Character character) {
            return subNodes.get(character);
        }
    }
}
