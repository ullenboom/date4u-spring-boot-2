package com.tutego.date4u.interfaces.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.YearMonth;
import java.util.List;

public class LastSeenStatistics {
  public static class Data {
    @JsonProperty( "x" ) public YearMonth yearMonth;
    @JsonProperty( "y" ) public int count;

    public Data() { }
    public Data( YearMonth yearMonth, int count ) {
      this.yearMonth = yearMonth;
      this.count = count;
    }
  }

  public List<Data> data;

  public LastSeenStatistics() { }
  public LastSeenStatistics( List<Data> data ) { this.data = data; }
}