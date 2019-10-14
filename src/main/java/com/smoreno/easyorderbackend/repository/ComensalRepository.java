package com.smoreno.easyorderbackend.repository;
import com.smoreno.easyorderbackend.domain.Comensal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Comensal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComensalRepository extends JpaRepository<Comensal, Long> {

}
