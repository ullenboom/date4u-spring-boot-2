package com.tutego.date4u.interfaces.rest;

import com.tutego.date4u.core.profile.ProfileRepository;
import com.tutego.date4u.interfaces.rest.LastSeenStatistics.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RestController
public class StatisticRestController {

  private final ProfileRepository profiles;

  public StatisticRestController( ProfileRepository profiles ) {
    this.profiles = profiles;
  }

  @RequestMapping( "/api/stat/total" )
  public String totalNumberOfRegisteredUnicorns() {
    return String.valueOf( profiles.count() );
  }

  @GetMapping( "/api/stat/last-seen" )
  public LastSeenStatistics lastSeenStatistics(
      @RequestParam( "start" ) Optional<String> maybeStart,
      @RequestParam( "end" ) Optional<String> maybeEnd
  ) {
    //    YearMonth start = YearMonth.now().minusYears( 2 );
    //    YearMonth end = YearMonth.now();
    YearMonth start = maybeStart.map( YearMonth::parse ).orElse( YearMonth.now().minusYears( 2 ) );
    YearMonth end = maybeEnd.map( YearMonth::parse ).orElse( YearMonth.now() );

    List<Data> data = profiles.findMonthlyProfileCount( start.atDay( 1 ), end.atEndOfMonth() ).stream().map(
        tuple -> {
          return new Data( YearMonth.of( Integer.parseInt( tuple.get( "y" ).toString() ),
                                         Integer.parseInt( tuple.get( "m" ).toString() ) ),
                           Integer.parseInt( tuple.get( "count" ).toString() ) );
        } ).toList();

    return new LastSeenStatistics( data );
  }
}