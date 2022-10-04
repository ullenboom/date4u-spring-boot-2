package com.tutego.date4u.core.photo;

import com.tutego.date4u.core.profile.Profile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Access( AccessType.FIELD )
public class Photo {

  @Id @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Long id;

  @ManyToOne
  @JoinColumn( name = "profile_fk" )
  private Profile profile;

  @NotNull @Pattern( regexp = "[\\w_-]{1,200}" )
  private String name;

  @Column( name = "is_profile_photo" )
  private boolean isProfilePhoto;

  @NotNull @Past
  private LocalDateTime created;

  protected Photo() {
  }

  public Photo( Long id, Profile profile, String name, boolean isProfilePhoto, LocalDateTime created ) {
    this.id = id;
    this.profile = profile;
    this.name = name;
    this.isProfilePhoto = isProfilePhoto;
    this.created = created;
  }

  public Long getId() {
    return id;
  }

  public Profile getProfile() {
    return profile;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public boolean isProfilePhoto() {
    return isProfilePhoto;
  }

  public void setProfilePhoto( boolean profilePhoto ) {
    isProfilePhoto = profilePhoto;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated( LocalDateTime created ) {
    this.created = created;
  }

  @Override public String toString() {
    return "Photo[" + id + "]";
  }
}
