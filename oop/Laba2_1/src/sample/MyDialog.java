package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

class MyDialog extends Stage {

    public MyDialog(Stage owner, final Flat flat) {
        super();
        initOwner(owner);
        setTitle("Додати нову квартиру");
        initModality(Modality.APPLICATION_MODAL);
        Group root = new Group();
        Scene scene = new Scene(root, 280, 230, Color.WHITE);
        setScene(scene);

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);

        Label flatNumber_label = new Label("Номер квартири: ");
        gridpane.add(flatNumber_label, 0, 1);

        Label flatSquare_label = new Label("Площа квартири: ");
        gridpane.add(flatSquare_label, 0, 2);

        Label flatFloor_label = new Label("Поверх: ");
        gridpane.add(flatFloor_label, 0, 3);

        Label countRooms_label = new Label("Кількість кімнат: ");
        gridpane.add(countRooms_label, 0, 4);

        Label typeBuild_label = new Label("Тип будівлі: ");
        gridpane.add(typeBuild_label, 0, 5);

        Label expluatationTime_label = new Label("Строк експлуатації: ");
        gridpane.add(expluatationTime_label, 0, 6);

        final TextField flatNumber_field = new TextField();
        gridpane.add(flatNumber_field, 1, 1);

        final TextField flatSquare_field = new TextField();
        gridpane.add(flatSquare_field, 1, 2);

        final TextField flatFloor_field = new TextField();
        gridpane.add(flatFloor_field, 1, 3);

        final TextField countRooms_field = new TextField();
        gridpane.add(countRooms_field, 1, 4);

        final TextField typeBuild_field = new TextField();
        gridpane.add(typeBuild_field, 1, 5);

        final TextField expluatationTime_field = new TextField();
        gridpane.add(expluatationTime_field, 1, 6);

        flatNumber_field.setText("2");
        flatSquare_field.setText("2");
        flatFloor_field.setText("2");
        countRooms_field.setText("2");
        typeBuild_field.setText("2");
        expluatationTime_field.setText("2");
        Button add_btn = new Button("Додати");
        add_btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                try {
                    if (!flatNumber_field.getText().equals("") && !flatSquare_field.getText().equals("") &&
                            !expluatationTime_field.getText().equals("") && !countRooms_field.getText().equals("") &&
                            !flatFloor_field.getText().equals("") && !typeBuild_field.getText().equals("")) {
                        flat.setFlatNumber(Integer.parseInt(flatNumber_field.getText()));
                        flat.setFlatSquare(Double.parseDouble(flatSquare_field.getText()));
                        flat.setCountRooms(Integer.parseInt(countRooms_field.getText()));
                        flat.setFlatFloor(Integer.parseInt(flatFloor_field.getText()));
                        flat.setExpluatationTime(expluatationTime_field.getText());
                        flat.setTypeBuild(typeBuild_field.getText());
                        close();
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        gridpane.add(add_btn, 1, 7);
        GridPane.setHalignment(add_btn, HPos.RIGHT);
        root.getChildren().add(gridpane);
    }
}