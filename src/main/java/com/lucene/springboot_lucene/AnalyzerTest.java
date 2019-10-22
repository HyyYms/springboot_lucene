package com.lucene.springboot_lucene;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;

import java.io.IOException;

public class AnalyzerTest {
    public static void main(String[] args) throws IOException {
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("name","黄裕煜牛逼");
        tokenStream.reset();
        while(tokenStream.incrementToken()) {
            System.out.println(tokenStream.reflectAsString(false));
        }
    }
}
