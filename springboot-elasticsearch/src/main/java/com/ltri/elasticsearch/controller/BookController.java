package com.ltri.elasticsearch.controller;


import com.google.common.collect.Lists;
import com.ltri.elasticsearch.entity.Book;
import com.ltri.elasticsearch.repository.BookRepository;
import com.ltri.elasticsearch.service.IBookService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private IBookService bookService;

    @PutMapping("/book")
    public void save() {
        List<Book> books = bookService.list();
        bookRepository.saveAll(books);
    }

    @GetMapping("/book")
    public Object list() {
        return Lists.newArrayList(bookRepository.findAll());
    }

    @DeleteMapping("/book")
    public void remove() {
        bookRepository.deleteAll();
    }

    @GetMapping("/search")
    public Object search() {
        return Lists.newArrayList(bookRepository.search(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("reviews", 5))));
    }
}
