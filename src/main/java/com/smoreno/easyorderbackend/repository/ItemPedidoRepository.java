package com.smoreno.easyorderbackend.repository;
import com.smoreno.easyorderbackend.domain.ItemPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ItemPedido entity.
 */
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    @Query(value = "select distinct itemPedido from ItemPedido itemPedido left join fetch itemPedido.tipoItemPedidos left join fetch itemPedido.tipoCocinas left join fetch itemPedido.ingredientes left join fetch itemPedido.menus",
        countQuery = "select count(distinct itemPedido) from ItemPedido itemPedido")
    Page<ItemPedido> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct itemPedido from ItemPedido itemPedido left join fetch itemPedido.tipoItemPedidos left join fetch itemPedido.tipoCocinas left join fetch itemPedido.ingredientes left join fetch itemPedido.menus")
    List<ItemPedido> findAllWithEagerRelationships();

    @Query("select itemPedido from ItemPedido itemPedido left join fetch itemPedido.tipoItemPedidos left join fetch itemPedido.tipoCocinas left join fetch itemPedido.ingredientes left join fetch itemPedido.menus where itemPedido.id =:id")
    Optional<ItemPedido> findOneWithEagerRelationships(@Param("id") Long id);


    //-----------------

    //Obtener todos los platos y bebidas que hay
    @Query("select itemPedido from ItemPedido itemPedido")
    List<ItemPedido>findAllItemPedido();

    //Obtener items por nombre
    @Query("select itemPedido from ItemPedido itemPedido where itemPedido.nombre LIKE CONCAT('%',:nombreParam,'%')")
    List<ItemPedido>findByNombre(@Param("nombreParam") String nombreParam);

    //Obtener Items del menu de un dia concreto
    @Query("select itemPedido from ItemPedido itemPedido join itemPedido.menus mJoin where :startDate BETWEEN mJoin.fechaInicio and mJoin.fechaFinal")
    List<ItemPedido>findByMenu(@Param("startDate") ZonedDateTime startDate);

    //Obtener items de pedido por tipo (bebida, comida, primer plato...)
    @Query("select itemPedido from ItemPedido itemPedido join itemPedido.tipoItemPedidos tipoJoin where tipoJoin.nombreTipo like :tipoParam")
    List<ItemPedido>findByTipoItemPedidos(@Param("tipoParam")String tipoParam);

}
