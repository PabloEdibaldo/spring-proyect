package src.main.java.se.magnus.api.core.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import src.main.java.se.magnus.api.core.recommendation.Recommendation;

import java.util.List;

public interface ProductService {

    @GetMapping(
            value = "/product/{productId}",
            produces = "application/json"
    )
    Product getProduct(@PathVariable int productId);

    List<Recommendation> getRecommendations(int productId);
}
