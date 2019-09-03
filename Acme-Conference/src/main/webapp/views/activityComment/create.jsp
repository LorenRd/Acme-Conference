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

<form:form action="activityComment/create.do?activityId=${param['activityId']}" modelAttribute="activityComment">
		<form:hidden path="id"/>
		
		<h3><spring:message code="activityComment" /></h3>
		
		<acme:textbox code="activityComment.title" path="title"/>
		
		<acme:textbox code="activityComment.author" path="author"/>
		
		<acme:textbox code="activityComment.text" path="text"/>
		
		<acme:submit name="save" code="activityComment.save"/>

		<acme:cancel url="welcome/index.do" code="activityComment.cancel"/>
		
</form:form>
