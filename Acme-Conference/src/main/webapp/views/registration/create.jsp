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

<form:form action="registration/author/create.do" modelAttribute="registrationForm">
		<form:hidden path="id"/>
		
		<spring:message code="registration.conference" />
		
		<form:select id="conference" path="conference">
		<form:options items="${conferences}" itemLabel="title" />
		</form:select>
		<br />
		
		<h3><spring:message code="registration.creditCard" /></h3>
		
		<acme:textbox code="registration.creditCard.holderName" path="holderName"/>
		
		<spring:message code="registration.creditCard.brandName" />
		
		<form:select id="brandName" path="brandName">
		<form:options items="${creditCardMakes}"/>
		</form:select>
		
		<acme:textbox code="registration.creditCard.number" path="number"/>
		
		<acme:textbox code="registration.creditCard.expirationMonth" path="expirationMonth"/>
		
		<acme:textbox code="registration.creditCard.expirationYear" path="expirationYear"/>
		
		<acme:textbox code="registration.creditCard.CVV" path="CVV"/>
		
		<acme:submit name="save" code="registration.save"/>

		<acme:cancel url="welcome/index.do" code="registration.cancel"/>
		
</form:form>
