

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Base64;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

 

public class Servidor extends UnicastRemoteObject implements contratoRMI {

	protected Servidor() throws RemoteException {
		super();

	}

	@Override
	public Sublote conversionOffice(Sublote objeto) throws RemoteException {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);

		System.out.println("Se esta haciendo la conversion");
		Archivo[] archivos = objeto.archivos;
		Archivo archivospdf[] = new Archivo[archivos.length];
		
	 
 

		for (int i = 0; i < archivos.length; i++) {

			Callable<Archivo> worker = new ConvertirArchivo(archivos[i]);
			Future<Archivo> future = executor.submit(worker);
			
		 
			try {
				Archivo temp = future.get();
				archivospdf[i] = temp;
			} catch (InterruptedException | ExecutionException e) {
				 
				e.printStackTrace();
			}
			 
			
			 

		}
		
		executor.shutdown();
        while (!executor.isTerminated()) {
        }
       
		Sublote ConversionRealizada = new Sublote(objeto.idLote, objeto.idSublote, objeto.idUsuario, archivospdf);

		return ConversionRealizada;
	}



	@Override
	public Sublote conversionURL(Sublote objeto) throws RemoteException {



		ExecutorService executor = Executors.newFixedThreadPool(3);

		System.out.println("SE ESTA HACIENDO LA CONVERSION ");
		Archivo[] archivos = objeto.archivos;
		Archivo archivospdf[] = new Archivo[archivos.length];
		
	 
 

		for (int i = 0; i < archivos.length; i++) {

			Callable<Archivo> worker = new ConvertirURL(archivos[i]);
			Future<Archivo> future = executor.submit(worker);
			
		 
			try {
				Archivo temp = future.get();
				System.out.println(temp.toString());
				archivospdf[i] = temp;
				
			} catch (InterruptedException | ExecutionException e) {
				 
				e.printStackTrace();
			}
			 
			
			 

		}
		
		executor.shutdown();
        while (!executor.isTerminated()) {
        }
       
		Sublote ConversionRealizada = new Sublote(objeto.idLote, objeto.idSublote, objeto.idUsuario, archivospdf);

		return ConversionRealizada;
	}
	
	@Override
		public String pruba() throws RemoteException {
		
		return "Conectado";
		
	}
	
	public static void main(String[] args) {
		
		
		Archivo cliente4 = new Archivo("1","https://jarroba.com/multitarea-e-hilos-en-java-con-ejemplos-thread-runnable/", 1);
		Archivo cliente5 = new Archivo("2", "https://www.autodraw.com/", 2);
		Archivo cliente6 = new Archivo("3", "http://www.homestyler.com/", 3);
		
		Archivo[] l = new Archivo[3];
		
		l[0] = cliente4;
		l[1] = cliente5;
		l[2] = cliente6;

		
		Sublote completo = new Sublote("12324","1232415","12324",l);
		
		Servidor server;
		try {
			server = new Servidor();
			Sublote nuevo = server.conversionURL(completo);
			System.out.println("TERMINO");
			Archivo[] recibir = nuevo.archivos;
			
			for (int i = 0; i < recibir.length; i++) {
				
			}
	 
			
			// DECODIFICAR ARCHIVOS
			byte[] decoded = Base64.getDecoder().decode(recibir[0].url);

			/*
			 * LOS ARCHIVOS SE GUARDAN EN C:\Users\USUARIO\AppData\Local\Temp\ Varia de
			 * acuerdo al computador C:\Users\admin\AppData\Local\Temp\
			 */
 
			try {
				File tempFile = File.createTempFile("ejemplo", "pdf");
				FileOutputStream fos = new FileOutputStream(tempFile);
				fos.write(decoded);
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 
		} catch (RemoteException e) {
		 
			e.printStackTrace();
		}
 
		
		
		
	}



}
