package br.com.imaster7.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "mesanotitulo")
@IdClass(MesAnoIdComposto.class)
public class MesAnoTitulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int mes;
	
	@Id
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
	
}
