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

/**
 * Created by Администратор on 23.04.2016.
 */
public class SquareAndFloor extends Stage {
    private double square;
    private int floor;

    public double getSquare() {
        return square;
    }

    public int getFloor() {
        return floor;
    }

    public SquareAndFloor(Stage owner) {
        super();
        initOwner(owner);
        setTitle("Пошук по Площі і Поверху");
        initModality(Modality.APPLICATION_MODAL);
        Group root = new Group();
        Scene scene = new Scene(root, 280, 230, Color.WHITE);
        setScene(scene);

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);

        Label floor_label = new Label("Поверх: ");
        gridpane.add(floor_label, 0, 1);

        Label quare_label = new Label("Площа: ");
        gridpane.add(quare_label, 0, 2);

        final TextField floor_field = new TextField();
        gridpane.add(floor_field, 1, 1);

        floor_field.setText("2");
        final TextField square_field = new TextField();
        gridpane.add(square_field, 1, 2);

        square_field.setText("2");
        Button add_btn = new Button("Пошук");
        add_btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                try {
                    if (!floor_field.getText().equals("") && !square_field.getText().equals("")) {
                        square = Double.parseDouble(square_field.getText());
                        floor = Integer.parseInt(floor_field.getText());
                        close();
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        gridpane.add(add_btn, 1, 2);
        GridPane.setHalignment(add_btn, HPos.CENTER);
        root.getChildren().add(gridpane);
    }
}
