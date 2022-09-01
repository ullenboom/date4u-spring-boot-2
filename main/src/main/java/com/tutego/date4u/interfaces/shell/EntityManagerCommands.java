package com.tutego.date4u.interfaces.shell;

import com.tutego.date4u.core.photo.Photo;
import com.tutego.date4u.core.profile.Profile;
import com.tutego.date4u.core.profile.Unicorn;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class EntityManagerCommands {

  private static final int PAGE_SIZE = 10;
  private final EntityManager em;

  public EntityManagerCommands( EntityManager em ) { this.em = em; }

  @ShellMethod( "Display profile" )
  public String findprofile( long id ) {
    return Optional.ofNullable( em.find( Profile.class, id ) )
        .map( this::formatProfileAsTable )
        .orElse( "Unknown profile for ID " + id );
  }

  private String formatProfileAsTable( Profile profile ) {
    TableModel tableModel = new TableModelBuilder<String>()
        .addRow().addValue( "ID" ).addValue( "" + profile.getId() )
        .addRow().addValue( "Horn length" ).addValue( "" + profile.getHornlength() )
        .addRow().addValue( "Nickname" ).addValue( profile.getNickname() )
        .addRow().addValue( "Birthdate" ).addValue( profile.getBirthdate().toString() )
        .build();
    Table table = new TableBuilder( tableModel ).addFullBorder( BorderStyle.fancy_light ).build();
    return table.render( 100 );
  }

  @ShellMethod( "Display profiles for a given page" )
  public List<Profile> page( int page ) {
    return em.createQuery( "SELECT p FROM Profile p", Profile.class )
        .setFirstResult( page * PAGE_SIZE )
        .setMaxResults( PAGE_SIZE )
        .getResultList();
  }

  @ShellMethod( "Display latest seen profiles" )
  public List<Profile> lastseen() {
    return em.createQuery( "SELECT p FROM Profile p WHERE p.lastseen >:lastseen", Profile.class )
        .setParameter( "lastseen", LocalDateTime.now().minusMonths( 6 ) )
        .getResultList();
  }

  @ShellMethod( "Set horn length of a given profile" )
  @Transactional
  public void updateHornlength( long id, int hornlength ) {
    Optional.ofNullable( em.find( Profile.class, id ) )
        .ifPresent( p -> p.setHornlength( hornlength ) );
  }

  @ShellMethod( "Display unicorn" )
  public String showUnicorn( long id ) {
    return Optional.ofNullable( em.find( Unicorn.class, id ) )
        .map( unicorn -> unicorn.toString() + unicorn.getProfile() )
        .orElse( "Unknown unicorn for ID " + id );
  }

  @ShellMethod( "Display all photos of a given profile by ID" )
  public void photos( long id ) {
    Optional.ofNullable( em.find( Profile.class, id ) ).ifPresent( profile -> {
      for ( Photo photo : profile.getPhotos() ) {
        System.out.println( photo.getName() );
      }
    } );
  }

  @ShellMethod( "Display likes information" )
  public void likes( long id ) {
    Optional.ofNullable( em.find( Profile.class, id ) )
        .ifPresent( profile -> {
          System.out.println( profile.getProfilesThatILike() );
          System.out.println( profile.getProfilesThatLikeMe() );
        } );
  }
}
