package com.example.review.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.review.Review;
import com.example.api.review.ReviewService;
import com.example.review.persistence.ReviewEntity;
import com.example.review.persistence.ReviewRepository;

import se.magnus.util.exceptions.InvalidInputException;
import se.magnus.util.http.ServiceUtil;

@RestController
public class ReviewServiceImpl implements ReviewService {

  private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);

  private final ServiceUtil serviceUtil;
  private final ReviewMapper mapper;
  private final ReviewRepository repository;

  @Autowired
  public ReviewServiceImpl(ServiceUtil serviceUtil, ReviewMapper mapper, ReviewRepository repository) {
    this.serviceUtil = serviceUtil;
    this.mapper = mapper;
    this.repository = repository;
  }

  @Override
  public List<Review> getReviews(int productId) {

    if (productId < 1)
      throw new InvalidInputException("Invalid productId: " + productId);

    List<ReviewEntity> list = repository.findByProductId(productId);
    List<Review> dtoList = mapper.entityToApi(list);
    dtoList.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));

    LOG.debug("getReviews: response size {}", dtoList.size());
    return dtoList;
  }

  @Override
  public Review createReview(Review body) {
    ReviewEntity entity = mapper.apiToEntity(body);
    ReviewEntity newEntity = repository.save(entity);

    LOG.debug("create review: {}/{}", body.getProductId(), body.getReviewId());
    return mapper.entityToApi(newEntity);
  }

  @Override
  public void deleteReviews(int productId) {
    LOG.debug("try to delete review for product id: {}", productId);
    repository.deleteAll(repository.findByProductId(productId));
  }

}
