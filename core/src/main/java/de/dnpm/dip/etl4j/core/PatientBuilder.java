package de.dnpm.dip.etl4j.core;


import java.time.YearMonth;
import java.time.LocalDate;
import java.util.Optional;
import de.dnpm.dip.model.Address;
import de.dnpm.dip.model.Gender;
import de.dnpm.dip.model.Gender$;
import de.dnpm.dip.model.HealthInsurance;
import de.dnpm.dip.model.Id;
import de.dnpm.dip.model.Patient;
import de.dnpm.dip.model.Reference;
import static de.dnpm.dip.etl4j.core.Adapters.*;


public abstract class PatientBuilder
{

  private PatientBuilder(){}

  public static interface HealthInsuranceType{}


  public static final CodedEnumFacade<Gender> GENDER =
    CodedEnumFacade.of(Gender$.MODULE$);

  public static final CodedEnumFacade<HealthInsuranceType> HEALTH_INSURANCE_TYPE =
    CodedEnumFacade.of(HealthInsurance.Type$.MODULE$);



  public static Patient create(
    Id<Patient> id,
    EnumValue<Gender> gender,
    YearMonth birthDate,
    Optional<LocalDate> dateOfDeath,
    EnumValue<HealthInsuranceType> healthInsuranceType,
    Optional<Reference<HealthInsurance>> healthInsurance,
    Optional<String> municipalityCode
  ){
    return new Patient(
      id,
      toEnumCoding(GENDER.codingOf(gender)),
      birthDate,
      toScala(dateOfDeath),
      None,
      new Patient.Insurance(
        toEnumCoding(HEALTH_INSURANCE_TYPE.codingOf(healthInsuranceType)),
        toScala(healthInsurance)
      ),
      toScala(municipalityCode.map(Address::new))
    );
  }

}
