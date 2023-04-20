package com.example.SOAPwebservice;

import java.io.Serializable;
import java.util.Arrays;

public class Archivo implements Serializable {

	int idArchivo;
	String idSubLote;
	String url;
	String base64;
	String nombre;
	int size;
	
	public int getIdArchivo() {
		return idArchivo;
	}
	
	public void setIdArchivo( int idArchivo ) {
		this.idArchivo = idArchivo;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl( String url ) {
		this.url = url;
	}
	
	public Archivo(String idSubLote, String base64, String nombre) {

		this.idSubLote = idSubLote;
		this.base64 = base64;
		this.nombre = nombre;
	}

	public Archivo(String idSubLote, String url, int idArchivo) {

		this.idSubLote = idSubLote;
		this.url = url;
		this.idArchivo = idArchivo;
	}

	public String getIdSubLote() {
		return idSubLote;
	}

	public void setIdSubLote(String idSubLote) {
		this.idSubLote = idSubLote;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "{\n" +
			"        \"fileID\": "+idArchivo+",\n" +
			"        \"subBatchID\": \""+idSubLote+"\",\n" +
			"        \"url\": \""+ url +"\",\n" +
			"        \"base64\": \""+ base64 +"\",\n" +
			"        \"fileName\": \""+ nombre +"\",\n" +
			"    }";
	}

	
	

}