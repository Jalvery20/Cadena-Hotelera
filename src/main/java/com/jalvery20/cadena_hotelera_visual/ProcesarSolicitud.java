package com.jalvery20.cadena_hotelera_visual;

import com.jalvery20.cadena_hotelera_visual.classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class ProcesarSolicitud extends TimerTask {

    private boolean isDisponible(String nombreHotel,String num){
        Sistema sis=new Sistema();
        boolean isDisponible=false;
        for(Hotel hotel:sis.getListaHoteles()){
            if(hotel.getNombre().equals(nombreHotel)){
                for(Habitación habitación:hotel.getHabitaciones()){
                    if(habitación.getNumero().equals(num) && habitación.getEstado().equals("Disponible")){
                        isDisponible=true;
                    }
                }
            }

        }
        return isDisponible;
    }

    private Habitación habitacionOcupadaAnteriormente(String clienteID,String rangoSolicitado,String hotel){
        Sistema sis=new Sistema();
        Habitación habitación=null;
        for(Cliente cliente:sis.getListaClientes()){
            if(cliente.getIdNum().equals(clienteID)){
                for(Habitación hab:cliente.getHab_que_ha_estado()){
                    if(hotel!=null){
                        if(hab.getHotel()!=null&& hab.getHotel().equals(hotel)){
                            if (rangoSolicitado.equals(hab.getRango())) {
                                if(isDisponible(hotel,hab.getNumero())){
                                    habitación=hab;
                                }
                            }
                        }
                    }else{
                        if (rangoSolicitado.equals(hab.getRango())) {
                            if(isDisponible(hab.getHotel(),hab.getNumero())){
                                habitación=hab;
                            }
                        }
                    }

                }

            }
        }
        return habitación;
    }
    @Override
    public void run() {
        Sistema sis=new Sistema();
        List<Solicitud> listaSolicitudes=sis.getListaSolicitudes();

        List<Solicitud> listaSolicitudesVIP=new ArrayList<>();
        List<Solicitud> listaSolicitudesRegulares=new ArrayList<>();



        //Separar los clientes VIP del resto
        for(Solicitud solicitud:listaSolicitudes){
            if(solicitud.getCliente().isVIP()){
                listaSolicitudesVIP.add(solicitud);
            }else{
                listaSolicitudesRegulares.add(solicitud);
            }
        }

        int indiceVIP=0;

        int indiceRegular=0;

        while(indiceVIP<listaSolicitudesVIP.size()||indiceRegular<listaSolicitudesRegulares.size()){

            //Procesa 3 solicitudes VIP
            for(int i=0;i<3&&indiceVIP<listaSolicitudesVIP.size();i++){
                Solicitud solicitud=listaSolicitudesVIP.get(indiceVIP);

               Habitación habitacionOcupadaAnteriormente=habitacionOcupadaAnteriormente(solicitud.getCliente().getIdNum(),solicitud.getRango(),solicitud.getHotel());

                if(habitacionOcupadaAnteriormente!=null){
                    Reserva newReserva=new Reserva(solicitud.getCliente().getIdNum(),habitacionOcupadaAnteriormente.getNumero(),habitacionOcupadaAnteriormente.getRango(),habitacionOcupadaAnteriormente.getHotel());

                    sis.addReserva(newReserva,solicitud.getCliente());

                }else{
                    Habitación habitacionDisponible=null;
                    for (Hotel hotel: sis.getListaHoteles()){

                        if(solicitud.getHotel()!=null ){
                            if(hotel.getNombre().equals(solicitud.getHotel())){
                                for(Habitación habitación:hotel.getHabitaciones()){
                                    if(habitación.getRango().equals(solicitud.getRango()) && habitación.getEstado().equals("Disponible")){
                                        habitacionDisponible=habitación;
                                    }
                                }
                            }

                        }else{
                            for(Habitación habitación:hotel.getHabitaciones()){
                                if(habitación.getRango().equals(solicitud.getRango()) && habitación.getEstado().equals("Disponible")){

                                    habitacionDisponible=habitación;
                                }
                            }
                        }

                    }
                    if(habitacionDisponible!=null){
                        Reserva newReserva=new Reserva(solicitud.getCliente().getIdNum(),habitacionDisponible.getNumero(),habitacionDisponible.getRango(),habitacionDisponible.getHotel());

                        sis.addReserva(newReserva,solicitud.getCliente());
                    }else{
                        sis.addSolicitudRechazada(solicitud);
                    }
                }

                indiceVIP++;
            }

            //Procesa una solicitud del Cliente Regular
            if(indiceRegular<listaSolicitudesRegulares.size()){
                Solicitud solicitud=listaSolicitudesRegulares.get(indiceRegular);


                Habitación habitacionOcupadaAnteriormente=habitacionOcupadaAnteriormente(solicitud.getCliente().getIdNum(),solicitud.getRango(),solicitud.getHotel());

                if(habitacionOcupadaAnteriormente!=null){
                    Reserva newReserva=new Reserva(solicitud.getCliente().getIdNum(),habitacionOcupadaAnteriormente.getNumero(),habitacionOcupadaAnteriormente.getRango(),habitacionOcupadaAnteriormente.getHotel());

                    sis.addReserva(newReserva,solicitud.getCliente());
                }else{
                    Habitación habitacionDisponible=null;
                    for (Hotel hotel: sis.getListaHoteles()){
                        if(solicitud.getHotel()!=null ){
                            if( hotel.getNombre().equals(solicitud.getHotel())){
                                for(Habitación habitación:hotel.getHabitaciones()){
                                    if(habitación.getRango().equals(solicitud.getRango()) && habitación.getEstado().equals("Disponible")){
                                        habitacionDisponible=habitación;
                                    }
                                }
                            }
                        }else{
                            for(Habitación habitación:hotel.getHabitaciones()){
                                if(habitación.getRango().equals(solicitud.getRango()) && habitación.getEstado().equals("Disponible")){
                                    habitacionDisponible=habitación;
                                }
                            }
                        }

                    }
                    if(habitacionDisponible!=null){
                        Reserva newReserva=new Reserva(solicitud.getCliente().getIdNum(),habitacionDisponible.getNumero(),habitacionDisponible.getRango(),habitacionDisponible.getHotel());

                        sis.addReserva(newReserva,solicitud.getCliente());
                    }else{
                        sis.addSolicitudRechazada(solicitud);
                    }
                }

                indiceRegular++;
            }
        }
    }
}
