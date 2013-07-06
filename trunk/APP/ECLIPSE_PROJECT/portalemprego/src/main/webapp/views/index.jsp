<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="/struts-tags" prefix="s" %>   
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>  
<!DOCTYPE html >
<html><head>
	<title>Guia do Emprego</title>
	<meta name="description" content="Guia do Emprego">
	<meta name="keywords" content="sectec; vagas; bolsa; emprego;">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/> 
	<meta name="robots" content="index, follow">
	<meta name="revisit-after" content="7 days">
	<sj:head jqueryui="true" locale="pt-BR" />
	<!-- Inserir as folhas de estilo necessárias ao template -->
	<link rel=stylesheet type="text/css" href="/portalemprego/css/base.css"> 
	<link rel=stylesheet type="text/css" href="/portalemprego/css/conteudo.css"> 
	<link rel=stylesheet type="text/css" href="/portalemprego/css/css.css"> 
	<link rel=stylesheet type="text/css" href="/portalemprego/css/footer.css"> 
	<link rel=stylesheet type="text/css" href="/portalemprego/css/header.css"> 
	<link rel=stylesheet type="text/css" href="/portalemprego/css/home.css"> 
	
	<style type="text/css">
	.errors {
	background-color: #FFCCCC;
	border: 1px solid #CC0000;
	width: 400px;
	margin-bottom: 8px;
}

.errors li {
	list-style: none;
}
	  
	</style>
	
</head><body>
	<!-- begin: #página -->
	<div id="all">
		<!-- begin: cabecalho -->
		<div id="cabecalho">
		</div>
		<!-- end: cabecalho -->

		<!-- begin: área de conteúdo -->
		<div id="content">

	  		<!-- begin: coluna da esquerda -->
			<div id="box1">
				<div class="quadro">
					<!-- Incluir o menu lateral -->
					<h2 class="titulo-campo">Instituição / Empresa</h2>
					<s:form>
					  <s:url var="remoteurlIniciarEmpresa" action="iniciarEmpresa" namespace="/empresa" /> 
					  <s:url var="remoteurlVisualizarCurriculo" action="abreVisualizarCurriculo" namespace="/visualizar" /> 
					 <div class="quadro-branco">
						<ul class="instituicao-empresa">
							<li><s:a  href="%{remoteurlVisualizarCurriculo}" >Buscar Currículo</s:a></li>
							<li><s:a  href="%{remoteurlIniciarEmpresa}" >Cadastrar Instituição/Empresa</s:a></li>
							<li><a href="#">Esqueceu a senha?</a></li>
						</ul>
					</div>
					</s:form>
				</div>
				<!-- Incluir conteúdo lateral -->
				<div class="quadro-branco">
				</div>
	  		</div>
	  		<!-- end: coluna da esquerda -->

			<!-- begin: coluna central -->
	  		<div id="box2">
	  			<div class="text">
					<!-- Conteúdo principal -->
					<a id="content" name="content"></a>
	      		</div>
	      		<!-- end: coluna central -->
      		</div>

	  		<!-- begin: coluna da direita -->
	  		<div id="box3">
		  		<div class="quadro">
					<!-- Incluir login lateral -->
							
					<h2 class="titulo-campo">Atualize seu currículo</h2>
					<div class="quadro-branco">
							<s:form id="us" namespace="curriculo" action="logarAluno" >

                           	<s:if test="hasActionErrors()">
						     <div class="errors" style="width:90%;font-weight:bold; border-radius: 3px; padding-top: 5px;padding-bottom: 5px;" >
							  <s:actionerror cssStyle="font-size: 8px;margin-left: 10px;font-family: inherit;font-weight: bold;"  />
						     </div>
					       	 <br/>
					        </s:if>

							<div>Usuario:</div>
							<label><s:textfield  name="login" id="idUsuarioLogin" maxLength="60"  theme="simple"/></label> 
							<div>Senha:</div>
							<label><s:password  name="senha" id="idSenhaLogin" maxLength="20"  theme="simple"/></label> 
							<div class="senha"><a href="#">Esqueci minha senha</a></div>
							<s:submit  value="OK"  align="left" />
							</s:form>
					</div>
				</div>
				<!-- Incluir conteúdo lateral -->
				<div class="quadro-branco">
				</div>
				<div class="titulo">Vagas de emprego</div>
	            <ul id="vagas"> 
					<div class="quadros">
						<li class="vaga"><a href="#">Arquiteto de Software</a></li>
                		<li class="quant">1 vaga - Goiânia</li>
                		<li class="data">05/06/2013</li>
              		</div>
					<div class="quadros">
						<li class="vaga"><a href="#">Desenvolvedor Java</a></li>
                		<li class="quant">13 vagas - Goiânia</li>
                		<li class="data">07/06/2013</li>
              		</div>
	            </ul>
			</div>
			<!-- end: coluna da direita -->
		</div>
		<!-- end: área do conteúdo -->

		<!-- begin: #rodapé -->
		<div id="footer">
		</div>
		<!-- end: #rodapé -->
	</div>
	<!-- end: #página -->
</body></html>