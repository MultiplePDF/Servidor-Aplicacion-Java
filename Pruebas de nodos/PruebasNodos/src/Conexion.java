
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Conexion {

	public static void main(String[] args) {
		
		try {
	
			contratoRMI servidor=new Servidor();
		
			LocateRegistry.createRegistry(1902);

			String rmiObjectName = "rmi://127.0.0.1:1902/convertidor";
			Naming.rebind(rmiObjectName, servidor);
			
			System.out.println("Servidor Conectado de Convertidor");

		} catch (RemoteException | MalformedURLException e) {

			e.printStackTrace();
		}

	}

}