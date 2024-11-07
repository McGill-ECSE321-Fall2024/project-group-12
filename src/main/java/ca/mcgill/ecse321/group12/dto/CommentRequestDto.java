package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Comment;

import ca.mcgill.ecse321.group12.model.Review;

public class CommentRequestDto {
    
        private String text;

        private Review review;
    
        @SuppressWarnings("unused")
        private CommentRequestDto() {
        }
    
        public CommentRequestDto(Comment model) {
            this.text = model.getText();
            this.review = model.getReview();
        }
    
        public String getText() {
            return text;
        }
    
        public Review getReview() {
            return review;
        }
    
        public void setText(String text) {
            this.text = text;
        }
    
        public void setReview(Review review) {
            this.review = review;
        }
}
