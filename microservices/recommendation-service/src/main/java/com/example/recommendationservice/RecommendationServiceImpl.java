package com.example.recommendationservice;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.recommendation.Recommendation;
import com.example.api.recommendation.RecommendationService;
import com.example.recommendationservice.persistence.RecommendationEntity;
import com.example.recommendationservice.persistence.RecommendationRepository;

import se.magnus.util.exceptions.InvalidInputException;
import se.magnus.util.http.ServiceUtil;

@RestController
public class RecommendationServiceImpl implements RecommendationService {

  private static final Logger LOG = LoggerFactory.getLogger(RecommendationServiceImpl.class);

  private final RecommendationRepository repository;
  private final ServiceUtil serviceUtil;
  private final RecommendationMapper mapper;

  @Autowired
  public RecommendationServiceImpl(ServiceUtil serviceUtil, RecommendationRepository repository,
      RecommendationMapper mapper) {
    this.serviceUtil = serviceUtil;
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public List<Recommendation> getRecommendations(int productId) {

    if (productId < 1)
      throw new InvalidInputException("Invalid productId: " + productId);

    List<Recommendation> list = mapper.entityToApi(repository.findByProductId(productId));
    list.forEach(r -> r.setServiceAddress(serviceUtil.getServiceAddress()));
    LOG.debug("/recommendation response size: {}", list.size());

    return list;
  }

  @Override
  public Recommendation createRecommendation(Recommendation body) {
    RecommendationEntity saved = this.repository.save(mapper.apiToEntity(body));
    LOG.debug("Recommendation created for productId : {}. AUthor: {}, content: {}", saved.getProductId(),
        saved.getAuthor(), saved.getContent());
    return mapper.entityToApi(saved);
  }

  @Override
  public void deleteRecommendations(int productId) {
    repository.deleteAll(repository.findByProductId(productId));
  }

}
