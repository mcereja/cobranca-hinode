package br.com.imaster7.repository.filter;

import java.time.LocalDate;

public class TituloFilter {

	private String descricao;
	private String status;
	private int mes;
	private int ano;
	private String formaPagamento;
	
	public TituloFilter() {
		this.mes = LocalDate.now().getMonthValue();
	}
	/*
	 * Getters and setters
	 */
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

}
