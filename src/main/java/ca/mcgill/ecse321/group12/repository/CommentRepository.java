package ca.mcgill.ecse321.group12.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.group12.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer>{

	Comment findCommentById(int id);

}