package com.example.productcompositeservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.api.product.Product;
import com.example.api.product.ProductService;
import com.example.api.recommendation.Recommendation;
import com.example.api.recommendation.RecommendationService;
import com.example.api.review.Review;
import com.example.api.review.ReviewService;

@Component
public class ProductCompositeIntegration implements ProductService, ReviewService, RecommendationService {

  private RestTemplate restTemplate;
  private String productServiceUrl;
  private String reviewServiceUrl;
  private String recommendationServiceUrl;

  @Autowired
  public ProductCompositeIntegration(RestTemplate template, @Value("${app.product-service.host}") String productHost,
      @Value("${app.product-service.port}") int productPort, @Value("${app.review-service.host}") String reviewHost,
      @Value("${app.review-service.port}") int reviewPort,
      @Value("${app.recommendation-service.host}") String recommendationHost,
      @Value("${app.recommendation-service.port}") int recommendationPort) {
    this.restTemplate = template;
    this.productServiceUrl = "http://" + productHost + ":" + productPort + "/product";
    this.reviewServiceUrl = "http://" + reviewHost + ":" + reviewPort + "/review";
    this.recommendationServiceUrl = "http://" + recommendationHost + ":" + recommendationPort + "/recommendation";
  }

  @Override
  public List<Recommendation> getRecommendations(int productId) {
    return restTemplate.exchange(recommendationServiceUrl + "?productId=" + productId, HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Recommendation>>() {
        }).getBody();
  }

  @Override
  public List<Review> getReviews(int productId) {
    return restTemplate.exchange(reviewServiceUrl + "?productId=" + productId, HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Review>>() {
        }).getBody();
  }

  @Override
  public Product getProduct(int productId) {
    Product product = restTemplate.getForObject(productServiceUrl + "/" + productId, Product.class);
    return product;
  }

  @Override
  public Recommendation createRecommendation(Recommendation body) {
    return restTemplate.postForObject(recommendationServiceUrl , body, Recommendation.class);
  }

  @Override
  public void deleteRecommendations(int productId) {
    restTemplate.delete(recommendationServiceUrl + "?productId=" + productId);

  }

  @Override
  public Review createReview(Review body) {
    return restTemplate.postForObject(reviewServiceUrl, body, Review.class);
  }

  @Override
  public void deleteReviews(int productId) {
    restTemplate.delete(reviewServiceUrl + "?productId=" + productId);

  }

  @Override
  public Product createProduct(Product body) {
    return restTemplate.postForObject(productServiceUrl, body, Product.class);
  }

  @Override
  public void deleteProduct(int productId) {
    restTemplate.delete(productServiceUrl + "/" + productId);
  }

}
