package de.dnpm.dip.etl4j.genomde;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import scala.Enumeration;
import de.dnpm.dip.model.Id;
import de.dnpm.dip.model.EpisodeOfCare;
import de.dnpm.dip.model.Reference;
import de.dnpm.dip.service.DataUpload;
import de.dnpm.dip.service.mvh.BroadConsent;
import de.dnpm.dip.service.mvh.BroadConsent$;
import de.dnpm.dip.service.mvh.Consent;
import de.dnpm.dip.service.mvh.Consent$Provision$Type$;
import de.dnpm.dip.service.mvh.ModelProjectConsent;
import de.dnpm.dip.service.mvh.Submission;
import de.dnpm.dip.service.mvh.TransferTAN;
import de.dnpm.dip.etl4j.core.EnumValue;
import de.dnpm.dip.etl4j.core.EnumFacade;
import static de.dnpm.dip.etl4j.core.Adapters.*;
import de.dnpm.dip.etl4j.core.BaseTypes;
import play.api.libs.json.Json;
import play.api.libs.json.JsObject;
import play.api.libs.json.Reads;
 

abstract class GenomDE
{

  private GenomDE(){}


  public static interface ConsentProvisionType { }
  public static interface ModelProjectConsentPurpose { }
  public static interface SubmissionType { }
  public static interface BroadConsentReasonMissing { }



  public static final EnumFacade<ModelProjectConsentPurpose> MODEL_PROJECT_PURPOSE =
    EnumFacade.of(ModelProjectConsent.Purpose$.MODULE$);

  public static final EnumFacade<ConsentProvisionType> CONSENT_PROVISION_TYPE =
    EnumFacade.of(Consent$Provision$Type$.MODULE$);

  public static final EnumFacade<SubmissionType> SUBMISSION_TYPE =
    EnumFacade.of(Submission.Type$.MODULE$);

  public static final EnumFacade<BroadConsentReasonMissing> BROADCONSENT_REASON_MISSING =
    EnumFacade.of(BroadConsent.ReasonMissing$.MODULE$);



  public static Consent.Provision<Enumeration.Value> provision(
    LocalDate date,
    EnumValue<ModelProjectConsentPurpose> purpose,
    EnumValue<ConsentProvisionType> type
  ){
    return new Consent.Provision(
      date,
      purpose.value(),
      type.value()
    );
  }
  
  public static ModelProjectConsent modelProjectConsent(
    String version,
    Optional<LocalDate> date,
    List<Consent.Provision<Enumeration.Value>> provisions
  ){ 
    return new ModelProjectConsent(
      version,
      toScala(date),
      toScala(provisions)
    );
  }

  
  private static final Reads<BroadConsent> READS_BC =
    BroadConsent$.MODULE$.reads();


  public static BroadConsent researchConsent(String fhirJsonConsent){
    return READS_BC.reads(Json.parse(fhirJsonConsent)).get();  //TODO: reconsider using this unsafe operation get() here
  }


  public static Submission.Metadata submissionMetadata(
    EnumValue<SubmissionType> type,
    Id<TransferTAN> tan,
    Optional<Id<EpisodeOfCare>> episodeOfCare,
    ModelProjectConsent mvConsent,
    Optional<List<BroadConsent>> researchConsents,
    Optional<EnumValue<BroadConsentReasonMissing>> missingBCReason
  ){
    return new Submission.Metadata(
      type.value(),
      tan,
      toScala(episodeOfCare.map(BaseTypes::reference)),
      mvConsent,
      toScala(
        researchConsents.map(
          bcs -> toScala(bcs)
        )
      ),
      toScala(missingBCReason.map(EnumValue::value))
    );
  }

}
