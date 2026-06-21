import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import vls.VLSService;

public class GenresApp extends Application {

    private static final String HOST = "localhost";
    private VLSService service;

    private void connect() {
        try {
            Registry r = LocateRegistry.getRegistry(HOST, 1099);
            service = (VLSService) r.lookup("VLSService");
        } catch (Exception e) { System.out.println("RMI Error: " + e.getMessage()); }
    }

    @Override
    public void start(Stage stage) {
        connect();

        Text pgTitle = new Text("Movie Genres Menu");
        pgTitle.setStyle("-fx-font: normal bold 20px 'serif'; -fx-alignment: center");

        Text text1 = new Text("Name:");
        Text text2 = new Text("Registered:");
        TextField textField1 = new TextField();
        ComboBox<String> comboBox = new ComboBox<>();
        Button button1 = new Button("Save");
        Button button2 = new Button("Remove");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 400);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        GridPane.setColumnSpan(pgTitle, 2);
        GridPane.setHalignment(pgTitle, HPos.CENTER);

        gridPane.add(pgTitle, 0, 0);
        gridPane.add(text1, 0, 1);
        gridPane.add(textField1, 1, 1);
        gridPane.add(button1, 1, 2);

        gridPane.add(text2, 0, 3);
        gridPane.add(comboBox, 1, 3);
        gridPane.add(button2, 1, 4);

        String btnStyle = "-fx-background-color: CORNFLOWERBLUE; -fx-text-fill: white; -fx-font-size: 13pt; -fx-pref-width: 200px;-fx-background-radius: 8px;";
        String hoverStyle = "-fx-background-color: darkslateblue;-fx-text-fill: white; -fx-font-size: 13pt;-fx-pref-width: 200px;-fx-background-radius: 8px;";

        for (Button btn : new Button[]{button1, button2}) {
            btn.setStyle(btnStyle);
            btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
            btn.setOnMouseExited(e ->  btn.setStyle(btnStyle));
        }

        comboBox.setStyle("-fx-pref-width: 200px");
        text1.setStyle("-fx-font: normal bold 20px 'serif'");
        text2.setStyle("-fx-font: normal bold 20px 'serif'");
        gridPane.setStyle("-fx-background-color: White;");

        try { comboBox.getItems().setAll(service.getGenres()); }
        catch (Exception ex) { System.out.println(ex.getMessage()); }

        button1.setOnAction(e -> {
            String val = textField1.getText().trim();
            if (val.isEmpty()) return;
            try {
                service.addGenre(val);
                comboBox.getItems().add(val);
                textField1.clear();
            } catch (Exception ex) { System.out.println(ex.getMessage()); }
        });

        button2.setOnAction(e -> {
            String val = comboBox.getValue();
            if (val == null) return;
            new Thread(() -> {
                try {
                    System.out.println(val);

                    service.removeGenre(val);
                    javafx.application.Platform.runLater(() -> comboBox.getItems().remove(val));
                    comboBox.setValue(null);
                } catch (Exception ex) { System.out.println(ex.getMessage()); }
            }).start();
        });

        Scene scene = new Scene(gridPane);

        stage.setTitle("Genres");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}