package de.dnpm.dip.etl4j.core;


import java.util.Optional;
import scala.Enumeration;
import de.dnpm.dip.coding.Coding;


public abstract class Adapters
{

  private Adapters(){}


  public static final scala.Option None = scala.Option$.MODULE$.empty();


  /**
   * Converter of scala.Option to java.util.Optional
   */
  public static <T> scala.Option<T> toOption(Optional<T> opt){
    return scala.jdk.javaapi.OptionConverters$.MODULE$.toScala(opt);
  }


  public static <T> Coding<Enumeration.Value> toEnumCoding(Coding<T> coding){
    return (Coding<Enumeration.Value>) coding;
  }

}
