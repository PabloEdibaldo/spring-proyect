package se.magnus.microservices.composite.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import src.main.java.se.magnus.api.compose.product.*;
import src.main.java.se.magnus.api.core.product.Product;
import src.main.java.se.magnus.api.core.product.ProductService;
import src.main.java.se.magnus.api.core.recommendation.Recommendation;
import src.main.java.se.magnus.api.core.review.Review;
import src.main.java.se.magnus.api.core.review.ReviewService;
import src.main.java.se.magnus.util.http.ServiceUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductCompositeServiceImpl implements ProductComposeService {
    private final ServiceUtil serviceUtil;
    private ProductCompositeIntegration integration;

    @Autowired
    public ProductCompositeServiceImpl(
            ServiceUtil serviceUtil,
            ProductCompositeIntegration integration
    ){
        this.serviceUtil = serviceUtil;
        this.integration = integration;
    }



    @Override
    public ProductAggregate getProduct(int productId) {
        Product product = integration.getProduct(productId);
        List<Recommendation> recommendations= integration.getRecommendations(productId);
        List<Review> reviews = integration.getReviews(productId);

        return  createProductAggregate(product,recommendations,reviews,serviceUtil.getServiceAddress());
    }

    private ProductAggregate createProductAggregate(
            Product product,
            List<Recommendation> recommendations,
            List<Review> reviews,
            String serviceAddress) {

        //Setup product info
        int productId = product.getProductId();
        String name = product.getName();
        int weight = product.getWeight();

        //Copy summary recommendations info, if available
        List<RecommendationSummary> recommendationSummaries=(recommendations == null)?null:recommendations.stream()
                .map(
                        r -> new RecommendationSummary(
                                r.getRecommendationId(),
                                r.getAuthor(),
                                r.getRate()
                        )
                ).toList();

        //copy summary review info, if available
        List<ReviewSummary> reviewSummaries = (reviews==null)?null:reviews.stream()
                .map(r->new ReviewSummary(
                        r.getReviewId(),
                        r.getAuthor(),
                        r.getSubject()
                )).toList();

        //create info regarding the invoice microservices address

        String productAddress = product.getServiceAddress();
        String reviewAddress = (reviews !=null && !reviews.isEmpty())
                ? reviews.get(0).getServiceAddress()
                : "";
        String recommendationAddress = (recommendations != null && recommendations.isEmpty())
                ?recommendations.get(0).getServiceAddress()
                :"";
        ServiceAddress serviceAddress1 = new ServiceAddress(serviceAddress,productAddress,reviewAddress,recommendationAddress);

        return new ProductAggregate(
                productId,
                name,
                weight,
                recommendationSummaries,
                reviewSummaries,
                serviceAddress1);
    }


}
