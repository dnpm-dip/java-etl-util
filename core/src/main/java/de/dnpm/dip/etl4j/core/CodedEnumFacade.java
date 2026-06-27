package de.dnpm.dip.etl4j.core;


import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.toUnmodifiableSet;
import scala.Enumeration;
import scala.Tuple2;
import scala.collection.immutable.Map$;
import scala.jdk.javaapi.CollectionConverters$;
import de.dnpm.dip.coding.Coding;
import de.dnpm.dip.coding.CodedEnum;


// Java-facing Façade around a CodedEnum
public final class CodedEnumFacade<Tag> implements EnumValue.Resolver<Tag>
{

  private final CodedEnum codedEnum;

  private final Set<EnumValue<Tag>> values;

  private final Map<scala.Enumeration.Value,Coding<Tag>> codingsByValue; 


  private CodedEnumFacade(CodedEnum codedEnum){

    this.codedEnum = codedEnum;

    this.codingsByValue = CollectionConverters$.MODULE$.asJava(
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

    this.values =
      CollectionConverters$.MODULE$
        .asJava(codedEnum.values())
        .stream()
        .map(EnumValue::<Tag>of)
        .collect(toUnmodifiableSet());
  }

  public static <Tag> CodedEnumFacade<Tag> of(CodedEnum codedEnum){ 
    return new CodedEnumFacade<Tag>(codedEnum);
  }

  @Override
  public EnumValue<Tag> withName(String name){
    return EnumValue.of(codedEnum.withName(name));
  }

  @Override
  public Set<EnumValue<Tag>> values(){
    return values;
  }

  public Coding<Tag> codingOf(EnumValue<Tag> value){
    return codingsByValue.get(value.unwrap());
  }

  public Coding<Tag> codingOf(String code){
    return codingOf(withName(code));
  }

}
