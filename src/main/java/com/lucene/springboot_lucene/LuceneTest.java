package com.lucene.springboot_lucene;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LuceneTest {
    public static void main(String[] args) throws Exception {
        // 1.准备中文分词器
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

        // 2.索引
        Directory index = createIndex(analyzer);
/*        List<String> productNames = new ArrayList<>();
        productNames.add("飞利浦led灯泡e27螺口暖白球泡灯家用照明超亮节能灯泡转色温灯泡");
        productNames.add("飞利浦led灯泡e14螺口蜡烛灯泡3W尖泡拉尾节能灯泡暖黄光源Lamp");
        productNames.add("雷士照明 LED灯泡 e27大螺口节能灯3W球泡灯 Lamp led节能灯泡");
        productNames.add("飞利浦 led灯泡 e27螺口家用3w暖白球泡灯节能灯5W灯泡LED单灯7w");
        productNames.add("飞利浦led小球泡e14螺口4.5w透明款led节能灯泡照明光源lamp单灯");
        productNames.add("飞利浦蒲公英护眼台灯工作学习阅读节能灯具30508带光源");
        productNames.add("欧普照明led灯泡蜡烛节能灯泡e14螺口球泡灯超亮照明单灯光源");
        productNames.add("欧普照明led灯泡节能灯泡超亮光源e14e27螺旋螺口小球泡暖黄家用");
        productNames.add("聚欧普照明led灯泡节能灯泡e27螺口球泡家用led照明单灯超亮光源");
        Directory index = createIndex(analyzer,productNames);*/

        // 3.查询器
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入查询关键字：");
            String keyword = scanner.nextLine();
            System.out.println("当前关键字是："+keyword);
            Query query = new QueryParser( "name", analyzer).parse(keyword);

            // 4. 搜索
            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher=new IndexSearcher(reader);
            int numberPerPage = 10;
            ScoreDoc[] hits = searcher.search(query, numberPerPage).scoreDocs;

            // 5. 显示查询结果
            showSearchResults(searcher, hits,query,analyzer);
            // 6. 关闭查询
            reader.close();
        }
        /*String keyword = "护眼带光源";
        Query query = new QueryParser("name",analyzer).parse(keyword);*/

        // 4.搜索
/*        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        int numberPerpage = 1000;
        System.out.println("总记录数"+productNames.size());
        System.out.println("查询的关键字是"+keyword);
        ScoreDoc[] hits = searcher.search(query,numberPerpage).scoreDocs;

        // 5.显示查询结果
        showSearchResults(searcher,hits,query,analyzer);

        // 6.关闭查询
        reader.close();*/
    }

    private static void showSearchResults(IndexSearcher searcher, ScoreDoc[] hits, Query query, SmartChineseAnalyzer analyzer) throws Exception {
        System.out.println("命中"+hits.length);
        for (int i=0; i<hits.length; ++i) {
            ScoreDoc scoreDoc = hits[i];
            int docId = scoreDoc.doc;
            Document d = searcher.doc(docId);
            List<IndexableField> fields = d.getFields();
            System.out.print((i + 1));
            System.out.print("\t" + scoreDoc.score);
            for (IndexableField f : fields) {
                System.out.print("\t" + d.get(f.name()));
            }
            System.out.println();
        }
    }

    private static Directory createIndex(SmartChineseAnalyzer analyzer) throws IOException {
        Directory index = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(index, config);
        String fileName = "140k_products.txt";
        List<Product> products = ProductUtil.file2list(fileName);
        int total = products.size();
        int count = 0;
        int per = 0;
        int oldPer =0;
        for (Product p : products) {
            addDoc(writer, p);
            count++;
            per = count*100/total;
            if(per!=oldPer){
                oldPer = per;
                System.out.printf("索引中，总共要添加 %d 条记录，当前添加进度是： %d%% %n",total,per);
            }

        }
        writer.close();
        return index;
/*        for (String name : productNames) {
            addDoc(writer, name);
        }
        writer.close();
        return index;*/
    }

    private static void addDoc(IndexWriter writer, Product product) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("id", String.valueOf(product.getId()), Field.Store.YES));
        doc.add(new TextField("name", product.getName(), Field.Store.YES));
        doc.add(new TextField("category", product.getCategory(), Field.Store.YES));
        doc.add(new TextField("price", String.valueOf(product.getPrice()), Field.Store.YES));
        doc.add(new TextField("place", product.getPlace(), Field.Store.YES));
        doc.add(new TextField("code", product.getCode(), Field.Store.YES));
        writer.addDocument(doc);
    }
}
