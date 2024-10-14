/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321.group12.model;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

// line 91 "../../../../../../ReindeerGames.ump"
@Entity
public class CardPayment
{

  public CardPayment() {}

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, CardPayment> cardpaymentsById = new HashMap<Integer, CardPayment>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CardPayment Attributes
  @Id
	@GeneratedValue
  private int id;
  private String nameOnCard;
  private String cvc;
  private String cardNumber;
  private String billingAddress;
  private boolean isSaved;
  @Temporal(TemporalType.TIMESTAMP)
  private Date expiryDate;

  //CardPayment Associations
  @ManyToOne
  private Customer customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CardPayment(int aId, String aNameOnCard, String aCvc, String aCardNumber, String aBillingAddress, boolean aIsSaved, Date aExpiryDate)
  {
    nameOnCard = aNameOnCard;
    cvc = aCvc;
    cardNumber = aCardNumber;
    billingAddress = aBillingAddress;
    isSaved = aIsSaved;
    expiryDate = aExpiryDate;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See https://manual.umple.org?RE003ViolationofUniqueness.html");
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
      cardpaymentsById.remove(anOldId);
    }
    cardpaymentsById.put(aId, this);
    return wasSet;
  }

  public boolean setNameOnCard(String aNameOnCard)
  {
    boolean wasSet = false;
    nameOnCard = aNameOnCard;
    wasSet = true;
    return wasSet;
  }

  public boolean setCvc(String aCvc)
  {
    boolean wasSet = false;
    cvc = aCvc;
    wasSet = true;
    return wasSet;
  }

  public boolean setCardNumber(String aCardNumber)
  {
    boolean wasSet = false;
    cardNumber = aCardNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setBillingAddress(String aBillingAddress)
  {
    boolean wasSet = false;
    billingAddress = aBillingAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsSaved(boolean aIsSaved)
  {
    boolean wasSet = false;
    isSaved = aIsSaved;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpiryDate(Date aExpiryDate)
  {
    boolean wasSet = false;
    expiryDate = aExpiryDate;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static CardPayment getWithId(int aId)
  {
    return cardpaymentsById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(int aId)
  {
    return getWithId(aId) != null;
  }

  public String getNameOnCard()
  {
    return nameOnCard;
  }

  public String getCvc()
  {
    return cvc;
  }

  public String getCardNumber()
  {
    return cardNumber;
  }

  public String getBillingAddress()
  {
    return billingAddress;
  }

  public boolean getIsSaved()
  {
    return isSaved;
  }

  public Date getExpiryDate()
  {
    return expiryDate;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }

  public boolean hasCustomer()
  {
    boolean has = customer != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCustomer(Customer aNewCustomer)
  {
    boolean wasSet = false;
    customer = aNewCustomer;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    cardpaymentsById.remove(getId());
    customer = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "nameOnCard" + ":" + getNameOnCard()+ "," +
            "cvc" + ":" + getCvc()+ "," +
            "cardNumber" + ":" + getCardNumber()+ "," +
            "billingAddress" + ":" + getBillingAddress()+ "," +
            "isSaved" + ":" + getIsSaved()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "expiryDate" + "=" + (getExpiryDate() != null ? !getExpiryDate().equals(this)  ? getExpiryDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}