package com.smoreno.easyorderbackend.repository;
import com.smoreno.easyorderbackend.domain.ItemPedido;
import com.smoreno.easyorderbackend.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Pedido entity.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query(value = "select distinct pedido from Pedido pedido left join fetch pedido.menus left join fetch pedido.itemPedidos",
        countQuery = "select count(distinct pedido) from Pedido pedido")
    Page<Pedido> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct pedido from Pedido pedido left join fetch pedido.menus left join fetch pedido.itemPedidos")
    List<Pedido> findAllWithEagerRelationships();

    @Query("select pedido from Pedido pedido left join fetch pedido.menus left join fetch pedido.itemPedidos where pedido.id =:id")
    Optional<Pedido> findOneWithEagerRelationships(@Param("id") Long id);

    //--------------------

    //Obtener el plato mas pedido
    //Retorna una lista por si queremos devolver mas de un plato en algun momento
    @Query("select ip from Pedido pedido join pedido.itemPedidos ip group by ip order by count (ip) desc")
    List<ItemPedido> findByMasPedido();

}
