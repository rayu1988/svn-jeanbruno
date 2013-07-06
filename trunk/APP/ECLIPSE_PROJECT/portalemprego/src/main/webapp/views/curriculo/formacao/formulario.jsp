<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="grid" uri="/struts-jquery-grid-tags"%>

<script type="text/javascript">

	function deleteLinkFormatter(cellval, options, rowselect, selarrrow, icon,
			link_class, link_action, event, rowid) {

		var idDeletarFormacao = cellval;
		return "<a onClick='javascript: return deleteModalFormacao("+ idDeletarFormacao+")'><div style='cursor:pointer' ><img src='/portalemprego/img/delete.png' alt='excluir' /></div></a>";

	}

	function deleteModalFormacao(idDeletarFormacao) {
		$('input[name=indexEcluirFormacao]').val(idDeletarFormacao);
		$('#excluirFormacao').dialog('open');
	}
	
	function excluirTelefone() {
		$.ajax({
		    type : "post",
		    url : "excluirFormacaoCurriculo",
		    data:$("#formCurriculoFormacaoId").serialize(),
		    dataType: "json",       
		    success : function(data) {
				        $("#datagridFormacap").trigger("reloadGrid");
		    }
		});

		fecharModal();
	}

	function fecharModal() {

		 $('#excluirFormacao').dialog('close');
		
	}
	
</script>


  <s:form  cssClass="dados-empresariais" method="post" theme="simple" id="formCurriculoFormacaoId" > 
         <ul class="dados">
           	    <li>
           	      <label><spam>*</spam> Grau:</label>
           	      <s:url var="remoteurlTipoGrauCurriculo" action="listarTipoGrauCurriculo" namespace="curriculo" />
           	      <sj:select cssStyle="width:340px;"   href="%{remoteurlTipoGrauCurriculo}" id="idTipoGrau"
			                 name="formacaoAcademica.idGrau" list="tipoGrauList" listKey="idDTO" listValue="nomeDTO" emptyOption="false" headerKey="0"
			                 headerValue="Selecione..." />
           	    </li>
                                                                
                 <li>
                   <label><spam>*</spam> Intituição:</label >
                    <s:textfield  name="formacaoAcademica.instituicao" id="idInstituicao" maxLength="250" size="55"  theme="simple" />
                 </li>
                 
                    <li>
                       <label><spam>*</spam> Curso:</label>
                        <s:textfield  name="formacaoAcademica.curso" id="idInstituicao" maxLength="250" size="55"  theme="simple" />
                    </li>

                    <li>
                       <label><spam>*</spam> Período do Curso:</label>
                        <sj:datepicker id="dataInicio"  name="formacaoAcademica.dtInicio" displayFormat="dd/mm/yy" size="20" readonly="true" changeMonth="true" changeYear="true"   />
                          à
                        <sj:datepicker id="dataFinal"  name="formacaoAcademica.dtFim" displayFormat="dd/mm/yy" size="20" readonly="true" changeMonth="true" changeYear="true"  />
                    </li>
                    

                    <br/>                    
                    <li style="text-align: center;">
					  <s:submit action="adicionarFormacao" value=""
								cssStyle="background: url(/portalemprego/img/incluir.jpg); width: 78px; height: 27px;  margin-bottom:20px;" />
					</li>
                    <li>
    
						<s:url var="listarDadosFormacaoAcademica" action="listarDadosFormacaoAcademica" namespace="curriculo" />
	        			<grid:grid gridModel="formacaoList"
									id="datagridFormacap" reloadTopics="grdiFormacao"  formIds="formCurriculoFormacaoId"
									href="%{listarDadosFormacaoAcademica}"  dataType="json"
									width="825" 
								    loadingText="Carregando..."  >

								<grid:gridColumn name="grau" index="grau" title="Grau" />
								<grid:gridColumn name="instituicao" index="instituicao" title="Instituição"   />
								<grid:gridColumn name="curso" index="curso" title="Curso" />
								<grid:gridColumn name="periodo" index="periodo" title="Periodo do Curso" />
								<grid:gridColumn name="index" index="index" title="Exluir"
				                                 align="center" width="80" 
				                                 formatter="deleteLinkFormatter" />

						</grid:grid>
					</li>
                    
                    
           </ul>
      <s:hidden name="indexEcluirFormacao" />
      <sj:dialog
        id="excluirFormacao"
        buttons="{
                'Sim':function() { excluirTelefone(); },
                'Não':function() { fecharModal(); }
                }"
        autoOpen="false"
        modal="true"
        title="Confirmação"
    >
     
     Deseja realmente excluir a formação academica ?
     
    </sj:dialog>
           
  </s:form>

  <s:form  namespace="/curriculo" action="abreDadosExperienciaProfissional"  method="post" theme="simple" id="formProceguirId" > 
    <div style="text-align: center;">
		<s:submit value="Prosseguir" id="botaoAbreFormacao" cssClass="ui-state-default ui-button"  cssStyle="border-radius: 3px; margin-top:20px;" />
		
		<s:submit value="Voltar" action="abreDadosPessoais"   cssClass="ui-state-default ui-button"  cssStyle="border-radius: 3px; margin-top:20px;" />
    </div>
  </s:form>
  

