package de.dnpm.dip.etl4j.core;


import play.api.libs.json.Writes;
import play.api.libs.json.Json;


public abstract class Builder<T>
{

  protected final Writes<T> writes;


  protected Builder(Writes<T> writes){ 
    this.writes = writes;
  }


//  public abstract T value();


//  public String jsonValue(){
//    return Json.stringify(Json.toJson(this.value(),writes));
//  }

}
