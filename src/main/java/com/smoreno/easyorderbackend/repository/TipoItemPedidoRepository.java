package com.smoreno.easyorderbackend.repository;
import com.smoreno.easyorderbackend.domain.TipoItemPedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoItemPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoItemPedidoRepository extends JpaRepository<TipoItemPedido, Long> {

}
