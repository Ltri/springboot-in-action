package com.ltri.elasticsearch.service;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author ltri
 * @date 2020/5/25 12:41 上午
 */
public interface HtmlParseService {

    void importJdData(String keyword) throws IOException;
}
