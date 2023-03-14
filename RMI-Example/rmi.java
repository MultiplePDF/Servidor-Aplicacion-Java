

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface rmi extends Remote {

    public String getBase64PDF (String file64 )throws RemoteException;






}
