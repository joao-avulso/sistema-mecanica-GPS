package br.com.gps.mecanica;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javafx.application.Application;

@EnableScheduling
@SpringBootApplication
public class MecanicaApplication {

	public static void main(String[] args) {
		// SpringApplication.run(MecanicaApplication.class, args);
		Application.launch(MecanicaFxMainApplication.class, args);
	}

}
