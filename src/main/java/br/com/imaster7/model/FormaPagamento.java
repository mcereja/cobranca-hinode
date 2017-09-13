package br.com.imaster7.model;

import java.math.BigDecimal;

/*
 * Para inserir a taxa pegar o percentual do site pagseguro e dividir por 100
 *   encontrando assim o fator de multiplicação
 *   
 *   Ex. DEBITO 2,39 / 100 = 0,0239  
 *   Dinheiro não terá taxa de desconto nenhuma então o fator de multiplicacao é 0
 */
public enum FormaPagamento {

	DINHEIRO("Dinheiro", new BigDecimal("0") ),
	DEBITO("Cartão de Débito", new BigDecimal("0.0239")),
	CREDITO_VISTA("Crédito a Vista", new BigDecimal("0.0499")),
	CREDITO_PARCELADO("Crédito 2 Vezes", new BigDecimal("0.09912"));
	
	private String descricao;
	private BigDecimal taxa;
	private FormaPagamento(String descricao, BigDecimal taxa) {
		this.descricao = descricao;
		this.taxa = taxa;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
	
	public BigDecimal getTaxa() {
		return this.taxa;
	}
	
}
