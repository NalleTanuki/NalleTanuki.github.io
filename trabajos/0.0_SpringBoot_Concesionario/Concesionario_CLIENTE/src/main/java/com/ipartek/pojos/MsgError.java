package com.ipartek.pojos;

public class MsgError {

	private int codigo;
	private String mensaje;
	
	public MsgError(int codigo, String mensaje) {
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
	}
	
	public MsgError() {
		super();
		this.codigo = 0;
		this.mensaje = "";
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "MsgError [codigo=" + codigo + ", mensaje=" + mensaje + "]";
	}
}
