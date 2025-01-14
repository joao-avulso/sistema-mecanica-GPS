package br.com.gps.mecanica;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StageReadyEventListener implements ApplicationListener<StageReadyEvent> {

    @Value("Sistema para Oficina Mecanica")
    private String windowTitle;

    private Scene scene;

    @SuppressWarnings("null")
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        var stage = event.getStage();

        try {
            scene = new Scene(loadFXML("main"), 640, 480);
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo FXML: " + e.getMessage());
            e.printStackTrace();
        }

        stage.setScene(scene);
        stage.setTitle(windowTitle);
        // stage.setMaximized(true);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MecanicaApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
