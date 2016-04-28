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
public class Count extends Stage {
    private int count;

    public int getCount() {
        return count;
    }

    public Count(Stage owner) {
        super();
        initOwner(owner);
        setTitle("Пошук");
        initModality(Modality.APPLICATION_MODAL);
        Group root = new Group();
        Scene scene = new Scene(root, 280, 230, Color.WHITE);
        setScene(scene);

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);

        Label count_label = new Label("Кількість: ");
        gridpane.add(count_label, 0, 1);

        final TextField count_field = new TextField();
        gridpane.add(count_field, 1, 1);

        count_field.setText("2");
        Button add_btn = new Button("Пошук");
        add_btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                try {
                    if (!count_field.getText().equals("")) {
                        count = Integer.parseInt(count_field.getText());
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
