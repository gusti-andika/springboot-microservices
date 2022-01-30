package com.example.productcompositeservice;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.product.Product;
import com.example.api.product.ProductAggregate;
import com.example.api.product.ProductCompositeService;
import com.example.api.product.RecommendationSummary;
import com.example.api.product.ReviewSummary;
import com.example.api.product.ServiceAddresses;
import com.example.api.recommendation.Recommendation;
import com.example.api.review.Review;

import se.magnus.util.http.ServiceUtil;

@RestController
public class ProductCompositeServiceImpl implements ProductCompositeService {

	private ProductCompositeIntegration integration;
	private ServiceUtil serviceUtil;
	private static final Logger LOG  = LoggerFactory.getLogger(ProductCompositeServiceImpl.class);

	@Autowired
	public ProductCompositeServiceImpl(ProductCompositeIntegration integration, ServiceUtil serviceUtil) {
		this.integration = integration;
		this.serviceUtil = serviceUtil;
		LOG.debug("product-composite started...");
	}

	@Override
	public ProductAggregate getProduct(int productId) {
		Product product = integration.getProduct(productId);
		List<Recommendation> recommendations = integration.getRecommendations(productId);
		List<Review> reviews = integration.getReviews(productId);

		return createProductAggregate(product, recommendations, reviews, serviceUtil.getServiceAddress());
	}

	private ProductAggregate createProductAggregate(Product product, List<Recommendation> recommendations,
			List<Review> reviews, String serviceAddress) {

		// 1. Setup product info
		int productId = product.getProductId();
		String name = product.getName();
		int weight = product.getWeight();

		// 2. Copy summary recommendation info, if available
		List<RecommendationSummary> recommendationSummaries = (recommendations == null) ? null
				: recommendations.stream()
						.map(r -> new RecommendationSummary(r.getRecommendationId(), r.getAuthor(), r.getRate(), r.getContent()))
						.collect(Collectors.toList());

		// 3. Copy summary review info, if available
		List<ReviewSummary> reviewSummaries = (reviews == null) ? null
				: reviews.stream().map(r -> new ReviewSummary(r.getReviewId(), r.getAuthor(), r.getSubject(), r.getContent()))
						.collect(Collectors.toList());

		// 4. Create info regarding the involved microservices addresses
		String productAddress = product.getServiceAddress();
		String reviewAddress = (reviews != null && reviews.size() > 0) ? reviews.get(0).getServiceAddress() : "";
		String recommendationAddress = (recommendations != null && recommendations.size() > 0)
				? recommendations.get(0).getServiceAddress()
				: "";
		ServiceAddresses serviceAddresses = new ServiceAddresses(serviceAddress, productAddress, reviewAddress,
				recommendationAddress);

		return new ProductAggregate(productId, name, weight, recommendationSummaries, reviewSummaries,
				serviceAddresses);
	}

  @Override
  public void createProduct(ProductAggregate body) {
    Product product = new Product(body.getProductId(),body.getName(), body.getWeight(), null);
    integration.createProduct(product);
    
    if (body.getRecommendations() != null) {
      body.getRecommendations().forEach(r -> {
        Recommendation obj = new Recommendation(body.getProductId(), 
            r.getRecommendationId() , 
            r.getAuthor(), 
            r.getRate(), r.getContent(), null);
        integration.createRecommendation(obj);
      });
    }
    
    if (body.getReviews() != null) {
      body.getReviews().forEach(r -> {
        Review review = new Review(body.getProductId(), r.getReviewId(), r.getAuthor(), r.getSubject(), r.getContent(), null);
        integration.createReview(review);
      });
    }
    
  }

  @Override
  public void deleteProduct(int productId) {
    integration.deleteProduct(productId);
    integration.deleteRecommendations(productId);
    integration.deleteReviews(productId);
    
  }

}
