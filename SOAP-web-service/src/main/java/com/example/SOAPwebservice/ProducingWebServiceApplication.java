package com.example.SOAPwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

@SpringBootApplication
public class ProducingWebServiceApplication {
	public static contratoRMI server;

	public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
		SpringApplication.run(ProducingWebServiceApplication.class, args);
		try {
			//server = (contratoRMI) Naming.lookup("rmi://10.154.12.166:1099/convertidor");
			server= (contratoRMI) Naming.lookup("rmi://127.0.0.1:1902/convertidor");
	
		} catch (NotBoundException e) {
			throw new RuntimeException(e);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

}
