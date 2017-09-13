// cap. 2.16
$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var codigoTitulo = button.data('codigo');
	var descricaoTitulo = button.data('descricao');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	
	if(!action.endsWith('/')){
		action += '/';
	}
	
	form.attr('action', action + codigoTitulo);
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o título: <strong>' + descricaoTitulo + '</strong> ?');
	
});
 // cap. 3.1 executa após a pag. ser carregada ativando tooltip do bootstrap
//   OBS. não podemos seguir a doc do bootstrap ativando data-toogle pq utilizamos p/ outra coisa na pag.
//       então ativa-se por rel="tooltip"
$(function(){
	$('[rel="tooltip"]').tooltip();
	$('.js-moeda').maskNumber({ decimal: ',', thousand: '.', allowZero: true });
	
	$('.js-atualizar-status').on('click', function(event){
		// qdo clica no botao receber sem preventDefault da erro HTML 404 e mostra url no endereço do navegador
		//   com preventDefault o javascript indica que não é pra executar o procedimento padrão para link 
		//   que seria submeter o formulario via GET pois iremos usar PUT nesse caso
		event.preventDefault();
		
		// pega o evento do botao que esta vindo atraves de um link
		var botaoReceber = $(event.currentTarget);
		var urlReceber   = botaoReceber.attr('href');
		
		// Requisição ajax no cap. 3.6 e 3.7
		var resposta = $.ajax({
			url: urlReceber,
			type: 'PUT'
		});
		
		resposta.done(function(e) {
			// Para dar refresh apenas no status recupera o codigo pela th:attr="data-codigo=${titulo.codigo}"
			//  adicionado junto com o link do botãozinho receber da pag. PesquisaTitulos.html
			// No (e) da função acima virá o retorno do controller = valor do enum
			var codigoTitulo = botaoReceber.data('codigo');
			$('[data-role=' + codigoTitulo + ']').html('<span class="label label-success">' + e + '</span>');
			
			botaoReceber.hide();
		});
		
		resposta.fail(function(e) {
			console.log(e);
			alert('Atenção!!! Infelizmente ocorreu um erro ao receber essa cobrança.');
		});
		
	});
});
