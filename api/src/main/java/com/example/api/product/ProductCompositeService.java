package com.example.api.product;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "REST API for composite product information.")
public interface ProductCompositeService {
  
    @ApiOperation(
        value = "${api.product-composite.get-product.description}", 
        notes = "${api.product-composite.get-product.notes}")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request. Invalid input parameter"), 
        @ApiResponse(code=404, message = "Not Found.") ,
        @ApiResponse(code=422, message = "Unprocessable Entity, input parameters caused processing to fails. See response message for more information") }
    )
    @GetMapping(
        value    = "/product-composite/{productId}",
        produces = "application/json")
    ProductAggregate getProduct(@PathVariable int productId);
    
    
    @ApiOperation(
        value = "${api.product-composite.create-product.description}",
        notes = "${api.product-composite.create-product.notes}"
        )
    @ApiResponses(
        value = {
            @ApiResponse(code = 400, message="Bad Request. Invalid input parameters"),
            @ApiResponse(code = 422, message="Unprocessable Entity , input parameters caused  processing to failes")
            })
    @PostMapping(
        value = "/product-composite",
        produces = "application/json"
       )
    void createProduct(@RequestBody ProductAggregate product);
    
    @ApiOperation(
        value = "${api.product-composite.delete-product.description}",
        notes = "${api.product-composite.delete-product.notes}"
        )
    @ApiResponses(
        value = {
            @ApiResponse(code = 400, message="Bad Request. Invalid input parameters"),
            @ApiResponse(code = 422, message="Unprocessable Entity , input parameters caused  processing to failes")
            })
    @DeleteMapping(
        value = "/product-composite/{productId}",
        produces = "application/json"
        )
    void deleteProduct(@PathVariable int productId);
}