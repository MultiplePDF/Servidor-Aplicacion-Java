import Modelos.SubLote;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface contratoRMI extends Remote {
	public SubLote conversionOffice(SubLote objeto) throws RemoteException;
	public SubLote conversionURL(SubLote objeto) throws RemoteException;
}
