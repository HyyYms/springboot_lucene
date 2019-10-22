package com.lucene.springboot_lucene.ithm;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

public class deleteAllIndex {
    public static void main(String[] args) throws IOException {
        // 1. 指定索引库存放的路径
        Directory directory = FSDirectory.open(new File("F:/lucene").toPath());
        //索引库还可以存放到内存中
        //Directory directory = new RAMDirectory();

        // 2. 创建indexWriterConfig对象
        IndexWriterConfig config = new IndexWriterConfig(new SmartChineseAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory,config);
        //删除全部索引
        indexWriter.deleteAll();
        //关闭indexwriter
        indexWriter.close();

    }
}
