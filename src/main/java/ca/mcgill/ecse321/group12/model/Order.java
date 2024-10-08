/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321.group12.model;
import java.util.*;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

// line 78 "../../../../../../ReindeerGames.ump"
@Entity(name = "orders")
public class Order
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Order> ordersById = new HashMap<Integer, Order>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  @Id
  @GeneratedValue
  private int id;
  private Date purchaseDate;
  private float purchaseTotal;
  private String deliveryAddress;

  //Order Associations
  @ManyToMany
  private List<Game> games;
  @ManyToOne
  private Customer customer;
  @ManyToOne
  private CardPayment cardPayment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(int aId, Date aPurchaseDate, float aPurchaseTotal, String aDeliveryAddress, Customer aCustomer, CardPayment aCardPayment, Game... allGames)
  {
    purchaseDate = aPurchaseDate;
    purchaseTotal = aPurchaseTotal;
    deliveryAddress = aDeliveryAddress;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    games = new ArrayList<Game>();
    boolean didAddGames = setGames(allGames);
    if (!didAddGames)
    {
      throw new RuntimeException("Unable to create Order, must have at least 1 games. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCustomer(aCustomer))
    {
      throw new RuntimeException("Unable to create Order due to aCustomer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCardPayment(aCardPayment))
    {
      throw new RuntimeException("Unable to create Order due to aCardPayment. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
      ordersById.remove(anOldId);
    }
    ordersById.put(aId, this);
    return wasSet;
  }

  public boolean setPurchaseDate(Date aPurchaseDate)
  {
    boolean wasSet = false;
    purchaseDate = aPurchaseDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setPurchaseTotal(float aPurchaseTotal)
  {
    boolean wasSet = false;
    purchaseTotal = aPurchaseTotal;
    wasSet = true;
    return wasSet;
  }

  public boolean setDeliveryAddress(String aDeliveryAddress)
  {
    boolean wasSet = false;
    deliveryAddress = aDeliveryAddress;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static Order getWithId(int aId)
  {
    return ordersById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(int aId)
  {
    return getWithId(aId) != null;
  }

  public Date getPurchaseDate()
  {
    return purchaseDate;
  }

  public float getPurchaseTotal()
  {
    return purchaseTotal;
  }

  public String getDeliveryAddress()
  {
    return deliveryAddress;
  }
  /* Code from template association_GetMany */
  public Game getGame(int index)
  {
    Game aGame = games.get(index);
    return aGame;
  }

  public List<Game> getGames()
  {
    List<Game> newGames = Collections.unmodifiableList(games);
    return newGames;
  }

  public int numberOfGames()
  {
    int number = games.size();
    return number;
  }

  public boolean hasGames()
  {
    boolean has = games.size() > 0;
    return has;
  }

  public int indexOfGame(Game aGame)
  {
    int index = games.indexOf(aGame);
    return index;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public CardPayment getCardPayment()
  {
    return cardPayment;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 1;
  }
  /* Code from template association_AddUnidirectionalMStar */
  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    games.add(aGame);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    if (!games.contains(aGame))
    {
      return wasRemoved;
    }

    if (numberOfGames() <= minimumNumberOfGames())
    {
      return wasRemoved;
    }

    games.remove(aGame);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMStar */
  public boolean setGames(Game... newGames)
  {
    boolean wasSet = false;
    ArrayList<Game> verifiedGames = new ArrayList<Game>();
    for (Game aGame : newGames)
    {
      if (verifiedGames.contains(aGame))
      {
        continue;
      }
      verifiedGames.add(aGame);
    }

    if (verifiedGames.size() != newGames.length || verifiedGames.size() < minimumNumberOfGames())
    {
      return wasSet;
    }

    games.clear();
    games.addAll(verifiedGames);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameAt(Game aGame, int index)
  {  
    boolean wasAdded = false;
    if(addGame(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameAt(Game aGame, int index)
  {
    boolean wasAdded = false;
    if(games.contains(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameAt(aGame, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCustomer(Customer aNewCustomer)
  {
    boolean wasSet = false;
    if (aNewCustomer != null)
    {
      customer = aNewCustomer;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCardPayment(CardPayment aNewCardPayment)
  {
    boolean wasSet = false;
    if (aNewCardPayment != null)
    {
      cardPayment = aNewCardPayment;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    ordersById.remove(getId());
    games.clear();
    customer = null;
    cardPayment = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "purchaseTotal" + ":" + getPurchaseTotal()+ "," +
            "deliveryAddress" + ":" + getDeliveryAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cardPayment = "+(getCardPayment()!=null?Integer.toHexString(System.identityHashCode(getCardPayment())):"null");
  }
}