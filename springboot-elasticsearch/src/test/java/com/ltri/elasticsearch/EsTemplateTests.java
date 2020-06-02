package com.ltri.elasticsearch;

import com.ltri.elasticsearch.entity.Book;
import com.ltri.elasticsearch.entity.Goods;
import com.ltri.elasticsearch.service.BookService;
import com.ltri.elasticsearch.service.GoodsService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ltri
 * @date 2020/5/25 1:11 上午
 */

@SpringBootTest
public class EsTemplateTests {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private BookService bookService;
    @Autowired
    private GoodsService goodsService;


    @Test
    void createIndex() {
        elasticsearchRestTemplate.createIndex(Goods.class);
        elasticsearchRestTemplate.putMapping(Goods.class);
        AliasQuery aliasQuery = new AliasQuery();
        aliasQuery.setIndexName("goods_v1");
        aliasQuery.setAliasName("goods");
        elasticsearchRestTemplate.addAlias(aliasQuery);
    }

    @Test
    void search() {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("reviews", 4))).build();
        List<Book> list = elasticsearchRestTemplate.queryForList(query, Book.class);
        list.forEach(System.out::println);
    }

    @Test
    void batchAdd() {
        List<Book> bookList = bookService.list();
        List<IndexQuery> list = new ArrayList<>();
        for (Book book : bookList) {
            list.add(new IndexQueryBuilder().withObject(book).build());
        }
        elasticsearchRestTemplate.bulkIndex(list);
    }

    @Test
    void add() {
        List<Goods> goodsList = goodsService.list();
        List<IndexQuery> list = new ArrayList<>();
        for (Goods goods : goodsList) {
            list.add(new IndexQueryBuilder().withObject(goods).build());
        }
        elasticsearchRestTemplate.bulkIndex(list);
    }

    @Test
    void pageSearch(){
        SearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("description", "测试")))
                .withSort(SortBuilders.fieldSort("stock").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 2)).build();

        Page<Goods> goods = elasticsearchRestTemplate.queryForPage(nativeSearchQuery, Goods.class);
        for (Goods goods1 : goods) {
            System.out.println("goods1 = " + goods1);
        }
    }

    @Test
    void aggregation(){
    }

}
