/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321.group12.model;

import jakarta.persistence.Entity;

// line 26 "../../../../../../ReindeerGames.ump"
@Entity
public class Employee extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

    
  public Employee() {}
  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Employee(int aId, String aEmail, String aPassword, String aName, String aPhoneNumber)
  {
    super(aId, aEmail, aPassword, aName, aPhoneNumber);
  
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}