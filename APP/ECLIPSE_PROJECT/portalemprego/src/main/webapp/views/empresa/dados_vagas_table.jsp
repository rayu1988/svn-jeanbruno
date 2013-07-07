<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="grid" uri="/struts-jquery-grid-tags"%>

	  <s:url var="obterVagasOfertadasURL" action="listarVagasOfertadas" namespace="empresa" /> 
	  <grid:grid gridModel="vagaList"
				id="datagridVagas"
				formIds="formEmpresaId"
				href="%{obterVagasOfertadasURL}"
    			loadingText="Carregando..."
    			pager="true"
    	        rowNum="10"
    	        width="670">
		    <grid:gridColumn name="dsCidade" index="dsCidade" title="Cidade" sortable="false" align="left"/>
	    	<grid:gridColumn name="dsArea" index="dsArea" title="Área" sortable="false" align="left"/>
	    	<grid:gridColumn name="qtdVagas" index="qtdVagas" title="Quantidade de Vagas" sortable="false" align="left"/>
	    	<grid:gridColumn name="dataExpiracaoStr" index="dataExpiracaoStr"  title="Data de Expiração" sortable="false" align="left"/>
	    	<grid:gridColumn name="dsVagaOfertada" index="dsVagaOfertada"  title="Descrição" sortable="false" align="left"/>
	    	<grid:gridColumn name="index" index="index" title="Ação" sortable="left" align="center" formatter="buildHtmlContentButtons_JobVacancy" />
		</grid:grid>