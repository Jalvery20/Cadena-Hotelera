package com.jalvery20.cadena_hotelera_visual;

public class ObservableHabitacion {

    private String numero;


    private String rango;

    private String estado;
    private String empleadoID;
    private String hotel;

    private String jefePisoID;

    public ObservableHabitacion(String numero, String rango, String estado, String empleadoID, String hotel,String jefePisoID) {

        this.numero = numero;
        this.hotel =hotel;
        this.rango = rango;
        this.empleadoID = empleadoID;
        this.estado = estado;
        this.jefePisoID = jefePisoID;
    }

    public String getNumero() {
        return numero;
    }


    public String getRango() {
        return rango;
    }

    public String getEstado() {
        return estado;
    }


    public String getEmpleadoID() {
        return empleadoID;
    }


    public String getHotel() {
        return hotel;
    }

    public String getJefePisoID() {
        return jefePisoID;
    }
}
