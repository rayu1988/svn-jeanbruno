var namespace_empresa = "empresa/"
var indexElement_PhoneNumber = "";
var indexElement_JobVacancy = "";

$(document).ready(function(){
	$("#idDDI").keypress(verificaNumero);
	$("#idDDD").keypress(verificaNumero);
	$("#idTelefoneCadastro").keypress(verificaNumero);
	$("#idQtdVagas").keypress(verificaNumero);
	
	// start phone number section
	$('#idButtonAddPhoneNumber').click(function(){
		$.ajax({
			url: namespace_empresa + 'adicionarTelefoneEmpresa.action',
			type: 'POST',
			data: getParameters_PhoneNumber(),
			dataType: 'json',
			success: function (data) {
				if (data.processingReturn.success) {
					refreshPartialContent_PhoneNumber();
				} else {
					alert(data.processingReturn.errorMsg);
				}
			},
			error: function(xhr, ajaxOptions, thrownError){
				alert('An error occurred! ' + thrownError);
			}
		});
    });
	$('#idButtonCancelPhoneNumber').click( clearForm_PhoneNumber );
	$('#idButtonSavePhoneNumber').click(function(){
    	$.ajax({
			url: namespace_empresa + 'salvarTelefoneEmpresa.action',
			type: 'POST',
			data: getParameters_PhoneNumber(),
			dataType: 'json',
			success: function (data) {
				if (data.processingReturn.success) {
					refreshPartialContent_PhoneNumber();
				} else {
					alert(data.processingReturn.errorMsg);
				}
			},
			error: function(xhr, ajaxOptions, thrownError){
				alert('An error occurred! ' + thrownError);
			}
		});
	});
	// end phone number section
	
	// start job vacancy section
	$('#idButtonAddJobVacancy').click(function(){
		$.ajax({
			url: namespace_empresa + 'adicionarVagasOfertasEmpresa.action',
			type: 'POST',
			data: getParameters_JobVacancy(),
			dataType: 'json',
			success: function (data) {
				if (data.processingReturn.success) {
					refreshPartialContent_JobVacancy();
				} else {
					alert(data.processingReturn.errorMsg);
				}
			},
			error: function(xhr, ajaxOptions, thrownError){
				alert('An error occurred! ' + thrownError);
			}
		});
    });
	$('#idButtonCancelJobVacancy').click( clearForm_JobVacancy );
	$('#idButtonSaveJobVacancy').click(function(){
    	$.ajax({
			url: namespace_empresa + 'salvarVagasOfertasEmpresa.action',
			type: 'POST',
			data: getParameters_JobVacancy(),
			dataType: 'json',
			success: function (data) {
				if (data.processingReturn.success) {
					refreshPartialContent_JobVacancy();
				} else {
					alert(data.processingReturn.errorMsg);
				}
			},
			error: function(xhr, ajaxOptions, thrownError){
				alert('An error occurred! ' + thrownError);
			}
		});
	});
	// end job vacancy section
});

// start phone number section
function getParameters_PhoneNumber(phoneIndex) {
	if (phoneIndex != null) indexElement_PhoneNumber = phoneIndex;
	
	var valDDI = $('#idDDI').val();
	var valDDD = $('#idDDD').val();
	var valTelefoneCadastro = $('#idTelefoneCadastro').val();
	var valIdTipoTelefone = $('#idTipoTelefoneEmpresa').val();
	var param = { 'telefone.nuDdi':valDDI , 'telefone.nuDdd':valDDD, 'telefone.nuTelefone':valTelefoneCadastro, 'idTipoTelefone':valIdTipoTelefone, 'indexElementList':indexElement_PhoneNumber };
	return param;
}
function clearForm_PhoneNumber() {
	$('#idDDI').val("");
	$('#idDDD').val("");
	$('#idTelefoneCadastro').val("");
	$('#idTipoTelefoneEmpresa').val("0");
	
	indexElement_PhoneNumber = "";
	
	$('#idButtonSavePhoneNumber').hide();
	$('#idButtonCancelPhoneNumber').hide();
	$('#idButtonAddPhoneNumber').show();
}
function refreshPartialContent_PhoneNumber() {
	$.ajax({
		url: namespace_empresa + 'loadTablePhoneNumber.action',
		type: 'POST',
		data: getParameters_PhoneNumber(),
		dataType: 'html',
		success: function (htmlContent) {
			clearForm_PhoneNumber();
			
			$('#idDadosTelefoneTable').empty();
			$('#idDadosTelefoneTable').html(htmlContent);
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert('An error occurred! ' + thrownError);
		}
	});
}
function buildHtmlContentButtons_PhoneNumber(cellval, options, rowselect, selarrrow, icon, link_class, link_action, event, rowid) {
	var htmlContent = "<div style='white-space: nowrap;'>";
	htmlContent += "<a href='#' alt='Alterar' class='customizedLinks' style='margin-right: 15px;' onclick='edit_PhoneNumber(" + cellval + "); return false;'>Alterar</a>";
	htmlContent += "<a href='#' alt='Excluir' class='customizedLinks' onclick='delete_PhoneNumber(" + cellval + "); return false;'>Excluir</a>";
	htmlContent += "</div>";
	return htmlContent;
}
function delete_PhoneNumber(phoneIndex) {
	$.ajax({
		url: namespace_empresa + 'excluirTelefoneEmpresa.action',
		type: 'POST',
		data: getParameters_PhoneNumber(phoneIndex),
		dataType: 'json',
		success: function (data) {
			if (data.processingReturn.success) {
				refreshPartialContent_PhoneNumber();
			} else {
				alert(data.processingReturn.errorMsg);
			}
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert('An error occurred! ' + thrownError);
		}
	});
}
function edit_PhoneNumber(phoneIndex) {
	$.ajax({
		url: namespace_empresa + 'editarTelefoneEmpresa.action',
		type: 'POST',
		data: getParameters_PhoneNumber(phoneIndex),
		dataType: 'json',
		success: function(data) {
			indexElement_PhoneNumber = data.indexElementList;
			
			$('#idButtonSavePhoneNumber').show();
			$('#idButtonCancelPhoneNumber').show();
			$('#idButtonAddPhoneNumber').hide();
			
			$('#idDDI').val(data.telefone.nuDdi);
	    	$('#idDDD').val(data.telefone.nuDdd);
	    	$('#idTelefoneCadastro').val(data.telefone.nuTelefone);
	    	$('#idTipoTelefoneEmpresa').val(data.idTipoTelefone);
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert('An error occurred! ' + thrownError);
		}
	});
}
// end phone number section

// start job vacancy section
function getParameters_JobVacancy(jobVacancyIndex) {
	if (jobVacancyIndex != null) indexElement_JobVacancy = jobVacancyIndex;

	var valCidadeVagaSelecao = $('#idCidadeVagaSelecao').val();
	var valAreaInteresse = $('#idAreaInteresse').val();
	var valQtdVagas = $('#idQtdVagas').val();
	var valDataExpiracao = $('#idDataExpiracao').val();
	var valDsVagas = $('#idDsVagas').val();
	
	var param = { 'vaga.idCidade':valCidadeVagaSelecao, 'vaga.idArea':valAreaInteresse, 'vaga.qtdVagas':valQtdVagas, 'vaga.dataExpiracaoStr':valDataExpiracao, 'vaga.dsVagaOfertada':valDsVagas, 'indexElementList':indexElement_JobVacancy };
	return param;
}
function clearForm_JobVacancy() {
	$('#idCidadeVagaSelecao').val("0");
	$('#idAreaInteresse').val("0");
	$('#idQtdVagas').val("");
	$('#idDataExpiracao').val("");
	$('#idDsVagas').val("");
	
	indexElement_JobVacancy = "";
	
	$('#idButtonSaveJobVacancy').hide();
	$('#idButtonCancelJobVacancy').hide();
	$('#idButtonAddJobVacancy').show();
}
function refreshPartialContent_JobVacancy() {
	$.ajax({
		url: namespace_empresa + 'loadTableJobVacancy.action',
		type: 'POST',
		data: getParameters_JobVacancy(),
		dataType: 'html',
		success: function (htmlContent) {
			clearForm_JobVacancy();
			
			$('#idDadosVagasTable').empty();
			$('#idDadosVagasTable').html(htmlContent);
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert('An error occurred! ' + thrownError);
		}
	});
}
function buildHtmlContentButtons_JobVacancy(cellval, options, rowselect, selarrrow, icon, link_class, link_action, event, rowid) {
	var htmlContent = "<div style='white-space: nowrap;'>";
	htmlContent += "<a href='#' alt='Alterar' class='customizedLinks' style='margin-right: 15px;' onclick='edit_JobVacancy(" + cellval + "); return false;'>Alterar</a>";
	htmlContent += "<a href='#' alt='Excluir' class='customizedLinks' onclick='delete_JobVacancy(" + cellval + "); return false;'>Excluir</a>";
	htmlContent += "</div>";
	return htmlContent;
}
function delete_JobVacancy(jobVacancyIndex) {
	$.ajax({
		url: namespace_empresa + 'excluirVagasOfertasEmpresa.action',
		type: 'POST',
		data: getParameters_JobVacancy(jobVacancyIndex),
		dataType: 'json',
		success: function (data) {
			if (data.processingReturn.success) {
				refreshPartialContent_JobVacancy();
			} else {
				alert(data.processingReturn.errorMsg);
			}
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert('An error occurred! ' + thrownError);
		}
	});
}
function edit_JobVacancy(jobVacancyIndex) {
	$.ajax({
		url: namespace_empresa + 'editarVagasOfertasEmpresa.action',
		type: 'POST',
		data: getParameters_JobVacancy(jobVacancyIndex),
		dataType: 'json',
		success: function(data) {
			indexElement_JobVacancy = data.indexElementList;
			
			$('#idButtonSaveJobVacancy').show();
			$('#idButtonCancelJobVacancy').show();
			$('#idButtonAddJobVacancy').hide();
			
			$('#idCidadeVagaSelecao').val(data.vaga.idCidade);
			$('#idAreaInteresse').val(data.vaga.idArea);
			$('#idQtdVagas').val(data.vaga.qtdVagas);
			$('#idDataExpiracao').val(data.vaga.dataExpiracaoStr);
			$('#idDsVagas').val(data.vaga.dsVagaOfertada);
		},
		error: function(xhr, ajaxOptions, thrownError){
			alert('An error occurred! ' + thrownError);
		}
	});
}
// end job vacancy section





function deleteTelefoneModal(idDeletar) {
	$('input[name=idTelefoneEx]').val(idDeletar);
	$('#excluirTelefoneCurriculo').dialog('open');
}

function excluirExperiencia() {
	$.ajax({
	    type : "post",
	    url : "excluirTelefoneEmpresa",
	    data:$("#formEmpresaId").serialize(),
	    dataType: "json",       
	    success : function(data) {
			        $("#datagridTelefoneEmpresa").trigger("reloadGrid");
	    }
	});
	fecharModalTelefone();
}

function fecharModalTelefone() {
	 $('#excluirTelefoneCurriculo').dialog('close');
}