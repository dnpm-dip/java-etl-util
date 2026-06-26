package de.dnpm.dip.etl4j.core;


import java.net.URI;
import java.time.YearMonth;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.Optional;
import scala.Enumeration;
import scala.Tuple2;
import scala.collection.immutable.Map$;
import de.dnpm.dip.coding.CodedEnum;
import de.dnpm.dip.coding.Coding;
import de.dnpm.dip.coding.Coding$;
import de.dnpm.dip.model.*;



public abstract class Builders
{

  private Builders(){}


  private static final scala.Option None = scala.Option$.MODULE$.empty();


  /**
   * Converter of scala.Option to java.util.Optional
   */
  private static <T> scala.Option<T> toOption(Optional<T> opt){
    return scala.jdk.javaapi.OptionConverters$.MODULE$.toScala(opt);
  }


  public static <E extends CodedEnum> Map<Enumeration.Value,Coding<Enumeration.Value>> codingsByValue(E codedEnum){
    return scala.jdk.javaapi.CollectionConverters$.MODULE$.asJava(
      Map$.MODULE$.from(
        codedEnum
          .codeSystem()
          .concepts()
          .map(
            concept -> new Tuple2(
              codedEnum.toEnumValue(concept.code()),
              concept.toCoding(codedEnum.system().uri())
            )
          )
      )
    );
  }


  public static final Map<Enumeration.Value,Coding<Enumeration.Value>> GENDERS =
    codingsByValue(Gender$.MODULE$);

  public static final Map<Enumeration.Value,Coding<Enumeration.Value>> HEALTH_INSURANCES =
    codingsByValue(HealthInsurance.Type$.MODULE$);


  public static <T> Reference<T> Reference(Id<T> id){
    return Reference$.MODULE$.apply(id);
  }


  public static Patient Patient(
    Id<Patient> id,
    Coding<Enumeration.Value> gender,
    YearMonth birthDate,
    Optional<LocalDate> dateOfDeath,
    Coding<Enumeration.Value> healthInsuranceType,
    Optional<String> municipalityCode
  ){
    return new Patient(
      id,
      gender,
      birthDate,
      toOption(dateOfDeath),
      None,
      new Patient.Insurance(healthInsuranceType,None),
      toOption(
	municipalityCode.map(Address::new)
      )
    );
  }


}
