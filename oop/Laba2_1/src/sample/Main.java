package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class Main extends Application {
    TableView<Flat> tw;
    String host = "127.0.0.1";
    int port = 4444;

    @Override
    public void start(final Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Laba 2_1");

        Button btn = new Button("Отримати дані");

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                connectToServer();
            }
        });

        Button btn_add_flat = new Button("Додати новий запис");

        btn_add_flat.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Flat flat = new Flat(0, 0.0, 0, 0, "", "");
                Stage myDialog = new MyDialog(primaryStage, flat);
                myDialog.sizeToScene();
                myDialog.showAndWait();
                loadDataToServer(flat.serializeObj());
                ObservableList<Flat> flats = tw.getItems();
                flats.add(flat);
                tw.setItems(flats);
            }
        });

        Button btn_search_by_count = new Button("Пошук по кількості кімнат");

        btn_search_by_count.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Count myDialog = new Count(primaryStage);
                myDialog.sizeToScene();
                myDialog.showAndWait();
                int count = myDialog.getCount();
                search_by_count_rooms(count);
            }
        });

        Button btn_search_by_floor_and_square = new Button("Пошук по вищому поверху і більшій площі");

        btn_search_by_floor_and_square.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                SquareAndFloor myDialog = new SquareAndFloor(primaryStage);
                myDialog.sizeToScene();
                myDialog.showAndWait();
                int floor = myDialog.getFloor();
                double square = myDialog.getSquare();
                search_by_floor_and_square(floor, square);
            }
        });

        tw = new TableView<Flat>();
        final Label label = new Label("Квартири");
        label.setFont(new Font("Arial", 20));

        ObservableList<Flat> data =
                FXCollections.observableArrayList();

        tw.setEditable(true);

        TableColumn flatNumber = new TableColumn("Номер квартири");
        flatNumber.setMinWidth(100);
        flatNumber.setCellValueFactory(
                new PropertyValueFactory<Flat, Integer>("flatNumber"));

        TableColumn flatSquare = new TableColumn("Площа квартири");
        flatSquare.setMinWidth(100);
        flatSquare.setCellValueFactory(
                new PropertyValueFactory<Flat, Float>("flatSquare"));

        TableColumn flatFloor = new TableColumn("Поверх");
        flatFloor.setMinWidth(100);
        flatFloor.setCellValueFactory(
                new PropertyValueFactory<Flat, Integer>("flatFloor"));

        TableColumn countRooms = new TableColumn("Кількість кімнат");
        countRooms.setMinWidth(100);
        countRooms.setCellValueFactory(
                new PropertyValueFactory<Flat, Integer>("countRooms"));

        TableColumn typeBuild = new TableColumn("Тип будівлі");
        typeBuild.setMinWidth(100);
        typeBuild.setCellValueFactory(
                new PropertyValueFactory<Flat, String>("typeBuild"));

        TableColumn expluatationTime = new TableColumn("Строк експлуатації");
        expluatationTime.setMinWidth(100);
        expluatationTime.setCellValueFactory(
                new PropertyValueFactory<Flat, String>("expluatationTime"));

        tw.getColumns().addAll(flatNumber, flatSquare, flatFloor, countRooms, typeBuild, expluatationTime);
        tw.setItems(data);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, tw, btn, btn_add_flat, btn_search_by_count, btn_search_by_floor_and_square);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

//        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void loadDataToServer(String flat) {
        communication("add_new_flat", flat);
    }

    private void search_by_count_rooms(int count) {
        String result = communication("search_by_count_rooms", String.valueOf(count));
        List<Flat> start_data = Flat.deserializeFlat(result);
        ObservableList<Flat> observablelist_flats = FXCollections.observableArrayList(start_data);
        tw.setItems(observablelist_flats);
    }

    private void search_by_floor_and_square(int floor, double square) {
        String result = communication("search_by_floor_and_square", String.valueOf(floor) + "%" + String.valueOf(square));
        List<Flat> start_data = Flat.deserializeFlat(result);
        ObservableList<Flat> observablelist_flats = FXCollections.observableArrayList(start_data);
        tw.setItems(observablelist_flats);
    }


    public void connectToServer() {
        String result = communication("get_start_data", "");
        List<Flat> start_data = Flat.deserializeFlat(result);
        ObservableList<Flat> observablelist_flats = FXCollections.observableArrayList(start_data);
        tw.setItems(observablelist_flats);
    }


    private String communication(String action, String data) {
        try {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            Socket clientSocket = new Socket(host, port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer.writeBytes(action + '\n');
            int recieve_str;
            String result = "";
            while ((recieve_str = inFromServer.read()) != 13) {
                result += (char) recieve_str;
            };
            if (!data.equals("")) {
                result = "";
                outToServer.writeBytes(data + '\n');
                while ((recieve_str = inFromServer.read()) != 13) {
                    result += (char) recieve_str;
                }
                result = result.substring(3);
                if (result.equals("success")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Запис даних на сервер");
                    alert.setHeaderText(null);
                    alert.setContentText("Запис даних на сервер було виконано успішно!");
                    alert.showAndWait();
                }
            }
            clientSocket.close();
            return result;
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
