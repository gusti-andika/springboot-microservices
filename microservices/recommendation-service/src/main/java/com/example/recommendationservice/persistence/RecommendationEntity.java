package com.example.recommendationservice.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "recommendations")
@CompoundIndex(unique = true, def = "{'productId':1, 'recommendationId':1}")
public class RecommendationEntity {
  @Id
  private String id;
  @Version
  private Integer version;

  private int productId;
  private int recommendationId;
  private String author;
  private int rating;
  private String content;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public int getRecommendationId() {
    return recommendationId;
  }

  public void setRecommendationId(int recommendationId) {
    this.recommendationId = recommendationId;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public RecommendationEntity() {

  }

  public RecommendationEntity(int productId, int recomId, String author, int rating, String content) {
    this.productId = productId;
    this.recommendationId = recomId;
    this.author = author;
    this.rating = rating;
    this.content = content;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("RecommendationEntity [id=");
    builder.append(id);
    builder.append(", version=");
    builder.append(version);
    builder.append(", productId=");
    builder.append(productId);
    builder.append(", recommendationId=");
    builder.append(recommendationId);
    builder.append(", author=");
    builder.append(author);
    builder.append(", rating=");
    builder.append(rating);
    builder.append(", content=");
    builder.append(content);
    builder.append("]");
    return builder.toString();
  }

  
}
