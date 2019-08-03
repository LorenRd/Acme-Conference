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

<jstl:if test="${permission }">

	<form:form action="${actionURI}" modelAttribute="reviewer" id="form">

		<form:hidden path="id"/>
	<form:hidden path="version" />
	
	<acme:textbox code="reviewer.name" path="name"/>
	
	<acme:textbox code="reviewer.middleName" path="middleName"/>
	
	<acme:textbox code="reviewer.surname" path="surname"/>
	
	<acme:textbox code="reviewer.email" path="email"/>
	
	<acme:textbox code="reviewer.phone" path="phone"/>
	
	<acme:textbox code="reviewer.address" path="address"/>
	
	<acme:textbox code="reviewer.photo" path="photo"/>
	<br />
	<br />

		<jstl:choose>
			<jstl:when test="${reviewer.id == 0}">

				<form:hidden path="userAccount.authorities[0].authority"
					value="reviewer" />


				<form:label path="userAccount.username">
					<spring:message code="reviewer.username" />:
	</form:label>
				<spring:message code="reviewer.username.placeholder"
					var="usernamePlaceholder" />
				<form:input path="userAccount.username"
					placeholder="${usernamePlaceholder}" size="25" />
				<form:errors cssClass="error" path="userAccount.username" />
				<br />
				<br />



				<form:label path="userAccount.password">
					<spring:message code="reviewer.password" />:
	</form:label>
				<spring:message code="reviewer.password.placeholder"
					var="passwordPlaceholder" />
				<form:password path="userAccount.password"
					placeholder="${passwordPlaceholder}" size="25" />
				<form:errors cssClass="error" path="userAccount.password" />
				<br />
				<br />


			</jstl:when>
			<jstl:otherwise>


				<form:hidden path="userAccount" />


			</jstl:otherwise>
		</jstl:choose>


		<input type="submit" name="save" id="save"
		value="<spring:message code="rookie.save" />" />
		<input type="button" name="cancel"
		value="<spring:message code="rookie.cancel" />"
		onclick="javascript: relativeRedir('${redirectURI}');" />
		<br />
		<br />

	</form:form>

</jstl:if>

<jstl:if test="${!permission }">
	<h3>
		<spring:message code="reviewer.nopermission" />
	</h3>
</jstl:if>