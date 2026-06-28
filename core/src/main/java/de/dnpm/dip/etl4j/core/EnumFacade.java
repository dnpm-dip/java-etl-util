package de.dnpm.dip.etl4j.core;


import java.util.Set;
import static java.util.stream.Collectors.toUnmodifiableSet;
import scala.Enumeration;
import scala.jdk.javaapi.CollectionConverters$;


// Java-facing Façade around a scala.Enumeration
public final class EnumFacade<Tag> implements EnumValue.Resolver<Tag>
{

  private final Enumeration enumeration;

  private final Set<EnumValue<Tag>> values;


  private EnumFacade(Enumeration enumeration){
    this.enumeration = enumeration;

    this.values =
      CollectionConverters$.MODULE$
        .asJava(enumeration.values())
        .stream()
        .map(EnumValue::<Tag>of)
        .collect(toUnmodifiableSet());
  }

  public static <Tag> EnumFacade<Tag> of(Enumeration enumeration){ 
    return new EnumFacade<Tag>(enumeration);
  }

  @Override
  public EnumValue<Tag> withName(String name){
    return EnumValue.of(enumeration.withName(name));
  }
  
  @Override
  public Set<EnumValue<Tag>> values(){
    return values;
  }

}
