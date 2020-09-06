package com.smoreno.easyorderbackend.repository;
import com.smoreno.easyorderbackend.domain.TipoItemPedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the TipoItemPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoItemPedidoRepository extends JpaRepository<TipoItemPedido, Long> {

    @Query("select tip from TipoItemPedido as tip where tip.nombreTipo not like 'Primer plato' and tip.nombreTipo not like 'Segundo plato'")
    List<TipoItemPedido>findAllByNombreTipo();


}
