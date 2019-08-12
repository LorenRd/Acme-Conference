<%--
 * create.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
 <%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="cameraReadyPaper/author/create.do" modelAttribute="cameraReadyPaper">
		<form:hidden path="id"/>
		
		<spring:message code="cameraReadyPaper.submission" />
		
		<form:select id="submission" path="submission">
		<form:options items="${submissions}" itemLabel="ticker" />
		</form:select>
		<br />
		
		<h3><spring:message code="cameraReadyPaper" /></h3>
		
		<acme:textbox code="cameraReadyPaper.title" path="title"/>
		
		<acme:textbox code="cameraReadyPaper.author" path="author"/>
		
		<acme:textbox code="cameraReadyPaper.summary" path="summary"/>
		
		<acme:textbox code="cameraReadyPaper.document" path="document"/>
		
		<acme:submit name="save" code="cameraReadyPaper.save"/>

		<acme:cancel url="welcome/index.do" code="cameraReadyPaper.cancel"/>
		
</form:form>
