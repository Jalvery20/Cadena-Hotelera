package com.jalvery20.cadena_hotelera_visual;

public class ObservableJefePiso {

    private String ID;
    private int numeroHabitaciones=0;

    private String hotel;

    public ObservableJefePiso(String ID, int numeroHabitaciones, String hotel) {
        this.numeroHabitaciones = numeroHabitaciones;
        this.hotel = hotel;
        this.ID=ID;
    }

    public int getNumeroHabitaciones() {
        return numeroHabitaciones;
    }

    public String getHotel() {
        return hotel;
    }

    public String getID() {
        return ID;
    }
}
