package br.com.imaster7.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.imaster7.model.FormaPagamento;
import br.com.imaster7.model.MesAnoTitulo;
import br.com.imaster7.model.StatusTitulo;
import br.com.imaster7.model.Titulo;
import br.com.imaster7.repository.MesesAnosTitulos;
import br.com.imaster7.repository.Titulos;
import br.com.imaster7.repository.filter.TituloFilter;

@Service
public class CadastroTituloService {
	
	@Autowired
	private Titulos titulos;
	
	@Autowired
	private MesesAnosTitulos mesesAnos;
	
	public void salvar(Titulo titulo){

		// Calcula o valor do desconto sobre a taxa e alimenta o valorLiguido
		BigDecimal valorDesconto =  
				titulo.getValor().multiply(titulo.getFormaPagamento().getTaxa());
		titulo.setValorLiquido(titulo.getValor().subtract(valorDesconto));
		
		
		LocalDate dataVencimento = 
				titulo.getDataVencimento().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate();

		MesAnoTitulo mesAnoTitulo = new MesAnoTitulo();
		mesAnoTitulo.setMes(dataVencimento.getMonthValue());
		mesAnoTitulo.setAno(dataVencimento.getYear());
		
		try{
			titulos.save(titulo);
		} catch(DataIntegrityViolationException e){
			throw new IllegalArgumentException("Atenção!!! Formato de data inválido.");
		}
		
		mesesAnos.save(mesAnoTitulo);
	}

	public void excluir(Long codigo) {
		titulos.delete(codigo);
	}

	// Requisição ajax no cap. 3.6 e 3.7
	public String receber(Long codigo) {
		Titulo titulo = titulos.findOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		return titulo.getStatus().getDescricao();
	}
	
	// Não pode passar null na primeira carga da pagina de pesquisa em nenhum componente
	//  do filtro
	public List<Titulo> filtrar(TituloFilter filtro){
		LocalDate dataAtual = LocalDate.now();
		LocalDate dataFiltroInicial, dataFiltroFinal;
		
		String descricao = filtro.getDescricao() == null ? "%" : "%" + filtro.getDescricao() + "%";
		String status = Optional.ofNullable(filtro.getStatus()).orElse("TODOS");
		String formaPagto = Optional.ofNullable(filtro.getFormaPagamento()).orElse("TODAS"); 
		
		FormaPagamento[] formaPagtoFiltro;
		if ( formaPagto.equals("TODAS") ) {
			formaPagtoFiltro = FormaPagamento.values();
		} else {
			formaPagtoFiltro = new FormaPagamento[1];
			formaPagtoFiltro[0] = FormaPagamento.valueOf(filtro.getFormaPagamento());
		}
		
		StatusTitulo[] statusFiltro;
		if (status.equals("TODOS") ){
			statusFiltro = StatusTitulo.values();
		} else {
			statusFiltro = new StatusTitulo[1];
			statusFiltro[0] = StatusTitulo.valueOf(filtro.getStatus()); 
		}

		int mes = filtro.getMes() == 0 ? dataAtual.getMonthValue() : filtro.getMes();
		int ano = filtro.getAno() == 0 ? dataAtual.getYear() : filtro.getAno();
		dataFiltroInicial = LocalDate.of(ano, mes, 01);
		dataFiltroFinal   = LocalDate.of(ano, mes, dataFiltroInicial.lengthOfMonth() );
		
		return titulos
				.findByDescricaoContainingAndDataVencimentoBetweenAndStatusInAndFormaPagamentoIn(
						descricao, 
						Date.valueOf(dataFiltroInicial), 
						Date.valueOf(dataFiltroFinal),
						statusFiltro,
						formaPagtoFiltro);
		
	}
	
	public List<MesAnoTitulo> buscaTodosMesesAnosTitulos() {
		return mesesAnos.findAll();
	}
	
}
