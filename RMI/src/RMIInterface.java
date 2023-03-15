
import Modelos.SubLote;

import java.io.IOException;
import java.rmi.*;
import java.text.ParseException;
import java.util.ArrayList;

public interface RMIInterface extends Remote {

    public SubLote getBase64PDF (SubLote file64)throws RemoteException;

}