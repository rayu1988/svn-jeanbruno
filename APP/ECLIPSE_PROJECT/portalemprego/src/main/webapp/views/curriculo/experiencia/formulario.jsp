<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="grid" uri="/struts-jquery-grid-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF -8"    
pageEncoding="UTF-8"%>   

<script type="text/javascript">

	function deleteExperienciaFormatter(cellval, options, rowselect, selarrrow,
			icon, link_class, link_action, event, rowid) {

		var idDeletar = cellval;
		return "<a onClick='javascript: return deleteExperienciaModal("
				+ idDeletar
				+ ")'><div style='cursor:pointer' ><img src='/portalemprego/img/delete.png' alt='excluir' /></div></a>";

	}

	function deleteExperienciaModal(idDeletar) {
		$('input[name=indexExcluirExperiencia]').val(idDeletar);
		$('#excluirExperienciaAcademica').dialog('open');
	}

	function excluirExperiencia() {
		$.ajax({
			type : "post",
			url : "excluirExperienciaCurriculo",
			data : $("#formCurriculoExperienciaId").serialize(),
			dataType : "json",
			success : function(data) {
				$("#datagridExperiencia").trigger("reloadGrid");
			}
		});

		fecharModal();
	}

	function fecharModal() {

		$('#excluirExperienciaAcademica').dialog('close');

	}

	function verificarBotaoGravar() {
		if ($("#checkCompromisso").is(':checked')) {
			$('#botaoGravar').removeAttr("disabled");
			$('#botaoGravar').removeClass("disabledbutton");
		} else {
			$('#botaoGravar').attr("disabled", "disabled");
			$('#botaoGravar').addClass("disabledbutton");
		}
	}
</script>

  <s:form  cssClass="dados-empresariais" method="post" theme="simple" id="formCurriculoExperienciaId" > 
    
    <ul class="dados">
       
       <li>
        <label><spam>*</spam>Empresa:</label>
         <s:textfield  name="experienciaProfissional.dsEmpresaDTO" id="idEmpresa" maxLength="100" size="50"  theme="simple" />
       </li>
       
       <li >
         <label><spam>*</spam>Cargo:</label>
        <s:textfield  name="experienciaProfissional.dsCargo" id="idDsCargo" maxLength="100" size="50"  theme="simple" />
       </li>
              
       <li>
         <label><spam>*</spam>Função:</label>
         <s:textarea cols="60" rows="6"  name="experienciaProfissional.dsFuncaoDTO"  />
       </li>
       
        <li>
          <label><spam>*</spam>Data da Entrada</label>
           <sj:datepicker id="dataEntrada" changeMonth="true" changeYear="true"  name="experienciaProfissional.dataEntradaStr" displayFormat="dd/mm/yy"  size="40" readonly="true"  />
        </li>
        
         <li>
            <label>Data da Saída</label>
           <sj:datepicker id="dataSaida" changeMonth="true" changeYear="true"  name="experienciaProfissional.dataSaidaStr" displayFormat="dd/mm/yy"  size="40" readonly="true"  />
         </li>
         
            
         <li style="text-align: center;">
			<s:submit action="adicionarExperienciaCurriculo" value="" cssStyle="background: url(/portalemprego/img/incluir.jpg); width: 78px; height: 27px;  margin-bottom:20px;" />
         </li>
        
         <li>
		    <s:url var="listarExperienciaCurriculo" action="listarExperienciaCurriculo" namespace="curriculo" />
	        <grid:grid gridModel="experienciaList"
					   id="datagridExperiencia"  formIds="formCurriculoFormacaoId"
					   href="%{listarExperienciaCurriculo}"  dataType="json"
					   width="825" 
					   loadingText="Carregando..."  >

				  <grid:gridColumn name="dsEmpresaDTO" index="dsEmpresaDTO" title="Empresa" />
				  <grid:gridColumn name="dsCargo" index="dsCargo" title="Cargo"   />
				  <grid:gridColumn name="dataEntradaStr" index="dataEntradaStr" title="Data Entrada" />
				  <grid:gridColumn name="dataSaidaStr" index="dataSaidaStr" title="Data Saída" />
				  <grid:gridColumn name="index" index="index" title="Exluir"
				                                 align="center" width="80" 
				                                 formatter="deleteExperienciaFormatter" />

						</grid:grid>
					</li>
      </ul>
      
      
     <div id="termo">
     
     <br/>
     <br/>
     <br/>
     
     <h1 class="termo">TERMO DE COMPROMISSO E ACEITE CURRÍCULO</h1>
     <textarea cols="100" rows="6">
      LEIA O TERMO DE COMPROMISSO E ACEITE

                Ao publicar seu currículo na Internet, por meio do Portal www.bolsafuturo.com.br, o aluno declara ter plena consciência das condições estabelecidas, as quais aceitas sem ressalvas, em sua totalidade.
                
                01- A Secretaria de Estado de Ciência e Tecnologia - Sectec não poderá ser responsabilizada por nenhum dano decorrente do mau uso do currículo publicado;
                
                02- O aluno é o único responsável pelos dados fornecidos, o qual deve redigir, conferir e alterar, quando necessário;
                
                03- O Login e a Senha serão gerados e repassados para o aluno, pelo Sistema do Programa Bolsa Futuro, no ato do cadastramento do curso e, seu uso será exclusivo, não sendo permitido fornecê-los a terceiros;
                
                04- Os dados inseridos em seu currículo devem ser verdadeiros;
                
                05- Não é permitido colocar no corpo do currículo dados que identifiquem o aluno, tais como RG e CPF;
                
                06- A Sectec fica, desde já, autorizada pelo o aluno a fornecer os dados completos do seu currículo às empresas que se interessarem por seu perfil, não podendo ser responsabilizada por nenhum uso indevido de t                erceiros;
                
                07- Este serviço será oferecido para todos os alunos das Unidades de Educação Profissional da Sectec, gratuitamente;
                
                09- A Sectec pode, a seu exclusivo critério, excluir o currículo do aluno de sua base de dados e retirá-lo do ar, quando assim entender, sem prévio aviso ao aluno;
                
                10- O aluno tem ampla liberdade de excluir, ele próprio, seu currículo, a qualquer momento, sem prévio aviso à Sectec;
                
                11- O foro para dirimir quaisquer dúvidas decorrentes da publicação do currículo será da responsabilidade das Unidades de Educação Profissional da Sectec – Rede CEPs.

				Carmem Sandra Ribeiro do Carmo
				Chefe do Núcleo Bolsa Futuro</textarea>
			<div class="check"align="center"> 
			   <s:checkbox onclick="verificarBotaoGravar();"  id="checkCompromisso" name="aceiteTermoCompromisso" label="Li e concordo com os termos e aceite"  />
			    Li e concordo com os termos e aceite</div>
            </div>
            
            
      <s:hidden name="indexExcluirExperiencia" />
      <sj:dialog
        id="excluirExperienciaAcademica"
        buttons="{
                'Sim':function() { excluirExperiencia(); },
                'Não':function() { fecharModal(); }
                }"
        autoOpen="false"
        modal="true"
        title="Confirmação"
    >
     
     Deseja realmente excluir a experiência profissional ?
     
    </sj:dialog>
            
  
  </s:form>

  <s:form namespace="/curriculo" action="salvarCurriculo"  method="post" theme="simple" id="formProceguirId" > 
   
  <div style="text-align: center;">
		
	<s:submit value="Gravar" disabled="true" id="botaoGravar" cssClass="ui-state-default ui-button disabledbutton"  cssStyle="border-radius: 3px; margin-top:20px;" />
		
	<s:submit value="Voltar" action="voltarFormacaoAcademica"   cssClass="ui-state-default ui-button"  cssStyle="border-radius: 3px; margin-top:20px;" />
    
    </div>
  </s:form>

