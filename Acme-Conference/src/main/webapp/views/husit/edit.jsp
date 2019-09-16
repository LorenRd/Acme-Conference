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
<form:form action="husit/administrator/edit.do" modelAttribute="husit">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="publicationMoment"/>
		<form:hidden path="ticker"/>
		<form:hidden path="isDraft"/>
		
		<acme:textarea code="husit.body" path="body"/>
		<br />		
		<acme:textbox code="husit.picture" path="picture" />
		<br />
		
		<acme:submit name="saveDraft" code="husit.saveDraft"/>
		
		<acme:submit name="saveFinal" code="husit.save"/>
		
		<acme:cancel url="welcome/index.do" code="husit.cancel"/>
		
</form:form>