package com.example.SOAPwebservice;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface InterfaceRMI extends Remote {
    public SubBatch conversionOffice(SubBatch objeto) throws RemoteException;

    public SubBatch conversionURL(SubBatch objeto) throws RemoteException;
}