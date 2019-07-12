package com.shsxt.ego.search.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "ego",type = "goods")
public class GoodsVo {

    @Id
    @Field(index = FieldIndex.not_analyzed, type = FieldType.Long)
    private Long goodsId;

    @Field(type = FieldType.String, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String goodsName;

    @Field(type = FieldType.Long,searchAnalyzer = "ik", analyzer = "ik")
    private Long price;

    @Field(type = FieldType.String, searchAnalyzer = "ik", analyzer = "ik")
    private String image;


    private String goodsNameH;

    public String getGoodsNameH() {
        return goodsNameH;
    }

    public void setGoodsNameH(String goodsNameH) {
        this.goodsNameH = goodsNameH;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "GoodsVo{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
