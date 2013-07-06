<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="grid" uri="/struts-jquery-grid-tags"%>

<div class="end">
	<spam style="color: #ff0101;">*</spam>
	Telefone:
</div>

<ul class="end">
	<li>
		<label> <spam>*</spam> Tipo: </label>
		<s:url var="remoteurlTipotelefone" action="obterTipoTelefoneEmpresa" namespace="empresa" /> 
		<sj:select cssStyle="width:210px;"
			href="%{remoteurlTipotelefone}" id="idTipoTelefoneEmpresa"
			name="idTipoTelefone" list="tipoTelefoneList" listKey="idDTO"
			listValue="nomeDTO" emptyOption="false" headerKey="0"
			headerValue="Selecione..." />
	</li>
	<li>
		<label> <spam>*</spam> DDI: </label>
		<s:textfield name="telefone.nuDdi" id="idDDI" maxLength="2" size="30" theme="simple" />
	</li>
	<li>
		<label> <spam>*</spam> DDD:	</label>
		<s:textfield name="telefone.nuDdd" id="idDDD" maxLength="3" size="30" theme="simple" />
	</li>
	<li>
		<label> <spam>*</spam> Numero:</label>
		<s:textfield name="telefone.nuTelefone" id="idTelefoneCadastro" maxLength="8" size="30" theme="simple" />
	</li>
	<li>
		<div style="margin-left: 100px;">
			<!-- incluir -->
			<input type="button" value="Adicionar Telefone" name="buttonAddPhoneNumber" id="idButtonAddPhoneNumber" class="customizedButton"/>
			
			<!-- salvar e cancelar -->
			<input type="button" value="Salvar" name="buttonSavePhoneNumber" id="idButtonSavePhoneNumber" class="customizedButton" style="display:none;"/>
			<input type="button" value="Cancelar" name="buttonCancelPhoneNumber" id="idButtonCancelPhoneNumber" class="customizedButton" style="display:none;"/>
		</div>
	</li>
	<li id="idDadosTelefoneTable">
		<jsp:include page="dados_telefone_table.jsp" />
	</li>
</ul>