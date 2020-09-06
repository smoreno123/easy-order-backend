package com.smoreno.easyorderbackend.repository;
import com.smoreno.easyorderbackend.domain.PagoPedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PagoPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PagoPedidoRepository extends JpaRepository<PagoPedido, Long> {

    @Query("select count(pp.cantidad) from PagoPedido pp join pp.comensal comensal where comensal.dni like :param")
    Integer countByDni(@Param("param") String param);

}
