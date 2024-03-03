package com.jalvery20.cadena_hotelera_visual;

import com.jalvery20.cadena_hotelera_visual.classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class TableController implements Initializable {


    private Scene scene;
    private Parent root;
    private Stage stage;
    @FXML
    TableView<ObservableHotel> tablaHoteles;

    @FXML
     VBox hotelesActionsVbox,habitacionesActionsVbox,clientesVIPActionsVbox,AdminIDActionsVbox,empleadoActionsVbox,empleadoHabitacionesActionsVbox,JefesPisoActionsVbox;

    TableView<ObservableHabitacion>  tablaHabitaciones=new TableView<>();

    TableView<ObservableAdminID>  tablaAdminID=new TableView<>();

    TableView<ObservableClienteVIP>  tablaClientesVIP=new TableView<>();

    TableView<ObservableEmpleado> tablaEmpleados=new TableView<>();

    TableView<ObservableJefePiso> tablaJefesPiso=new TableView<>();


    @FXML
    Button habitacionesButton,hotelesButton,clientesVIPButton,adminIDButton,empleadosButton,jefePisoButton;

    @FXML
    BorderPane ContainerBorderPane;






    //Guardar nombre del Hotel para el Empleado
    String empleadoHotel;

    //Guardar ID del empleado ver las habitaciones
    String actualEmpleadoID;

    //Guardar nombre del Hotel del Jefe de piso
    String actualJefePisoHotel;

    //Seleccionar la tabla en particular

    @FXML
    Button goHome;

    @FXML
    public void ActiveJefePisoTable(ActionEvent e){

        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Escriba el Hotel");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField
        Label label=new Label("Nombre del Hotel:");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(17));
        label.setPadding(new Insets(10,10,10,10));

        TextField textField=new TextField();

        textField.setMaxWidth(100);

        textField.setPromptText("Escriba el nombre del hotel");


        //Crea el boton y establece su acción al ser pulsado

        Button buscarButton=new Button("Buscar");
        buscarButton.setDisable(true);

        buscarButton.setOnAction(event -> {
            actualJefePisoHotel=textField.getText();
            List<Jefe_de_Piso> listaJefesPiso=sis.getListaJefesPiso();

            //Columna para el ID del Jefe de Piso
            TableColumn<ObservableJefePiso,String> columnaID=new TableColumn<>("ID");
            columnaID.setCellValueFactory(new PropertyValueFactory<>("ID"));


            //Columna para el numero de Habitaciones
            TableColumn<ObservableJefePiso,Integer> columnaNumeroHabitaciones=new TableColumn<>("No. de habitaciones ");
            columnaNumeroHabitaciones.setCellValueFactory(new PropertyValueFactory<>("numeroHabitaciones"));

            //Columna para el nombre del Hotel
            TableColumn<ObservableJefePiso,String> columnaHotel=new TableColumn<>("Hotel");
            columnaHotel.setCellValueFactory(new PropertyValueFactory<>("hotel"));


            if(tablaJefesPiso.getColumns().size()!=3){
                tablaJefesPiso.getColumns().addAll(columnaID,columnaNumeroHabitaciones,columnaHotel);
            }

            ObservableList<ObservableJefePiso> datos=FXCollections.observableArrayList();

            //Obtiene el texto
            String hotelTextfield=textField.getText();
            //Recorriendo la lista de Jefes de Piso
            for(Jefe_de_Piso jefePiso:listaJefesPiso){
                if(jefePiso.getHotel().equals(hotelTextfield)){
                    int numeroHabitaciones=jefePiso.getListaNumeroHabitaciones()!=null? jefePiso.getListaNumeroHabitaciones().size():0;

                    datos.add(new ObservableJefePiso( jefePiso.getID(),numeroHabitaciones,hotelTextfield));

                }


            }
            tablaJefesPiso.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tablaJefesPiso.setItems(datos);

            VBox tablaVbox=new VBox();
            tablaVbox.setAlignment(Pos.CENTER);

            tablaVbox.getChildren().add(tablaJefesPiso);
            VBox.setMargin(tablaJefesPiso,new Insets(20,0,20,20));
            ContainerBorderPane.setCenter(tablaVbox);


            empleadosButton.setDisable(false);
            habitacionesButton.setDisable(false);
            hotelesButton.setDisable(false);
            clientesVIPButton.setDisable(false);
            adminIDButton.setDisable(false);
            jefePisoButton.setDisable(true);


            empleadoHabitacionesActionsVbox.setVisible(false);
            empleadoActionsVbox.setVisible(false);
            AdminIDActionsVbox.setVisible(false);
            hotelesActionsVbox.setVisible(false);
            clientesVIPActionsVbox.setVisible(false);
            habitacionesActionsVbox.setVisible(false);
            JefesPisoActionsVbox.setVisible(true);
            dialog.close();
        });


        //Valida el textfield y habilita el boton
        textField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean isHotel=false;
            List<Hotel> listaHoteles=sis.getListaHoteles();
            for (int i=0;i<listaHoteles.size();i++){
                if(listaHoteles.get(i).getNombre().equals(textField.getText())) {
                    isHotel=true;
                }
            }
            if(!isHotel){
                textField.setStyle("-fx-border-color:red;");
                buscarButton.setDisable(true);
            }else{
                textField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(label,textField,buscarButton);
        VBox.setMargin(buscarButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);

        dialog.showAndWait();


    }
    @FXML
    public void ActiveHabitacionEmpleadoTable(ActionEvent e){
        //Inicializar el gestor
        Sistema sis=new Sistema();
        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Escriba el ID del Empleado");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField
        Label label=new Label("ID del Empleado:");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(17));
        label.setPadding(new Insets(10,10,10,10));

        TextField textField=new TextField();

        textField.setMaxWidth(100);

        textField.setPromptText("Escriba el ID del Empleado");



        //Crea el boton y establece su acción al ser pulsado

        Button buscarButton=new Button("Buscar");
        buscarButton.setDisable(true);

        buscarButton.setOnAction(event -> {

            List<Hotel> listaHoteles=sis.getListaHoteles();

            //Columna para el numero de la Habitación
            TableColumn<ObservableHabitacion,String> columnaNumero=new TableColumn<>("No. de la Habitación ");
            columnaNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));

            //Columna para el rango de la Habitacion
            TableColumn<ObservableHabitacion,String> columnaRango=new TableColumn<>("Rango");
            columnaRango.setCellValueFactory(new PropertyValueFactory<>("rango"));

            //Columna para el estado de la Habitacion
            TableColumn<ObservableHabitacion,String> columnaEstado=new TableColumn<>("Estado");
            columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

            //Columna para el empleadoID de la Habitacion
            TableColumn<ObservableHabitacion,String> columnaEmpleadoID=new TableColumn<>("Empleado ID");
            columnaEmpleadoID.setCellValueFactory(new PropertyValueFactory<>("empleadoID"));

            //Columna para el Jefe de Piso ID de la Habitacion
            TableColumn<ObservableHabitacion,String> columnaJefePisoID=new TableColumn<>("Jefe de Piso ID");
            columnaEmpleadoID.setCellValueFactory(new PropertyValueFactory<>("jefePisoID"));

            //Columna para el nombre del Hotel
            TableColumn<ObservableHabitacion,String> columnaNombreHotel=new TableColumn<>("Hotel");
            columnaNombreHotel.setCellValueFactory(new PropertyValueFactory<>("hotel"));


            if(tablaHabitaciones.getColumns().size()!=6){
                tablaHabitaciones.getColumns().addAll(columnaNumero,columnaRango,columnaEstado,columnaEmpleadoID,columnaJefePisoID,columnaNombreHotel);
            }

            ObservableList<ObservableHabitacion> datos=FXCollections.observableArrayList();

            //Obtiene el texto
            String empleadoIDTextfield=textField.getText();
            actualEmpleadoID=empleadoIDTextfield;
            //Recorriendo la lista de hoteles
            for(Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(empleadoHotel)){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getEmpleadoID()!=null && hab.getEmpleadoID().equals(empleadoIDTextfield)){
                            String numero=hab.getNumero();
                            String nombreHotel=hab.getHotel();
                            String rango=hab.getRango();
                            String empleadoID=hab.getEmpleadoID();
                            String estado=hab.getEstado();
                            String jefePisoID = null;

                            for(Jefe_de_Piso jefePiso:sis.getListaJefesPiso()){
                                for (String numeroHab:jefePiso.getListaNumeroHabitaciones()){
                                    if(numeroHab.equals(numero)){
                                        jefePisoID=jefePiso.getID();
                                    }
                                }
                            }

                            datos.add(new ObservableHabitacion(numero,rango,estado,empleadoID,nombreHotel,jefePisoID));
                        }
                    }
                }

            }
            tablaHabitaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tablaHabitaciones.setItems(datos);

            VBox tablaVbox=new VBox();
            tablaVbox.setAlignment(Pos.CENTER);

            tablaVbox.getChildren().add(tablaHabitaciones);
            VBox.setMargin(tablaHabitaciones,new Insets(20,0,20,20));
            ContainerBorderPane.setCenter(tablaVbox);

            empleadosButton.setDisable(false);
            empleadoHabitacionesActionsVbox.setVisible(true);
            empleadoActionsVbox.setVisible(false);
            dialog.close();
        });


        //Valida el textfield y habilita el boton
        textField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean isEmpleadoID=false;
            List<Empleado> listaEmpleados=sis.getListaEmpleados();
            for (Empleado empleado:listaEmpleados){
                if(empleado.getHotel().equals(empleadoHotel)) {
                    if(empleado.getID().equals(newValue)){
                        isEmpleadoID=true;
                    }
                }
            }
            if(!isEmpleadoID){
                textField.setStyle("-fx-border-color:red;");
                buscarButton.setDisable(true);
            }else{
                textField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(label,textField,buscarButton);
        VBox.setMargin(buscarButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);

        dialog.showAndWait();

    }
    @FXML
    public void  ActiveHotelesTable(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        List<Hotel> listaHoteles=sis.getListaHoteles();
        List<Empleado> listaEmpleados=sis.getListaEmpleados();


        TableColumn<ObservableHotel,String> columnaNombre=new TableColumn<>("Nombre del Hotel");

        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        //Crear columna para la cantidad de habitaciones
        TableColumn<ObservableHotel,Integer> columnaHabitaciones=new TableColumn<>("No. de Habitaciones");
        columnaHabitaciones.setCellValueFactory(new PropertyValueFactory<>("numHabitaciones"));

        //Crear columna para la cantidad de Empleados
        TableColumn<ObservableHotel,Integer> columnaEmpleados=new TableColumn<>("No. de Empleados");
        columnaEmpleados.setCellValueFactory(new PropertyValueFactory<>("numEmpleados"));



        ObservableList<ObservableHotel> datos=FXCollections.observableArrayList();

        //Recorriendo la lista de hoteles
        for(Hotel hotel:listaHoteles){
            String nombre=hotel.getNombre();
            int numeroEmpleados=0;
            for(Empleado empleado:listaEmpleados){
                if(empleado.getHotel().equals(hotel.getNombre())){
                    numeroEmpleados+=1;
                }
            }
            int numerodeHab=hotel.getHabitaciones().size();
            if(hotel.getHabitaciones().get(0).getNumero()==null){
                numerodeHab=0;
            }
            datos.add(new ObservableHotel(nombre,numerodeHab,numeroEmpleados));

        }


        tablaHoteles.setItems(datos);
        ContainerBorderPane.setCenter(tablaHoteles);

        empleadosButton.setDisable(false);
        hotelesButton.setDisable(true);
        clientesVIPButton.setDisable(false);
        habitacionesButton.setDisable(false);
        adminIDButton.setDisable(false);
        jefePisoButton.setDisable(false);

        empleadoActionsVbox.setVisible(false);
        AdminIDActionsVbox.setVisible(false);
        hotelesActionsVbox.setVisible(true);
        clientesVIPActionsVbox.setVisible(false);
        habitacionesActionsVbox.setVisible(false);
        JefesPisoActionsVbox.setVisible(false);
    }

    @FXML
    public void ActiveHabitacionesTable(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Escriba el Hotel");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField
        Label label=new Label("Nombre del Hotel:");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(17));
        label.setPadding(new Insets(10,10,10,10));

        TextField textField=new TextField();

        textField.setMaxWidth(100);

        textField.setPromptText("Escriba el nombre del hotel");



        //Crea el boton y establece su acción al ser pulsado

        Button buscarButton=new Button("Buscar");
        buscarButton.setDisable(true);

        buscarButton.setOnAction(event -> {

            List<Hotel> listaHoteles=sis.getListaHoteles();
            //Columna para el numero de la Habitación
            TableColumn<ObservableHabitacion,String> columnaNumero=new TableColumn<>("No. de la Habitación ");
            columnaNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));

            //Columna para el rango de la Habitacion
            TableColumn<ObservableHabitacion,String> columnaRango=new TableColumn<>("Rango");
            columnaRango.setCellValueFactory(new PropertyValueFactory<>("rango"));

            //Columna para el estado de la Habitacion
            TableColumn<ObservableHabitacion,String> columnaEstado=new TableColumn<>("Estado");
            columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

            //Columna para el empleadoID de la Habitacion
            TableColumn<ObservableHabitacion,String> columnaEmpleadoID=new TableColumn<>("Empleado ID");
            columnaEmpleadoID.setCellValueFactory(new PropertyValueFactory<>("empleadoID"));

            //Columna para el Jefe de Piso ID de la Habitacion
            TableColumn<ObservableHabitacion,String> columnaJefePisoID=new TableColumn<>("Jefe de Piso ID");
            columnaJefePisoID.setCellValueFactory(new PropertyValueFactory<>("jefePisoID"));

            //Columna para el nombre del Hotel
            TableColumn<ObservableHabitacion,String> columnaNombreHotel=new TableColumn<>("Hotel");
            columnaNombreHotel.setCellValueFactory(new PropertyValueFactory<>("hotel"));


            if(tablaHabitaciones.getColumns().size()!=6){
                tablaHabitaciones.getColumns().addAll(columnaNumero,columnaRango,columnaEstado,columnaEmpleadoID,columnaJefePisoID,columnaNombreHotel);
            }

            ObservableList<ObservableHabitacion> datos=FXCollections.observableArrayList();

            //Obtiene el texto
            String hotelTextfield=textField.getText();
            //Recorriendo la lista de hoteles
            for(Hotel hotel:listaHoteles) {
                if (hotel.getNombre().equals(hotelTextfield)) {
                    for (Habitación hab : hotel.getHabitaciones()) {
                        String numero = hab.getNumero();
                        String nombreHotel = hab.getHotel();
                        String rango = hab.getRango();
                        String empleadoID = hab.getEmpleadoID();
                        String estado = hab.getEstado();
                        String jefePisoID = null;

                        for(Jefe_de_Piso jefePiso:sis.getListaJefesPiso()){
                            for (String numeroHab:jefePiso.getListaNumeroHabitaciones()){
                                if(numeroHab.equals(numero)){
                                    jefePisoID=jefePiso.getID();
                                }
                            }
                        }

                        datos.add(new ObservableHabitacion(numero, rango, estado, empleadoID, nombreHotel, jefePisoID));

                    }


                }
                tablaHabitaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                tablaHabitaciones.setItems(datos);

                VBox tablaVbox = new VBox();
                tablaVbox.setAlignment(Pos.CENTER);

                tablaVbox.getChildren().add(tablaHabitaciones);
                VBox.setMargin(tablaHabitaciones, new Insets(20, 0, 20, 20));
                ContainerBorderPane.setCenter(tablaVbox);


                empleadosButton.setDisable(false);
                habitacionesButton.setDisable(true);
                hotelesButton.setDisable(false);
                clientesVIPButton.setDisable(false);
                adminIDButton.setDisable(false);
                jefePisoButton.setDisable(false);

                empleadoHabitacionesActionsVbox.setVisible(false);
                empleadoActionsVbox.setVisible(false);
                AdminIDActionsVbox.setVisible(false);
                hotelesActionsVbox.setVisible(false);
                clientesVIPActionsVbox.setVisible(false);
                habitacionesActionsVbox.setVisible(true);
                JefesPisoActionsVbox.setVisible(false);
                dialog.close();
            }

        });


        //Valida el textfield y habilita el boton
        textField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean isHotel=false;
            List<Hotel> listaHoteles=sis.getListaHoteles();
            for (int i=0;i<listaHoteles.size();i++){
                if(listaHoteles.get(i).getNombre().equals(textField.getText())) {
                    isHotel=true;
                }
            }
            if(!isHotel){
                textField.setStyle("-fx-border-color:red;");
                buscarButton.setDisable(true);
            }else{
                textField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(label,textField,buscarButton);
        VBox.setMargin(buscarButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);

        dialog.showAndWait();


    }

    @FXML
    public void ActiveEmpleadoTable(ActionEvent event){


        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Escriba el Hotel");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField
        Label label=new Label("Nombre del Hotel:");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(17));
        label.setPadding(new Insets(10,10,10,10));

        TextField textField=new TextField();

        textField.setMaxWidth(100);

        textField.setPromptText("Escriba el nombre del hotel");



        //Crea el boton y establece su acción al ser pulsado

        Button buscarButton=new Button("Buscar");
        buscarButton.setDisable(true);

        buscarButton.setOnAction(e -> {

            List<Hotel> listaHoteles=sis.getListaHoteles();

            //Columna para el id del Empleado
            TableColumn<ObservableEmpleado,String> columnaID=new TableColumn<>("ID del Empleado ");
            columnaID.setCellValueFactory(new PropertyValueFactory<>("ID"));

            //Columna para el nombre del Empleado
            TableColumn<ObservableEmpleado,String> columnaNombre=new TableColumn<>("Nombre");
            columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

            //Columna para la cantidad de Habitaciones a cargo
            TableColumn<ObservableEmpleado,Integer> columnaCantHabitaciones=new TableColumn<>("Habitaciones a cargo");
            columnaCantHabitaciones.setCellValueFactory(new PropertyValueFactory<>("cantDeHabitaciones"));

            //Columna para el % de cantidad de Habitaciones a cargo
            TableColumn<ObservableEmpleado,Double> columnaPorcentajeCantHabitaciones=new TableColumn<>("% Habitaciones a cargo");
            columnaPorcentajeCantHabitaciones.setCellValueFactory(new PropertyValueFactory<>("cantHabitacionesPorcentaje"));

            //Columna para el nombre del Hotel
            TableColumn<ObservableEmpleado,String> columnaNombreHotel=new TableColumn<>("Hotel");
            columnaNombreHotel.setCellValueFactory(new PropertyValueFactory<>("hotel"));


            if(tablaEmpleados.getColumns().size()!=5){
                tablaEmpleados.getColumns().addAll(columnaID,columnaNombre,columnaCantHabitaciones,columnaPorcentajeCantHabitaciones,columnaNombreHotel);
            }

            ObservableList<ObservableEmpleado> datos=FXCollections.observableArrayList();

            //Obtiene el texto
            String hotelTextfield=textField.getText();
            //Recorriendo la lista de hoteles
            for(Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(hotelTextfield)){
                        for(Empleado empleado:sis.getListaEmpleados()){
                            if(empleado.getHotel().equals(hotelTextfield)){
                                int numeroHab=0;
                                int cantHabitacionesHotel=hotel.getHabitaciones()!=null? hotel.getHabitaciones().size():0;
                                for(Habitación hab:hotel.getHabitaciones()){
                                    String empleadoID=hab.getEmpleadoID();
                                    if(empleadoID!=null&& empleadoID.equals(empleado.getID())){
                                        numeroHab+=1;
                                    }
                                }

                                double porcentajeHab=0;
                                if(cantHabitacionesHotel!=0){
                                    porcentajeHab= (double) numeroHab / cantHabitacionesHotel*100;
                                }

                                datos.add(new ObservableEmpleado(empleado.getNombre(), empleado.getID(), empleado.getHotel(),numeroHab,Math.round(porcentajeHab)));

                            }

                        }
                    }





            }
            tablaEmpleados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tablaEmpleados.setItems(datos);

            VBox tablaVbox=new VBox();
            tablaVbox.setAlignment(Pos.CENTER);

            tablaVbox.getChildren().add(tablaEmpleados);
            VBox.setMargin(tablaEmpleados,new Insets(20,0,20,20));
            ContainerBorderPane.setCenter(tablaVbox);


            habitacionesButton.setDisable(false);
            hotelesButton.setDisable(false);
            clientesVIPButton.setDisable(false);
            adminIDButton.setDisable(false);
            empleadosButton.setDisable(true);
            jefePisoButton.setDisable(false);

            empleadoActionsVbox.setVisible(true);
            AdminIDActionsVbox.setVisible(false);
            hotelesActionsVbox.setVisible(false);
            clientesVIPActionsVbox.setVisible(false);
            habitacionesActionsVbox.setVisible(false);
            empleadoHabitacionesActionsVbox.setVisible(false);
            JefesPisoActionsVbox.setVisible(false);

            empleadoHotel=textField.getText();
            dialog.close();
        });


        //Valida el textfield y habilita el boton
        textField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean isHotel=false;
            List<Hotel> listaHoteles=sis.getListaHoteles();
            for (int i=0;i<listaHoteles.size();i++){
                if(listaHoteles.get(i).getNombre().equals(textField.getText())) {
                    isHotel=true;
                }
            }
            if(!isHotel){
                textField.setStyle("-fx-border-color:red;");
                buscarButton.setDisable(true);
            }else{
                textField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(label,textField,buscarButton);
        VBox.setMargin(buscarButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);

        dialog.showAndWait();
    }
    @FXML
    public  void ActiveAdminIDTable(ActionEvent e){
        //Inicializar el gestor
        Sistema sis=new Sistema();

        List<String> listaAdminID=sis.getListaAdminID();

        TableColumn<ObservableAdminID,String> columnaID=new TableColumn<>("ID del Admin");

        columnaID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        //Añadir columnas a la tabla
        if(tablaAdminID.getColumns().size()!=1){
            tablaAdminID.getColumns().add(columnaID);
        }


        ObservableList<ObservableAdminID> datos=FXCollections.observableArrayList();

        //Recorriendo la lista de clientes VIP
        for(String lista:listaAdminID){

            datos.add(new ObservableAdminID(lista));

        }

        tablaAdminID.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox tablaVbox=new VBox();
        tablaVbox.setAlignment(Pos.CENTER);

        tablaVbox.getChildren().add(tablaAdminID);
        VBox.setMargin(tablaAdminID,new Insets(20,0,20,20));

        tablaAdminID.setItems(datos);

        ContainerBorderPane.setCenter(tablaVbox);

        empleadosButton.setDisable(false);
        adminIDButton.setDisable(true);
        clientesVIPButton.setDisable(false);
        hotelesButton.setDisable(false);
        habitacionesButton.setDisable(false);
        jefePisoButton.setDisable(false);

        hotelesActionsVbox.setVisible(false);
        empleadoActionsVbox.setVisible(false);
        clientesVIPActionsVbox.setVisible(false);
        AdminIDActionsVbox.setVisible(true);
        habitacionesActionsVbox.setVisible(false);
        empleadoHabitacionesActionsVbox.setVisible(false);
        JefesPisoActionsVbox.setVisible(false);
    }


    public  void ActiveClientesVIPTable(ActionEvent e){
        //Inicializar el gestor
        Sistema sis=new Sistema();

        List<String> listaClientesVIP=sis.getListaClientesVIP();

        TableColumn<ObservableClienteVIP,String> columnaID=new TableColumn<>("ID del Cliente VIP");

        columnaID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        //Añadir columnas a la tabla
        if(tablaClientesVIP.getColumns().size()!=1){
            tablaClientesVIP.getColumns().add(columnaID);
        }


        ObservableList<ObservableClienteVIP> datos=FXCollections.observableArrayList();

        //Recorriendo la lista de clientes VIP
        for(String lista:listaClientesVIP){

            datos.add(new ObservableClienteVIP(lista));

        }

        tablaClientesVIP.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox tablaVbox=new VBox();
        tablaVbox.setAlignment(Pos.CENTER);

        tablaVbox.getChildren().add(tablaClientesVIP);
        VBox.setMargin(tablaClientesVIP,new Insets(20,0,20,20));

        tablaClientesVIP.setItems(datos);

        ContainerBorderPane.setCenter(tablaVbox);

        clientesVIPButton.setDisable(true);
        hotelesButton.setDisable(false);
        habitacionesButton.setDisable(false);
        adminIDButton.setDisable(false);
        empleadosButton.setDisable(false);
        jefePisoButton.setDisable(false);

        hotelesActionsVbox.setVisible(false);
        clientesVIPActionsVbox.setVisible(true);
        habitacionesActionsVbox.setVisible(false);
        AdminIDActionsVbox.setVisible(false);
        empleadoActionsVbox.setVisible(false);
        empleadoHabitacionesActionsVbox.setVisible(false);
        JefesPisoActionsVbox.setVisible(false);
    }

    @FXML
    public void RemoveHotel(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        //Crear el dialogo
        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Remover Hotel");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField
        Label label=new Label("Nombre del Hotel:");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(17));
        label.setPadding(new Insets(10,10,10,10));

        TextField textField=new TextField();

        textField.setMaxWidth(100);

        textField.setPromptText("Escriba el nombre del hotel");



        //Crea el boton y establece su acción al ser pulsado

        Button removerButton=new Button("Remover");
        removerButton.setDisable(true);

        removerButton.setOnAction(event -> {
            //Obtiene el texto
            String nombreHotel=textField.getText();




            sis.removeHotel(nombreHotel);


            List<Hotel> listaHoteles=sis.getListaHoteles();
            List<Empleado> listaEmpleados=sis.getListaEmpleados();


            ObservableList<ObservableHotel> datos=FXCollections.observableArrayList();

            //Recorriendo la lista de hoteles
            for(Hotel hot:listaHoteles){
                String nombre=hot.getNombre();
                int numeroEmpleados=0;
                for(Empleado empleado:listaEmpleados){
                    if(empleado.getHotel().equals(hot.getNombre())){
                        numeroEmpleados+=1;
                    }
                }
                int numerodeHab=hot.getHabitaciones().size();
                if(hot.getHabitaciones().get(0).getNumero()==null){
                    numerodeHab=0;
                }
                datos.add(new ObservableHotel(nombre,numerodeHab,numeroEmpleados));

            }


            tablaHoteles.setItems(datos);


            dialog.close();
        });


        //Valida el textfield y habilita el boton
        textField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean isHotel=false;
            List<Hotel> listaHoteles=sis.getListaHoteles();
            for (int i=0;i<listaHoteles.size();i++){
                if(listaHoteles.get(i).getNombre().equals(textField.getText())) {
                    isHotel=true;
                }
            }
            if(!isHotel){
                textField.setStyle("-fx-border-color:red;");
                removerButton.setDisable(true);
            }else{
                textField.setStyle("-fx-border-color:black;");
                removerButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(label,textField,removerButton);
        VBox.setMargin(removerButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);
        dialog.showAndWait();




    }
    @FXML
    public void AddHotel(ActionEvent e){


        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Añadir Hotel");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField
        Label label=new Label("Nombre del Hotel:");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(17));
        label.setPadding(new Insets(10,10,10,10));

        TextField textField=new TextField();

        textField.setMaxWidth(100);

        textField.setPromptText("Escriba el nombre del hotel");



        //Crea el boton y establece su acción al ser pulsado

        Button añadirButton=new Button("Añadir");
        añadirButton.setDisable(true);

        añadirButton.setOnAction(event -> {
            //Obtiene el texto
            String nombreHotel=textField.getText();

            Hotel hotel=new Hotel(nombreHotel);


            sis.addHotel(hotel);

            List<Hotel> listaHoteles=sis.getListaHoteles();
            List<Empleado> listaEmpleados=sis.getListaEmpleados();


            ObservableList<ObservableHotel> datos=FXCollections.observableArrayList();

            //Recorriendo la lista de hoteles
            for(Hotel hot:listaHoteles){
                String nombre=hot.getNombre();
                int numeroEmpleados=0;
                for(Empleado empleado:listaEmpleados){
                    if(empleado.getHotel().equals(hot.getNombre())){
                        numeroEmpleados+=1;
                    }
                }
                int numerodeHab=hot.getHabitaciones().size();
                if(hot.getHabitaciones().get(0).getNumero()==null){
                    numerodeHab=0;
                }
                datos.add(new ObservableHotel(nombre,numerodeHab,numeroEmpleados));

            }


            tablaHoteles.setItems(datos);


            dialog.close();
        });


        //Valida el textfield y habilita el boton
        textField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean existeHotel=false;
            List<Hotel> listaHoteles=sis.getListaHoteles();
            for (int i=0;i<listaHoteles.size();i++){
                if(listaHoteles.get(i).getNombre().equals(textField.getText())) {
                    existeHotel=true;
                }
            }
            if(newValue.length()<3||existeHotel){
                textField.setStyle("-fx-border-color:red;");
                añadirButton.setDisable(true);
            }else{
                textField.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(label,textField,añadirButton);
        VBox.setMargin(añadirButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);
        dialog.showAndWait();


    }

    //Acciones de Habitaciones
    @FXML
    public void AddHabitacion(ActionEvent e){
        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Añadir Habitación");

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


        //Crear el label y el boxChoice del rango
        Label rangoLabel=new Label("Seleccione el rango:");
        rangoLabel.setTextFill(Color.WHITE);
        rangoLabel.setFont(new Font(17));
        rangoLabel.setPadding(new Insets(10,10,10,10));

        ChoiceBox<String> rangoChoice=new ChoiceBox<>();
        String[] rangos = {"Individual", "Doble", "Triple", "Quad", "Queen", "King", "Suite"};
        rangoChoice.getItems().addAll(rangos);

        //Crear el label del Empleado a Cargo y el textfield
        Label empleadoLabel=new Label("Escriba el ID del empleado a cargo:");
        empleadoLabel.setTextFill(Color.WHITE);
        empleadoLabel.setFont(new Font(17));
        empleadoLabel.setPadding(new Insets(10,10,10,10));

        TextField empleadoIDTextField=new TextField();

        empleadoIDTextField.setMaxWidth(100);

        empleadoIDTextField.setPromptText("Escriba el ID del empleado");

        //Crear el label del Jefe de Piso a Cargo y el textfield
        Label jefePisoLabel=new Label("Escriba el ID del Jefe de Piso a cargo:");
        jefePisoLabel.setTextFill(Color.WHITE);
        jefePisoLabel.setFont(new Font(17));
        jefePisoLabel.setPadding(new Insets(10,10,10,10));

        TextField jefePisoIDTextField=new TextField();

        jefePisoIDTextField.setMaxWidth(100);

        jefePisoIDTextField.setPromptText("Escriba el ID del Jefe de Piso");



        //Crea el boton y establece su acción al ser pulsado

        Button añadirButton=new Button("Añadir");
        añadirButton.setDisable(true);

        añadirButton.setOnAction(event -> {
            //Obtiene el texto
            String numero=numeroTextField.getText();
            String rango=rangoChoice.getValue();
            String empleadoID=empleadoIDTextField.getText();

            for(Hotel hotel:sis.getListaHoteles()){
             if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                 Habitación hab=new Habitación(numero,hotel.getNombre(),rango,empleadoID,"Disponible");
                 sis.addHabitacion(hotel.getNombre(),hab);
                }
            }
            sis.addHabitacionIDJefePiso(tablaHabitaciones.getItems().get(0).getHotel(),numero,jefePisoIDTextField.getText());

            //Actualizar la tabla

            List<Hotel> listaHoteles=sis.getListaHoteles();



            ObservableList<ObservableHabitacion> datos=FXCollections.observableArrayList();

            //Obtiene el nombre del Hotel
            String hotelnombre=tablaHabitaciones.getItems().get(0).getHotel();
            //Recorriendo la lista de hoteles
            for(Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(hotelnombre)){
                    for(Habitación hab:hotel.getHabitaciones()){
                        String numeroHab=hab.getNumero();
                        String nombreHotelHab=hab.getHotel();
                        String rangoHab=hab.getRango();
                        String empleadoIDHab=hab.getEmpleadoID();
                        String estadoHab=hab.getEstado();
                        String jefePisoID = null;

                        for(Jefe_de_Piso jefePiso:sis.getListaJefesPiso()){
                            for (String numeroHabitacion:jefePiso.getListaNumeroHabitaciones()){
                                if(numeroHabitacion.equals(numero)){
                                    jefePisoID=jefePiso.getID();
                                }
                            }
                        }


                        datos.add(new ObservableHabitacion(numeroHab,rangoHab,estadoHab,empleadoIDHab,nombreHotelHab,jefePisoID));
                    }
                }


            }
            tablaHabitaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tablaHabitaciones.setItems(datos);

            dialog.close();
        });


        //Valida los textfield y habilita el boton
        numeroTextField.textProperty().addListener( (observable,oldValue,newValue)->{

            //Validar el numero
            boolean existeNumero=false;
            for(Hotel hotel:sis.getListaHoteles()){
                if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero()!=null && hab.getNumero().equals(numeroTextField.getText())){
                            existeNumero=true;
                        }
                    }
                }
            }

            //Validar el IDEmpleado
            boolean existeEmpleado=false;
            for(Empleado empleado:sis.getListaEmpleados()){
                if(empleado.getHotel().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(empleado.getID().equals(empleadoIDTextField.getText())){
                        existeEmpleado=true;
                    }
                }
            }

            boolean validJefePisoID=false;
            List<Jefe_de_Piso> listaJefesPiso=sis.getListaJefesPiso();
            for (Jefe_de_Piso jefePiso:listaJefesPiso){
                if(jefePiso.getHotel().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(jefePiso.getID().equals(jefePisoIDTextField.getText())){
                        validJefePisoID=true;
                    }
                }
            }
            if(newValue.length()<1||existeNumero){
                numeroTextField.setStyle("-fx-border-color:red;");
                añadirButton.setDisable(true);
            }else if(rangoChoice.getValue()==null){
                añadirButton.setDisable(true);
                numeroTextField.setStyle("-fx-border-color:black;");
            }else if(empleadoIDTextField.getLength()<1||!existeEmpleado){
                añadirButton.setDisable(true);
                numeroTextField.setStyle("-fx-border-color:black;");
            }else if(!validJefePisoID){
                numeroTextField.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(true);
            }
            else{
                numeroTextField.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(false);
            }

        } );

        rangoChoice.valueProperty().addListener( (observable,oldValue,newValue)->{
            //Validar el numero
            boolean existeNumero=false;
            for(Hotel hotel:sis.getListaHoteles()){
                if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero()!=null && hab.getNumero().equals(numeroTextField.getText())){
                            existeNumero=true;
                        }
                    }
                }
            }

            //Validar el IDEmpleado
            boolean existeEmpleado=false;
            for(Empleado empleado:sis.getListaEmpleados()){
                if(empleado.getHotel().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(empleado.getID().equals(empleadoIDTextField.getText())){
                        existeEmpleado=true;
                    }
                }
            }

            boolean validJefePisoID=false;
            List<Jefe_de_Piso> listaJefesPiso=sis.getListaJefesPiso();
            for (Jefe_de_Piso jefePiso:listaJefesPiso){
                if(jefePiso.getHotel().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(jefePiso.getID().equals(jefePisoIDTextField.getText())){
                        validJefePisoID=true;
                    }
                }
            }
            if(newValue==null){
                rangoChoice.setStyle("-fx-border-color:red;");
                añadirButton.setDisable(true);
            }else if(numeroTextField.getLength()<1||existeNumero){
                rangoChoice.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(true);
            }else if(empleadoIDTextField.getLength()<1||!existeEmpleado){
                rangoChoice.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(true);
            }else if(!validJefePisoID){
                rangoChoice.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(true);
            }
            else{
                rangoChoice.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(false);
            }
        } );

        empleadoIDTextField.textProperty().addListener( (observable,oldValue,newValue)->{
            //Validar el numero
            boolean existeNumero=false;
            for(Hotel hotel:sis.getListaHoteles()){
                if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero()!=null && hab.getNumero().equals(numeroTextField.getText())){
                            existeNumero=true;
                        }
                    }
                }
            }

            //Validar el IDEmpleado
            boolean existeEmpleado=false;
            for(Empleado empleado:sis.getListaEmpleados()){
                if(empleado.getHotel().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(empleado.getID().equals(empleadoIDTextField.getText())){
                        existeEmpleado=true;
                    }
                }
            }
            boolean validJefePisoID=false;
            List<Jefe_de_Piso> listaJefesPiso=sis.getListaJefesPiso();
            for (Jefe_de_Piso jefePiso:listaJefesPiso){
                if(jefePiso.getHotel().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(jefePiso.getID().equals(jefePisoIDTextField.getText())){
                        validJefePisoID=true;
                    }
                }
            }
            if(newValue.length()<1||!existeEmpleado){
                empleadoIDTextField.setStyle("-fx-border-color:red;");
                añadirButton.setDisable(true);
            }else if(numeroTextField.getLength()<1||existeNumero){
                añadirButton.setDisable(true);
                empleadoIDTextField.setStyle("-fx-border-color:black;");
            }else if(rangoChoice.getValue()==null){
                añadirButton.setDisable(true);
                empleadoIDTextField.setStyle("-fx-border-color:black;");
            }else if (!validJefePisoID){
                añadirButton.setDisable(true);
                empleadoIDTextField.setStyle("-fx-border-color:black;");
            }else{
                empleadoIDTextField.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(false);
            }



        } );

        jefePisoIDTextField.textProperty().addListener( (observable,oldValue,newValue)->{
            //Validar el numero
            boolean existeNumero=false;
            for(Hotel hotel:sis.getListaHoteles()){
                if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero()!=null && hab.getNumero().equals(numeroTextField.getText())){
                            existeNumero=true;
                        }
                    }
                }
            }

            //Validar el IDEmpleado
            boolean existeEmpleado=false;
            for(Empleado empleado:sis.getListaEmpleados()){
                if(empleado.getHotel().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(empleado.getID().equals(empleadoIDTextField.getText())){
                        existeEmpleado=true;
                    }
                }
            }

            boolean validJefePisoID=false;
            List<Jefe_de_Piso> listaJefesPiso=sis.getListaJefesPiso();
            for (Jefe_de_Piso jefePiso:listaJefesPiso){
                if(jefePiso.getHotel().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(jefePiso.getID().equals(newValue)){
                        validJefePisoID=true;
                    }
                }
            }
            if(!validJefePisoID){
                jefePisoIDTextField.setStyle("-fx-border-color:red;");
                añadirButton.setDisable(true);
            }else if(empleadoIDTextField.getLength()<1||!existeEmpleado){
                jefePisoIDTextField.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(true);
            }else if(numeroTextField.getLength()<1||existeNumero){
                añadirButton.setDisable(true);
                jefePisoIDTextField.setStyle("-fx-border-color:black;");
            }else if(rangoChoice.getValue()==null){
                añadirButton.setDisable(true);
                jefePisoIDTextField.setStyle("-fx-border-color:black;");
            }else{
                jefePisoIDTextField.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(false);
            }



        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(numeroLabel,numeroTextField,rangoLabel,rangoChoice,empleadoLabel,empleadoIDTextField,jefePisoLabel,jefePisoIDTextField,añadirButton);
        VBox.setMargin(añadirButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,500,400);
        dialog.setScene(scene);
        dialog.showAndWait();


    }

    @FXML
    public void RemoveHabitacion(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Remover Habitación");

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



        //Crea el boton y establece su acción al ser pulsado

        Button removerButton=new Button("Remover");
        removerButton.setDisable(true);

        removerButton.setOnAction(event -> {
            //Obtiene el texto
            String numero=numeroTextField.getText();

            for(Hotel hotel:sis.getListaHoteles()){
                if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(hotel.getHabitaciones().size()==1){
                        Habitación hab=new Habitación(hotel.getNombre());
                        sis.addHabitacion(hotel.getNombre(),hab);
                        sis.removeHabitacion(hotel.getNombre(),numero);
                    }else{
                        sis.removeHabitacion(hotel.getNombre(),numero);
                    }


                }
            }

            //Actualizar la tabla

            List<Hotel> listaHoteles=sis.getListaHoteles();

            ObservableList<ObservableHabitacion> datos=FXCollections.observableArrayList();

            //Obtiene el nombre del Hotel
            String hotelnombre=tablaHabitaciones.getItems().get(0).getHotel();
            //Recorriendo la lista de hoteles
            for(Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(hotelnombre)){
                    for(Habitación hab:hotel.getHabitaciones()){
                        String numeroHab=hab.getNumero();
                        String nombreHotelHab=hab.getHotel();
                        String rangoHab=hab.getRango();
                        String empleadoIDHab=hab.getEmpleadoID();
                        String estadoHab=hab.getEstado();
                        String jefePisoID = null;

                        for(Jefe_de_Piso jefePiso:sis.getListaJefesPiso()){
                            for (String numeroHabitacion:jefePiso.getListaNumeroHabitaciones()){
                                if(numeroHabitacion.equals(numero)){
                                    jefePisoID=jefePiso.getID();
                                }
                            }
                        }

                        datos.add(new ObservableHabitacion(numeroHab,rangoHab,estadoHab,empleadoIDHab,nombreHotelHab,jefePisoID));
                    }
                }


            }
            tablaHabitaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tablaHabitaciones.setItems(datos);

            dialog.close();
        });


        //Valida los textfield y habilita el boton
        numeroTextField.textProperty().addListener( (observable,oldValue,newValue)->{

            //Validar el numero
            boolean existe=false;
            for(Hotel hotel:sis.getListaHoteles()){
                if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero()!=null && hab.getNumero().equals(numeroTextField.getText())){
                            existe=true;
                        }
                    }
                }
            }
            if(newValue.length()<1||!existe){
                numeroTextField.setStyle("-fx-border-color:red;");
                removerButton.setDisable(true);
            }else{
                numeroTextField.setStyle("-fx-border-color:black;");
                removerButton.setDisable(false);
            }

            });


        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(numeroLabel,numeroTextField,removerButton);
        VBox.setMargin(removerButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);

        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);
        dialog.showAndWait();



    }


    //Añadir Cliente VIP
    @FXML
    public void AddClienteVIP(ActionEvent e){


        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Añadir Cliente VIP");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField
        Label label=new Label("ID del Cliente:");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(17));
        label.setPadding(new Insets(10,10,10,10));

        TextField textField=new TextField();

        textField.setMaxWidth(100);

        textField.setPromptText("Escriba el ID del Cliente");



        //Crea el boton y establece su acción al ser pulsado

        Button añadirButton=new Button("Añadir");
        añadirButton.setDisable(true);

        añadirButton.setOnAction(event -> {
            //Obtiene el texto
            String empleadoID=textField.getText();


            sis.addClienteVIP(empleadoID);

            List<String> listaClientesVIP=sis.getListaClientesVIP();


            ObservableList<ObservableClienteVIP> datos=FXCollections.observableArrayList();

            //Recorriendo la lista de hoteles
            for(String id:listaClientesVIP){

                datos.add(new ObservableClienteVIP(id));

            }


            tablaClientesVIP.setItems(datos);


            dialog.close();
        });


        //Valida el textfield y habilita el boton
        textField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean existeClienteID=false;
            List<String> listaClientesVIP=sis.getListaClientesVIP();
            for (int i=0;i<listaClientesVIP.size();i++){
                if(listaClientesVIP.get(i).equals(textField.getText())) {
                    existeClienteID=true;
                }
            }
            if(newValue.length()<8||existeClienteID){
                textField.setStyle("-fx-border-color:red;");
                añadirButton.setDisable(true);
            }else{
                textField.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(label,textField,añadirButton);
        VBox.setMargin(añadirButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);
        dialog.showAndWait();

    }


    //Remover Cliente VIP
    @FXML
    public void RemoveClienteVIP(ActionEvent e){


        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Remover Cliente VIP");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField
        Label label=new Label("ID del Cliente:");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(17));
        label.setPadding(new Insets(10,10,10,10));

        TextField textField=new TextField();

        textField.setMaxWidth(100);

        textField.setPromptText("Escriba el ID del Cliente");



        //Crea el boton y establece su acción al ser pulsado

        Button removerButton=new Button("Remover");
        removerButton.setDisable(true);

        removerButton.setOnAction(event -> {
            //Obtiene el texto
            String empleadoID=textField.getText();


            sis.removeClienteVIP(empleadoID);

            List<String> listaClientesVIP=sis.getListaClientesVIP();



            ObservableList<ObservableClienteVIP> datos=FXCollections.observableArrayList();

            //Recorriendo la lista de Clientes
            for(String id:listaClientesVIP){

                datos.add(new ObservableClienteVIP(id));

            }


            tablaClientesVIP.setItems(datos);


            dialog.close();
        });


        //Valida el textfield y habilita el boton
        textField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean existeClienteID=false;
            List<String> listaClientesVIP=sis.getListaClientesVIP();
            for (int i=0;i<listaClientesVIP.size();i++){
                if(listaClientesVIP.get(i).equals(textField.getText())) {
                    existeClienteID=true;
                }
            }
            if(newValue.length()<8||!existeClienteID){
                textField.setStyle("-fx-border-color:red;");
                removerButton.setDisable(true);
            }else{
                textField.setStyle("-fx-border-color:black;");
                removerButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(label,textField,removerButton);
        VBox.setMargin(removerButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    @FXML
    public void AddAdminID(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Añadir Admin ID");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField
        Label label=new Label("ID del Admin:");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(17));
        label.setPadding(new Insets(10,10,10,10));

        TextField textField=new TextField();

        textField.setMaxWidth(100);

        textField.setPromptText("Escriba el ID del Admin");



        //Crea el boton y establece su acción al ser pulsado

        Button añadirButton=new Button("Añadir");
        añadirButton.setDisable(true);

        añadirButton.setOnAction(event -> {
            //Obtiene el texto
            String adminID=textField.getText();


            sis.addAdminID(adminID);

            List<String> listaAdminID=sis.getListaAdminID();



            ObservableList<ObservableAdminID> datos=FXCollections.observableArrayList();

            //Recorriendo la lista de hoteles
            for(String id:listaAdminID){

                datos.add(new ObservableAdminID(id));

            }


            tablaAdminID.setItems(datos);


            dialog.close();
        });


        //Valida el textfield y habilita el boton
        textField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean existeAdminID=false;
            List<String> listaAdminID=sis.getListaAdminID();
            for (int i=0;i<listaAdminID.size();i++){
                if(listaAdminID.get(i).equals(textField.getText())) {
                    existeAdminID=true;
                }
            }
            if(newValue.length()<8||existeAdminID){
                textField.setStyle("-fx-border-color:red;");
                añadirButton.setDisable(true);
            }else{
                textField.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(label,textField,añadirButton);
        VBox.setMargin(añadirButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    @FXML
    public void RemoveAdminID(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Remover Admin ID");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField
        Label label=new Label("ID del Admin:");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(17));
        label.setPadding(new Insets(10,10,10,10));

        TextField textField=new TextField();

        textField.setMaxWidth(100);

        textField.setPromptText("Escriba el ID del Admin");



        //Crea el boton y establece su acción al ser pulsado

        Button removerButton=new Button("Remover");
        removerButton.setDisable(true);

        removerButton.setOnAction(event -> {
            //Obtiene el texto
            String adminID=textField.getText();


            sis.removeAdminID(adminID);

            List<String> listaAdminID=sis.getListaAdminID();



            ObservableList<ObservableAdminID> datos=FXCollections.observableArrayList();

            //Recorriendo la lista de Clientes
            for(String id:listaAdminID){

                datos.add(new ObservableAdminID(id));

            }


            tablaAdminID.setItems(datos);


            dialog.close();
        });


        //Valida el textfield y habilita el boton
        textField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean existeAdminID=false;
            List<String> listaAdminID=sis.getListaAdminID();
            for (int i=0;i<listaAdminID.size();i++){
                if(listaAdminID.get(i).equals(textField.getText())) {
                    existeAdminID=true;
                }
            }
            if(newValue.length()<8||!existeAdminID){
                textField.setStyle("-fx-border-color:red;");
                removerButton.setDisable(true);
            }else{
                textField.setStyle("-fx-border-color:black;");
                removerButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(label,textField,removerButton);
        VBox.setMargin(removerButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);
        dialog.showAndWait();
    }


    @FXML
    public void AddEmpleado(ActionEvent e){
        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Añadir Empleado");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField del ID del Empleado
        Label IDLabel=new Label("ID del Empleado:");
        IDLabel.setTextFill(Color.WHITE);
        IDLabel.setFont(new Font(17));
        IDLabel.setPadding(new Insets(10,10,10,10));

        TextField IDTextField=new TextField();

        IDTextField.setMaxWidth(100);

        IDTextField.setPromptText("Escriba el ID del empleado");

        //Crear el label y el textField del nombre del Empleado
        Label nombreLabel=new Label("Nombre:");
        nombreLabel.setTextFill(Color.WHITE);
        nombreLabel.setFont(new Font(17));
        nombreLabel.setPadding(new Insets(10,10,10,10));

        TextField nombreTextField=new TextField();

        nombreTextField.setMaxWidth(100);

        nombreTextField.setPromptText("Escriba el nombre del Empleado");



        //Crea el boton y establece su acción al ser pulsado

        Button añadirButton=new Button("Añadir");
        añadirButton.setDisable(true);

        añadirButton.setOnAction(event -> {
            //Obtiene el texto
            String ID=IDTextField.getText();
            String nombre=nombreTextField.getText();


            for(Hotel hotel:sis.getListaHoteles()){
                if(hotel.getNombre().equals(empleadoHotel)){
                    Empleado newEmpleado=new Empleado(nombre,ID,hotel.getNombre());
                    sis.addEmpleado(newEmpleado);
                }
            }

            //Actualizar la tabla

            List<Empleado> listaEmpleados=sis.getListaEmpleados();
            List<Hotel> listaHoteles=sis.getListaHoteles();


            ObservableList<ObservableEmpleado> datos=FXCollections.observableArrayList();

            //Obtiene el nombre del Hotel
            String hotelnombre=empleadoHotel;
            //Recorriendo la lista de hoteles
            for(Hotel hotel:listaHoteles){

                if(hotel.getNombre().equals(hotelnombre)){
                    for(Empleado empleado:listaEmpleados){
                        if(empleado.getHotel().equals(hotel.getNombre())){
                            String IDEmpleado=empleado.getID();
                            String nombreEmpleado=empleado.getNombre();

                            int numeroHab=0;
                            int cantHabitacionesHotel=hotel.getHabitaciones()!=null? hotel.getHabitaciones().size():0;
                            for(Habitación hab:hotel.getHabitaciones()){
                                String empleadoID=hab.getEmpleadoID();
                                if(empleadoID!=null && empleadoID.equals(empleado.getID())){
                                    numeroHab+=1;
                                }
                            }
                            double porcentajeHab=0;
                            if(cantHabitacionesHotel!=0){
                                 porcentajeHab= (double) numeroHab / cantHabitacionesHotel*100;
                            }



                            datos.add(new ObservableEmpleado(nombreEmpleado,IDEmpleado,empleado.getHotel(),numeroHab,Math.round(porcentajeHab)));
                        }



                    }
                }


            }
            tablaEmpleados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tablaEmpleados.setItems(datos);

            dialog.close();
        });


        //Valida los textfield y habilita el boton
       IDTextField.textProperty().addListener( (observable,oldValue,newValue)->{

            //Validar el ID
            boolean existe=false;
            for(Empleado empleado:sis.getListaEmpleados()){
                if(empleado.getHotel().equals(empleadoHotel)){
                    if(empleado.getID().equals(newValue)){
                        existe=true;
                    }
                }
            }
            if(newValue.length()<1||existe){
                IDTextField.setStyle("-fx-border-color:red;");
                añadirButton.setDisable(true);
            }else if(nombreTextField.getText().length()<3){
                añadirButton.setDisable(true);
                IDTextField.setStyle("-fx-border-color:black;");
            }else{
                IDTextField.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(false);
            }

        } );

        nombreTextField.textProperty().addListener( (observable,oldValue,newValue)->{
            //Validar el ID
            boolean existe=false;
            for(Empleado empleado:sis.getListaEmpleados()){
                if(empleado.getHotel().equals(empleadoHotel)){
                    if(empleado.getID().equals(IDTextField.getText())){
                        existe=true;
                    }
                }
            }
            if(newValue.length()<3){
                nombreTextField.setStyle("-fx-border-color:red;");
                añadirButton.setDisable(true);
            }else if(IDTextField.getLength()<1||existe){
                nombreTextField.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(true);
            }else{
                nombreTextField.setStyle("-fx-border-color:black;");
                añadirButton.setDisable(false);
            }
        } );


        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(IDLabel,IDTextField,nombreLabel,nombreTextField,añadirButton);


        VBox.setMargin(añadirButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);

        dialog.setScene(scene);
        dialog.showAndWait();


    }
    @FXML
    public void RemoveEmpleado(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Remover Empleado");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField del ID
        Label IDLabel=new Label("ID del Empleado:");
        IDLabel.setTextFill(Color.WHITE);
        IDLabel.setFont(new Font(17));
        IDLabel.setPadding(new Insets(10,10,10,10));

        TextField IDTextField=new TextField();

        IDTextField.setMaxWidth(100);

        IDTextField.setPromptText("Escriba el ID del Empleado");


        //Crea el boton y establece su acción al ser pulsado

        Button removerButton=new Button("Remover");
        removerButton.setDisable(true);

        removerButton.setOnAction(event -> {
            //Obtiene el texto
            String ID=IDTextField.getText();
            sis.removeEmpleado(empleadoHotel,ID);

            //Actualizar la tabla
            List<Hotel> listaHoteles=sis.getListaHoteles();

            ObservableList<ObservableEmpleado> datos=FXCollections.observableArrayList();

            //Obtiene el nombre del Hotel
            String hotelnombre=empleadoHotel;

            //Recorriendo la lista de hoteles
            for(Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(hotelnombre)){
                    for(Empleado empleado:sis.getListaEmpleados()){
                        if(empleado.getHotel().equals(hotelnombre)){
                            int numeroHab=0;
                            int cantHabHotel=hotel.getHabitaciones()!=null?hotel.getHabitaciones().size():0;
                            for(Habitación hab:hotel.getHabitaciones()){
                                String empleadoID=hab.getEmpleadoID();
                                if(empleadoID.equals(empleado.getID())){
                                    numeroHab+=1;
                                }
                            }
                            double porcentajeHab=0;
                            if(cantHabHotel!=0){
                                porcentajeHab=(double) numeroHab/cantHabHotel*100;
                            }
                            datos.add(new ObservableEmpleado(empleado.getNombre(), empleado.getID(), empleado.getHotel(),numeroHab,porcentajeHab));

                        }

                    }
                }

                tablaEmpleados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                tablaEmpleados.setItems(datos);

            dialog.close();


                }
        });


        //Valida los textfield y habilita el boton
        IDTextField.textProperty().addListener( (observable,oldValue,newValue)->{

            //Validar el ID
            boolean existe=false;
            for(Empleado empleado:sis.getListaEmpleados()){
                if(empleado.getHotel().equals(empleadoHotel)){
                   if(empleado.getID().equals(newValue)){
                       existe=true;
                   }
                }
            }
            if(newValue.length()<1||!existe){
                IDTextField.setStyle("-fx-border-color:red;");
                removerButton.setDisable(true);
            }else{
                IDTextField.setStyle("-fx-border-color:black;");
                removerButton.setDisable(false);
            }

        });


        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(IDLabel,IDTextField,removerButton);
        VBox.setMargin(removerButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);

        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);
        dialog.showAndWait();




    }
    @FXML
    public void AddEmpleadoHabitacion(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Escriba el Número de la Habitación:");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField
        Label label=new Label("Número de la Habitación:");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(17));
        label.setPadding(new Insets(10,10,10,10));

        TextField textField=new TextField();

        textField.setMaxWidth(100);

        textField.setPromptText("Escriba el número de la Habitación");



        //Crea el boton y establece su acción al ser pulsado

        Button buscarButton=new Button("Añadir");
        buscarButton.setDisable(true);

        buscarButton.setOnAction(event -> {

            List<Hotel> listaHoteles=sis.getListaHoteles();



            ObservableList<ObservableHabitacion> datos=FXCollections.observableArrayList();

            //Obtiene el texto
            String NumeroHab=textField.getText();
            //Recorriendo la lista de hoteles
            for(Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(empleadoHotel)){

                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero().equals(NumeroHab)){
                            sis.setEmpleadoID(empleadoHotel,actualEmpleadoID,NumeroHab);
                        }

                        if(hab.getEmpleadoID()!=null&& hab.getEmpleadoID().equals(actualEmpleadoID)){
                            String numero=hab.getNumero();
                            String nombreHotel=hab.getHotel();
                            String rango=hab.getRango();
                            String empleadoID=hab.getEmpleadoID();
                            String estado=hab.getEstado();
                            String jefePisoID = null;

                            for(Jefe_de_Piso jefePiso:sis.getListaJefesPiso()){
                                for (String numeroHabitacion:jefePiso.getListaNumeroHabitaciones()){
                                    if(numeroHabitacion.equals(numero)){
                                        jefePisoID=jefePiso.getID();
                                    }
                                }
                            }
                            datos.add(new ObservableHabitacion(numero,rango,estado,empleadoID,nombreHotel,jefePisoID));
                        }



                    }
                }


            }
            tablaHabitaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tablaHabitaciones.setItems(datos);

            VBox tablaVbox=new VBox();
            tablaVbox.setAlignment(Pos.CENTER);

            tablaVbox.getChildren().add(tablaHabitaciones);
            VBox.setMargin(tablaHabitaciones,new Insets(20,0,20,20));
            ContainerBorderPane.setCenter(tablaVbox);



            dialog.close();
        });


        //Valida el textfield y habilita el boton
        textField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean validNumeroHab=false;
            List<Hotel> listaHoteles=sis.getListaHoteles();
            for (Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(empleadoHotel)){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero().equals(newValue)){
                            validNumeroHab=true;
                        }
                    }
                }


            }
            if(!validNumeroHab){
                textField.setStyle("-fx-border-color:red;");
                buscarButton.setDisable(true);
            }else{
                textField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(label,textField,buscarButton);
        VBox.setMargin(buscarButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);

        dialog.showAndWait();
    }

    @FXML
    public void RemoveEmpleadoHabitacion(ActionEvent e){
        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Escriba el Número de la Habitación:");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField
        Label label=new Label("Número de la Habitación:");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(17));
        label.setPadding(new Insets(10,10,10,10));

        TextField textField=new TextField();

        textField.setMaxWidth(100);

        textField.setPromptText("Escriba el número de la Habitación");



        //Crea el boton y establece su acción al ser pulsado

        Button buscarButton=new Button("Remover");
        buscarButton.setDisable(true);

        buscarButton.setOnAction(event -> {

            List<Hotel> listaHoteles=sis.getListaHoteles();



            ObservableList<ObservableHabitacion> datos=FXCollections.observableArrayList();

            //Obtiene el texto
            String NumeroHab=textField.getText();
            //Recorriendo la lista de hoteles
            for(Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(empleadoHotel)){

                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero().equals(NumeroHab)){
                            sis.removeEmpleadoID(empleadoHotel,NumeroHab);
                        }

                        if(hab.getEmpleadoID()!=null&& hab.getEmpleadoID().equals(actualEmpleadoID)){
                            String numero=hab.getNumero();
                            String nombreHotel=hab.getHotel();
                            String rango=hab.getRango();
                            String empleadoID=hab.getEmpleadoID();
                            String estado=hab.getEstado();
                            String jefePisoID = null;

                            for(Jefe_de_Piso jefePiso:sis.getListaJefesPiso()){
                                for (String numeroHabitacion:jefePiso.getListaNumeroHabitaciones()){
                                    if(numeroHabitacion.equals(numero)){
                                        jefePisoID=jefePiso.getID();
                                    }
                                }
                            }
                            datos.add(new ObservableHabitacion(numero,rango,estado,empleadoID,nombreHotel,jefePisoID));
                        }



                    }
                }


            }
            tablaHabitaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tablaHabitaciones.setItems(datos);

            VBox tablaVbox=new VBox();
            tablaVbox.setAlignment(Pos.CENTER);

            tablaVbox.getChildren().add(tablaHabitaciones);
            VBox.setMargin(tablaHabitaciones,new Insets(20,0,20,20));
            ContainerBorderPane.setCenter(tablaVbox);



            dialog.close();
        });


        //Valida el textfield y habilita el boton
        textField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean validNumeroHab=false;
            List<Hotel> listaHoteles=sis.getListaHoteles();
            for (Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(empleadoHotel)){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero().equals(newValue)){
                            validNumeroHab=true;
                        }
                    }
                }


            }
            if(!validNumeroHab){
                textField.setStyle("-fx-border-color:red;");
                buscarButton.setDisable(true);
            }else{
                textField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(label,textField,buscarButton);
        VBox.setMargin(buscarButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);

        dialog.showAndWait();
    }

    @FXML
    public void AddHabitacionEmpleado(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Escriba el Número de la Habitación:");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField del Numero de la Habitacion
        Label numLabel=new Label("Número de la Habitación:");
        numLabel.setTextFill(Color.WHITE);
        numLabel.setFont(new Font(17));
        numLabel.setPadding(new Insets(10,10,10,10));

        TextField numTextField=new TextField();

        numTextField.setMaxWidth(100);

        numTextField.setPromptText("Escriba el número de la Habitación");

        //Crear el label y el textField del ID del Empleado
        Label IDLabel=new Label("ID del Empleado:");
        IDLabel.setTextFill(Color.WHITE);
        IDLabel.setFont(new Font(17));
        IDLabel.setPadding(new Insets(10,10,10,10));

        TextField IDTextField=new TextField();

        IDTextField.setMaxWidth(100);

        IDTextField.setPromptText("Escriba el ID del Empleado");



        //Crea el boton y establece su acción al ser pulsado

        Button buscarButton=new Button("Añadir");
        buscarButton.setDisable(true);

        buscarButton.setOnAction(event -> {

            List<Hotel> listaHoteles=sis.getListaHoteles();



            ObservableList<ObservableHabitacion> datos=FXCollections.observableArrayList();

            //Obtiene el texto
            String NumeroHab=numTextField.getText();
            String IDEmpleado=IDTextField.getText();

            //Recorriendo la lista de hoteles
            for(Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero().equals(NumeroHab)){
                            sis.setEmpleadoID(empleadoHotel,IDEmpleado,NumeroHab);
                        }
                            String numero=hab.getNumero();
                            String nombreHotel=hab.getHotel();
                            String rango=hab.getRango();
                            String empleadoID=hab.getEmpleadoID();
                            String estado=hab.getEstado();
                        String jefePisoID = null;

                        for(Jefe_de_Piso jefePiso:sis.getListaJefesPiso()){
                            for (String numeroHabitacion:jefePiso.getListaNumeroHabitaciones()){
                                if(numeroHabitacion.equals(numero)){
                                    jefePisoID=jefePiso.getID();
                                }
                            }
                        }
                            datos.add(new ObservableHabitacion(numero,rango,estado,empleadoID,nombreHotel,jefePisoID));




                    }
                }


            }
            tablaHabitaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tablaHabitaciones.setItems(datos);

            VBox tablaVbox=new VBox();
            tablaVbox.setAlignment(Pos.CENTER);

            tablaVbox.getChildren().add(tablaHabitaciones);
            VBox.setMargin(tablaHabitaciones,new Insets(20,0,20,20));
            ContainerBorderPane.setCenter(tablaVbox);



            dialog.close();
        });


        //Valida el textfield y habilita el boton
        numTextField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean validNumeroHab=false;
            List<Hotel> listaHoteles=sis.getListaHoteles();
            for (Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero().equals(newValue)){
                            validNumeroHab=true;
                        }
                    }
                }
            }

            boolean validIDEmpleado=false;
            List<Empleado> listaEmpleados=sis.getListaEmpleados();
                for (Empleado empleado:listaEmpleados){
                if(empleado.getHotel().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(empleado.getID().equals(IDTextField)){
                        validIDEmpleado=true;
                    }
                }
            }
            if(!validNumeroHab){
                numTextField.setStyle("-fx-border-color:red;");
                buscarButton.setDisable(true);
            }else if(!validIDEmpleado){
                buscarButton.setDisable(true);
            }else{
                numTextField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(false);
            }
        } );

        IDTextField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean validNumeroHab=false;
            List<Hotel> listaHoteles=sis.getListaHoteles();
            for (Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero().equals(numTextField.getText())){
                            validNumeroHab=true;
                        }
                    }
                }
            }

            boolean validIDEmpleado=false;
            List<Empleado> listaEmpleados=sis.getListaEmpleados();
            for (Empleado empleado:listaEmpleados){
                if(empleado.getHotel().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(empleado.getID().equals(newValue)){
                        validIDEmpleado=true;
                    }
                }
            }
            if(!validIDEmpleado){
                IDTextField.setStyle("-fx-border-color:red;");
                buscarButton.setDisable(true);
            }else if(!validNumeroHab){
                buscarButton.setDisable(true);
            }else{
                IDTextField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(numLabel,numTextField,IDLabel,IDTextField,buscarButton);
        VBox.setMargin(buscarButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);

        dialog.showAndWait();
    }

    //Acciones de Jefe de piso
    @FXML
    public void AddJefePiso(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Añadir Jefe de Piso:");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField del ID del Jefe de Piso
        Label IDLabel=new Label("ID:");
        IDLabel.setTextFill(Color.WHITE);
        IDLabel.setFont(new Font(17));
        IDLabel.setPadding(new Insets(10,10,10,10));

        TextField IDTextField=new TextField();

        IDTextField.setMaxWidth(100);

        IDTextField.setPromptText("Escriba el ID del Jefe de Piso");

        //Crea el boton y establece su acción al ser pulsado

        Button buscarButton=new Button("Añadir");
        buscarButton.setDisable(true);

        buscarButton.setOnAction(event -> {

            List<Jefe_de_Piso> listaJefesPiso=sis.getListaJefesPiso();



            ObservableList<ObservableJefePiso> datos=FXCollections.observableArrayList();

            //Obtiene el texto
            String ID=IDTextField.getText();

            Jefe_de_Piso newJefePiso=new Jefe_de_Piso(actualJefePisoHotel,ID);

            sis.addJefePiso(newJefePiso);

            //Recorriendo la lista de Jefes de Piso
            for(Jefe_de_Piso jefePiso:listaJefesPiso){
                if(jefePiso.getHotel().equals(actualJefePisoHotel)){
                    int numeroHabitaciones=jefePiso.getListaNumeroHabitaciones()!=null ? jefePiso.getListaNumeroHabitaciones().size():0;
                    datos.add(new ObservableJefePiso(jefePiso.getID(),numeroHabitaciones,actualJefePisoHotel));

                }


            }
            tablaJefesPiso.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tablaJefesPiso.setItems(datos);

            VBox tablaVbox=new VBox();
            tablaVbox.setAlignment(Pos.CENTER);

            tablaVbox.getChildren().add(tablaJefesPiso);
            VBox.setMargin(tablaJefesPiso,new Insets(20,0,20,20));
            ContainerBorderPane.setCenter(tablaVbox);


            dialog.close();
        });


        //Valida el textfield y habilita el boton
        IDTextField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean existe=false;
            List<Jefe_de_Piso> listaJefesPiso=sis.getListaJefesPiso();
            for(Jefe_de_Piso jefePiso:listaJefesPiso){
                if(jefePiso.getHotel().equals(actualJefePisoHotel)){
                    if(jefePiso.getID().equals(newValue)){
                        existe=true;
                    }
                }
            }

            if(newValue.length() < 1 || existe){
                IDTextField.setStyle("-fx-border-color:red;");
                buscarButton.setDisable(true);
            }else{
                IDTextField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(false);
            }
        } );


        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(IDLabel,IDTextField,buscarButton);
        VBox.setMargin(buscarButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);

        dialog.showAndWait();
    }

    @FXML
    public  void RemoveJefePiso(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Remover Jefe de Piso:");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField del ID del Jefe de Piso
        Label IDLabel=new Label("ID:");
        IDLabel.setTextFill(Color.WHITE);
        IDLabel.setFont(new Font(17));
        IDLabel.setPadding(new Insets(10,10,10,10));

        TextField IDTextField=new TextField();

        IDTextField.setMaxWidth(100);

        IDTextField.setPromptText("Escriba el ID del Jefe de Piso");

        //Crea el boton y establece su acción al ser pulsado

        Button buscarButton=new Button("Remover");
        buscarButton.setDisable(true);

        buscarButton.setOnAction(event -> {

            List<Jefe_de_Piso> listaJefesPiso=sis.getListaJefesPiso();



            ObservableList<ObservableJefePiso> datos=FXCollections.observableArrayList();

            //Obtiene el texto
            String ID=IDTextField.getText();



            sis.removeJefePiso(ID,actualJefePisoHotel);

            //Recorriendo la lista de Jefes de Piso
            for(Jefe_de_Piso jefePiso:listaJefesPiso){
                if(jefePiso.getHotel().equals(actualJefePisoHotel)){
                    int numeroHabitaciones=jefePiso.getListaNumeroHabitaciones().size();
                    datos.add(new ObservableJefePiso(jefePiso.getID(),numeroHabitaciones,actualJefePisoHotel));

                }


            }
            tablaJefesPiso.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tablaJefesPiso.setItems(datos);

            VBox tablaVbox=new VBox();
            tablaVbox.setAlignment(Pos.CENTER);

            tablaVbox.getChildren().add(tablaJefesPiso);
            VBox.setMargin(tablaJefesPiso,new Insets(20,0,20,20));
            ContainerBorderPane.setCenter(tablaVbox);


            dialog.close();
        });


        //Valida el textfield y habilita el boton
        IDTextField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean existe=false;
            List<Jefe_de_Piso> listaJefesPiso=sis.getListaJefesPiso();
            for(Jefe_de_Piso jefePiso:listaJefesPiso){
                if(jefePiso.getHotel().equals(actualJefePisoHotel)){
                    if(jefePiso.getID().equals(newValue)){
                        existe=true;
                    }
                }
            }

            if(!existe){
                IDTextField.setStyle("-fx-border-color:red;");
                buscarButton.setDisable(true);
            }else{
                IDTextField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(false);
            }
        } );


        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(IDLabel,IDTextField,buscarButton);
        VBox.setMargin(buscarButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);

        dialog.showAndWait();
    }

    @FXML
    public void AddHabitacionJefePiso(ActionEvent e){

        //Inicializar el gestor
        Sistema sis=new Sistema();

        Stage dialog=new Stage();
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Añadir Jefe de Piso a la Habitación:");

        //Crear el formulario

        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-background-color:#002");

        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);

        //Crear el label y el textField del Numero de la Habitacion
        Label numLabel=new Label("Número de la Habitación:");
        numLabel.setTextFill(Color.WHITE);
        numLabel.setFont(new Font(17));
        numLabel.setPadding(new Insets(10,10,10,10));

        TextField numTextField=new TextField();

        numTextField.setMaxWidth(100);

        numTextField.setPromptText("Escriba el número de la Habitación");

        //Crear el label y el textField del ID del Jefe de Piso
        Label IDLabel=new Label("ID del Jefe de Piso:");
        IDLabel.setTextFill(Color.WHITE);
        IDLabel.setFont(new Font(17));
        IDLabel.setPadding(new Insets(10,10,10,10));

        TextField IDTextField=new TextField();

        IDTextField.setMaxWidth(100);

        IDTextField.setPromptText("Escriba el ID del Jefe de Piso");



        //Crea el boton y establece su acción al ser pulsado

        Button buscarButton=new Button("Añadir");
        buscarButton.setDisable(true);

        buscarButton.setOnAction(event -> {

            List<Hotel> listaHoteles=sis.getListaHoteles();

            ObservableList<ObservableHabitacion> datos=FXCollections.observableArrayList();

            //Obtiene el texto
            String NumeroHab=numTextField.getText();
            String IDJefePiso=IDTextField.getText();

            sis.addHabitacionIDJefePiso(tablaHabitaciones.getItems().get(0).getHotel(),NumeroHab,IDJefePiso);


            //Recorriendo la lista de hoteles
            for(Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    for(Habitación hab:hotel.getHabitaciones()){

                        String numero=hab.getNumero();
                        String nombreHotel=hab.getHotel();
                        String rango=hab.getRango();
                        String empleadoID=hab.getEmpleadoID();
                        String estado=hab.getEstado();
                        String jefePisoID = null;

                        for(Jefe_de_Piso jefePiso:sis.getListaJefesPiso()){
                            for (String numeroHabitacion:jefePiso.getListaNumeroHabitaciones()){
                                if(numeroHabitacion.equals(numero)){
                                    jefePisoID=jefePiso.getID();
                                }
                            }
                        }
                        datos.add(new ObservableHabitacion(numero,rango,estado,empleadoID,nombreHotel,jefePisoID));




                    }
                }


            }



            tablaHabitaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tablaHabitaciones.setItems(datos);

            VBox tablaVbox=new VBox();
            tablaVbox.setAlignment(Pos.CENTER);

            tablaVbox.getChildren().add(tablaHabitaciones);
            VBox.setMargin(tablaHabitaciones,new Insets(20,0,20,20));
            ContainerBorderPane.setCenter(tablaVbox);



            dialog.close();

        });


        //Valida el textfield y habilita el boton
        numTextField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean validNumeroHab=false;
            List<Hotel> listaHoteles=sis.getListaHoteles();
            for (Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero().equals(newValue)){
                            validNumeroHab=true;
                        }
                    }
                }
            }

            boolean validJefePisoID=false;
            List<Jefe_de_Piso> listaJefesPiso=sis.getListaJefesPiso();
            for (Jefe_de_Piso jefePiso:listaJefesPiso){
                if(jefePiso.getHotel().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(jefePiso.getID().equals(IDTextField.getText())){
                        validJefePisoID=true;
                    }
                }
            }
            if(!validNumeroHab){
                numTextField.setStyle("-fx-border-color:red;");
                buscarButton.setDisable(true);
            }else if(!validJefePisoID){
                numTextField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(true);
            }else{
                numTextField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(false);
            }
        } );

        IDTextField.textProperty().addListener( (observable,oldValue,newValue)->{
            boolean validNumeroHab=false;
            List<Hotel> listaHoteles=sis.getListaHoteles();
            for (Hotel hotel:listaHoteles){
                if(hotel.getNombre().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    for(Habitación hab:hotel.getHabitaciones()){
                        if(hab.getNumero().equals(numTextField.getText())){
                            validNumeroHab=true;
                        }
                    }
                }
            }

            boolean validJefePisoID=false;
            List<Jefe_de_Piso> listaJefesPiso=sis.getListaJefesPiso();
            for (Jefe_de_Piso jefePiso:listaJefesPiso){
                if(jefePiso.getHotel().equals(tablaHabitaciones.getItems().get(0).getHotel())){
                    if(jefePiso.getID().equals(newValue)){
                        validJefePisoID=true;
                    }
                }
            }
            if(!validJefePisoID){
                IDTextField.setStyle("-fx-border-color:red;");
                buscarButton.setDisable(true);
            }else if(!validNumeroHab){
                IDTextField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(true);
            }else{
                IDTextField.setStyle("-fx-border-color:black;");
                buscarButton.setDisable(false);
            }
        } );

        //Añadir el label,textfield y el boton al formulario
        vbox.getChildren().addAll(numLabel,numTextField,IDLabel,IDTextField,buscarButton);
        VBox.setMargin(buscarButton,new Insets(20,0,0,0));
        borderPane.setCenter(vbox);
        //Crea la escena y establece la ventana de dialogo como escena principal
        Scene scene=new Scene(borderPane,400,300);
        dialog.setScene(scene);

        dialog.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Sistema sis=new Sistema();
        List<Hotel> listaHoteles=sis.getListaHoteles();
        List<Empleado> listaEmpleados=sis.getListaEmpleados();

        TableColumn<ObservableHotel,String> columnaNombre=new TableColumn<>("Nombre del Hotel");

       columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        //Crear columna para la cantidad de habitaciones
       TableColumn<ObservableHotel,Integer> columnaHabitaciones=new TableColumn<>("No. de Habitaciones");
        columnaHabitaciones.setCellValueFactory(new PropertyValueFactory<>("numHabitaciones"));


        //Crear columna para la cantidad de Empleados
        TableColumn<ObservableHotel,Integer> columnaEmpleados=new TableColumn<>("No. de Empleados");
        columnaEmpleados.setCellValueFactory(new PropertyValueFactory<>("numEmpleados"));


        //Añadir columnas a la tabla
        tablaHoteles.getColumns().addAll(columnaNombre,columnaHabitaciones,columnaEmpleados);

        ObservableList<ObservableHotel> datos=FXCollections.observableArrayList();

        //Recorriendo la lista de hoteles
       for(Hotel hotel:listaHoteles){
            String nombre=hotel.getNombre();
           int numeroEmpleados=0;
           for(Empleado empleado:listaEmpleados){
               if(empleado.getHotel().equals(hotel.getNombre())){
                   numeroEmpleados+=1;
               }
           }
            int numerodeHab=hotel.getHabitaciones().size();
            if(hotel.getHabitaciones().get(0).getNumero()==null){
                numerodeHab=0;
            }
            datos.add(new ObservableHotel(nombre,numerodeHab,numeroEmpleados));

        }


        tablaHoteles.setItems(datos);


        goHome.setOnAction(event -> {

            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            double x=stage.getWidth();
            double y=stage.getHeight();
            try {

                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene=new Scene(root);


            stage.setScene(scene);


            stage.setWidth(x);
            stage.setHeight(y);
            stage.show();
        });




       //Binding propiedades de visibilidad con managed

        hotelesActionsVbox.managedProperty().bind(hotelesActionsVbox.visibleProperty());
        habitacionesActionsVbox.managedProperty().bind(habitacionesActionsVbox.visibleProperty());
        clientesVIPActionsVbox.managedProperty().bind(clientesVIPActionsVbox.visibleProperty());
        AdminIDActionsVbox.managedProperty().bind(AdminIDActionsVbox.visibleProperty());
        empleadoActionsVbox.managedProperty().bind(empleadoActionsVbox.visibleProperty());
        empleadoHabitacionesActionsVbox.managedProperty().bind(empleadoHabitacionesActionsVbox.visibleProperty());
        JefesPisoActionsVbox.managedProperty().bind(JefesPisoActionsVbox.visibleProperty());
    }


}
