package com.smoreno.easyorderbackend.repository;
import com.smoreno.easyorderbackend.domain.ItemPedido;
import com.smoreno.easyorderbackend.domain.TipoCocina;
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
    @Query("select itemPedido from ItemPedido itemPedido where itemPedido.stock>0")
    List<ItemPedido>findAllItemPedido();

    //Obtener items por nombre
    @Query("select itemPedido from ItemPedido itemPedido where itemPedido.nombre LIKE CONCAT('%',:nombreParam,'%') and itemPedido.stock>0")
    List<ItemPedido>findByNombre(@Param("nombreParam") String nombreParam);

    //Obtener Items del menu de un dia concreto
    @Query("select itemPedido from ItemPedido itemPedido join itemPedido.menus mJoin where :startDate BETWEEN mJoin.fechaInicio and mJoin.fechaFinal and itemPedido.stock>0")
    List<ItemPedido>findByMenu(@Param("startDate") ZonedDateTime startDate);

    //Obtener items de pedido por tipo (bebida, comida, primer plato...)
    @Query("select itemPedido from ItemPedido itemPedido join itemPedido.tipoItemPedidos tipoJoin where tipoJoin.nombreTipo like :tipoParam and itemPedido.stock>0")
    List<ItemPedido>findByTipoItemPedidos(@Param("tipoParam")String tipoParam);

    //Obtener platos por tipo de cocina
    @Query(value = "SELECT itemPedido from ItemPedido itemPedido join itemPedido.tipoCocinas cocinaJoin where cocinaJoin.nombre like :cocinaParam and itemPedido.stock>0")
    List<ItemPedido>findByTipoCocinas(@Param("cocinaParam")String cocinaParam);

    //Obtener platos veganos
    @Query("select ip from ItemPedido ip join ip.ingredientes ing where ing.vegano = true and ip.stock>0")
    List<ItemPedido>findByVegan();

    //Obtener los mas recientes (cuanto mas alta sea su id, mas reciente sera)
    @Query("select ip from ItemPedido ip join ip.tipoItemPedidos tip where tip.nombreTipo like 'Primer plato' or tip.nombreTipo like 'Segundo plato' or tip.nombreTipo like 'Postre' and ip.stock>0 order by ip.id desc")
    List<ItemPedido>findAlliD(Pageable pageable);

    //Obtener los plaros "fit", para ello se buscara por kcal (menos de 200 cal p.ej)
    @Query("select ip from ItemPedido ip join ip.ingredientes ing where ip.stock>0 group by ip having sum(ing.kcal) < 200")
    List<ItemPedido>findByCalories();

    //Obtener los platos que esten marcados como sugerencia
    @Query("select ip from ItemPedido ip where ip.sugerencia=true and ip.stock>0")
    List<ItemPedido>findBySugerencia();

    //Obtener los platos de un menu concreto
    @Query("select ip from ItemPedido ip join ip.menus m where m.id = :param")
    List<ItemPedido>findByMenuName(@Param("param")Long param);


}
