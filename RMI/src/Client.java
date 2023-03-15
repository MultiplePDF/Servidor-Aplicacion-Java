import Modelos.SubLote;

import java.io.IOException;
import java.rmi.*;
import java.rmi.registry.*;
import java.text.ParseException;

public class Client {
    public static void main(String[] args) {
        RMIInterface rmi = null;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            rmi = (RMIInterface) registry.lookup("server");
            System.out.println("Connected to Server");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rmi != null) {
            try {
                SubLote lote = new SubLote();
                SubLote res = rmi.getBase64PDF(lote);
                System.out.println(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Finished");
        }
    }
}