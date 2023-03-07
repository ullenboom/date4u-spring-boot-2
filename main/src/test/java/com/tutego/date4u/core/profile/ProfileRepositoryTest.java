package com.tutego.date4u.core.profile;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProfileRepositoryTest {

  private final Logger log = LoggerFactory.getLogger( getClass() );

  @Autowired private DataSource dataSource;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private EntityManager em;
  @Autowired private ProfileRepository profileRepository;

  @Test
  void datasoure_jdbctemplate_entityManager_repository_not_null() throws SQLException {
    log.info( dataSource.getConnection().toString() );
    assertThat( dataSource ).isNotNull();
    assertThat( jdbcTemplate ).isNotNull();
    assertThat( em ).isNotNull();
    assertThat( profileRepository ).isNotNull();
  }

/*  @Test
  void database_contains_no_entities() {
    assertThat( profileRepository.count() ).isZero();
    assertThat( profileRepository.findAll() ).isEmpty();
  }*/
}
