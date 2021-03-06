package com.smoreno.easyorderbackend.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.smoreno.easyorderbackend.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.smoreno.easyorderbackend.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.smoreno.easyorderbackend.domain.User.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.Authority.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.User.class.getName() + ".authorities");
            createCache(cm, com.smoreno.easyorderbackend.domain.ItemPedido.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.ItemPedido.class.getName() + ".tipoItemPedidos");
            createCache(cm, com.smoreno.easyorderbackend.domain.ItemPedido.class.getName() + ".tipoCocinas");
            createCache(cm, com.smoreno.easyorderbackend.domain.ItemPedido.class.getName() + ".ingredientes");
            createCache(cm, com.smoreno.easyorderbackend.domain.ItemPedido.class.getName() + ".menus");
            createCache(cm, com.smoreno.easyorderbackend.domain.ItemPedido.class.getName() + ".valoracions");
            createCache(cm, com.smoreno.easyorderbackend.domain.ItemPedido.class.getName() + ".statuses");
            createCache(cm, com.smoreno.easyorderbackend.domain.ItemPedido.class.getName() + ".pedidos");
            createCache(cm, com.smoreno.easyorderbackend.domain.Pedido.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.Pedido.class.getName() + ".menus");
            createCache(cm, com.smoreno.easyorderbackend.domain.Pedido.class.getName() + ".itemPedidos");
            createCache(cm, com.smoreno.easyorderbackend.domain.Pedido.class.getName() + ".valoracions");
            createCache(cm, com.smoreno.easyorderbackend.domain.Pedido.class.getName() + ".pagos");
            createCache(cm, com.smoreno.easyorderbackend.domain.Pedido.class.getName() + ".statuses");
            createCache(cm, com.smoreno.easyorderbackend.domain.Ingrediente.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.Ingrediente.class.getName() + ".items");
            createCache(cm, com.smoreno.easyorderbackend.domain.Mesa.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.Mesa.class.getName() + ".pedidos");
            createCache(cm, com.smoreno.easyorderbackend.domain.Valoracion.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.TipoCocina.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.TipoCocina.class.getName() + ".items");
            createCache(cm, com.smoreno.easyorderbackend.domain.PagoPedido.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.TipoItemPedido.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.TipoItemPedido.class.getName() + ".items");
            createCache(cm, com.smoreno.easyorderbackend.domain.Menu.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.Menu.class.getName() + ".items");
            createCache(cm, com.smoreno.easyorderbackend.domain.Menu.class.getName() + ".pedidos");
            createCache(cm, com.smoreno.easyorderbackend.domain.StatusItemPedido.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.Comensal.class.getName());
            createCache(cm, com.smoreno.easyorderbackend.domain.Comensal.class.getName() + ".valoracions");
            createCache(cm, com.smoreno.easyorderbackend.domain.Comensal.class.getName() + ".pagos");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
