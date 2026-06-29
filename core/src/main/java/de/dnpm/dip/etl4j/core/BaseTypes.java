package de.dnpm.dip.etl4j.core;


import java.util.Arrays;
import cats.data.NonEmptyList;
import cats.data.NonEmptyList$;
import de.dnpm.dip.model.History;
import de.dnpm.dip.model.Id;
import de.dnpm.dip.model.Reference;
import de.dnpm.dip.model.Reference$;
import static de.dnpm.dip.etl4j.core.Adapters.*;


public abstract class BaseTypes
{

  private BaseTypes(){}


  public static <T> Reference<T> reference(Id<T> id){
    return Reference$.MODULE$.apply(id);
  }


  public static <T> NonEmptyList<T> nonEmptyList(T head, T... tail){
    return NonEmptyList$.MODULE$.of(
      head,
      toScala(Arrays.asList(tail))
    );
  }

  
  public static <T> History<T> history(T t, T... ts){
    return new History(nonEmptyList(t,ts));
  }
 
}
