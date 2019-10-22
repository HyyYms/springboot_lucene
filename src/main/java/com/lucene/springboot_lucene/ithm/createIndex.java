package com.lucene.springboot_lucene.ithm;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

public class createIndex {
    public static void main(String[] args) throws IOException {
        // 1. 指定索引库存放的路径
        Directory directory = FSDirectory.open(new File("F:/lucene").toPath());
        //索引库还可以存放到内存中
        //Directory directory = new RAMDirectory();

        // 2. 创建indexWriterConfig对象
        IndexWriterConfig config = new IndexWriterConfig(new SmartChineseAnalyzer());

        // 3. 创建indexWriter对象
        IndexWriter indexWriter = new IndexWriter(directory,config);

        // 4. 原文档的路径
        File dir = new File("E:/BaiduNetdiskDownload/lucene/02.参考资料/searchsource");
        for (File file : dir.listFiles()) {
            // 文件名
            String fileName = file.getName();
            // 文件内容
            String fileContent = FileUtils.readFileToString(file);
            // 文件路径
            String filePath = file.getPath();
            // 文件的大小
            long fileSize = FileUtils.sizeOf(file);

            // 创建域
            Field fileNameField = new TextField("name",fileName, Field.Store.YES);
            Field fileContentField = new TextField("content",fileContent, Field.Store.YES);
            Field filePathField = new TextField("path",filePath, Field.Store.YES);
            Field fileSizeField = new TextField("size",fileSize+"", Field.Store.YES);

            // 创建Document对象
            Document document = new Document();
            document.add(fileNameField);
            document.add(fileContentField);
            document.add(filePathField);
            document.add(fileSizeField);

            // 创建索引，并写入索引库
            indexWriter.addDocument(document);
        }
        // 关闭indexWriter
        indexWriter.close();
    }
}
