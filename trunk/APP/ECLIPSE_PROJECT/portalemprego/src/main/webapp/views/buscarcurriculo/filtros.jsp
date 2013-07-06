<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<h1 id="filtro">Filtros</h1>

<ul>
	
	<li>
	 <label> <spam style="color: #ff0101;">*</spam>Cidade:</label>
	  <s:url var="remoteurlCidadeGoias" action="listarVisualizarCidadeGoias" namespace="visualizar" /> 
	  <sj:select 
			cssStyle="width:210px;"
			href="%{remoteurlCidadeGoias}" 
			id="idCidadeSelecao"
			name="idCidadeSelecao" 
			list="cidadeList" 
			listKey="idDTO" 
			listValue="nomeDTO" 
			emptyOption="false" 
			headerKey="0" 
			headerValue="Selecione..."
		/>
	</li>
	
	<li>
	  <label>Sexo:</label>
	   <s:select cssStyle="width:210px;" name="idTipoSexo" list="sexoList" listKey="tipo" listValue="descricao" headerKey="0" headerValue="Selecione..." theme="simple" />
    </li>
    
	<li>
	  <label>Faixa Etária:</label>
	   <s:select cssStyle="width:210px;" name="idTipoFaixaEtaria" list="faixaEtarialist" listKey="tipo" listValue="descricao" headerKey="0" headerValue="Selecione..." theme="simple" />
    </li>
    
	<li>
	  <label>Área:</label>
	  <s:url var="remoteUrlAreaInteresse" action="listarAreaInteresseVisualizar" namespace="visualizar" /> 
	  <sj:select 
			cssStyle="width:210px;"
			href="%{remoteUrlAreaInteresse}" 
			id="idAreaInteresseelecao"
			name="idAreaInteresSelecao" 
			list="areaInteresseList" 
			listKey="idDTO" 
			listValue="nomeDTO" 
			emptyOption="false" 
			headerKey="0" 
			headerValue="Selecione..."
		/>
	</li>
	
	<li>
	  <s:submit value="Pesquisar" action="pesquisarCurriculoPorArea"   cssClass="ui-state-default ui-button"  cssStyle="border-radius: 3px; margin-top:20px;" />
	</li>
	
</ul>