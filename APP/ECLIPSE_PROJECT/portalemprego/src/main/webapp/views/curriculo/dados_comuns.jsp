<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<script>
	$(document).ready(function() {
        $("#idQtdFilhos").keypress(verificaNumero);

    	$.subscribe("verificarDeficienciaSubs", function(event,data) {
    		verificarDeficiencia();
		});

        
    });

 	 jQuery(function(){
         jQuery('input[id*=idPretencao]').maskMoney({symbol:'R$ ', showSymbol:true, thousands:'.', decimal:',', symbolStay: true});
        });
 

		function verificarDeficiencia() {

			var teste = $("#idSelectDeficiencia").val();
			  if( teste == 'true'){
				  $('select[id*=idTipoDeficiencia]').removeAttr("disabled", "true");  
				  
		      } else {

				  $('select[id*=idTipoDeficiencia]').attr("disabled", "true"); 

					  $('select[id*=idTipoDeficiencia]').val('0');
		      }
		}
     
	</script>

<ul class="detalhes" style="padding-top:5px;padding-bottom:5px;padding-left:5px;">
	<li>
	  <label>Email Pessoal:</label> 
	  <s:textfield name="aluno.dsEmailPessoal" maxLength="245" id="emailAluno" theme="simple" size="30" />
	</li>
	
	  <li>
	  <label>Disponibilidade de viajar?</label> 
	  <s:select  cssStyle="width:220px;" name="aluno.isDisponibilidadeViajar" list="simNaoList" listKey="value" listValue="descricao"  theme="simple" />
	  </li>
	
	<li>
	  <label>Quantos filhos?</label> 
	 <s:textfield name="aluno.nuFilho" maxLength="2" id="idQtdFilhos" theme="simple" size="30" />
	</li>
	
	<li>
	  <label>Pessoa com Deficiência:</label> 
	  <s:select  cssStyle="width:225px;" id="idSelectDeficiencia"  onchange="verificarDeficiencia();" name="aluno.isDeficiente"   list="simNaoList" listKey="value" listValue="descricao"  theme="simple" />
    </li>
    
	<li >
	  <label>Tipos de Deficiência</label>
	  <s:url id="listarTipoDeficiencia" action="listarTipoDeficiencia" namespace="curriculo"/> 
	       <sj:select 
				    cssStyle="width:225px;"
					href="%{listarTipoDeficiencia}" 
					id="idTipoDeficiencia"
					name="idTipoDeficiencia" 
					dataType="json"
					list="tipoDeficienciaList" 
					listKey="idDTO"
					onCompleteTopics="verificarDeficienciaSubs"
					listValue="nomeDTO" 
					emptyOption="false" 
					headerKey="0" 
					headerValue="Selecione..."
				/>
	</li>
	
	<li>
	  <label>Pretensão Salarial:</label> 
	  <s:textfield name="aluno.vlPretSalarioStr" maxLength="14" id="idPretencao"  theme="simple" size="30" />
	</li>
	
	<li>
	  <label>Cargo de Interesse:</label> 
	   <s:url id="listarCargoInteresse" action="listarCargoInteresse" namespace="curriculo"/> 
	    	   <sj:select 
				    cssStyle="width:225px;"
					href="%{listarCargoInteresse}" 
					id="idCargoInteresse"
					name="idCargoInteresse" 
					dataType="json"
					list="cargoInteresseList" 
					listKey="idDTO" 
					listValue="nomeDTO" 
					emptyOption="false" 
					headerKey="0" 
					headerValue="Selecione..."
				/>
				
	</li>
	
	<li>
	  <label>Divulgar Dados Pessoais?</label> 
	  	  <s:select  cssStyle="width:225px;" name="aluno.isDivulgarDado"  list="simNaoList" listKey="value" listValue="descricao"  theme="simple" />
   </li>
</ul>