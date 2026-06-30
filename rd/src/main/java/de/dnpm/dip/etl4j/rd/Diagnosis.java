package de.dnpm.dip.etl4j.rd;


import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.YearMonth;
import cats.data.NonEmptyList;
import cats.data.NonEmptyList$;
import de.dnpm.dip.coding.Coding;
import de.dnpm.dip.model.Id;
import de.dnpm.dip.model.Patient;
import de.dnpm.dip.model.Reference;
import de.dnpm.dip.rd.model.RDDiagnosis;
import de.dnpm.dip.rd.model.RDDiagnosis$;
import de.dnpm.dip.etl4j.core.EnumValue;
import de.dnpm.dip.etl4j.core.CodedEnumFacade;
import de.dnpm.dip.etl4j.core.Adapters;
import de.dnpm.dip.etl4j.core.Codings;
import static de.dnpm.dip.etl4j.core.Adapters.*;



public abstract class Diagnosis
{

  private Diagnosis(){}


  public static interface FamilyControlLevel {}
  public static interface VerificationStatus {}
  public static interface MissingCodeReason {}


  public static final CodedEnumFacade<FamilyControlLevel> FAMILY_CONTROL_LEVEL =
    CodedEnumFacade.of(RDDiagnosis.FamilyControlLevel$.MODULE$);

  public static final CodedEnumFacade<VerificationStatus> VERIFICATION_STATUS =
    CodedEnumFacade.of(RDDiagnosis.VerificationStatus$.MODULE$);

  public static final CodedEnumFacade<MissingCodeReason> MISSING_CODE_REASON =
    CodedEnumFacade.of(RDDiagnosis.MissingCodeReason$.MODULE$);

/*
  public static final CodedEnumFacade<RDDiagnosis.FamilyControlLevel$> FAMILY_CONTROL_LEVEL =
    CodedEnumFacade.of(RDDiagnosis.FamilyControlLevel$.MODULE$);

  public static final CodedEnumFacade<RDDiagnosis.VerificationStatus$> VERIFICATION_STATUS =
    CodedEnumFacade.of(RDDiagnosis.VerificationStatus$.MODULE$);

  public static final CodedEnumFacade<RDDiagnosis.MissingCodeReason$> MISSING_CODE_REASON =
    CodedEnumFacade.of(RDDiagnosis.MissingCodeReason$.MODULE$);
*/

  /**
   * Helper method for the expected Scala representation Coding[Orphanet :+: ICD10GM ...],
   * for which there is no Java-side representation:
   * By resolving the unspecific Coding<?> in RDDiagnosis.valueSet, type inference gets the correct Coding<...> type.
   * This has the additional advantage that any incorrect, i.e. unresolvable Coding is reported as an IllegalArgumentException 
   */
  @SuppressWarnings("unchecked")
  private static <S> Coding<S> resolve(Coding<?> coding){
    return RDDiagnosis.Systems$.MODULE$
      .valueSet()
      .coding(coding.code())
      .filter(r -> r.system().equals(coding.system()))
      .getOrElse(
        () -> { throw new IllegalArgumentException("Invalid RD Diagnosis Coding" + coding.toString()); }
      );
  }


  public static RDDiagnosis of(
    Id<RDDiagnosis> id,
    Reference<Patient> patient,
    LocalDate recordedOn,
    Optional<YearMonth> onsetDate,
    Optional<EnumValue<FamilyControlLevel>> familyControlLevel,
    EnumValue<VerificationStatus> verificationStatus,
    NonEmptyList<Coding<?>> codings,
    Optional<EnumValue<MissingCodeReason>> missingCodeReason,
    Optional<List> notes
  ){
    return new RDDiagnosis(
      id,
      patient,
      recordedOn,
      toScala(onsetDate),
      toScala(familyControlLevel.map(FAMILY_CONTROL_LEVEL::codingOf).map(Codings::toEnumCoding)),
      Codings.toEnumCoding(VERIFICATION_STATUS.codingOf(verificationStatus)),
      codings.map(c -> resolve(c)),
      toScala(missingCodeReason.map(MISSING_CODE_REASON::codingOf).map(Codings::toEnumCoding)),
      toScala(notes.map(Adapters::toScala))
    );
  }

}
