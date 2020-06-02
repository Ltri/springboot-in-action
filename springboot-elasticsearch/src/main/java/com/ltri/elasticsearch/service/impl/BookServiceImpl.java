package com.ltri.elasticsearch.service.impl;

import com.ltri.elasticsearch.entity.Book;
import com.ltri.elasticsearch.mapper.BookMapper;
import com.ltri.elasticsearch.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ltri
 * @since 2020-04-17
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

}
