package com.jalvery20.cadena_hotelera_visual.classes;

public class Empleado {
    private String nombre;
    private String ID;

    private String hotel;


    public Empleado(String nombre, String id, String hotel) {
        this.nombre = nombre;
        this.ID = id;
        this.hotel = hotel;
    }



    public String getNombre() {
        return nombre;
    }

    public String getID() {
        return ID;
    }

    public String getHotel() {
        return hotel;
    }
}
