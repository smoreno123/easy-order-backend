package com.smoreno.easyorderbackend.web.rest;

import com.smoreno.easyorderbackend.domain.Ingrediente;
import com.smoreno.easyorderbackend.domain.ItemPedido;
import com.smoreno.easyorderbackend.domain.TipoCocina;
import com.smoreno.easyorderbackend.repository.ItemPedidoRepository;
import com.smoreno.easyorderbackend.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link com.smoreno.easyorderbackend.domain.ItemPedido}.
 */
@RestController
@RequestMapping("/api")
public class ItemPedidoResource {

    private final Logger log = LoggerFactory.getLogger(ItemPedidoResource.class);

    private static final String ENTITY_NAME = "itemPedido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoResource(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    /**
     * {@code POST  /item-pedidos} : Create a new itemPedido.
     *
     * @param itemPedido the itemPedido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemPedido, or with status {@code 400 (Bad Request)} if the itemPedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-pedidos")
    public ResponseEntity<ItemPedido> createItemPedido(@RequestBody ItemPedido itemPedido) throws URISyntaxException {
        log.debug("REST request to save ItemPedido : {}", itemPedido);
        if (itemPedido.getId() != null) {
            throw new BadRequestAlertException("A new itemPedido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPedido result = itemPedidoRepository.save(itemPedido);
        return ResponseEntity.created(new URI("/api/item-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-pedidos} : Updates an existing itemPedido.
     *
     * @param itemPedido the itemPedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemPedido,
     * or with status {@code 400 (Bad Request)} if the itemPedido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemPedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-pedidos")
    public ResponseEntity<ItemPedido> updateItemPedido(@RequestBody ItemPedido itemPedido) throws URISyntaxException {
        log.debug("REST request to update ItemPedido : {}", itemPedido);
        if (itemPedido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPedido result = itemPedidoRepository.save(itemPedido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemPedido.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-pedidos} : get all the itemPedidos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemPedidos in body.
     */
    @GetMapping("/item-pedidos")
    public List<ItemPedido> getAllItemPedidos(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ItemPedidos");
        return itemPedidoRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /item-pedidos/:id} : get the "id" itemPedido.
     *
     * @param id the id of the itemPedido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemPedido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-pedidos/{id}")
    public ResponseEntity<ItemPedido> getItemPedido(@PathVariable Long id) {
        log.debug("REST request to get ItemPedido : {}", id);
        Optional<ItemPedido> itemPedido = itemPedidoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(itemPedido);
    }

    /**
     * {@code DELETE  /item-pedidos/:id} : delete the "id" itemPedido.
     *
     * @param id the id of the itemPedido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-pedidos/{id}")
    public ResponseEntity<Void> deleteItemPedido(@PathVariable Long id) {
        log.debug("REST request to delete ItemPedido : {}", id);
        itemPedidoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    //Funcion obtener todos los items pedido
    @GetMapping("/item-pedidos/all")
    @Transactional
    public List<ItemPedido> getAllItemPedidos() {
        List<ItemPedido> itemPedidoList = new ArrayList<>();

        itemPedidoList = itemPedidoRepository.findAllItemPedido();

        return itemPedidoList;
    }

    //Funcion obtener itemspPedido por nombre (contains)
    @GetMapping("/item-pedidos/by-name/{nombreParam}")
    public List<ItemPedido> finItemsByName(@PathVariable String nombreParam) {
        List<ItemPedido> itemPedidoList;

        itemPedidoList = itemPedidoRepository.findByNombre(nombreParam);

        return itemPedidoList;
    }

    //Obtener Items del menu de un dia concreto
    // IMPORTANTE!!!! Ejemplo de fecha para probar la query: 2019-10-07T00:00:00+00:00
    @GetMapping("/item-pedidos/by-date")
    @Transactional
    public List<ItemPedido> findMenuByDate(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDate) {
        List<ItemPedido> itemPedidoList;

        itemPedidoList = itemPedidoRepository.findByMenu(startDate);

        return itemPedidoList;

    }

    //Obtener items de pedido por tipo (bebida, comida, primer plato...)
    @GetMapping("/item-pedidos/by-type")
    public List<ItemPedido> findByType(@RequestParam("tipoParam") String tipoParam) {
        List<ItemPedido> itemPedidoList;

        //TipoItemPedido tipoItemPedido = new TipoItemPedido();
        //tipoItemPedido.setNombreTipo(tipoParam);
        itemPedidoList = itemPedidoRepository.findByTipoItemPedidos(tipoParam);

        return itemPedidoList;

    }

    @GetMapping("/item-pedidos/by-cuisine")
    public List<ItemPedido> findByCuisine(@RequestParam("cocinaParam") String cocinaParam){
        return itemPedidoRepository.findByTipoCocinas(cocinaParam);
    }

    @GetMapping("/item-pedidos/by-vegetarian")
    public List<ItemPedido> findByVegetarian(){

        return itemPedidoRepository.findByVegetarian()
            .stream()
            .filter(itemPedido ->
                itemPedido.getIngredientes().stream()
                    .allMatch(Ingrediente::isVegetariano))
            .collect(Collectors.toList());

    }

    @GetMapping("/item-pedido/by-recent")
    public List<ItemPedido> findByRecent(){

        Pageable top10 = PageRequest.of(0,10);
        return itemPedidoRepository.findAlliD(top10);
    }

    @GetMapping("/item-pedido/by-calories")
    public List<ItemPedido> findByCalories(){
        return itemPedidoRepository.findByCalories();
    }

}
