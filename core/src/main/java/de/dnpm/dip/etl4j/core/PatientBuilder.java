package de.dnpm.dip.etl4j.core;


import java.time.YearMonth;
import java.time.LocalDate;
import de.dnpm.dip.coding.Coding;
import de.dnpm.dip.model.Patient;
import de.dnpm.dip.model.Patient$;
import de.dnpm.dip.model.Gender$;



public final class PatientBuilder extends Builder<Patient>
{

  public PatientBuilder(){
    super(Patient$.MODULE$.writes()); 
  
  }



}

