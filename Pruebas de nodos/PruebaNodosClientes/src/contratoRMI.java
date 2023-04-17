
import java.rmi.Remote;
import java.rmi.RemoteException;
 

public interface contratoRMI extends Remote {
	
	public Sublote conversionOffice(Sublote objeto) throws RemoteException;
	public Sublote conversionURL(Sublote objeto) throws RemoteException;
	
	
}