package com.jalvery20.cadena_hotelera_visual;

public class ObservableEmpleado {

    private int cantDeHabitaciones;
    private String nombre;
    private String ID;

    private double cantHabitacionesPorcentaje;

    private String hotel;


    public ObservableEmpleado(String nombre, String id, String hotel,int cantDeHabitaciones,double cantHabitacionesPorcentaje) {
        this.nombre = nombre;
        this.ID = id;
        this.hotel = hotel;
        this.cantDeHabitaciones=cantDeHabitaciones;
        this.cantHabitacionesPorcentaje=cantHabitacionesPorcentaje;
    }

    public int getCantDeHabitaciones() {
        return cantDeHabitaciones;
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

    public double getCantHabitacionesPorcentaje() {
        return cantHabitacionesPorcentaje;
    }
}
