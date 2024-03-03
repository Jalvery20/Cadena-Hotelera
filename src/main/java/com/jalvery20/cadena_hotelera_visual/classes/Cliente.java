package com.jalvery20.cadena_hotelera_visual.classes;

import java.util.List;

public class Cliente {
    private String idNum;
    private String nombre;
    private boolean isVIP;

    private List<Habitación> hab_que_ha_estado;

    public Cliente(String idNum, String nombre, boolean isVIP,List<Habitación> hab_que_ha_estado) {
        this.idNum = idNum;
        this.nombre = nombre;
        this.isVIP = isVIP;
        this.hab_que_ha_estado=hab_que_ha_estado;
    }

    public String getIdNum() {
        return idNum;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public List<Habitación> getHab_que_ha_estado() {
        return hab_que_ha_estado;
    }
}
