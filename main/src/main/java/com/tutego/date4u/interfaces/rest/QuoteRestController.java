package com.tutego.date4u.interfaces.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/quotes" )
public class QuoteRestController {

  private final static String[] QUOTES = {
      "Date to be known, not to be liked",
      "Dating is all about the chase. It’s fun!",
      "You can’t blame gravity for falling in love"
  };

  @GetMapping
  public String retrieveQuote( int index ) {
    return QUOTES[ index ];
  }
}