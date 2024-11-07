package ca.mcgill.ecse321.group12.dto;

import ca.mcgill.ecse321.group12.model.Comment;

import ca.mcgill.ecse321.group12.model.Review;

public class CommentResponseDto {

        private int id;
    
        private String text;

        private Review review;
    
        @SuppressWarnings("unused")
        private CommentResponseDto() {
        }
    
        public CommentResponseDto(Comment model) {
            this.id = model.getId();
            this.text = model.getText();
            this.review = model.getReview();
        }

        public int getId() {
            return id;
        }
    
        public String getText() {
            return text;
        }
    
        public Review getReview() {
            return review;
        }
        
        public void setId(int id) {
            this.id = id;
        }
    
        public void setText(String text) {
            this.text = text;
        }
    
        public void setReview(Review review) {
            this.review = review;
        }
}
