package com.smoreno.easyorderbackend.repository;
import com.smoreno.easyorderbackend.domain.ItemPedido;
import com.smoreno.easyorderbackend.domain.Valoracion;
import com.smoreno.easyorderbackend.domain.ValoracionMedia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Valoracion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    //Obtiene los platos mejor valorados (se calcula una media entre la nota que le dan y el numero de valoraciones que tiene)
    //Retorna el ItemPedido entero, y la nota media que corresponde a la valoracion
    @Query("select ip,avg (val.nota) from Valoracion val join val.itemPedido ip group by ip order by avg (val.nota) desc ")
    List<Object[]>findByBetterValue();

}
