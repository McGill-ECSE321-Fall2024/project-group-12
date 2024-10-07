/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321.group12.model;
import java.util.*;

// line 11 "../../../../../../ReindeerGames.ump"
public class UserRole
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, UserRole> userrolesById = new HashMap<Integer, UserRole>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserRole Attributes
  private int id;
  private String email;
  private String password;
  private String name;
  private String address;
  private String phoneNumber;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserRole(int aId, String aEmail, String aPassword, String aName, String aPhoneNumber)
  {
    email = aEmail;
    password = aPassword;
    name = aName;
    address = null;
    phoneNumber = aPhoneNumber;
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
      userrolesById.remove(anOldId);
    }
    userrolesById.put(aId, this);
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static UserRole getWithId(int aId)
  {
    return userrolesById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(int aId)
  {
    return getWithId(aId) != null;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }

  public String getAddress()
  {
    return address;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public void delete()
  {
    userrolesById.remove(getId());
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "," +
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]";
  }
}