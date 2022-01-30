package com.example.productservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.product.Product;
import com.example.api.product.ProductService;
import com.example.productservice.persistence.ProductEntity;
import com.example.productservice.persistence.ProductMapper;
import com.example.productservice.persistence.ProductRepository;

import se.magnus.util.exceptions.InvalidInputException;
import se.magnus.util.exceptions.NotFoundException;
import se.magnus.util.http.ServiceUtil;

@RestController
public class ProductServiceImpl implements ProductService {

  private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
  private final ProductRepository repository;
  private final ServiceUtil util;
  private final ProductMapper mapper;

  @Autowired
  public ProductServiceImpl(ProductRepository repository, ServiceUtil util, ProductMapper mapper) {
    this.repository = repository;
    this.util = util;
    this.mapper = mapper;
  }

  @Override
  public Product getProduct(int productId) {
    if (productId <= 0)
      throw new InvalidInputException("invalid productId parameter");

    ProductEntity entity = this.repository.findByProductId(productId)
        .orElseThrow(() -> new NotFoundException("not product with id" + productId + " found"));
    Product product = mapper.entityToApi(entity);
    product.setServiceAddress(util.getServiceAddress());
    return product;
  }

  @Override
  public Product createProduct(Product body) {
    ProductEntity saved = repository.save(mapper.apiToEntity(body));
    LOG.debug("product entity created. ProductID: {}", saved.getProductId());
    return mapper.entityToApi(saved);
  }

  @Override
  public void deleteProduct(int productId) {
    ProductEntity entity = repository.findByProductId(productId)
        .orElseThrow(() -> new NotFoundException("PRoduct with productId: " + productId + " not found"));
    repository.delete(entity);

  }

}
