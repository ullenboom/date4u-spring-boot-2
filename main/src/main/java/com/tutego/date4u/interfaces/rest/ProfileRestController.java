package com.tutego.date4u.interfaces.rest;

import com.tutego.date4u.core.photo.PhotoService;
import com.tutego.date4u.core.profile.Profile;
import com.tutego.date4u.core.profile.ProfileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping( "/api/profiles" )
public class ProfileRestController {

  private final Logger log = LoggerFactory.getLogger( getClass() );

  final ProfileRepository profiles;

  final PhotoService photos;

  public ProfileRestController( ProfileRepository profiles, PhotoService photos ) {
    this.profiles = profiles;
    this.photos = photos;
  }

  @GetMapping( path = "{id}/photos/{index}",
      produces = MediaType.IMAGE_JPEG_VALUE )
  public ResponseEntity<?> get( @PathVariable long id,
                                @PathVariable int index ) {

    Optional<Profile> maybeProfile = profiles.findById( id );
    if ( maybeProfile.isEmpty() )
      return new ResponseEntity<>( HttpStatus.NOT_FOUND );

    Profile profile = maybeProfile.get();
    if ( index >= 0 && index < profile.getPhotos().size() ) {
      // Photo photo = profile.getPhotos().get( index );
      // Optional<byte[]> download = photos.download( photo );
      Optional<byte[]> download = photos.download( "unicorn001" );
      return ResponseEntity.ok( download.get() );
    }

    return new ResponseEntity<>( HttpStatus.NOT_FOUND );
  }

  @GetMapping
  public Stream<ProfileDto> profiles() {
    return profiles.findAll().stream().map( ProfileDto::fromDomain );
  }

  @Operation( summary = "Get a profile by its id" )
  @ApiResponses( {
      @ApiResponse( responseCode = "200", description = "Profile found",
          content = { @Content( mediaType = "application/json",
              schema = @Schema( implementation = ProfileDto.class ) ) } ),
      @ApiResponse( responseCode = "400", description = "Invalid id",
          content = @Content ),
      @ApiResponse( responseCode = "404", description = "Profile not found",
          content = @Content ) } )
  @GetMapping( "/{id}" )
  public ResponseEntity<?> get( @PathVariable long id ) {
    Optional<ProfileDto> maybeProfileDto = profiles.findById( id ).map( ProfileDto::fromDomain );
    return ResponseEntity.of( maybeProfileDto );
  }

  @DeleteMapping( "/{id}" )
  public ResponseEntity<?> delete( @PathVariable long id ) {
    if ( !profiles.existsById( id ) )
      return new ResponseEntity<>( HttpStatus.NOT_FOUND );
    // profiles.deleteById( id );
    log.info( "delete resource {}", id );
    return new ResponseEntity<>( HttpStatus.OK );
  }

  @PostMapping
  public ResponseEntity<?> create( @RequestBody ProfileDto profileDto ) {
    if ( profileDto.id() != null )
      return ResponseEntity.badRequest().body( "ID of profile must be null" );

    Profile newProfile = new Profile( profileDto.nickname(),
                                      profileDto.birthdate(),
                                      profileDto.hornlength(),
                                      profileDto.gender(),
                                      profileDto.attractedToGender(),
                                      profileDto.description(),
                                      profileDto.lastseen() );
    newProfile = profiles.save( newProfile );

    return ResponseEntity.created( URI.create( "/api/profiles/" + newProfile.getId() ) )
        .body( ProfileDto.fromDomain( newProfile ) );
  }

  @PutMapping( "/{id}" )
  public ResponseEntity<?> update( @PathVariable long id, @RequestBody ProfileDto profileDto ) {
    if ( profileDto.id() != null && profileDto.id() != id )
      return new ResponseEntity<>( "Path variable of id is not equal to profile id",
                                   HttpStatus.CONFLICT );

    Optional<Profile> maybeProfile = profiles.findById( id );
    if ( maybeProfile.isEmpty() )
      return new ResponseEntity<>( "Unable to update profile with given id " + id,
                                   HttpStatus.NOT_FOUND );

    Profile profile = maybeProfile.get();
    profile.setNickname( profileDto.nickname() );
    profile.setBirthdate( profileDto.birthdate() );
    profile.setHornlength( profileDto.hornlength() );
    profile.setGender( profileDto.gender() );
    profile.setAttractedToGender( profileDto.attractedToGender() );
    profile.setDescription( profileDto.description() );
    profile.setLastseen( profileDto.lastseen() );
    profile = profiles.save( profile );

    return ResponseEntity.ok( ProfileDto.fromDomain( profile ) );
  }
}
