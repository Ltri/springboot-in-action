package com.ltri.elasticsearch.service.impl;

import com.ltri.elasticsearch.service.HtmlParseService;
import org.elasticsearch.client.RestHighLevelClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

/**
 * @author ltri
 * @date 2020/5/25 12:42 上午
 */
@Service
public class HtmlParseServiceImpl implements HtmlParseService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void importJdData(String keyword) throws IOException {
        String url = "https://search.jd.com/Search?keyword=" + keyword;

        Document document = Jsoup.parse(new URL(url), 30000);

        Element goodsList = document.getElementById("J_goodsList");

        Elements elements = goodsList.getElementsByTag("li");

        for (Element element : elements) {
            System.out.println(element);
            //String price = element.getElementsByTag("p-price").eq(0).text();
            //String title = element.getElementsByTag("p-name").eq(0).text();
            //System.out.println(price);
            //System.out.println(title);
        }
    }
}
