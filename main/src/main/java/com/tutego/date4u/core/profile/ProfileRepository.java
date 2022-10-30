package com.tutego.date4u.core.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

  @Query( nativeQuery = true, value = """
      SELECT YEAR(lastseen) AS y, MONTH(lastseen) AS m, COUNT(*) AS COUNT
      FROM profile
      WHERE lastseen > ?1 AND lastseen < ?2
      GROUP BY YEAR(lastseen), MONTH(lastseen)""" )
  List<Tuple> findMonthlyProfileCount( LocalDate startDate, LocalDate endDate );

}
