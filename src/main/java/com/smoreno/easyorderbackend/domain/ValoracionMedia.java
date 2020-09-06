package com.smoreno.easyorderbackend.domain;

public class ValoracionMedia {
    private ItemPedido itemPedido;
    private double media;

    public ValoracionMedia(ItemPedido itemPedido, double media) {
        this.itemPedido = itemPedido;
        this.media = media;
    }

    public ItemPedido getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(ItemPedido itemPedido) {
        this.itemPedido = itemPedido;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }
}
