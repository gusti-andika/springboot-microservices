package com.example.recommendationservice;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.example.api.recommendation.Recommendation;
import com.example.recommendationservice.persistence.RecommendationEntity;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

  @Mappings({ @Mapping(target = "rate", source = "entity.rating"), @Mapping(target = "serviceAddress", ignore = true) })
  Recommendation entityToApi(RecommendationEntity entity);
  List<Recommendation> entityToApi(List<RecommendationEntity> entity);

  @Mappings({ @Mapping(target = "rating", source = "api.rate"), @Mapping(target = "id", ignore = true),
      @Mapping(target = "version", ignore = true) })
  RecommendationEntity apiToEntity(Recommendation api);
  List<RecommendationEntity> apiToEntity(List<Recommendation> api);
}
