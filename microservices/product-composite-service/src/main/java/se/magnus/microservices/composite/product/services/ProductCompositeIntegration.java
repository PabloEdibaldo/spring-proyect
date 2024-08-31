package se.magnus.microservices.composite.product.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import se.magnus.api.core.product.Product;
import se.magnus.api.core.product.ProductService;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.api.core.recommendation.RecommendationService;
import se.magnus.api.core.review.Review;
import se.magnus.api.core.review.ReviewService;
import org.slf4j.Logger;
import se.magnus.api.exceptions.InvalidInputException;
import se.magnus.api.exceptions.NotFoundException;
import se.magnus.util.http.HttpErrorInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpMethod.GET;

@Component
public class ProductCompositeIntegration implements  ProductService,ReviewService,RecommendationService{

    private static  final Logger LOG = LoggerFactory.getLogger(ProductCompositeIntegration.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    public ProductCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,
            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") int productServicePort,
            @Value("${app.recommendation-service.host}") String recommendationServiceHost,
            @Value("${app.recommendation-service.port}") int recommendationServicePort,
            @Value("${app.review-service.host}") String reviewServiceHost,
            @Value("${app.review-service.port}") int reviewServicePort
    ){
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        productServiceUrl = "http://"+productServiceHost+":"+productServicePort+"/product/";
        recommendationServiceUrl = "http://"+recommendationServiceHost+":"+recommendationServicePort+"/recommendation?productId=";
        reviewServiceUrl = "http://"+reviewServicePort+":"+ reviewServiceHost+"/review?productId=";
    }


    @Override
    public Product getProduct(int productId) {
        try{
            String url = productServiceUrl + productId;
            LOG.debug("Will call getProduct Api on URL:{}",url);

            Product product = restTemplate.getForObject(url,Product.class);
            LOG.debug("Found a product with id: {}",product.getProductId());

            return product;

        }catch (HttpClientErrorException e){
            switch (Objects.requireNonNull(HttpStatus.resolve(e.getStatusCode().value()))){
                case NOT_FOUND -> {throw new NotFoundException(getErrorMessage(e));}
                case UNPROCESSABLE_ENTITY -> { throw new InvalidInputException(getErrorMessage(e));}
                default -> {
                    LOG.warn("Got an unexpected HTTP error: {} rethrow it",e.getStatusCode());
                    LOG.warn("Error body: {}",e.getResponseBodyAsString());
                    throw e;
                }
            }
        }
    }

    private String getErrorMessage(HttpClientErrorException e){
        try {
            return  mapper.readValue(e.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }catch (IOException ioex){
            return e.getMessage();
        }
    }


    @Override
    public List<Recommendation> getRecommendations(int productId) {
        try{
            String url = recommendationServiceUrl + productId;

            LOG.debug("will call getRecommendations API in URL:{}",url);
            List<Recommendation> recommendations = restTemplate
                    .exchange(
                            url,
                            GET,
                            null,
                            new ParameterizedTypeReference<List<Recommendation>>() {}).getBody();

            LOG.debug("Found {} recommendations for a product with id: {}", recommendations.size(),productId);
            return recommendations;

        }catch (Exception e){
            LOG.warn("Got an exception while requesting recommendations, return zero recommendations: {}",e.getMessage());
            return new ArrayList<>();
        }
    }


    @Override
    public List<Review> getReviews(int productId) {
        try{
            String url = reviewServiceUrl + productId;

            LOG.debug("Will call getReviews API on URL: {}", url);
            List<Review> reviews = restTemplate
                    .exchange(
                            url,
                            GET,
                            null,
                            new ParameterizedTypeReference<List<Review>>() {}
                    ).getBody();
            assert reviews != null;
            LOG.debug("Found {} reviews for a product with id: {}",reviews.size(),productId);
            return reviews;
        }catch (Exception e){
            LOG.warn("Got an exception while requesting reviews, return zero reviews: {}",e.getMessage());
            return new ArrayList<>();
        }
    }


}

