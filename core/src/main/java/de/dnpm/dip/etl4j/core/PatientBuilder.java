package de.dnpm.dip.etl4j.core;


import java.time.YearMonth;
import java.time.LocalDate;
import java.util.Optional;
import de.dnpm.dip.model.*;
import static de.dnpm.dip.etl4j.core.Adapters.*;


public abstract class PatientBuilder
{

  private PatientBuilder(){}


  public static final CodedEnumFacade<Gender> GENDER =
    CodedEnumFacade.of(Gender$.MODULE$);

  public static final CodedEnumFacade<HealthInsurance> HEALTH_INSURANCE_TYPE =
    CodedEnumFacade.of(HealthInsurance.Type$.MODULE$);


  public static <T> Reference<T> Reference(Id<T> id){
    return Reference$.MODULE$.apply(id);
  }


  public static Patient create(
    Id<Patient> id,
    EnumValue<Gender> gender,
    YearMonth birthDate,
    Optional<LocalDate> dateOfDeath,
    EnumValue<HealthInsurance> healthInsuranceType,
    Optional<String> municipalityCode
  ){
    return new Patient(
      id,
      toEnumCoding(GENDER.codingOf(gender)),
      birthDate,
      toOption(dateOfDeath),
      None,
      new Patient.Insurance(
        toEnumCoding(HEALTH_INSURANCE_TYPE.codingOf(healthInsuranceType)),
	None
      ),
      toOption(municipalityCode.map(Address::new))
    );
  }

}
