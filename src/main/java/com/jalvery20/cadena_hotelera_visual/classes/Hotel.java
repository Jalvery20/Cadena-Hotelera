package com.jalvery20.cadena_hotelera_visual.classes;

import java.util.ArrayList;
import java.util.List;


public class Hotel {
    String nombre;
    List<Habitación> habitaciones=new ArrayList<>();

    public Hotel() {
    }

    public Hotel(String nombre) {
        this.nombre = nombre;
    }


    public Hotel(String nombre, ArrayList<Habitación> habitaciones) {
        this.nombre = nombre;
        this.habitaciones=habitaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Habitación> getHabitaciones() {
        return habitaciones;
    }


    public void addHabitacion(Habitación newHab){
        habitaciones.add(newHab);

    }

    public void removeHabitacion(String num)   {

        for(int i=0;i<habitaciones.size();i++){
            if(habitaciones.get(i).getNumero().equals(num)){
                habitaciones.remove(i);
            }

        }


    }

    public void removeHabitacion()   {

        habitaciones.clear();


    }



}

