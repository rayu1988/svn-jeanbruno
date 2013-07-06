<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

	<script>
	$(document).ready(function() {
        $("#idEnderecoNumero").keypress(verificaNumero);
    });
	$(document).ready(function() {
        $("#iCEP").keypress(verificaNumero);
    });
	</script>

<div class="end" >
	<spam style="color: #ff0101;">*</spam>
	Endereço:
</div>

<ul class="end">
	<li> 
	  <label> <spam>*</spam> Pais:</label> 
			    <s:url id="remoteurl" action="obterPaisCurriculo"  namespace="curriculo" /> 
				   <sj:select 
				    cssStyle="width:210px;"
					href="%{remoteurl}" 
					id="idPaisSelecao"
					onChangeTopics="reloadUfList" 
					name="idPaisSelecao" 
					dataType="json"
					list="paisList" 
					listKey="idDTO" 
					listValue="nomeDTO" 
					tabindex="1"
					emptyOption="false" 
					headerKey="0" 
					headerValue="Selecione..."
				/>
				
	
	</li>
	
	<li>
	 <label> <spam>*</spam> Estado:</label> 
	    <s:url id="remoteurlEstado" action="obterUf"  namespace="curriculo" /> 
				   <sj:select 
				    cssStyle="width:210px;"
					href="%{remoteurlEstado}" 
					id="idUfSelecao"
					formIds="formCurriculoDadosId" 
					reloadTopics="reloadUfList"
					onChangeTopics="reloadCidadeList" 
					name="idUfSelecao" 
					tabindex="2"
					list="ufList" 
					timeout="5000"
					dataType="json"
					listKey="idDTO" 
					listValue="siglaDTO" 
					emptyOption="false" 
					headerKey="0" 
					headerValue="Selecione..."
				/>
	</li>
	
	<li>
	  <label> <spam>*</spam> Cidade:</label> 
	  	    <s:url id="remoteurlCidade" action="obterCidade" namespace="curriculo" /> 
				   <sj:select 
				    cssStyle="width:210px;"
					href="%{remoteurlCidade}" 
					id="idCidadeSelecao"
					tabindex="3"
					timeout="7000"
					formIds="formCurriculoDadosId" 
					reloadTopics="reloadCidadeList"
					name="idCidadeSelecao" 
					list="cidadeList" 
					listKey="idDTO" 
					listValue="nomeDTO" 
					emptyOption="false"
					dataType="json"
					headerKey="0" 
					headerValue="Selecione..."
				/>
	</li>
	
		<li>
	  <label> <spam>*</spam> Logadouro:</label> 
	  <s:textfield  name="aluno.endereco.dsEndereco" id="idEnderecoaluno" maxLength="60" size="30"  theme="simple" />
	</li>
	
	<li>
	  <label> <spam>*</spam> Número:</label> 
	   <s:textfield  name="aluno.endereco.nuEndereco"id="idEnderecoNumeroaluno" maxLength="8" size="30"  theme="simple" />
	</li>
	
	<li>
	  <label> Complemento</label> 
	  <s:textfield  name="aluno.endereco.dsComplemento" id="iComplementoaluno" maxLength="60" size="30"  theme="simple" />
	</li>
	
	<li>
	  <label> <spam>*</spam> Bairro:</label> 
	  <s:textfield  name="aluno.endereco.dsBairro" id="idBairroaluno" maxLength="60" size="30"  theme="simple" />
    </li>
    
	<li>
	  <label> <spam>*</spam> CEP:</label> 
	   <s:textfield  name="aluno.endereco.nuCep"  maxLength="8" id="iCEPaluno"  size="30"  theme="simple" />
	</li>
	
</ul>