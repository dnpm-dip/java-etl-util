package de.dnpm.dip.etl4j.core;


import java.util.Set;
import scala.Enumeration;


// Java-facing wrapper around a scala.Enumeration.Value
public final class EnumValue<Tag>
{

  public static sealed interface Resolver<Tag> permits EnumFacade, CodedEnumFacade { 

    public EnumValue<Tag> withName(String name);

    public Set<EnumValue<Tag>> values();

  }


  private final Enumeration.Value value;

  private EnumValue(Enumeration.Value value){
    this.value = value;
  }

  static <Tag> EnumValue<Tag> of(Enumeration.Value value){
    return new EnumValue<>(value);
  }

  scala.Enumeration.Value unwrap(){
    return this.value;
  }

  @Override
  public String toString(){
    return value.toString();
  }

  @Override
  public boolean equals(Object other) {
    return other instanceof EnumValue<?> e && this.value.equals(e.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

}
