package com.example.productcompositeservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import java.util.Collections;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"se.magnus.util", "com.example"})
public class ProductCompositeApp {

	public static void main(String[] args) {
		SpringApplication.run(ProductCompositeApp.class, args);
	}

	@Value("${api.common.title}")
  String apiTitle;
	@Value("${api.common.description}")
  String apiDesc;
	@Value("${api.common.version}")
  String apiVersion;
	@Value("${api.common.termsOfServiceUrl}")
  String apiTermsOfServiceUrl;
	@Value("${api.common.contact.name}")
  String apiContactName;
	@Value("${api.common.license}")
  String apiLicense;
	@Value("${api.common.licenseUrl}")
  String apiLicenseUrl;
	@Value("${api.common.contact.url}")
  String apiContactUrl;
	@Value("${api.common.contact.email}")
  String apiContactEmail;
	
	@Bean
	public Docket apiDocumentation() {
	   return new Docket(SWAGGER_2).select().apis(basePackage("com.example"))
	   .paths(PathSelectors.any()).build()
	   .globalResponseMessage(RequestMethod.POST  , Collections.emptyList())
	   .globalResponseMessage(RequestMethod.GET  , Collections.emptyList())
	   .globalResponseMessage(RequestMethod.DELETE  , Collections.emptyList())
	   .apiInfo(new ApiInfo(
	       apiTitle, 
	       apiDesc, 
	       apiVersion, 
	       apiTermsOfServiceUrl, 
	       new Contact(apiContactName, apiContactUrl, apiContactEmail), 
	       apiLicense, 
	       apiLicenseUrl, Collections.emptyList()));
	   
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
