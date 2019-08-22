<%--
 * edit.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	<form:form action="${actionURI}" modelAttribute="reviewer" id="form">

		<form:hidden path="id"/>
	<form:hidden path="version" />
	
	<form:label path="name">
			<spring:message code="reviewer.name" />:
	</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br />
	
	<form:label path="middleName">
			<spring:message code="reviewer.middleName" />:
	</form:label>
		<form:input path="middleName" />
		<form:errors cssClass="error" path="middleName" />
		<br />
	
	<form:label path="surname">
			<spring:message code="reviewer.surname" />:
	</form:label>
		<form:input path="surname" />
		<form:errors cssClass="error" path="surname" />
		<br />
	
	<form:label path="email">
			<spring:message code="reviewer.email" />:
	</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
		<br />
	
	<form:label path="phone">
			<spring:message code="reviewer.phone" />:
	</form:label>
		<form:input path="phone" />
		<form:errors cssClass="error" path="phone" />
		<br />
	
	<form:label path="address">
			<spring:message code="reviewer.address" />:
	</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="address" />
		<br />
	
	<form:label path="photo">
			<spring:message code="reviewer.photo" />:
	</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
		<br />
	
	<acme:textarea code="reviewer.expertises" path="expertises"/>
	
	<br />
	
	<form:hidden path="userAccount" />

		<input type="submit" name="save" id="save"
		value="<spring:message code="reviewer.save" />" />
		<input type="button" name="cancel"
		value="<spring:message code="reviewer.cancel" />"
		onclick="javascript: relativeRedir('${redirectURI}');" />
		<br />
		<br />

	</form:form>