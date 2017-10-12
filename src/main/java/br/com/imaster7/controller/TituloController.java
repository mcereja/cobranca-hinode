package br.com.imaster7.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.imaster7.model.FormaPagamento;
import br.com.imaster7.model.MesAnoTitulo;
import br.com.imaster7.model.StatusTitulo;
import br.com.imaster7.model.Titulo;
import br.com.imaster7.repository.filter.TituloFilter;
import br.com.imaster7.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	private static final String RETORNO_VIEW = "CadastroTitulo";
	
	@Autowired
	private CadastroTituloService cadastroTituloService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView(RETORNO_VIEW);
		mv.addObject(new Titulo());  // add no cap. 2.13 impl. bean validation na pag. cadastro
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors erros, RedirectAttributes atributos){
		
		if (erros.hasErrors()){
			return RETORNO_VIEW;
		}
		
		try{
			cadastroTituloService.salvar(titulo);
			atributos.addFlashAttribute("mensagem", "Título guardado com sucesso !!!");
			return "redirect:/titulos/novo";
		} catch (IllegalArgumentException e) {
				erros.rejectValue("dataVencimento", null, e.getMessage());
				return RETORNO_VIEW;
		}
		
	}
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro){
		List<Titulo> tdTitulos = cadastroTituloService.filtrar(filtro);
		
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("todosTitulos", tdTitulos);
		
		BigDecimal acumuladorValorLiquido = new BigDecimal("0");
		for ( Titulo titulo : tdTitulos ) {
			acumuladorValorLiquido = acumuladorValorLiquido.add(titulo.getValorLiquido());
		}
		
		mv.addObject("totalValorLiquido", acumuladorValorLiquido.toString());
		return mv;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Titulo titulo){
		ModelAndView mv = new ModelAndView(RETORNO_VIEW);
		mv.addObject(titulo);
		return mv;
	}
	
	@RequestMapping(value="{codigo}", method=RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes atributos){
		cadastroTituloService.excluir(codigo);
		
		atributos.addFlashAttribute("mensagem", "Título excluído com sucesso !!!");
		return "redirect:/titulos";
	}
	
	@RequestMapping(value="/{codigo}/receber", method=RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo){
		return cadastroTituloService.receber(codigo);
	}

	/*
	 * Define os componentes que serao utilizados na pagina html
	 */
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> tdStatusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}
	
	@ModelAttribute("todasFormasPagamento")
	public List<FormaPagamento> tdFormasPagamento() {
		return Arrays.asList(FormaPagamento.values());
	}
	
	@ModelAttribute("todosMesesAnosVencimentoTitulos")
	public List<MesAnoTitulo> tdMesAnoTitulos() {
		return cadastroTituloService.buscaTodosMesesAnosTitulos();
	}
	
}
