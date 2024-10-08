package se.magnus.api.compose.product;

import java.util.List;

public class ProductAggregate {

    private final int productId;
    private final String name;
    private final int weight;
    private final List<RecommendationSummary> recommendations;
    private final List<ReviewSummary> reviews;
    private final ServiceAddresses serviceAddresses;

    public ProductAggregate(
            int productId,
            String name,
            int weight,
            List<RecommendationSummary> recommendations,
            List<ReviewSummary> reviews,
            ServiceAddresses serviceAddresses) {
        this.productId = productId;
        this.name = name;
        this.weight = weight;
        this.recommendations = recommendations;
        this.reviews = reviews;
        this.serviceAddresses = serviceAddresses;
    }

    public ServiceAddresses getServiceAddress() {
        return serviceAddresses;
    }

    public List<ReviewSummary> getReviews() {
        return reviews;
    }

    public List<RecommendationSummary> getRecommendations() {
        return recommendations;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public int getProductId() {
        return productId;
    }
}
