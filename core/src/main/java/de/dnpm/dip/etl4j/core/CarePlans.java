package de.dnpm.dip.etl4j.core;


import de.dnpm.dip.model.CarePlan;


public abstract class CarePlans
{

  private CarePlans(){}

  public static interface BoardType{}
  public static interface NoSequencingPerformedReason{}


  public static final CodedEnumFacade<BoardType> BOARD_TYPE =
    CodedEnumFacade.of(CarePlan.BoardType$.MODULE$);

  public static final CodedEnumFacade<NoSequencingPerformedReason> NO_SEQUENCING_PERFORMED_REASON =
    CodedEnumFacade.of(CarePlan.NoSequencingPerformedReason$.MODULE$);

}
