package de.dnpm.dip.etl4j.core;


import java.util.Random;
import java.time.YearMonth;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static de.dnpm.dip.etl4j.core.Builders.*;
import de.dnpm.dip.model.*;


class Tests
{

  private static final Random RND = new Random();


  public static <T> T oneOf(List<T> ts){
    return ts.get(RND.nextInt(ts.size()));
  }

  public static <K,V> V anyValueOf(Map<K,V> map){
    return map.entrySet().iterator().next().getValue();  //TODO
  }


  @Test
  public void testPatientBuilder(){
    var pat = Patient(
      new Id<>("12345678"),
      anyValueOf(GENDERS),
      YearMonth.now().minusYears(RND.nextInt(74)),
      Optional.of(LocalDate.now()),
      anyValueOf(HEALTH_INSURANCES),
      Optional.empty()
    ); 

    System.out.println(pat);  
  }


}
