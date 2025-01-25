package br.com.gps.mecanica;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

@Component
public class MecanicaFxMainApplication extends Application {

    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
    applicationContext =
        new SpringApplicationBuilder(MecanicaApplication.class)
            .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }
}
