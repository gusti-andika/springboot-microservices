package com.example.productservice.persistence;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.example.api.product.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mappings(value = { @Mapping(target = "id", ignore = true), @Mapping(target = "version", ignore = true) })
  public ProductEntity apiToEntity(Product product);

  @Mappings(value = { @Mapping(target = "serviceAddress", ignore = true) })
  public Product entityToApi(ProductEntity entity);
}
