package com.smoreno.easyorderbackend.repository;
import com.smoreno.easyorderbackend.domain.StatusItemPedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StatusItemPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusItemPedidoRepository extends JpaRepository<StatusItemPedido, Long> {

}
