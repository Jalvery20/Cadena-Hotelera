package com.jalvery20.cadena_hotelera_visual;

import com.jalvery20.cadena_hotelera_visual.classes.Sistema;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author jalvery20
 */
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Image icon=new Image(String.valueOf(getClass().getResource("icon.png")));

        stage.getIcons().add(icon);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                //Codigo que se ejecutara cuando se cierre la ventana
                try(FileWriter file= new FileWriter("./data/tiempoCierre.json")) {
                    Calendar calendar=Calendar.getInstance();
                    int day=calendar.get(Calendar.DAY_OF_MONTH);
                    JSONArray array=new JSONArray();
                    array.add(day);
                    file.write(array.toJSONString());
                    file.flush();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
        stage.setTitle("Cadena Hotelera");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {




        try(FileReader reader=new FileReader("./data/tiempoCierre.json")){
            JSONParser jsonParser = new JSONParser();

            Object obj=jsonParser.parse(reader);

            JSONArray jsonArray=(JSONArray) obj;

            long dayClose=(long) jsonArray.get(0);

            Calendar calendar=Calendar.getInstance();
            long day=calendar.get(Calendar.DAY_OF_MONTH);
            if(dayClose<day){
                new ProcesarSolicitud();
                try(FileWriter file= new FileWriter("./data/tiempoCierre.json")) {

                    JSONArray array=new JSONArray();
                    array.add(day);
                    file.write(array.toJSONString());
                    file.flush();
                } catch(IOException e){
                    e.printStackTrace();
                }

            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar calendar=Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        calendar.set(Calendar.MILLISECOND,0);

        TimerTask task=new ProcesarSolicitud();

        Timer timer=new Timer();

        timer.schedule(task,calendar.getTime(),24 * 60 * 60 * 1000);

        launch();
    }
}