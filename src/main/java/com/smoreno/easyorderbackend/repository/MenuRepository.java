package com.smoreno.easyorderbackend.repository;
import com.smoreno.easyorderbackend.domain.Menu;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;


/**
 * Spring Data  repository for the Menu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {


    HashSet<Menu> findByFechaInicioBeforeAndFechaFinalAfter(ZonedDateTime fechaFinal, ZonedDateTime fechaFinal2);
}
