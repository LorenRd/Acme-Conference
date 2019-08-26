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

<form:form action="submission/author/edit.do" modelAttribute="submissionForm">
		<form:hidden path="id"/>
		<form:hidden path="conference"/>
		
		<h3><spring:message code="submission.paper" /></h3>
		
		<acme:textbox code="submission.paper.title" path="title"/>
		
		<acme:textbox code="submission.paper.author" path="author"/>
		
		<acme:textbox code="submission.paper.summary" path="summary"/>
		
		<acme:textbox code="submission.paper.document" path="document"/>
		
		<acme:submit name="save" code="submission.save"/>

		<acme:cancel url="welcome/index.do" code="submission.cancel"/>
		
</form:form>