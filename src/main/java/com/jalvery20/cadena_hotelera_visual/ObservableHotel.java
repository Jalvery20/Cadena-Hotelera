package com.jalvery20.cadena_hotelera_visual;

public class ObservableHotel {

    private String nombre;
    private int numHabitaciones;

    private int numEmpleados;

    public ObservableHotel(String nombre, int numHabitaciones,int numEmpleados) {
        this.nombre = nombre;
        this.numHabitaciones = numHabitaciones;
        this.numEmpleados=numEmpleados;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumHabitaciones(int numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    public int getNumEmpleados() {
        return numEmpleados;
    }
}
