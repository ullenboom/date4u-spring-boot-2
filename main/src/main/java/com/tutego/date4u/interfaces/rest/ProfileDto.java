package com.tutego.date4u.interfaces.rest;

import com.tutego.date4u.core.profile.Profile;

import java.time.LocalDate;
import java.time.LocalDateTime;

record ProfileDto(
    Long id, String nickname, LocalDate birthdate, int hornlength, int gender,
    Integer attractedToGender, String description, LocalDateTime lastseen
) {
  static ProfileDto fromDomain( Profile p ) {
    return new ProfileDto(
        p.getId(), p.getNickname(), p.getBirthdate(), p.getHornlength(),
        p.getGender(), p.getAttractedToGender(), p.getDescription(),
        p.getLastseen()
    );
  }
}