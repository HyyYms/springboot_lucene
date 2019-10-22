package com.lucene.springboot_lucene.ithm;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

public class searchIndex {
    public static void main(String[] args) throws IOException {
        // 1. 指定索引库存放的路径
        Directory directory = FSDirectory.open(new File("F:/lucene").toPath());

        // 2. 创建indexReader对象
        IndexReader indexReader = DirectoryReader.open(directory);

        // 3. 创建indexSearcher对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        // 4. 创建查询
        Query query = new TermQuery(new Term("name","文档"));
        //执行查询
        //第一个参数是查询对象，第二个参数是查询结果返回的最大值
        TopDocs topDocs = indexSearcher.search(query, 10);
        //查询结果的总条数
        System.out.println("查询结果的总条数："+ topDocs.totalHits);
        //遍历查询结果
        //topDocs.scoreDocs存储了document对象的id
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //scoreDoc.doc属性就是document对象的id
            //根据document的id找到document对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println(document.get("name"));
            System.out.println(document.get("content"));
            System.out.println(document.get("path"));
            System.out.println(document.get("size"));
            System.out.println("-------------------------");
        }
        //关闭indexreader对象
        indexReader.close();
    }
}
