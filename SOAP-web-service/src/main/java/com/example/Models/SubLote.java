package Models;

import java.io.Serializable;
import java.util.Arrays;

public class SubLote implements Serializable {
	String idSublote;
	String idLote;
	String idUsuario;
	Archivo[] archivos;

	public SubLote(String idSublote, String idLote, String idUsuario, Archivo[] archivos) {
		super();
		this.idSublote = idSublote;
		this.idLote = idLote;
		this.idUsuario = idUsuario;
		this.archivos = archivos;
	}
	
	public String getIdSublote() {
		return idSublote;
	}

	public void setIdSublote(String idSublote) {
		this.idSublote = idSublote;
	}

	public String getIdLote() {
		return idLote;
	}

	public void setIdLote(String idLote) {
		this.idLote = idLote;
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
		return "Sublote [idSublote=" + idSublote + ", idLote=" + idLote + ", idUsuario=" + idUsuario + ", archivos="
				+ Arrays.toString(archivos) + "]";
	}
}
