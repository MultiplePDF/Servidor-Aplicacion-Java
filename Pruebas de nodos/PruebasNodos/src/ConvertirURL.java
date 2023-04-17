import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.Callable;

public class ConvertirURL implements Callable<Archivo> {

	Archivo obj;

	public ConvertirURL(Archivo obj) {
		super();
		this.obj = obj;

	}

	@Override
	public Archivo call() throws Exception {

		String UbicacionNuevoArchivo = convertirURL(obj);
	 
 
		System.out.println(UbicacionNuevoArchivo);

		Path path = Paths.get(UbicacionNuevoArchivo);
		byte[] fileBytes = Files.readAllBytes(path);
		byte[] encodedBytes = Base64.getEncoder().encode(fileBytes);

		String base64PDF = new String(encodedBytes);

		/*
		 * 
		 * // Crea el archivo y el objeto FileWriter File file = new
		 * File("D:\\documentos convertidos\\texto.txt"); FileWriter fw = new
		 * FileWriter(file);
		 * 
		 * // Crea el objeto BufferedWriter BufferedWriter bw = new BufferedWriter(fw);
		 * 
		 * // Escribe la cadena en el archivo bw.write(base64PDF);
		 * 
		 * // Cierra el BufferedWriter y el FileWriter bw.close(); fw.close();
		 */

		Archivo archivopdf = new Archivo(obj.idSubLote, base64PDF, obj.idArchivo);
		System.out.println("SE HA CREADO EL NUEVO OBJETO");

		return archivopdf;
	}

	public String convertirURL(Archivo obj) throws IOException {

		String url = obj.url;
		String titulo = "";
		String tit = url.replaceAll("[^a-zA-Z]", "");

		if (tit.length() >= 100) {
			titulo = tit.substring(0, 20);

		} else {

			titulo = tit;
		}

		System.out.println(titulo);

		String UbicacionNuevoArchivo = "D:\\documentos convertidos\\" + titulo + ".pdf";

		System.out.println(Thread.currentThread().getName() + " Start");

		/*
		 * System.out.println(
		 * "\"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe\" --headless --disable-gpu --print-to-pdf=\"D:\\documentos convertidos\\"
		 * + titulo + ".pdf\" \"" + url + "\""); Runtime.getRuntime().exec(
		 * "\"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe\" --headless --disable-gpu --print-to-pdf=\"D:\\documentos convertidos\\"
		 * + titulo + ".pdf\" \"" + url + "\"");
		 * 
		 * 
		 */

		System.out.println(
				"\"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\" --headless --disable-gpu --print-to-pdf=\"D:\\documentos convertidos\\"
						+ titulo + ".pdf\" \"" + url + "\"");
		Runtime.getRuntime().exec(
				"\"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\" --headless --disable-gpu --print-to-pdf=\"D:\\documentos convertidos\\"
						+ titulo + ".pdf\" \"" + url + "\"");
		
		File verificacion = new File(UbicacionNuevoArchivo);

		// AHORA SE VERIFICA QUE EL ARCHIVO EXISTE

		while (!verificacion.exists()) {
		}
		
 


		System.out.println(Thread.currentThread().getName() + " Termino Hilo");

		return UbicacionNuevoArchivo;

	}

}
