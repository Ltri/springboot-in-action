package com.ltri.elasticsearch;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.ltri.elasticsearch.entity.Book;
import com.ltri.elasticsearch.service.IBookService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElasticsearchApplicationTests {
    @Autowired
    private IBookService bookService;

    @Test
    void contextLoads() {
        List<Book> list = Lists.newLinkedList();
        for (int i = 0; i < 100; i++) {
            Book book = new Book();
            book.setTitle(RandomStringUtils.random(10, true, false));
            book.setAuthors(RandomStringUtils.random(5, true, false));
            book.setSummary(RandomStringUtils.random(50, true, false));
            book.setReviews(RandomUtils.nextLong(3,6));
            //book.setReleaseTime(LocalDateTime.now().plusDays(i).plusMonths(i));
            //book.setCreateTime(LocalDateTime.now());
            list.add(book);
        }
        bookService.saveBatch(list);
    }

    @Test
    void test() {
        bookService.list().forEach(System.out::println);
    }

}
