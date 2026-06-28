package de.dnpm.dip.etl4j.core;


import java.util.Optional;
import scala.Enumeration;
import de.dnpm.dip.coding.Coding;
import scala.jdk.javaapi.CollectionConverters$;
import scala.jdk.javaapi.OptionConverters$;


public abstract class Adapters
{

  private Adapters(){}


  public static final scala.Option None = scala.Option$.MODULE$.empty();


  /**
   * Converter of scala.Option to java.util.Optional
   */
  public static <T> scala.Option<T> toScala(Optional<T> opt){
    return OptionConverters$.MODULE$.toScala(opt);
  }


  public static <T> Coding<Enumeration.Value> toEnumCoding(Coding<T> coding){
    return (Coding<Enumeration.Value>) coding;
  }

  
  public static <T> scala.collection.immutable.List<T> toScala(java.util.List<T> ts){
    return CollectionConverters$.MODULE$.asScala(ts).toList();
  }

}
