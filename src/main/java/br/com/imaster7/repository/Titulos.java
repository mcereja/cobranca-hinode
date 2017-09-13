package br.com.imaster7.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imaster7.model.FormaPagamento;
import br.com.imaster7.model.StatusTitulo;
import br.com.imaster7.model.Titulo;

public interface Titulos extends JpaRepository<Titulo, Long> {
	
	// Containing faz um like, combina a clausula in com uma colecao 
	public List<Titulo> 
		findByDescricaoContainingAndDataVencimentoBetweenAndStatusInAndFormaPagamentoIn(
				String descricao, 
				Date dataInicio,
				Date dataFinal,
				StatusTitulo[] colecaoStatus,
				FormaPagamento[] colecaoFormaPagamento);
	
	// Executa query nativa da base de dados mysql
	/*@Query(	value = "select distinct concat(monthname(data_vencimento), ' / ', year(data_vencimento)) from titulo;", 
			nativeQuery = true)
	public List<String> buscaMesAnoTitulos();*/
	
	/*@Query( value = "select sum(valor_liquido) from titulo where data_vencimento between ?1 and ?2",
			nativeQuery = true)
	public BigDecimal somaValorLiquidoTitulosPorPeriodo(Date dataInicio, Date dataFinal);
	*/
}
