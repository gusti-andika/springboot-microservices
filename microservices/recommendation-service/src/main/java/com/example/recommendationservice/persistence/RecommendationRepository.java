package com.example.recommendationservice.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RecommendationRepository extends CrudRepository<RecommendationEntity , String> {

  public List<RecommendationEntity> findByProductId(int productId);
}
