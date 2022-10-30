package com.tutego.date4u.interfaces.rest;

import com.tutego.date4u.core.photo.PhotoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotoRestController {

  private final PhotoService photos;

  public PhotoRestController( PhotoService photos ) {
    this.photos = photos;
  }

  @GetMapping( path     = "/api/photos/{image}",
               produces = MediaType.IMAGE_JPEG_VALUE
  )
  public ResponseEntity<?> photo( @PathVariable String image ) {
    return ResponseEntity.of( photos.download( image ) );
  }

}