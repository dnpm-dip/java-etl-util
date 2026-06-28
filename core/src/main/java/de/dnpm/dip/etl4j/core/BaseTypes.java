package de.dnpm.dip.etl4j.core;


import java.time.YearMonth;
import java.time.LocalDate;
import java.util.Optional;
import de.dnpm.dip.model.Id;
import de.dnpm.dip.model.Reference;
import de.dnpm.dip.model.Reference$;


public abstract class BaseTypes
{

  private BaseTypes(){}


  public static <T> Reference<T> reference(Id<T> id){
    return Reference$.MODULE$.apply(id);
  }
}
