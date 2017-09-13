package br.com.imaster7.model;

import java.io.Serializable;

public class MesAnoIdComposto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int mes;
	private int ano;
	
	/*
	 * Métodos
	 */
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ano;
		result = prime * result + mes;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MesAnoIdComposto other = (MesAnoIdComposto) obj;
		if (ano != other.ano)
			return false;
		if (mes != other.mes)
			return false;
		return true;
	}

}
