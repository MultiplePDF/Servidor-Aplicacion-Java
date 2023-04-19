package com.example.SOAPwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

@SpringBootApplication
public class ProducingWebServiceApplication {
	public static contratoRMI nodo1;
	public static contratoRMI nodo2;
	public static contratoRMI nodo3;


	public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
		SpringApplication.run(ProducingWebServiceApplication.class, args);
		try {
			nodo1 = (contratoRMI) Naming.lookup("rmi://nodo1.bucaramanga.upb.edu.co:1099/convertidor");
			nodo2 = (contratoRMI) Naming.lookup("rmi://nodo2.bucaramanga.upb.edu.co:1099/convertidor");
			nodo3 = (contratoRMI) Naming.lookup("rmi://nodo3.bucaramanga.upb.edu.co:1099/convertidor");
	
		} catch (NotBoundException | MalformedURLException | RemoteException e) {
			System.out.println("Can't connect to RMI server");
			System.out.println(e);
		}
	}

}
