package com.smoreno.easyorderbackend.repository;
import com.smoreno.easyorderbackend.domain.Ingrediente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ingrediente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

}
