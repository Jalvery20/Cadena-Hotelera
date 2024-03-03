package com.jalvery20.cadena_hotelera_visual.classes;

public class Reserva {

    private String ID;

    private String numeroHab;

    private String tipoHab;

    private String Hotel;

    public Reserva(String ID, String numeroHab, String tipoHab, String hotel) {
        this.ID = ID;
        this.numeroHab = numeroHab;
        this.tipoHab = tipoHab;
        Hotel = hotel;
    }

    public String getID() {
        return ID;
    }

    public String getNumeroHab() {
        return numeroHab;
    }

    public String getTipoHab() {
        return tipoHab;
    }

    public String getHotel() {
        return Hotel;
    }
}
