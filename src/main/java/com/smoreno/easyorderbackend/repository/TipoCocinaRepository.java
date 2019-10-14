package com.smoreno.easyorderbackend.repository;
import com.smoreno.easyorderbackend.domain.TipoCocina;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoCocina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoCocinaRepository extends JpaRepository<TipoCocina, Long> {

}
