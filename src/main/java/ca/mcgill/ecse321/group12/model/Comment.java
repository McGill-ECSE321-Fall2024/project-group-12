/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321.group12.model;

// line 65 "../../../../../../ReindeerGames.ump"
public class Comment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Comment Attributes
  private String text;

  //Comment Associations
  private Review review;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Comment(String aText, Review aReview)
  {
    text = aText;
    boolean didAddReview = setReview(aReview);
    if (!didAddReview)
    {
      throw new RuntimeException("Unable to create comment due to review. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setText(String aText)
  {
    boolean wasSet = false;
    text = aText;
    wasSet = true;
    return wasSet;
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
            "text" + ":" + getText()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "review = "+(getReview()!=null?Integer.toHexString(System.identityHashCode(getReview())):"null");
  }
}