package classes;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Scanner;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class MainCliente {
    public static void main(String[] args) {


        try {

            rmi service =  (rmi) Naming.lookup("rmi://127.0.0.1:1099/servicio");

          
            String response= service.getBase64PDF(year);

            


        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            throw new RuntimeException(e);
        }


    }




}