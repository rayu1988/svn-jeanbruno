<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="grid" uri="/struts-jquery-grid-tags"%>

	<s:form namespace="empresa" method="post" id="formEmpresaTelefoneTable" >
		<s:url var="obterTelefonesURL" action="obterTelefones" namespace="empresa" />
		<grid:grid gridModel="telefoneList" id="datagridTelefoneEmpresa" reloadTopics="meuTeste" formIds="formEmpresaTelefoneTable" 
			href="%{obterTelefonesURL}" loadingText="Carregando..."
			onSelectRowTopics="rowselect"   width="670" >

			<grid:gridColumn name="nuDdi" index="nuDdi" title="DDI" sortable="false" align="left"/>
			<grid:gridColumn name="nuDdd" index="nuDdd" title="DDD" sortable="false" align="left"/>
			<grid:gridColumn name="numeroDTO" index="numeroDTO" title="Telefone" sortable="false" align="left"/>
			<grid:gridColumn name="dsTipo" index="dsTipo" title="Tipo" sortable="false" align="left"/>
			<grid:gridColumn name="index" index="index" title="Ação" sortable="false" align="center" formatter="buildHtmlContentButtons_PhoneNumber" />
			
		</grid:grid>
	</s:form>
