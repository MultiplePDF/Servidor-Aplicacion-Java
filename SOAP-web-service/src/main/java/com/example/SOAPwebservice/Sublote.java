package com.example.SOAPwebservice;

import java.io.Serializable;
import java.util.Arrays;

public class Sublote implements Serializable{
	
	
	String idSublote;
	String idUsuario;
	Archivo[] archivos;
	
	
	
	
	public Sublote(String idSublote, String idUsuario, Archivo[] archivos) {
		super();
		this.idSublote = idSublote;
		this.idUsuario = idUsuario;
		this.archivos = archivos;
	}
	
	
	public String getIdSublote() {
		return idSublote;
	}
	public void setIdSublote(String idSublote) {
		this.idSublote = idSublote;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Archivo[] getArchivos() {
		return archivos;
	}
	public void setArchivos(Archivo[] archivos) {
		this.archivos = archivos;
	}


	@Override
	public String toString() {
		return "com.example.SOAPwebservice.Sublote [idSublote=" + idSublote + ", idUsuario=" + idUsuario + ", archivos="
				+ Arrays.toString(archivos) + "]";
	}
	
	
	
	
	

}