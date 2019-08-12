<%--
 * edit.jsp
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

<form:form action="cameraReadyPaper/author/edit.do" modelAttribute="cameraReadyPaper">
		<form:hidden path="id"/>
		
		<acme:textbox code="cameraReadyPaper.title" path="title"/>
		
		<acme:textbox code="cameraReadyPaper.author" path="author"/>
		
		<acme:textbox code="cameraReadyPaper.summary" path="summary"/>
		
		<acme:textbox code="cameraReadyPaper.document" path="document"/>
		
		<acme:submit name="save" code="cameraReadyPaper.save"/>

		<acme:cancel url="welcome/index.do" code="cameraReadyPaper.cancel"/>
		
</form:form>