package co.edu.upb.bucaramanga.java.soapserver;

import org.springframework.boot.SpringApplication;
import jakarta.ws.rs.ApplicationPath;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class SoapServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapServerApplication.class, args);
	}

}
