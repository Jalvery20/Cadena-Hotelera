package com.jalvery20.cadena_hotelera_visual.classes;


public class Habitación {
    private String numero;

    private String hotel;
    private String rango;
    private String empleadoID;
    private String estado;



    public Habitación(String hotel) {
        this.hotel = hotel;
    }

    public Habitación(String numero, String hotel, String rango, String empleadoID, String estado) {

        this.numero = numero;
        this.hotel =hotel;
        this.rango = rango;
        this.empleadoID = empleadoID;
        this.estado = estado;
    }

    public String getHotel() {
        return hotel;
    }

    public String getRango() {
        return rango;
    }

    public String getEmpleadoID() {
        return empleadoID;
    }

    public String getEstado() {
        return estado;
    }

    public String getNumero() {
        return numero;
    }
    public void setEmpleadoID(String empleadoID) {
        this.empleadoID = empleadoID;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
