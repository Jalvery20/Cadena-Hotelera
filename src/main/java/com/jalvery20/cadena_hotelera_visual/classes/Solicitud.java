package com.jalvery20.cadena_hotelera_visual.classes;

public class Solicitud {
    private Cliente cliente;
    private String hotel;

    private String rango;


     public Solicitud(Cliente cliente, String hotelEnParticular, String rangoPedido) {
        this.cliente = cliente;
        this.hotel = hotelEnParticular;
        this.rango = rangoPedido;
    }

     Solicitud(Cliente cliente, String rango) {
        this.cliente = cliente;
        this.rango = rango;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getHotel() {
        return hotel;
    }

    public String getRango() {
        return rango;
    }
}
