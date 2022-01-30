package com.example.review.service;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.example.api.review.Review;
import com.example.review.persistence.ReviewEntity;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

  @Mappings(
      {
        @Mapping(target = "id", ignore=true),
        @Mapping(target = "version", ignore=true)
      }
      )
  public ReviewEntity apiToEntity(Review api);
  public List<ReviewEntity> apiToEntity(List<Review> api);
  
  @Mappings({ @Mapping(target = "serviceAddress", ignore = true) })
  public Review entityToApi(ReviewEntity api);
  public List<Review> entityToApi(List<ReviewEntity> api);
}
