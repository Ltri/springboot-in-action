package com.ltri.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.ltri.elasticsearch.entity.Book;
import com.ltri.elasticsearch.entity.User;
import com.ltri.elasticsearch.service.BookService;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author ltri
 * @date 2020/5/25 12:47 上午
 */
@SpringBootTest
public class RestHighLevelClientTest {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private BookService bookService;


    /**
     * 创建索引
     */
    @Test
    void createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("book_v1");
        // setting 设置
        request.settings(Settings.builder()
                .put("number_of_shards", 5)
                .put("number_of_replicas", 1)
        );
        // mapping设置
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("id");
                {
                    builder.field("type", "long");
                }
                builder.endObject();
                builder.startObject("title");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "ik_smart");
                }
                builder.endObject();
                builder.startObject("authors");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();
                builder.startObject("summary");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "ik_smart");
                }
                builder.endObject();
                builder.startObject("reviews");
                {
                    builder.field("type", "long");
                }
                builder.endObject();
                builder.startObject("releaseTime");
                {
                    builder.field("type", "date");
                }
                builder.endObject();
                builder.startObject("createTime");
                {
                    builder.field("type", "date");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.mapping(builder);

        //别名设置
        request.alias(new Alias("book"));

        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    @Test
    void getDocById() throws IOException {
        GetRequest request = new GetRequest("test", "_doc", "1");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSource());
    }

    /**
     * 添加文档数据
     */
    @Test
    void addDoc() throws IOException {
        User user = new User(1L, "zhangsan");
        IndexRequest request = new IndexRequest("test");
        request.id("1");
        request.timeout("1s");
        request.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    /**
     * 批量添加
     */
    @Test
    void batchAdd() throws IOException {
        List<Book> bookList = bookService.list();
        BulkRequest request = new BulkRequest();
        for (Book book : bookList) {
            request.add(new IndexRequest("book").source(JSON.toJSONString(book), XContentType.JSON));
        }
        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 查询
     */
    @Test
    void search() throws IOException {
        SearchRequest request = new SearchRequest("test");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "zhangsan");

        boolQueryBuilder.must(matchQueryBuilder);

        searchSourceBuilder.query(boolQueryBuilder);

        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);
        searchSourceBuilder.sort("id", SortOrder.ASC);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        highlightBuilder.preTags("<h1>");
        highlightBuilder.postTags("</h1>");
        searchSourceBuilder.highlighter(highlightBuilder);

        request.source(searchSourceBuilder);

        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println(search.toString());
        System.out.println(Arrays.toString(search.getHits().getHits()));
    }
}
