/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321.group12.model;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

// line 66 "../../../../../../ReindeerGames.ump"
@Entity
public class Comment
{
  public Comment() {}
  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Comment> commentsById = new HashMap<Integer, Comment>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Comment Attributes
  @Id
  @GeneratedValue
  private int id;
  private String text;

  //Comment Associations
  @ManyToOne
  private Review review;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Comment(int aId, String aText, Review aReview)
  {
    text = aText;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddReview = setReview(aReview);
    if (!didAddReview)
    {
      throw new RuntimeException("Unable to create comment due to review. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    Integer anOldId = getId();
    if (anOldId != null && anOldId.equals(aId)) {
      return true;
    }
    if (hasWithId(aId)) {
      return wasSet;
    }
    id = aId;
    wasSet = true;
    if (anOldId != null) {
      commentsById.remove(anOldId);
    }
    commentsById.put(aId, this);
    return wasSet;
  }

  public boolean setText(String aText)
  {
    boolean wasSet = false;
    text = aText;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static Comment getWithId(int aId)
  {
    return commentsById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(int aId)
  {
    return getWithId(aId) != null;
  }

  public String getText()
  {
    return text;
  }
  /* Code from template association_GetOne */
  public Review getReview()
  {
    return review;
  }
  /* Code from template association_SetOneToMany */
  public boolean setReview(Review aReview)
  {
    boolean wasSet = false;
    if (aReview == null)
    {
      return wasSet;
    }

    Review existingReview = review;
    review = aReview;
    if (existingReview != null && !existingReview.equals(aReview))
    {
      existingReview.removeComment(this);
    }
    review.addComment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    commentsById.remove(getId());
    Review placeholderReview = review;
    this.review = null;
    if(placeholderReview != null)
    {
      placeholderReview.removeComment(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "text" + ":" + getText()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "review = "+(getReview()!=null?Integer.toHexString(System.identityHashCode(getReview())):"null");
  }
}