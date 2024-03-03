package com.jalvery20.cadena_hotelera_visual.classes;

import java.util.ArrayList;
import java.util.List;

public class Jefe_de_Piso {

    private String ID;
    private List<String> listaNumeroHabitaciones=new ArrayList<>();

    private String hotel;


    public Jefe_de_Piso(List<String> listaNumeroHabitaciones, String hotel,String ID) {
        this.listaNumeroHabitaciones = listaNumeroHabitaciones;
        this.hotel = hotel;
        this.ID = ID;
    }

    public Jefe_de_Piso(String hotel,String ID) {
        this.hotel = hotel;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public List<String> getListaNumeroHabitaciones() {
        return listaNumeroHabitaciones;
    }

    public String getHotel() {
        return hotel;
    }
}
