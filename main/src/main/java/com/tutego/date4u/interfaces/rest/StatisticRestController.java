package com.tutego.date4u.interfaces.rest;

import com.tutego.date4u.core.profile.ProfileRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatisticRestController {

  private final ProfileRepository profiles;

  public StatisticRestController( ProfileRepository profiles ) {
    this.profiles = profiles;
  }

  @RequestMapping( "/api/stat/total" )
  // long als Rückgabe ist auch in Ordnung
  public String totalNumberOfRegisteredUnicorns() {
    return String.valueOf( profiles.count() );
  }

}