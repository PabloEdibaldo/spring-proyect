package src.main.java.se.magnus.api.compose.product;

public class ReviewSummary {

    private final int reviewId;
    private final String author;
    private final String subject;


    public ReviewSummary(
            int reviewId,
            String author,
            String subject) {
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getSubject() {
        return subject;
    }

    public String getAuthor() {
        return author;
    }
}
