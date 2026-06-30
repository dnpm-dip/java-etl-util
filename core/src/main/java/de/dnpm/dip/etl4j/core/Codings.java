package de.dnpm.dip.etl4j.core;


import scala.Enumeration;
import de.dnpm.dip.coding.Coding;
//import scala.collection.immutable.Seq;


public abstract class Codings
{

  private Codings(){}

/*
  public static interface Resolver<T>
  {
    public <U> Coding<T> resolve(Coding<U> coding);

    public static <T> Resolver<T> of(Seq<Coding<T>> codings){ 
      return new ResolverImpl(codings);
    }
  }


  private static record ResolverImpl<T>(
    Seq<Coding<T>> codings
  )
  implements Resolver<T>
  {

    @Override
    public <U> Coding<T> resolve(Coding<U> coding){
      return codings
        .find(c -> c.code().equals(coding.code()) && c.system().equals(coding.system()))
        .getOrElse(
          () -> { throw new IllegalArgumentException("Invalid Coding" + coding.toString()); }
        );
    }
    
  } 
*/

  public static <T> Coding<Enumeration.Value> toEnumCoding(Coding<T> coding){
    return (Coding<Enumeration.Value>) coding;
  }


  public static <T,U> Coding<T> widen(Coding<U> coding){
    return (Coding<T>) coding;
  }

  
}
