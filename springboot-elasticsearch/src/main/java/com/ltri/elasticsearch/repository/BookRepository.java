package com.ltri.elasticsearch.repository;

import com.ltri.elasticsearch.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ltri
 * @date 2020/4/17 1:45 下午
 */
@Repository
public interface BookRepository extends ElasticsearchRepository<Book,Long> {
}
