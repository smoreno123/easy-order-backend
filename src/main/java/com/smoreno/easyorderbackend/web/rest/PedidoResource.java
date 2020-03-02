package com.smoreno.easyorderbackend.web.rest;

import com.smoreno.easyorderbackend.domain.ItemPedido;
import com.smoreno.easyorderbackend.domain.Menu;
import com.smoreno.easyorderbackend.domain.Pedido;
import com.smoreno.easyorderbackend.repository.MenuRepository;
import com.smoreno.easyorderbackend.repository.PedidoRepository;
import com.smoreno.easyorderbackend.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;

/**
 * REST controller for managing {@link com.smoreno.easyorderbackend.domain.Pedido}.
 */
@RestController
@RequestMapping("/api")
public class PedidoResource {

    private final Logger log = LoggerFactory.getLogger(PedidoResource.class);

    private static final String ENTITY_NAME = "pedido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PedidoRepository pedidoRepository;
    private final MenuRepository menuRepository;


    public PedidoResource(PedidoRepository pedidoRepository, MenuRepository menuRepository) {
        this.pedidoRepository = pedidoRepository;
        this.menuRepository = menuRepository;
    }

    /**
     * {@code POST  /pedidos} : Create a new pedido.
     *
     * @param pedido the pedido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pedido, or with status {@code 400 (Bad Request)} if the pedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pedidos")
    @Transactional
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) throws URISyntaxException {
        //establecemos el menu del pedido a traves de la fecha en la que se ha realizado, siempre que no haya ningun menu puesto
        if (pedido.getMenus().size()==0){
            pedido.setMenus(menuRepository.findByFechaInicioBeforeAndFechaFinalAfter(pedido.getFechaPedido(), pedido.getFechaPedido()));
        }

        pedido.setMesa(null);
        log.debug("REST request to save Pedido : {}", pedido);
        if (pedido.getId() != null) {
            throw new BadRequestAlertException("A new pedido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pedido result = pedidoRepository.save(pedido);
        return ResponseEntity.created(new URI("/api/pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pedidos} : Updates an existing pedido.
     *
     * @param pedido the pedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pedido,
     * or with status {@code 400 (Bad Request)} if the pedido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pedidos")
    public ResponseEntity<Pedido> updatePedido(@RequestBody Pedido pedido) throws URISyntaxException {
        log.debug("REST request to update Pedido : {}", pedido);
        if (pedido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pedido result = pedidoRepository.save(pedido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pedido.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pedidos} : get all the pedidos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pedidos in body.
     */
    @GetMapping("/pedidos")
    public List<Pedido> getAllPedidos(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Pedidos");
        return pedidoRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /pedidos/:id} : get the "id" pedido.
     *
     * @param id the id of the pedido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pedido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pedidos/{id}")
    public ResponseEntity<Pedido> getPedido(@PathVariable Long id) {
        log.debug("REST request to get Pedido : {}", id);
        Optional<Pedido> pedido = pedidoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(pedido);
    }

    /**
     * {@code DELETE  /pedidos/:id} : delete the "id" pedido.
     *
     * @param id the id of the pedido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        log.debug("REST request to delete Pedido : {}", id);
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("pedidos/masPedido")
    public List<ItemPedido> getMasPedido(){
        Pageable top5 = PageRequest.of(0,5);
        List<ItemPedido> itemPedido = pedidoRepository.findByMasPedido(top5);

        return itemPedido;
    }

    //Obtener la lista de los platos que sean de menu
    @GetMapping("pedidos/esMenu/{esMenu}")
    public Integer getEsMenu(@PathVariable Boolean esMenu){
        return pedidoRepository.findByEsMenu(esMenu);
    }
}
