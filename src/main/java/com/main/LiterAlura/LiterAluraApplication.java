package com.main.LiterAlura;

import com.Principal.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class LiterAluraApplication {

	public static void main(String[] args) {

		//SpringApplication.run(LiterAluraApplication.class, args);
		Principal principal = new Principal();
		principal.muestraElMenu();
	}

}
