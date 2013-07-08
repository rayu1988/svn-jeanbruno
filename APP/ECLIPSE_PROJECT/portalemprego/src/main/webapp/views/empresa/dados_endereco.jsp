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
		<s:url var="remoteurl" action="obterPaisEmpresa" namespace="empresa" /> 
		<sj:select 
		    cssStyle="width:210px;"
			href="%{remoteurl}" 
			id="idPaisSelecao"
			onChangeTopics="reloadUfList" 
			name="idPais" 
			list="paisList" 
			listKey="idDTO" 
			listValue="nomeDTO" 
			emptyOption="false" 
			headerKey="0" 
			headerValue="Selecione..." />
	</li>
	
	<li>
		<label> <spam>*</spam> Estado:</label> 
		<s:url var="remoteurlEstadoEmpresa" action="obterUfEmpresa" namespace="empresa" /> 
		<sj:select 
		    cssStyle="width:210px;"
			href="%{remoteurlEstadoEmpresa}" 
			id="idUfSelecao"
			formIds="formEmpresaId" 
			reloadTopics="reloadUfList"
			onChangeTopics="reloadCidadeList" 
			name="idUf" 
			list="ufList" 
			listKey="idDTO" 
			listValue="siglaDTO" 
			emptyOption="false" 
			headerKey="0" 
			headerValue="Selecione..." />
	</li>
	
	<li>
	  <label> <spam>*</spam> Cidade:</label> 
			<s:url var="remoteurlCidadeEmrpesa" action="obterCidadeEmpresa" namespace="empresa" /> 
			<sj:select 
			    cssStyle="width:210px;"
				href="%{remoteurlCidadeEmrpesa}" 
				id="idCidadeSelecao"
				formIds="formEmpresaId" 
				reloadTopics="reloadCidadeList"
				name="idCidade" 
				list="cidadeList" 
				listKey="idDTO" 
				listValue="nomeDTO" 
				emptyOption="false" 
				headerKey="0" 
				headerValue="Selecione..." />
	</li>
	
	<li>
	  <label> <spam>*</spam> Logadouro:</label> 
	  <s:textfield  name="empresa.endereco.dsEndereco" id="idEndereco" maxLength="60" size="30"  theme="simple" />
	</li>
	
	<li>
	  <label> <spam>*</spam> Número:</label> 
	   <s:textfield  name="empresa.endereco.nuEndereco"id="idEnderecoNumero" maxLength="8" size="30"  theme="simple" />
	</li>
	
	<li>
	  <label> Complemento</label> 
	  <s:textfield  name="empresa.endereco.dsComplemento" id="iComplemento" maxLength="60" size="30"  theme="simple" />
	</li>
	
	<li>
	  <label> <spam>*</spam> Bairro:</label> 
	  <s:textfield  name="empresa.endereco.dsBairro" id="idBairro" maxLength="60" size="30"  theme="simple" />
    </li>
    
	<li>
	  <label> <spam>*</spam> CEP:</label> 
	   <s:textfield  name="empresa.endereco.nuCep" maxLength="8" id="iCEP"  size="30"  theme="simple" />
	</li>
	
</ul>