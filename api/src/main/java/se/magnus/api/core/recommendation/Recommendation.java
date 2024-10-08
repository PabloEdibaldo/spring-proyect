package se.magnus.api.core.recommendation;

public class Recommendation {

    private final int productId;
    private final int recommendationId;
    private final String author;
    private final int rate;
    private final String content;
    private final String serviceAddress;

    public Recommendation(
            int productId,
            int recommendationId,
            String author,
            int rate,
            String content,
            String serviceAddress) {
        this.productId = productId;
        this.recommendationId = recommendationId;
        this.author = author;
        this.rate = rate;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }
    public Recommendation(){
        productId =0;
        recommendationId = 0;
        author = null;
        rate = 0;
        content = null;
        serviceAddress = null;

    }

    public int getProductId() {
        return productId;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public int getRate() {
        return rate;
    }

    public String getAuthor() {
        return author;
    }

    public int getRecommendationId() {
        return recommendationId;
    }

    public String getContent() {
        return content;
    }
}
