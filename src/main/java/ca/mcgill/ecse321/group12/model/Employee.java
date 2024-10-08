/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse321.group12.model;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

// line 26 "../../../../../../ReindeerGames.ump"
public class Employee extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

    

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @Entity
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