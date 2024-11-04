package ca.mcgill.ecse321.group12.dto;
import ca.mcgill.ecse321.group12.model.Review;
public class ReviewResponseDto {

    private int id;
    private String review;
    private int rating;
   
    @SuppressWarnings("unused")
    private ReviewResponseDto() {
    }

    public ReviewResponseDto(Review model) {
        this.id = model.getId();
        this.review = model.getText();
        this.rating = model.getRating();
        
    }
    public int getId() {
        return id;
    }
    public String getReview() {
        return review;
    }
    public int getText() {
        return rating;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public void setReview(String review) {
        this.review = review;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
   
    

    
}
