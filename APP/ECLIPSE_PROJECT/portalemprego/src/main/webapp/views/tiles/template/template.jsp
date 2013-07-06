<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML >
<html>

<head>
<title>Guia do Emprego</title>
<meta name="description" content="Guia do Emprego">
<meta name="keywords" content="sectec; vagas; bolsa; emprego;">
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/> 
<meta name="robots" content="index, follow">
<meta name="revisit-after" content="7 days">

<sj:head jqueryui="true" locale="pt-BR" /> 
<script type="text/javascript" src="${request['javax.servlet.forward.context_path']}/js/jquery.maskedinput.js"></script>
<script type="text/javascript" src="${request['javax.servlet.forward.context_path']}/js/jquery.maskMoney.js"></script>
<script type="text/javascript" src="${request['javax.servlet.forward.context_path']}/js/functions.js"></script>
<tiles:importAttribute name="appendJavaScript"/>
<c:if test="${appendJavaScript ne null}">
	<script type="text/javascript" src="${request['javax.servlet.forward.context_path']}${appendJavaScript}"></script>
</c:if>

<!-- Inserir as folhas de estilo necessárias ao template -->
<link rel=stylesheet type="text/css" href="${request['javax.servlet.forward.context_path']}/css/base.css">
<link rel=stylesheet type="text/css" href="${request['javax.servlet.forward.context_path']}/css/conteudo.css">
<link rel=stylesheet type="text/css" href="${request['javax.servlet.forward.context_path']}/css/css.css">
<link rel=stylesheet type="text/css" href="${request['javax.servlet.forward.context_path']}/css/footer.css">
<link rel=stylesheet type="text/css" href="${request['javax.servlet.forward.context_path']}/css/header.css">
<link rel=stylesheet type="text/css" href="${request['javax.servlet.forward.context_path']}/css/home.css">


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

.welcome {
	background-color: #DDFFDD;
	border: 1px solid #009900;
	width: 200px;
}

.welcome li {
	list-style: none;
}
</style>


</head>

<body>
	<div id="all">

		<div id="cabecalho"></div>

		<div id="content">

			<div id="menu-quadro">


				<tiles:insertAttribute name="menu" />

				<div id="formulario">

					<s:if test="hasActionMessages()">
						<div class="welcome" style="width:98%;font-weight:bold;border-radius: 3px;padding-top: 5px;padding-bottom: 5px; ">
							<s:actionmessage cssStyle="font-size: 11px;margin-left: 10px;font-family: inherit;font-weight: bold;" />
						</div>
						<br/>
					</s:if>
 

					<s:if test="hasActionErrors()">
						<div class="errors" style="width:98%;font-weight:bold; border-radius: 3px; padding-top: 5px;padding-bottom: 5px;" >
							<s:actionerror cssStyle="font-size: 11px;margin-left: 10px;font-family: inherit;font-weight: bold;"  />
						</div>
						<br/>
					</s:if>

					<tiles:insertAttribute name="formulario" />

				</div>


			</div>

		</div>
	</div>
</body>
</html>