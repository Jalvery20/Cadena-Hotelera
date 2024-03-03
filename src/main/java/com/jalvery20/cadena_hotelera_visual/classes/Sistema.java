package com.jalvery20.cadena_hotelera_visual.classes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sistema {

    private  List<Hotel> listaHoteles=new ArrayList<>();

    private  List<Empleado> listaEmpleados=new ArrayList<>();

    private  List<String> listaClientesVIP=new ArrayList<>();
    private  List<String> listaAdminID=new ArrayList<>();

    private  List<Solicitud> listaSolicitudes=new ArrayList<>();

    private List<Reserva> listaReservas=new ArrayList<>();

    private List<Cliente> listaClientes=new ArrayList<>();

    private List<Solicitud> listaSolicitudesRechazadas=new ArrayList<>();


    private List<Jefe_de_Piso> listaJefesPiso=new ArrayList<>();





    public Sistema(){

        JSONParser jsonParser = new JSONParser();

         new File("./data/").mkdir();


        //Leer lista de Hoteles
        try(FileReader reader=new FileReader("./data/listaHoteles.json")){
            Object obj=jsonParser.parse(reader);
            JSONArray jsonArray=(JSONArray) obj;

            //Crear una lista para almacenar los objetos
            List<List<JSONObject>> objectLists=new ArrayList<>();

            //Recorrer el array de arrays
            for(int i=0;i<jsonArray.size();i++){
                JSONArray array=(JSONArray) jsonArray.get(i);

                //Crear una lista para almacenar los objetos del array actual
                List<JSONObject> objects=new ArrayList<>();

                //Recorrer el array actual y agregar cada objeto a la lista
                for(int j=0;j<((JSONArray) jsonArray.get(i)).size();j++) {
                objects.add((JSONObject) array.get(j));
                }

                //Agregar la lista de objetos al array de listas
                objectLists.add(objects);
            }

            //Iterar sobre el objectList y acceder a los objetos
            for (List<JSONObject> objectList:objectLists){
                //Inicializar la clase Hotel
                Hotel newHotel=new Hotel();
                for (JSONObject object:objectList){
                    //Acceder a las prop del objeto

                    newHotel.setNombre((String) object.get("hotel"));

                    String numero=(String) object.get("numero");
                    String empleadoID=(String) object.get("empleadoID");
                    String hotel=(String)object.get("hotel");
                    String rango=(String)object.get("rango");

                    String estado=(String) object.get("estado");
                    Habitación hab=new Habitación(numero,hotel,rango,empleadoID,estado);
                    newHotel.addHabitacion(hab);

                }
                listaHoteles.add(newHotel);

            }
        }catch(FileNotFoundException e){
            try(FileWriter file=new FileWriter("./data/listaHoteles.json");){
                JSONArray array=new JSONArray();
                file.write(array.toJSONString());
                file.flush();

                } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }

        //Leer lista de Clientes VIP
        try(FileReader reader=new FileReader("./data/listaClientesVIP.json")){
            Object obj=jsonParser.parse(reader);
            JSONArray lista=(JSONArray) obj;
            listaClientesVIP=lista;
        }catch(FileNotFoundException e){

            try(FileWriter file=new FileWriter("./data/listaClientesVIP.json");){
                JSONArray array=new JSONArray();
                file.write(array.toJSONString());
                file.flush();
            }catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        }catch(IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }


        //Leer lista de Admin ID
        try(FileReader reader=new FileReader("./data/listaAdminID.json")){
            Object obj=jsonParser.parse(reader);
            JSONArray lista=(JSONArray) obj;
            listaAdminID=lista;
        }catch(FileNotFoundException e){

            try(FileWriter file=new FileWriter("./data/listaAdminID.json");){
                JSONArray array=new JSONArray();
                array.add("01042670565");
                file.write(array.toJSONString());
                file.flush();
            }catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        }catch(IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }

        //Leer lista de Empleado
        try(FileReader reader=new FileReader("./data/listaEmpleados.json")){
            Object obj=jsonParser.parse(reader);
            JSONArray jsonArray=(JSONArray) obj;


            for(int i=0;i<jsonArray.size();i++){
                JSONObject empleado=(JSONObject) jsonArray.get(i);
                    String ID=(String) empleado.get("ID");
                    String nombre=(String) empleado.get("nombre");
                    String hotel=(String) empleado.get("hotel");

                    Empleado newEmpleado=new Empleado(nombre,ID,hotel);

                    listaEmpleados.add(newEmpleado);



            }

        }catch(FileNotFoundException e){
            try(FileWriter file=new FileWriter("./data/listaEmpleados.json");){
                JSONArray array=new JSONArray();
                file.write(array.toJSONString());
                file.flush();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }



        //Leer Lista de Solicitudes
        try(FileReader reader=new FileReader("./data/listaSolicitudes.json")){
            Object obj=jsonParser.parse(reader);
            JSONArray jsonArray=(JSONArray) obj;


            for(int i=0;i<jsonArray.size();i++){
                JSONObject solicitud=(JSONObject) jsonArray.get(i);

                JSONObject cliente=(JSONObject) solicitud.get("cliente");

                String clienteNumId=(String) cliente.get("idNum");
                String clienteNombre=(String) cliente.get("nombre");
                boolean clienteIsVIP=(boolean) cliente.get("isVIP");


                JSONArray clienteHab=(JSONArray) cliente.get("hab_que_ha_estado");
                List<Habitación> listaHabitaciones=new ArrayList<>();
                for(int j=0;j<clienteHab.size();j++){
                    JSONObject habitacion=(JSONObject) clienteHab.get(j);
                    String numero=(String) habitacion.get("numero");
                    String hotel=(String) habitacion.get("hotel");
                    String rango=(String) habitacion.get("rango");
                    String empleadoID=(String) habitacion.get("empleadoID");
                    String estado=(String) habitacion.get("estado");

                    Habitación habitación=new Habitación(numero,hotel,rango,empleadoID,estado);
                    listaHabitaciones.add(habitación);
                }

                String hotel=(String) solicitud.get("hotel");
                String rango=(String) solicitud.get("rango");

                Cliente newCliente=new Cliente(clienteNumId,clienteNombre, clienteIsVIP, listaHabitaciones);
                Solicitud newSolicitud;
                if(hotel==null){
                    newSolicitud=new Solicitud(newCliente,rango);
                }else{
                    newSolicitud=new Solicitud(newCliente,hotel,rango);
                }
                listaSolicitudes.add(newSolicitud);
            }

        }catch(FileNotFoundException e){
            try(FileWriter file=new FileWriter("./data/listaSolicitudes.json");){
                JSONArray array=new JSONArray();
                file.write(array.toJSONString());
                file.flush();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }

        //Leer Lista de Clientes
        try(FileReader reader=new FileReader("./data/listaClientes.json")){
            Object obj=jsonParser.parse(reader);
            JSONArray jsonArray=(JSONArray) obj;

            for(int i=0;i<jsonArray.size();i++){
                JSONObject cliente=(JSONObject) jsonArray.get(i);
                String idNum=(String) cliente.get("idNum");
                String nombre=(String) cliente.get("nombre");
                boolean isVIP=(boolean) cliente.get("isVIP");

                List<Habitación> hab_que_ha_estado=new ArrayList<>();
                JSONArray habArray=(JSONArray) cliente.get("hab_que_ha_estado");

                for(int j=0;j<habArray.size();j++){
                    JSONObject hab=(JSONObject) jsonArray.get(i);

                    String numero=(String) hab.get("numero");
                    String hotel=(String) hab.get("hotel");
                    String rango=(String) hab.get("rango");
                    String empleadoID=(String) hab.get("empleadoID");
                    String estado=(String) hab.get("estado");

                    Habitación newHab=new Habitación(numero,hotel,rango,empleadoID,estado);

                    hab_que_ha_estado.add(newHab);
                }
                Cliente newCliente=new Cliente(idNum,nombre, isVIP,hab_que_ha_estado);

                listaClientes.add(newCliente);
            }

        }catch(FileNotFoundException e){
            try(FileWriter file=new FileWriter("./data/listaClientes.json");){
                JSONArray array=new JSONArray();
                file.write(array.toJSONString());
                file.flush();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }


        //Leer Lista de Reservas
        try(FileReader reader=new FileReader("./data/listaReservas.json")){
            Object obj=jsonParser.parse(reader);
            JSONArray jsonArray=(JSONArray) obj;

            for(int i=0;i<jsonArray.size();i++){
                JSONObject reserva=(JSONObject) jsonArray.get(i);
                String idNum=(String) reserva.get("ID");
                String numeroHab=(String) reserva.get("numeroHab");
                String tipoHab=(String) reserva.get("tipoHab");
                String hotel=(String) reserva.get("Hotel");

                Reserva newReserva=new Reserva(idNum,numeroHab,tipoHab,hotel);
                listaReservas.add(newReserva);
            }

        }catch(FileNotFoundException e){
            try(FileWriter file=new FileWriter("./data/listaReservas.json");){
                JSONArray array=new JSONArray();
                file.write(array.toJSONString());
                file.flush();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }


        //Leer lista de lista Solicitudes Rechazadas
        try(FileReader reader=new FileReader("./data/listaSolicitudesRechazadas.json")){
            Object obj=jsonParser.parse(reader);
            JSONArray listaArray=(JSONArray) obj;

            for(int i=0;i<listaArray.size();i++){
                JSONObject solicitud=(JSONObject) listaArray.get(i);

                JSONObject cliente=(JSONObject) solicitud.get("cliente");

                String clienteNumId=(String) cliente.get("idNum");
                String clienteNombre=(String) cliente.get("nombre");
                boolean clienteIsVIP=(boolean) cliente.get("isVIP");


                JSONArray clienteHab=(JSONArray) cliente.get("hab_que_ha_estado");
                List<Habitación> listaHabitaciones=new ArrayList<>();
                for(int j=0;j<clienteHab.size();j++){
                    JSONObject habitacion=(JSONObject) clienteHab.get(j);
                    String numero=(String) habitacion.get("numero");
                    String hotel=(String) habitacion.get("hotel");
                    String rango=(String) habitacion.get("rango");
                    String empleadoID=(String) habitacion.get("empleadoID");
                    String estado=(String) habitacion.get("estado");

                    Habitación habitación=new Habitación(numero,hotel,rango,empleadoID,estado);
                    listaHabitaciones.add(habitación);
                }

                String hotel=(String) solicitud.get("hotel");
                String rango=(String) solicitud.get("rango");

                Cliente newCliente=new Cliente(clienteNumId,clienteNombre, clienteIsVIP, listaHabitaciones);
                Solicitud newSolicitud;
                if(hotel==null){
                    newSolicitud=new Solicitud(newCliente,rango);
                }else{
                    newSolicitud=new Solicitud(newCliente,hotel,rango);
                }
                listaSolicitudesRechazadas.add(newSolicitud);

            }

        }catch(FileNotFoundException e){
            try(FileWriter file=new FileWriter("./data/listaSolicitudesRechazadas.json");){
                JSONArray array=new JSONArray();
                file.write(array.toJSONString());
                file.flush();
            }catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        }catch(IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }


        //Leer Lista de Jefes de Piso
        try(FileReader reader=new FileReader("./data/listaJefesPiso.json")){
            Object obj=jsonParser.parse(reader);
            JSONArray jsonArray=(JSONArray) obj;

            for(int i=0;i<jsonArray.size();i++){
                JSONObject jefePiso=(JSONObject) jsonArray.get(i);

                JSONArray numHabitacionArray=(JSONArray) jefePiso.get("listaNumeroHabitaciones");
                List<String> numHabitaciones=numHabitacionArray;
                String hotel=(String) jefePiso.get("hotel");
                String ID=(String) jefePiso.get("ID");

                Jefe_de_Piso newJefePiso=new Jefe_de_Piso(numHabitaciones,hotel,ID);


                listaJefesPiso.add(newJefePiso);
            }

        }catch(FileNotFoundException e){
            try(FileWriter file=new FileWriter("./data/listaJefesPiso.json");){
                JSONArray array=new JSONArray();
                file.write(array.toJSONString());
                file.flush();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    private void addCliente(Cliente newCliente,Habitación newHabitacion){
        boolean isThere=false;
        for (Cliente cliente:listaClientes){
            if(cliente.getIdNum().equals(newCliente.getIdNum())){
                isThere=true;
                boolean isHabitacion=false;
                for (Habitación habitación:cliente.getHab_que_ha_estado()){
                    if(habitación.getHotel()!=null&&(habitación.getHotel().equals(newHabitacion.getHotel()) && habitación.getNumero().equals(newHabitacion.getNumero()))){
                        isHabitacion=true;
                    }
                }
                if(!isHabitacion){
                    cliente.getHab_que_ha_estado().add(newHabitacion);
                }

            }
        }
        if(!isThere){
            newCliente.getHab_que_ha_estado().add(newHabitacion);
            listaClientes.add(newCliente);
        }


        //Actualizar el Json de CLientes

        JSONArray clientesArray = new JSONArray();


        for (Cliente cliente:listaClientes){

            JSONObject clienteJson=new JSONObject();

            clienteJson.put("idNum",cliente.getIdNum());
            clienteJson.put("nombre",cliente.getNombre());

            JSONArray habitacionesArray=new JSONArray();

            for(Habitación habitación:cliente.getHab_que_ha_estado()){

                JSONObject habitacionJson=new JSONObject();
                if(habitación.getNumero()!=null){
                    habitacionJson.put("numero",habitación.getNumero());
                    habitacionJson.put("estado",habitación.getEstado());
                    habitacionJson.put("rango",habitación.getRango());
                    habitacionJson.put("empleadoID",habitación.getEmpleadoID());
                    habitacionJson.put("hotel",habitación.getHotel());
                }

                habitacionesArray.add(habitacionJson);
            }

            clienteJson.put("hab_que_ha_estado",habitacionesArray);
            clienteJson.put("isVIP",cliente.isVIP());


            clientesArray.add(clienteJson);
        }
        try(FileWriter file= new FileWriter("./data/listaClientes.json")) {
            file.write(clientesArray.toJSONString());
            file.flush();

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    private void removeSolicitud(String clienteID){

            int indiceCliente=-1;
            for(int i=0;i<listaSolicitudes.size();i++){
                Solicitud solicitud=listaSolicitudes.get(i);
                if(solicitud.getCliente().getIdNum().equals(clienteID)){
                    indiceCliente=i;
                }
            }
            if(indiceCliente!=-1){
                listaSolicitudes.remove(indiceCliente);
            }

        //Actualizar el Json

        JSONArray solicitudesArray = new JSONArray();

        for (Solicitud solicitud:listaSolicitudes){

            JSONObject solicitudJson=new JSONObject();

            JSONObject clienteObjectJson=new JSONObject();

            clienteObjectJson.put("idNum",solicitud.getCliente().getIdNum());
            clienteObjectJson.put("nombre",solicitud.getCliente().getNombre());

            clienteObjectJson.put("isVIP",solicitud.getCliente().isVIP());

            JSONArray habVisitArray=new JSONArray();
            for(Habitación habitación:solicitud.getCliente().getHab_que_ha_estado()){
                if(habitación.getNumero()!=null){
                    JSONObject habitacionJson=new JSONObject();

                    habitacionJson.put("numero",habitación.getNumero());
                    habitacionJson.put("estado",habitación.getEstado());
                    habitacionJson.put("rango",habitación.getRango());
                    habitacionJson.put("empleadoID",habitación.getEmpleadoID());
                    habitacionJson.put("hotel",habitación.getHotel());

                    habVisitArray.add(habitacionJson);
                }
            }

            clienteObjectJson.put("hab_que_ha_estado",habVisitArray);

            solicitudJson.put("cliente",clienteObjectJson);
            solicitudJson.put("hotel",solicitud.getHotel());
            solicitudJson.put("rango",solicitud.getRango());
            solicitudesArray.add(solicitudJson);

        }
        try(FileWriter file= new FileWriter("./data/listaSolicitudes.json")) {
            file.write(solicitudesArray.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void addReserva(Reserva newReserva,Cliente cliente){
        Reserva reservDel=null;
        for (Reserva reserva:listaReservas){

            if(reserva.getID().equals(cliente.getIdNum())){
                reservDel=reserva;
            }
        }
        listaReservas.remove(reservDel);
        listaReservas.add(newReserva);


        //Cambiar el estado de la Habitacion
        for(Hotel hotel:listaHoteles){
            if(newReserva.getHotel().equals(hotel.getNombre())){
                for(Habitación habitación:hotel.getHabitaciones()){
                    if(habitación.getNumero().equals(newReserva.getNumeroHab())){
                        habitación.setEstado("Ocupada");

                    }
                }
            }
        }

        //Añadir el Cliente a nuestra lista de clientes
        for(Hotel hotel:listaHoteles){
            if(newReserva.getHotel().equals(hotel.getNombre())){
                for(Habitación habitación:hotel.getHabitaciones()){
                    if(habitación.getNumero().equals(newReserva.getNumeroHab())){
                        addCliente(cliente,habitación);
                    }
                }
            }
        }


        removeSolicitud(newReserva.getID());



        //Actualizar el Json de reservas

        JSONArray reservasArray = new JSONArray();

        for (Reserva reserva:listaReservas){

            JSONObject reservaJson=new JSONObject();

            reservaJson.put("ID",reserva.getID());
            reservaJson.put("numeroHab",reserva.getNumeroHab());
            reservaJson.put("tipoHab",reserva.getTipoHab());
            reservaJson.put("Hotel",reserva.getHotel());


            reservasArray.add(reservaJson);

        }
        try(FileWriter file= new FileWriter("./data/listaReservas.json")) {
            file.write(reservasArray.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }

        //Actualizar el Json de hoteles

        JSONArray Hoteles = new JSONArray();

        for (int i=0;i<listaHoteles.size();i++){
            JSONArray Habitaciones=new JSONArray();
            for (int j=0;j<listaHoteles.get(i).getHabitaciones().size();j++){
                JSONObject Habitacion=new JSONObject();
                Habitación hab=listaHoteles.get(i).getHabitaciones().get(j);

                Habitacion.put("numero",hab.getNumero());
                Habitacion.put("hotel",hab.getHotel());
                Habitacion.put("rango",hab.getRango());
                Habitacion.put("empleadoID",hab.getEmpleadoID());
                Habitacion.put("estado",hab.getEstado());

                Habitaciones.add(Habitacion);

            }
            Hoteles.add(Habitaciones);
        }
        try(FileWriter file= new FileWriter("./data/listaHoteles.json")) {
            file.write(Hoteles.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setHabitacionReparacion(String nombreHotel,String numeroHab){

        for (Hotel hotel:listaHoteles){
            if(hotel.getNombre().equals(nombreHotel)){
                for (Habitación hab:hotel.getHabitaciones()){
                    if(hab.getNumero().equals(numeroHab)){
                        hab.setEstado("Reparación");
                    }
                }
            }
        }

        //Actualizar el Json

        JSONArray Hoteles = new JSONArray();

        for (int i=0;i<listaHoteles.size();i++){
            JSONArray Habitaciones=new JSONArray();
            for (int j=0;j<listaHoteles.get(i).getHabitaciones().size();j++){
                JSONObject Habitacion=new JSONObject();
                Habitación hab=listaHoteles.get(i).getHabitaciones().get(j);

                Habitacion.put("numero",hab.getNumero());
                Habitacion.put("hotel",hab.getHotel());
                Habitacion.put("rango",hab.getRango());
                Habitacion.put("empleadoID",hab.getEmpleadoID());
                Habitacion.put("estado",hab.getEstado());

                Habitaciones.add(Habitacion);

            }
            Hoteles.add(Habitaciones);
        }
        try(FileWriter file= new FileWriter("./data/listaHoteles.json")) {
            file.write(Hoteles.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addSolicitud(Solicitud newSolicitud){

        listaSolicitudes.add(newSolicitud);

        //Actualizar el Json

        JSONArray solicitudesArray = new JSONArray();

        for (Solicitud solicitud:listaSolicitudes){

            JSONObject solicitudJson=new JSONObject();

            JSONObject clienteObjectJson=new JSONObject();

            clienteObjectJson.put("idNum",solicitud.getCliente().getIdNum());
            clienteObjectJson.put("nombre",solicitud.getCliente().getNombre());

            JSONArray habitacionArray=new JSONArray();
            for(Habitación habitación:solicitud.getCliente().getHab_que_ha_estado()){
                if(habitación.getNumero()!=null){
                    JSONObject habitacionJson=new JSONObject();

                    habitacionJson.put("numero",habitación.getNumero());
                    habitacionJson.put("estado",habitación.getEstado());
                    habitacionJson.put("rango",habitación.getRango());
                    habitacionJson.put("empleadoID",habitación.getEmpleadoID());
                    habitacionJson.put("hotel",habitación.getHotel());

                    habitacionArray.add(habitacionJson);
                }
            }
            clienteObjectJson.put("hab_que_ha_estado",habitacionArray);
            clienteObjectJson.put("isVIP",solicitud.getCliente().isVIP());


            solicitudJson.put("cliente",clienteObjectJson);
            solicitudJson.put("hotel",solicitud.getHotel());
            solicitudJson.put("rango",solicitud.getRango());
            solicitudesArray.add(solicitudJson);

        }
        try(FileWriter file= new FileWriter("./data/listaSolicitudes.json")) {
            file.write(solicitudesArray.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addHotel(Hotel newHotel){

        Habitación defaultHabitacion=new Habitación(newHotel.getNombre());
        newHotel.addHabitacion(defaultHabitacion);
        listaHoteles.add(newHotel);

        JSONArray Hoteles = new JSONArray();

        for (int i=0;i<listaHoteles.size();i++){
            JSONArray Habitaciones=new JSONArray();
            for (int j=0;j<listaHoteles.get(i).getHabitaciones().size();j++){
                JSONObject Habitacion=new JSONObject();
                Habitación hab=listaHoteles.get(i).getHabitaciones().get(j);

                Habitacion.put("numero",hab.getNumero());
                Habitacion.put("hotel",hab.getHotel());
                Habitacion.put("rango",hab.getRango());
                Habitacion.put("empleadoID",hab.getEmpleadoID());
                Habitacion.put("estado",hab.getEstado());

                Habitaciones.add(Habitacion);

            }
            Hoteles.add(Habitaciones);
        }
        try(FileWriter file= new FileWriter("./data/listaHoteles.json")) {
            file.write(Hoteles.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void removeHotel(String hotel){
        int indexOfHotel=-1;
        for (int i=0;i<listaHoteles.size();i++){
            if(listaHoteles.get(i).getNombre().equals(hotel)){
                indexOfHotel=i;
            }
        }
        listaHoteles.remove(indexOfHotel);


        //Actualizar el Json

        JSONArray Hoteles = new JSONArray();

        for (int i=0;i<listaHoteles.size();i++){
            JSONArray Habitaciones=new JSONArray();
            for (int j=0;j<listaHoteles.get(i).getHabitaciones().size();j++){
                JSONObject Habitacion=new JSONObject();
                Habitación hab=listaHoteles.get(i).getHabitaciones().get(j);

                Habitacion.put("numero",hab.getNumero());
                Habitacion.put("hotel",hab.getHotel());
                Habitacion.put("rango",hab.getRango());
                Habitacion.put("empleadoID",hab.getEmpleadoID());
                Habitacion.put("estado",hab.getEstado());

                Habitaciones.add(Habitacion);

            }
            Hoteles.add(Habitaciones);
        }
        try(FileWriter file= new FileWriter("./data/listaHoteles.json")) {
            file.write(Hoteles.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void addHabitacion(String nombreHotel,Habitación habitación){

        for(Hotel hotel:listaHoteles){
            if(hotel.getNombre().equals(nombreHotel)){
                if(hotel.getHabitaciones().get(0).getNumero()==null){
                    hotel.removeHabitacion();
                    hotel.addHabitacion(habitación);
                }else{
                    hotel.addHabitacion(habitación);
                }


            }
        }

        //Actualizar el Json

        JSONArray Hoteles = new JSONArray();

        for (int i=0;i<listaHoteles.size();i++){
            JSONArray Habitaciones=new JSONArray();
            for (int j=0;j<listaHoteles.get(i).getHabitaciones().size();j++){
                JSONObject Habitacion=new JSONObject();
                Habitación hab=listaHoteles.get(i).getHabitaciones().get(j);

                Habitacion.put("numero",hab.getNumero());
                Habitacion.put("hotel",hab.getHotel());
                Habitacion.put("rango",hab.getRango());
                Habitacion.put("empleadoID",hab.getEmpleadoID());
                Habitacion.put("estado",hab.getEstado());

                Habitaciones.add(Habitacion);

            }
            Hoteles.add(Habitaciones);
        }
        try(FileWriter file= new FileWriter("./data/listaHoteles.json")) {
            file.write(Hoteles.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void removeHabitacion(String nombreHotel,String numero){


        for(Hotel hotel:listaHoteles){
            if(hotel.getNombre().equals(nombreHotel)){
                    hotel.removeHabitacion(numero);
                }
        }

        //Actualizar el Json

        JSONArray Hoteles = new JSONArray();

        for(int i=0;i<listaHoteles.size();i++){
            JSONArray Habitaciones=new JSONArray();
            for (int j=0;j<listaHoteles.get(i).getHabitaciones().size();j++){
                JSONObject Habitacion=new JSONObject();
                Habitación hab=listaHoteles.get(i).getHabitaciones().get(j);

                Habitacion.put("numero",hab.getNumero());
                Habitacion.put("hotel",hab.getHotel());
                Habitacion.put("rango",hab.getRango());
                Habitacion.put("empleadoID",hab.getEmpleadoID());
                Habitacion.put("estado",hab.getEstado());

                Habitaciones.add(Habitacion);

            }
            Hoteles.add(Habitaciones);
        }
        try(FileWriter file= new FileWriter("./data/listaHoteles.json")) {
            file.write(Hoteles.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addClienteVIP(String ID){
        listaClientesVIP.add(ID);
        JSONArray array=new JSONArray();
        array.addAll(listaClientesVIP);

        try(FileWriter file= new FileWriter("./data/listaClientesVIP.json")) {
            file.write(array.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }



    public void removeClienteVIP(String ID){
        listaClientesVIP.remove(ID);

        JSONArray array=new JSONArray();
        array.addAll(listaClientesVIP);

        try(FileWriter file= new FileWriter("./data/listaClientesVIP.json")) {
            file.write(array.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addAdminID(String ID){
        listaAdminID.add(ID);
        JSONArray array=new JSONArray();
        array.addAll(listaAdminID);

        try(FileWriter file= new FileWriter("./data/listaAdminID.json")) {
            file.write(array.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void removeAdminID(String ID){
        listaAdminID.remove(ID);

        JSONArray array=new JSONArray();
        array.addAll(listaAdminID);

        try(FileWriter file= new FileWriter("./data/listaAdminID.json")) {
            file.write(array.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addEmpleado(Empleado newEmpleado){
        listaEmpleados.add(newEmpleado);

        //Actualizar el Json

        JSONArray empleadosArray = new JSONArray();

        for (Empleado empleado:listaEmpleados){

                JSONObject empleadoJson=new JSONObject();

                empleadoJson.put("ID",empleado.getID());
                empleadoJson.put("hotel",empleado.getHotel());
                empleadoJson.put("nombre",empleado.getNombre());
                empleadosArray.add(empleadoJson);


        }
        try(FileWriter file= new FileWriter("./data/listaEmpleados.json")) {
            file.write(empleadosArray.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void removeEmpleado(String hotelNombre,String ID){
        Empleado empleadoDel=null;
        for(Empleado empleado:listaEmpleados){
            if(empleado.getHotel().equals(hotelNombre)){
                if(empleado.getID().equals(ID)){
                   empleadoDel=empleado;
                }
            }

        }
        listaEmpleados.remove(empleadoDel);

        //Actualizar el Json

        JSONArray empleadosArray = new JSONArray();

        for (Empleado empleado:listaEmpleados){

            JSONObject empleadoJson=new JSONObject();

            empleadoJson.put("ID",empleado.getID());
            empleadoJson.put("hotel",empleado.getHotel());
            empleadoJson.put("nombre",empleado.getNombre());

            empleadosArray.add(empleadoJson);


        }
        try(FileWriter file= new FileWriter("./data/listaEmpleados.json")) {
            file.write(empleadosArray.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }



    public void setEmpleadoID(String nombreHotel,String empleadoID,String numeroHabitacion){
        for(Hotel hotel:listaHoteles){
            if(hotel.getNombre().equals(nombreHotel)){
                for(Habitación hab:hotel.getHabitaciones()){
                    if(hab.getNumero().equals(numeroHabitacion)){
                        hab.setEmpleadoID(empleadoID);
                    }
                }
            }
        }

        //Actualizar el Json

        JSONArray Hoteles = new JSONArray();

        for (int i=0;i<listaHoteles.size();i++){
            JSONArray Habitaciones=new JSONArray();
            for (int j=0;j<listaHoteles.get(i).getHabitaciones().size();j++){
                JSONObject Habitacion=new JSONObject();
                Habitación hab=listaHoteles.get(i).getHabitaciones().get(j);

                Habitacion.put("numero",hab.getNumero());
                Habitacion.put("hotel",hab.getHotel());
                Habitacion.put("rango",hab.getRango());
                Habitacion.put("empleadoID",hab.getEmpleadoID());
                Habitacion.put("estado",hab.getEstado());

                Habitaciones.add(Habitacion);

            }
            Hoteles.add(Habitaciones);
        }
        try(FileWriter file= new FileWriter("./data/listaHoteles.json")) {
            file.write(Hoteles.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void removeEmpleadoID(String nombreHotel,String numeroHabitacion){
        for(Hotel hotel:listaHoteles){
            if(hotel.getNombre().equals(nombreHotel)){
                for(Habitación hab:hotel.getHabitaciones()){
                    if(hab.getNumero().equals(numeroHabitacion)){
                        hab.setEmpleadoID(null);
                    }
                }
            }
        }

        //Actualizar el Json

        JSONArray Hoteles = new JSONArray();

        for (int i=0;i<listaHoteles.size();i++){
            JSONArray Habitaciones=new JSONArray();
            for (int j=0;j<listaHoteles.get(i).getHabitaciones().size();j++){
                JSONObject Habitacion=new JSONObject();
                Habitación hab=listaHoteles.get(i).getHabitaciones().get(j);

                Habitacion.put("numero",hab.getNumero());
                Habitacion.put("hotel",hab.getHotel());
                Habitacion.put("rango",hab.getRango());
                Habitacion.put("empleadoID",hab.getEmpleadoID());
                Habitacion.put("estado",hab.getEstado());

                Habitaciones.add(Habitacion);

            }
            Hoteles.add(Habitaciones);
        }
        try(FileWriter file= new FileWriter("./data/listaHoteles.json")) {
            file.write(Hoteles.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void addSolicitudRechazada(Solicitud newSolicitud){

        listaSolicitudesRechazadas.add(newSolicitud);

        removeSolicitud(newSolicitud.getCliente().getIdNum());


        //Actualizar el Json

        JSONArray solicitudesArray = new JSONArray();

        for (Solicitud solicitud:listaSolicitudesRechazadas){

            JSONObject solicitudJson=new JSONObject();

            JSONObject clienteObjectJson=new JSONObject();

            clienteObjectJson.put("idNum",solicitud.getCliente().getIdNum());
            clienteObjectJson.put("nombre",solicitud.getCliente().getNombre());

            JSONArray habVisitArray=new JSONArray();
            for(Habitación habitación:solicitud.getCliente().getHab_que_ha_estado()){
                if(habitación.getNumero()!=null){
                    JSONObject habitacionJson=new JSONObject();

                    habitacionJson.put("numero",habitación.getNumero());
                    habitacionJson.put("estado",habitación.getEstado());
                    habitacionJson.put("rango",habitación.getRango());
                    habitacionJson.put("empleadoID",habitación.getEmpleadoID());
                    habitacionJson.put("hotel",habitación.getHotel());

                    habVisitArray.add(habitacionJson);
                }
            }
            clienteObjectJson.put("hab_que_ha_estado",habVisitArray);
            clienteObjectJson.put("isVIP",solicitud.getCliente().isVIP());


            solicitudJson.put("cliente",clienteObjectJson);
            solicitudJson.put("hotel",solicitud.getHotel());
            solicitudJson.put("rango",solicitud.getRango());
            solicitudesArray.add(solicitudJson);

        }
        try(FileWriter file= new FileWriter("./data/listaSolicitudesRechazadas.json")) {
            file.write(solicitudesArray.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addJefePiso(Jefe_de_Piso newJefePiso){
        listaJefesPiso.add(newJefePiso);

        //actualizar json
        JSONArray jefesPisoArray = new JSONArray();

        for (Jefe_de_Piso jefePiso:listaJefesPiso){
            JSONObject jefePisoJson=new JSONObject();

            jefePisoJson.put("ID",jefePiso.getID());
            jefePisoJson.put("hotel",jefePiso.getHotel());
            jefePisoJson.put("listaNumeroHabitaciones",jefePiso.getListaNumeroHabitaciones());


            jefesPisoArray.add(jefePisoJson);
        }



        try(FileWriter file= new FileWriter("./data/listaJefesPiso.json")) {
            file.write(jefesPisoArray.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void removeJefePiso(String jefePisoID,String hotel){
        Jefe_de_Piso jefePisoDel=null;
        for(Jefe_de_Piso jefePiso:listaJefesPiso){
            if(jefePiso.getHotel().equals(hotel) && jefePiso.getID().equals(jefePisoID)){
                jefePisoDel=jefePiso;
            }
        }
        listaJefesPiso.remove(jefePisoDel);


        //actualizar json
        JSONArray jefesPisoArray = new JSONArray();

        for (Jefe_de_Piso jefePiso:listaJefesPiso){
            JSONObject jefePisoJson=new JSONObject();

            jefePisoJson.put("ID",jefePiso.getID());
            jefePisoJson.put("hotel",jefePiso.getHotel());
            jefePisoJson.put("listaNumeroHabitaciones",jefePiso.getListaNumeroHabitaciones());


            jefesPisoArray.add(jefePisoJson);
        }



        try(FileWriter file= new FileWriter("./data/listaJefesPiso.json")) {
            file.write(jefesPisoArray.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addHabitacionIDJefePiso(String nombreHotel,String numeroHab,String jefePisoID){

        for(Jefe_de_Piso jefePiso:listaJefesPiso){
            if(jefePiso.getHotel().equals(nombreHotel) && jefePisoID.equals(jefePiso.getID())){
                jefePiso.getListaNumeroHabitaciones().add(numeroHab);
            }

        }


        //actualizar json
        JSONArray jefesPisoArray = new JSONArray();

        for (Jefe_de_Piso jefePiso:listaJefesPiso){
            JSONObject jefePisoJson=new JSONObject();

            jefePisoJson.put("ID",jefePiso.getID());
            jefePisoJson.put("hotel",jefePiso.getHotel());
            jefePisoJson.put("listaNumeroHabitaciones",jefePiso.getListaNumeroHabitaciones());


            jefesPisoArray.add(jefePisoJson);
        }



        try(FileWriter file= new FileWriter("./data/listaJefesPiso.json")) {
            file.write(jefesPisoArray.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

        public List<Hotel> getListaHoteles() {
        return listaHoteles;
    }

    public List<String> getListaClientesVIP() {
        return listaClientesVIP;
    }
    public List<String> getListaAdminID() {
        return listaAdminID;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public List<Solicitud> getListaSolicitudes() {
        return listaSolicitudes;
    }

    public List<Reserva> getListaReservas() {
        return listaReservas;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public List<Solicitud> getListaSolicitudesRechazadas() {
        return listaSolicitudesRechazadas;
    }

    public List<Jefe_de_Piso> getListaJefesPiso() {
        return listaJefesPiso;
    }




}
