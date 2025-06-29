package org.example.powtorzenie_do_drugiego_kolokwium;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.powtorzenie_do_drugiego_kolokwium.client.ServerThread;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/powtorzenie_do_drugiego_kolokwium/hello-view.fxml"));

        // Создаём клиента
        ServerThread thread = new ServerThread();

        // Передаём в контроллер
        Controller controller = new Controller(thread);
        fxmlLoader.setController(controller);

        // Загружаем сцену
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dot Drawer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
