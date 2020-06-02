package com.ltri.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author ltri
 * @date 2020/5/25 3:24 下午
 */
@Data
@Document(indexName = "goods_v1", type = "_doc", shards = 3, replicas = 1)
public class Goods {
    @Field(type = FieldType.Long)
    private Long id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Long)
    private Long stock;
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String description;
    @Field(type = FieldType.Date)
    private Date createTime;
}
