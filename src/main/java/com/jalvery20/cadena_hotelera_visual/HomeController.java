package com.jalvery20.cadena_hotelera_visual;

import com.jalvery20.cadena_hotelera_visual.classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private Scene scene;
    private Parent root;
    private Stage stage;

    @FXML
    ChoiceBox<String> homeChoiceRange,homeTypeChoice,homeHotelChoice;
    @FXML
    Label formTitle,variableLabel,roomLabel,reserLabel,carnetLabel;
    @FXML
    TextField variableField,carnetField;
    @FXML
    VBox hotelBox;
    @FXML
    Button reservationBut,adminBut,solicitudBut,acceBut;


    @FXML
    BorderPane ContainerBorderPane;




    String[] rangos = {"Individual", "Doble", "Triple", "Quad", "Queen", "King", "Suite"};
    String[] reservaTipo = {"Directamente al Hotel", "En la Cadena"};






    //Change the scene given a stage and an address
    public void changeScene(Stage stage,String sceneAdress) throws IOException {
        double x=stage.getWidth();
        double y=stage.getHeight();
        Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource(sceneAdress)));
        scene=new Scene(root);
        stage.setScene(scene);

        stage.setWidth(x);
        stage.setHeight(y);
        stage.show();
    }

    //Visualize for input the hotel
    public void checkReserva(ActionEvent e){
        String value=homeTypeChoice.getValue();
        if(value!=null && value.equals("Directamente al Hotel")){
            hotelBox.setVisible(true);

        }else{
            hotelBox.setVisible(false);
        }
    }


    //Change to Solicitude Form
    public void activeSolic(ActionEvent event)  {
        variableField.setText("");
        formTitle.setText("Revisar Solicitud");
        variableLabel.setText("Carnet de ID");
        variableField.setPromptText("Escriba su carnet");
        acceBut.setText("Revisar");
        roomLabel.setVisible(false);
        carnetField.setVisible(false);
        carnetLabel.setVisible(false);
        reserLabel.setVisible(false);
        homeChoiceRange.setVisible(false);

        hotelBox.setVisible(false);
        homeTypeChoice.setVisible(false);

        reservationBut.setDisable(false);
        solicitudBut.setDisable(true);
        adminBut.setDisable(false);
    }
    //Change to Admin Form
    public void activeAdmin(ActionEvent event) {
        variableField.setText("");
        formTitle.setText("Autenticar Admin");
        variableLabel.setText("Carnet de ID");
        variableField.setPromptText("Escriba su carnet");
        roomLabel.setVisible(false);
        acceBut.setText("Logear");
        reserLabel.setVisible(false);
        homeChoiceRange.setVisible(false);
        carnetField.setVisible(false);
        carnetLabel.setVisible(false);
        hotelBox.setVisible(false);
        homeTypeChoice.setVisible(false);
        reservationBut.setDisable(false);
        solicitudBut.setDisable(false);
        adminBut.setDisable(true);
    }
    //Change to reservation form
    public void activeReservation(ActionEvent event)  {
        variableField.setText("");
        String value=homeTypeChoice.getValue();
        if(value!=null && value.equals("Directamente al Hotel")){
            hotelBox.setVisible(true);

        }
        formTitle.setText("Haga su reservación");
        variableField.setPromptText("Escriba su nombre");
        acceBut.setText("Reservar");
        variableLabel.setText("Nombre:");
        reserLabel.setVisible(true);
        carnetField.setVisible(true);
        carnetLabel.setVisible(true);
        homeChoiceRange.setVisible(true);
        roomLabel.setVisible(true);
        reservationBut.setDisable(true);
        solicitudBut.setDisable(false);
        adminBut.setDisable(false);

        homeTypeChoice.setVisible(true);
    }

    public void postAction(ActionEvent e) throws IOException {
        int elementIndexStart=e.getTarget().toString().indexOf("]")+2;
        int elementIndexEnd=e.getTarget().toString().length()-1;
        Alert alert=new Alert(Alert.AlertType.ERROR,"",ButtonType.OK);
        alert.setHeaderText("Faltan parámetros");
        String action=e.getTarget().toString().substring(elementIndexStart,elementIndexEnd);
        if(action.equals("Reservar")){
            if(variableField.getLength()<3){
                alert.setContentText("Escriba un nombre válido");
                alert.show();
            }else if(carnetField.getLength()<11){
                alert.setContentText("Escriba un carnet válido");
                alert.show();
            }else if(homeChoiceRange.getValue()==null){
                alert.setContentText("Seleccione un tipo de habitación");
                alert.show();
            }else if(homeTypeChoice.getValue()==null){
                alert.setContentText("Seleccione como desea reservar");
                alert.show();
            }else if(homeTypeChoice.getValue().equals("Directamente al Hotel")&& homeHotelChoice.getValue()==null){

                    alert.setContentText("Seleccione el hotel");

                    alert.show();

            }else{
                Sistema sis=new Sistema();
                List<Solicitud> listaSolicitudes=sis.getListaSolicitudes();
                boolean existe=false;
                for(Solicitud solicitud:listaSolicitudes){
                    if(solicitud.getCliente().getIdNum().equals(carnetField.getText())){
                        existe=true;
                    }
                }
                if(existe){
                    alert.setHeaderText("Solicitud ya existe");
                    alert.setContentText("Su reserva ya esta siendo procesada. En las próximas 24 horas tendrá su respuesta. Hasta entonces no debe hacer mas solicitudes de reserva. Gracias por confiar en nuestra Cadena.");
                    alert.show();
                }else{
                    List<String> listaClientesVIP=sis.getListaClientesVIP();


                    boolean isVIP=false;
                    for(String clienteVIP:listaClientesVIP){
                        if(clienteVIP.equals(carnetField.getText())){
                            isVIP=true;
                        }
                    }
                    List<Habitación>  habitaciones=new ArrayList<>();
                    List<Cliente> listaClientes=sis.getListaClientes();
                    for(Cliente cliente:listaClientes){
                        if (cliente.getIdNum().equals(carnetField.getText())){
                           habitaciones=cliente.getHab_que_ha_estado();
                        }
                    }

                    Cliente cliente=new Cliente(carnetField.getText(),variableField.getText(),isVIP,habitaciones);
                    Solicitud solicitud;
                    if(homeTypeChoice.getValue().equals("Directamente al Hotel")){
                         solicitud=new Solicitud(cliente,homeHotelChoice.getValue(),homeChoiceRange.getValue());

                    }else{

                         solicitud=new Solicitud(cliente,homeHotelChoice.getValue(),homeChoiceRange.getValue());

                    }

                    sis.addSolicitud(solicitud);
                    variableField.setText("");
                    carnetField.setText("");
                    homeChoiceRange.setValue(null);
                    homeHotelChoice.setValue(null);
                    homeTypeChoice.setValue(null);
                    Alert success=new Alert(Alert.AlertType.INFORMATION,"Su reserva ha sido registrada con éxito. Se le informará una respuesta en las próximas 24 horas. Gracias por confiar en nuestra Cadena.",ButtonType.OK);
                    success.show();
                }

            }

        }else if(action.equals("Revisar")){
            if(variableField.getLength()<11){
                alert.setContentText("Escriba un carnet válido");
                alert.show();
            }else{
                Sistema sis=new Sistema();
                List<Reserva> listaReservas=sis.getListaReservas();
                Reserva foundReserva=null;
                for(Reserva reserva:listaReservas){
                    if(reserva.getID().equals(variableField.getText())){
                        foundReserva=reserva;
                    }
                }
                if(foundReserva!=null){
                    Alert success=new Alert(Alert.AlertType.INFORMATION,"",ButtonType.OK);
                    success.setContentText("Usted ha reservado en la habitación No. "+foundReserva.getNumeroHab()+", la cual es una habitación del tipo "+foundReserva.getTipoHab()+", en el Hotel "+foundReserva.getHotel()+".Esperamos con ansias su visita.");
                    success.setHeaderText("Reserva realizada");
                    success.show();

                    variableField.setText("");
                }else{
                    List<Solicitud> listaSolicitudes=sis.getListaSolicitudes();
                    List<Solicitud> listaSolicitudesRechazadas=sis.getListaSolicitudesRechazadas();

                    boolean isListaSolicitudes=false;
                    for(Solicitud solicitud:listaSolicitudes){
                        if(solicitud.getCliente().getIdNum().equals(variableField.getText())){
                            isListaSolicitudes=true;
                        }
                    }

                    boolean isListaSolicitudesRechazadas=false;
                    for(Solicitud solicitud:listaSolicitudesRechazadas){
                        if(solicitud.getCliente().getIdNum().equals(variableField.getText())){
                            isListaSolicitudesRechazadas=true;
                        }
                    }

                    if(isListaSolicitudes){
                        variableField.setText("");
                        Alert success=new Alert(Alert.AlertType.INFORMATION,"",ButtonType.OK);
                        success.setContentText( "Su solicitud aún no ha sido procesada. Si su solicitud fue realizada hace más de 24 horas puede que nuestros servidores estén en matenimiento.");
                        success.setHeaderText("Solicitud existente");
                        success.show();
                    }else if(isListaSolicitudesRechazadas){
                        variableField.setText("");
                        Alert success=new Alert(Alert.AlertType.INFORMATION,"",ButtonType.OK);
                        success.setContentText( "No pudimos generar una reserva. Su solicitud fue rechazada porque no encontramos habitación con sus requerimientos. Gracias por su atención.");
                        success.setHeaderText("Solicitud rechazada");
                        success.show();
                    }else{
                        variableField.setText("");
                        Alert success=new Alert(Alert.AlertType.INFORMATION,"",ButtonType.OK);
                        success.setContentText( "Usted aún no ha realizado ninguna solicitud de reserva. Debe dirigirse a la sección de reservación para crearla. Gracias por su atención ");
                        success.setHeaderText("Solicitud no encontrada");
                        success.show();
                    }
                }
            }
        }else{

            if(variableField.getLength()<11){
                alert.setContentText("Escriba un carnet válido");
                alert.show();
            }else{
                stage=(Stage)((Node)e.getSource()).getScene().getWindow();
                changeScene(stage,"adminTable.fxml");
            }

        }
    }

    @FXML
    public void reportHab(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Reportar Habitación");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField del numero
        Label numeroLabel=new Label("Numero de la Habitación:");
        numeroLabel.setTextFill(Color.WHITE);
        numeroLabel.setFont(new Font(17));
        numeroLabel.setPadding(new Insets(10,10,10,10));

        TextField numeroTextField=new TextField();

        numeroTextField.setMaxWidth(100);

        numeroTextField.setPromptText("Escriba el numero de la habitación");


        //Crear el label y el boxChoice del Hotel
        Label hotelLabel=new Label("Seleccione el hotel:");
        hotelLabel.setTextFill(Color.WHITE);
        hotelLabel.setFont(new Font(17));
        hotelLabel.setPadding(new Insets(10,10,10,10));

        ChoiceBox<String> hotelChoice=new ChoiceBox<>();
        List<String> hoteles=new ArrayList<>();

        for(Hotel hotel:sis.getListaHoteles()){
            hoteles.add(hotel.getNombre());
        }

        hotelChoice.getItems().addAll(hoteles);


        //Crea el boton y establece su acción al ser pulsado

        Button añadirButton=new Button("Reportar");
        añadirButton.setDisable(true);

        añadirButton.setOnAction(event -> {
            //Obtiene el texto
            String numero=numeroTextField.getText();
            String nombreHotel=hotelChoice.getValue();

            for(Hotel hotel:sis.getListaHoteles()){
                if(hotel.getNombre().equals(nombreHotel)){
                    for (Habitación habitación:hotel.getHabitaciones()){
                        if(habitación.getNumero().equals(numero)){
                            if(habitación.getEstado().equals("Ocupada")){
                                sis.setHabitacionReparacion(hotel.getNombre(),habitación.getNumero());

                                for(Reserva reserva: sis.getListaReservas()){
                                    if(reserva.getHotel().equals(hotel.getNombre())&&reserva.getNumeroHab().equals(habitación.getNumero())){
                                        Habitación suitableHabitacion=null;
                                        Habitación suitableHabitacionRangoDiferente=null;
                                        for (Empleado empleado:sis.getListaEmpleados()){
                                            if(habitación.getEmpleadoID().equals(empleado.getID())){
                                                for (Hotel hotelj:sis.getListaHoteles()){
                                                    if(hotelj.getNombre().equals(reserva.getHotel())){

                                                        for(Habitación habj:hotelj.getHabitaciones()){
                                                            if(empleado.getID().equals(habj.getEmpleadoID())){
                                                                if(habj.getRango().equals(reserva.getTipoHab())){
                                                                    if(!habj.getEstado().equals("Disponible")){
                                                                        suitableHabitacion=habj;
                                                                    }

                                                                }else{
                                                                    if(!habj.getEstado().equals("Disponible")){
                                                                        suitableHabitacionRangoDiferente=habj;
                                                                    }

                                                                }

                                                            }
                                                        }

                                                    }

                                                }
                                            }
                                        }

                                        List<Habitación> suitableRoomsbyJefePiso=new ArrayList<>();
                                        List<Habitación> suitableRoomsbyJefePisoSinMantenerTipo=new ArrayList<>();

                                        for (Jefe_de_Piso jefePiso:sis.getListaJefesPiso()){
                                            if(jefePiso.getHotel().equals(reserva.getHotel())){
                                                for(String numeroHab:jefePiso.getListaNumeroHabitaciones()){
                                                    for (Hotel hotel1: sis.getListaHoteles()){
                                                        if(hotel1.getNombre().equals(reserva.getHotel())){
                                                            for(Habitación habitación1:hotel1.getHabitaciones()){
                                                                if(numeroHab.equals(habitación1.getNumero())){
                                                                    if(habitación1.getRango().equals(reserva.getTipoHab())){
                                                                        if(habitación1.getEstado().equals("Disponible")){
                                                                            suitableRoomsbyJefePiso.add(habitación1);
                                                                        }

                                                                    }else{
                                                                        if(habitación1.getEstado().equals("Disponible")){
                                                                            suitableRoomsbyJefePisoSinMantenerTipo.add(habitación1);
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                        List<Habitación> suitableRoomsByRangoOnly=new ArrayList<>();
                                        List<Habitación> suitableRoomsByRangoOnlyWithoutRange=new ArrayList<>();

                                        for (Hotel hotel1: sis.getListaHoteles()){
                                            if(hotel1.getNombre().equals(reserva.getHotel())){
                                                for (Habitación habitación1:hotel1.getHabitaciones()){
                                                    if(habitación1.getRango().equals(reserva.getTipoHab())){
                                                        if(habitación1.getEstado().equals("Disponible")){
                                                            suitableRoomsByRangoOnly.add(habitación1);
                                                        }

                                                    }else{
                                                        if(habitación1.getEstado().equals("Disponible")){
                                                            suitableRoomsByRangoOnlyWithoutRange.add(habitación1);
                                                        }

                                                    }
                                                }
                                            }

                                        }

                                        if(suitableHabitacion!=null){

                                            Reserva newReserva=new Reserva(reserva.getID(),suitableHabitacion.getNumero(),suitableHabitacion.getRango(),suitableHabitacion.getHotel());
                                            Cliente clienteReserva=null;
                                            for (Cliente cliente:sis.getListaClientes()){
                                                if(cliente.getIdNum().equals(reserva.getID())){
                                                    clienteReserva=cliente;
                                                }
                                            }
                                            System.out.println("1");

                                            sis.addReserva(newReserva,clienteReserva);

                                            Alert success=new Alert(Alert.AlertType.INFORMATION,"",ButtonType.OK);
                                            success.setHeaderText("Reserva cambiada");
                                            success.setContentText("Usted ha sido transferido a la habitación No."+newReserva.getNumeroHab()+", la cual es una habitación del tipo "+ newReserva.getTipoHab()+", en el Hotel "+newReserva.getHotel()+ ".Disculpe las molestias ocasionadas.");

                                            success.show();
                                        }else if(suitableRoomsbyJefePiso.size()!=0){

                                            Stage dialogChooseEmpleados=new Stage();
                                            dialogChooseEmpleados.setAlwaysOnTop(true);
                                            dialogChooseEmpleados.initModality(Modality.APPLICATION_MODAL);
                                            dialogChooseEmpleados.setTitle("Escoja un empleado para su nueva habitación");

                                            //Crear el formulario

                                            BorderPane chooseEmpleadoBorderPane=new BorderPane();
                                            chooseEmpleadoBorderPane.setStyle("-fx-background-color:#002");

                                            VBox chooseEmpleadoVbox=new VBox();
                                            chooseEmpleadoVbox.setAlignment(Pos.CENTER);

                                            //Crear el label y el boxChoice del Empleado
                                            Label empleadoLabel=new Label("Seleccione el Empleado:");
                                            empleadoLabel.setTextFill(Color.WHITE);
                                            empleadoLabel.setFont(new Font(17));
                                            empleadoLabel.setPadding(new Insets(10,10,10,10));

                                            ChoiceBox<String> empleadoChoice=new ChoiceBox<>();
                                            List<String> empleados=new ArrayList<>();


                                                for (Habitación suitableRoom : suitableRoomsbyJefePiso ){
                                                    for(Empleado empleado:sis.getListaEmpleados()){
                                                        if(empleado.getHotel().equals(suitableRoom.getHotel())&&empleado.getID().equals(suitableRoom.getEmpleadoID())){
                                                            empleados.add(empleado.getNombre());
                                                        }
                                                    }


                                            }

                                            empleadoChoice.getItems().addAll(empleados);


                                            //Crea el boton y establece su acción al ser pulsado

                                            Button elegirButton=new Button("Elegir");
                                            elegirButton.setDisable(true);

                                            elegirButton.setOnAction(event1 ->{

                                                String empleadoElegido=empleadoChoice.getValue();
                                                String empleadoID = null;
                                                for(Empleado empleado:sis.getListaEmpleados()){
                                                    if(empleado.getHotel().equals(reserva.getHotel())){
                                                        if(empleado.getNombre().equals(empleadoElegido)){
                                                            empleadoID=empleado.getID();
                                                        }
                                                    }
                                                }
                                                Cliente clienteReserva=null;
                                                for (Cliente cliente:sis.getListaClientes()){
                                                    if(cliente.getIdNum().equals(reserva.getID())){
                                                        clienteReserva=cliente;
                                                    }
                                                }
                                                Reserva newReserva=null;
                                                for (Habitación hab:suitableRoomsbyJefePiso){
                                                    if(hab.getEmpleadoID().equals(empleadoID)){
                                                        newReserva=new Reserva(reserva.getID(),hab.getNumero(),hab.getRango(), hab.getHotel());

                                                    }
                                                }
                                                System.out.println("2");

                                                sis.addReserva(newReserva,clienteReserva);
                                                Alert success=new Alert(Alert.AlertType.INFORMATION,"",ButtonType.OK);
                                                success.setHeaderText("Reserva cambiada");
                                                success.setContentText("Usted ha sido transferido a la habitación No."+newReserva.getNumeroHab()+", la cual es una habitación del tipo "+ newReserva.getTipoHab()+", en el Hotel "+newReserva.getHotel()+ ".Disculpe las molestias ocasionadas.");


                                                dialogChooseEmpleados.close();

                                                success.show();

                                            });

                                            empleadoChoice.valueProperty().addListener( (observable,oldValue,newValue)->{

                                                if(newValue==null){
                                                    empleadoChoice.setStyle("-fx-border-color:red;");
                                                    elegirButton.setDisable(true);
                                                }else{
                                                    empleadoChoice.setStyle("-fx-border-color:black;");
                                                    elegirButton.setDisable(false);
                                                }
                                            } );

                                            dialogChooseEmpleados.showAndWait();

                                        }else if(suitableRoomsByRangoOnly.size()!=0){

                                            Habitación hab=suitableRoomsByRangoOnly.get(0);

                                            Reserva newReserva=new Reserva(reserva.getID(),hab.getNumero(),hab.getRango(),hab.getHotel());

                                            Cliente clienteReserva=null;
                                            for (Cliente cliente:sis.getListaClientes()){
                                                if(cliente.getIdNum().equals(reserva.getID())){
                                                    clienteReserva=cliente;
                                                }
                                            }
                                            System.out.println("3");

                                            sis.addReserva(newReserva,clienteReserva);
                                            Alert success=new Alert(Alert.AlertType.INFORMATION,"",ButtonType.OK);
                                            success.setHeaderText("Reserva cambiada");
                                            success.setContentText("Usted ha sido transferido a la habitación No."+newReserva.getNumeroHab()+", la cual es una habitación del tipo "+ newReserva.getTipoHab()+", en el Hotel "+newReserva.getHotel()+ ".Disculpe las molestias ocasionadas.");

                                            success.show();

                                        }else if(suitableHabitacionRangoDiferente!=null){

                                            Reserva newReserva=new Reserva(reserva.getID(),suitableHabitacionRangoDiferente.getNumero(),suitableHabitacionRangoDiferente.getRango(),suitableHabitacionRangoDiferente.getHotel());
                                            Cliente clienteReserva=null;
                                            for (Cliente cliente:sis.getListaClientes()){
                                                if(cliente.getIdNum().equals(reserva.getID())){
                                                    clienteReserva=cliente;
                                                }
                                            }

                                            System.out.println("4");

                                            sis.addReserva(newReserva,clienteReserva);

                                            Alert success=new Alert(Alert.AlertType.INFORMATION,"",ButtonType.OK);
                                            success.setHeaderText("Reserva cambiada");
                                            success.setContentText("Usted ha sido transferido a la habitación No."+newReserva.getNumeroHab()+", la cual es una habitación del tipo "+ newReserva.getTipoHab()+", en el Hotel "+newReserva.getHotel()+ ".Disculpe las molestias ocasionadas.");
                                            success.show();
                                        }else if(suitableRoomsbyJefePisoSinMantenerTipo.size()!=0){

                                            Stage dialogChooseEmpleados=new Stage();
                                            dialogChooseEmpleados.setAlwaysOnTop(true);
                                            dialogChooseEmpleados.initModality(Modality.APPLICATION_MODAL);
                                            dialogChooseEmpleados.setTitle("Escoja un empleado para su nueva habitación");

                                            //Crear el formulario

                                            BorderPane chooseEmpleadoBorderPane=new BorderPane();
                                            chooseEmpleadoBorderPane.setStyle("-fx-background-color:#002");

                                            VBox chooseEmpleadoVbox=new VBox();
                                            chooseEmpleadoVbox.setAlignment(Pos.CENTER);

                                            //Crear el label y el boxChoice del Empleado
                                            Label empleadoLabel=new Label("Seleccione el Empleado:");
                                            empleadoLabel.setTextFill(Color.WHITE);
                                            empleadoLabel.setFont(new Font(17));
                                            empleadoLabel.setPadding(new Insets(10,10,10,10));

                                            ChoiceBox<String> empleadoChoice=new ChoiceBox<>();
                                            List<String> empleados=new ArrayList<>();


                                            for (Habitación suitableRoom : suitableRoomsbyJefePisoSinMantenerTipo ){
                                                for(Empleado empleado:sis.getListaEmpleados()){
                                                    if(empleado.getHotel().equals(suitableRoom.getHotel())&&empleado.getID().equals(suitableRoom.getEmpleadoID())){
                                                        empleados.add(empleado.getNombre());
                                                    }
                                                }


                                            }

                                            empleadoChoice.getItems().addAll(empleados);


                                            //Crea el boton y establece su acción al ser pulsado

                                            Button elegirButton=new Button("Elegir");
                                            elegirButton.setDisable(true);

                                            elegirButton.setOnAction(event1 ->{

                                                String empleadoElegido=empleadoChoice.getValue();
                                                String empleadoID = null;
                                                for(Empleado empleado:sis.getListaEmpleados()){
                                                    if(empleado.getHotel().equals(reserva.getHotel())){
                                                        if(empleado.getNombre().equals(empleadoElegido)){
                                                            empleadoID=empleado.getID();
                                                        }
                                                    }
                                                }
                                                Reserva newReserva=null;
                                                Cliente clienteReserva=null;
                                                for (Cliente cliente:sis.getListaClientes()){
                                                    if(cliente.getIdNum().equals(reserva.getID())){
                                                        clienteReserva=cliente;
                                                    }
                                                }
                                                for (Habitación hab:suitableRoomsbyJefePisoSinMantenerTipo){
                                                    if(hab.getEmpleadoID().equals(empleadoID)){
                                                         newReserva=new Reserva(reserva.getID(),hab.getNumero(),hab.getRango(), hab.getHotel());

                                                    }
                                                }
                                                System.out.println("5");

                                                sis.addReserva(newReserva,clienteReserva);
                                                Alert success=new Alert(Alert.AlertType.INFORMATION,"",ButtonType.OK);
                                                success.setHeaderText("Reserva cambiada");
                                                success.setContentText("Usted ha sido transferido a la habitación No."+newReserva.getNumeroHab()+", la cual es una habitación del tipo "+ newReserva.getTipoHab()+", en el Hotel "+newReserva.getHotel()+ ".Disculpe las molestias ocasionadas.");


                                                dialogChooseEmpleados.close();
                                                success.show();
                                            });

                                            empleadoChoice.valueProperty().addListener( (observable,oldValue,newValue)->{

                                                if(newValue==null){
                                                    empleadoChoice.setStyle("-fx-border-color:red;");
                                                    elegirButton.setDisable(true);
                                                }else{
                                                    empleadoChoice.setStyle("-fx-border-color:black;");
                                                    elegirButton.setDisable(false);
                                                }
                                            } );

                                            dialogChooseEmpleados.showAndWait();

                                        }else if(suitableRoomsByRangoOnlyWithoutRange.size()!=0){
                                            String[] rangos={"Individual", "Doble", "Triple", "Quad", "Queen", "King", "Suite"};
                                            int indiceReserva=-1;


                                            for (int i=0;i<rangos.length;i++){
                                                if(reserva.getTipoHab().equals(rangos[i])){
                                                    indiceReserva=i;
                                                }
                                            }
                                            Reserva newReserva=null;
                                            for (Habitación hab:suitableRoomsByRangoOnlyWithoutRange){
                                                int indiceHab=-1;

                                                for (int i=0;i<rangos.length;i++){
                                                    if(hab.getRango().equals(rangos[i])){
                                                        indiceHab=i;
                                                    }
                                                }

                                                if(indiceHab>indiceReserva){
                                                    newReserva=new Reserva(reserva.getID(),hab.getNumero(),hab.getRango(),hab.getHotel());
                                                }
                                            }


                                            Cliente clienteReserva=null;
                                            for (Cliente cliente:sis.getListaClientes()){
                                                if(cliente.getIdNum().equals(reserva.getID())){
                                                    clienteReserva=cliente;
                                                }
                                            }
                                            System.out.println("6");

                                            sis.addReserva(newReserva,clienteReserva);
                                            Alert success=new Alert(Alert.AlertType.INFORMATION,"",ButtonType.OK);
                                            success.setHeaderText("Reserva cambiada");
                                            success.setContentText("Usted ha sido transferido a la habitación No."+newReserva.getNumeroHab()+", la cual es una habitación del tipo "+ newReserva.getTipoHab()+", en el Hotel "+newReserva.getHotel()+ ".Disculpe las molestias ocasionadas.");

                                            success.show();
                                        }

                                    }
                                }
                            }else{
                                Alert alert=new Alert(Alert.AlertType.INFORMATION,"La Habitación reportada no esta ocupada actualmente",ButtonType.OK);
                                alert.setHeaderText("Reporte Incorrecto");
                                alert.show();
                            }

                        }
                    }

                }
            }

            dialog.close();
        });


        //Valida los textfield y habilita el boton
        numeroTextField.textProperty().addListener( (observable,oldValue,newValue)->{

            //Validar el numero
            boolean existeNumero=false;
            for(Hotel hotel:sis.getListaHoteles()){
                if(hotel.getNombre().equals(hotelChoice.getValue())){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero()!=null && hab.getNumero().equals(numeroTextField.getText())){
                            existeNumero=true;
                        }
                    }
                }
            }

            if(!existeNumero){
                numeroTextField.setStyle("-fx-border-color:red;");
                añadirButton.setDisable(true);
            }else if(hotelChoice.getValue()==null){
                añadirButton.setDisable(true);
                numeroTextField.setStyle("-fx-border-color:black;");
            }else{
                numeroTextField.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(false);
            }

        } );

        hotelChoice.valueProperty().addListener( (observable,oldValue,newValue)->{
            //Validar el numero
            boolean existeNumero=false;
            for(Hotel hotel:sis.getListaHoteles()){
                if(hotel.getNombre().equals(hotelChoice.getValue())){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero()!=null && hab.getNumero().equals(numeroTextField.getText())){
                            existeNumero=true;
                        }
                    }
                }
            }

            if(newValue==null){
                hotelChoice.setStyle("-fx-border-color:red;");
                añadirButton.setDisable(true);
            }else if(!existeNumero){
                hotelChoice.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(true);
            }else{
                hotelChoice.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(false);
            }
        } );




        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(hotelLabel,hotelChoice, numeroLabel,numeroTextField,añadirButton);

        VBox.setMargin(añadirButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,500,400);
        dialog.setScene(scene);
        dialog.showAndWait();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            homeChoiceRange.getItems().addAll(rangos);
            homeTypeChoice.getItems().addAll(reservaTipo);
            homeTypeChoice.setOnAction(this::checkReserva);

            Sistema sis=new Sistema();

            List<String> hoteles=new ArrayList<>();

            for(Hotel hotel:sis.getListaHoteles()){
                hoteles.add(hotel.getNombre());
            }

            homeHotelChoice.getItems().addAll(hoteles);


            Image image=new Image(String.valueOf(getClass().getResource("homePic.jpg")));
        BackgroundSize backgroundSize=new BackgroundSize(100,100,false,false,false,true);

        BackgroundImage backgroundImage=new BackgroundImage(image,null,null,null,backgroundSize);

        Background background=new Background(backgroundImage);

        ContainerBorderPane.setBackground(background);




    }
}