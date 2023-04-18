package com.example.SOAPwebservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 
import java.util.Base64;
import java.util.concurrent.Callable;

public class ConvertirArchivo implements Callable<Archivo> {

	Archivo obj;
 

	public ConvertirArchivo(Archivo obj) {
		super();
		this.obj = obj;

	}
 
 
	@Override
	public Archivo call() throws Exception {
		
		Archivo nuevo = convertirArchivo(obj);
		return nuevo;
	}
	
	



	public Archivo convertirArchivo(Archivo obj) throws IOException, IOException {

		System.out.println("OBTENCION DE DOCUMENTO: " + obj.nombre);
		String archivo[] = obj.nombre.split("\\.");
		String extension = "." + archivo[archivo.length - 1];
		String base64 = obj.getBase64();

		String nombre = "";
		for (int i = 0; i < archivo.length - 1; i++) {
			nombre = nombre + archivo[i];
		}

		System.out.println(nombre);

		String nuevaRuta = "";

		// DECODIFICAR ARCHIVOS
		byte[] decoded = Base64.getDecoder().decode(base64);

		/*
		 * LOS ARCHIVOS SE GUARDAN EN C:\Users\USUARIO\AppData\Local\Temp\ Varia de
		 * acuerdo al computador C:\Users\admin\AppData\Local\Temp\
		 */

		File tempFile = File.createTempFile(nombre, extension);
		FileOutputStream fos = new FileOutputStream(tempFile);
		fos.write(decoded);
		fos.close();

		System.out.println("Termino " + tempFile.getAbsolutePath());
		System.out.println(String.valueOf(tempFile));

		System.out.println(Thread.currentThread().getName() + " Start");

		System.out.println(
				"\"C:\\Program Files (x86)\\LibreOffice\\program\\soffice.exe\" --convert-to pdf --outdir \"D:\\documentos convertidos\" \""
						+ tempFile + "\" --headless");
		Runtime.getRuntime().exec(
				"\"C:\\Program Files (x86)\\LibreOffice\\program\\soffice.exe\" --convert-to pdf --outdir \"D:\\documentos convertidos\" \""
						+ tempFile + "\" --headless");

		/* EL DE LA UNIVERSIDAD
		 * // REALIZAMOS LA CONVERSION EN PDF System.out.println(
		 * "\"C:\\Program Files\\LibreOffice\\program\\soffice.exe\" --convert-to pdf --outdir \"D:\\documentos convertidos\" \""
		 * + tempFile + "\" --headless"); Runtime.getRuntime().exec(
		 * "\"C:\\Program Files\\LibreOffice\\program\\soffice.exe\" --convert-to pdf --outdir \"D:\\documentos convertidos\" \""
		 * + tempFile + "\" --headless");
		 */
		String documentoConvertido[] = tempFile.getName().split("\\.");
		String nuevoNombre = "";
		for (int i = 0; i < documentoConvertido.length - 1; i++) {
			nuevoNombre = nuevoNombre + documentoConvertido[i];
		}

		String UbicacionNuevoArchivo = "D:\\documentos convertidos\\" + nuevoNombre + ".pdf";

		// AHORA SE VERIFICA QUE EL ARCHIVO EXISTE

		File verificacion = new File(UbicacionNuevoArchivo);
		while (!verificacion.exists()) {
		}

		System.out.println(Thread.currentThread().getName() + " Termino Hilo");

		System.out.println(nuevoNombre);
		System.out.println(UbicacionNuevoArchivo);

		FileInputStream entrada = new FileInputStream(verificacion);
		byte[] bytes = new byte[(int) entrada.available()];
		entrada.read(bytes);
		String base64PDF = new String(Base64.getEncoder().encode(bytes));

		Archivo archivopdf = new Archivo(obj.idSubLote, base64PDF, nombre);
		System.out.println("SE HA CREADO EL NUEVO OBJETO");

		return archivopdf;


	}

	public Archivo getObj() {
		return obj;
	}

	public void setObj(Archivo obj) {
		this.obj = obj;
	}

	 

 
}
