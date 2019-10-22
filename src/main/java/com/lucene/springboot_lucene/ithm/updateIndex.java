package com.lucene.springboot_lucene.ithm;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import java.io.File;
import java.io.IOException;

public class updateIndex {
    public static void main(String[] args) throws IOException {
        // 1. 指定索引库存放的路径
        Directory directory = FSDirectory.open(new File("F:/lucene").toPath());
        //索引库还可以存放到内存中
        //Directory directory = new RAMDirectory();

        // 2. 创建indexWriterConfig对象
        IndexWriterConfig config = new IndexWriterConfig(new SmartChineseAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory,config);

        // 3. 创建一个Document对象
        Document document = new Document();

        //向document对象中添加域。
        //不同的document可以有不同的域，同一个document可以有相同的域。
        document.add(new TextField("name", "要更新的文档", Field.Store.YES));
        document.add(new TextField("content", " Lucene 简介 Lucene 是一个基于 Java 的全文信息检索工具包," +
                "它不是一个完整的搜索应用程序,而是为你的应用程序提供索引和搜索功能。",
                Field.Store.YES));
        indexWriter.updateDocument(new Term("content", "java"), document);
        //关闭indexWriter
        indexWriter.close();

    }
}
