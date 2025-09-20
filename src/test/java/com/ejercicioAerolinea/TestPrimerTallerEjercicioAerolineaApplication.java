package com.ejercicioAerolinea;

import org.springframework.boot.SpringApplication;

public class TestPrimerTallerEjercicioAerolineaApplication {

	public static void main(String[] args) {
		SpringApplication.from(PrimerTallerEjercicioAerolineaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
