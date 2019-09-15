<%--
 * register.jsp
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
		

	<form:form action="author/register.do" modelAttribute="author" onsubmit="return validatePhone('Phone does not match the pattern', ${countryCode})">
		
		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<fieldset>
    	<legend><spring:message code="author.fieldset.personalInformation"/></legend>
		<acme:textbox code="author.name" path="name" placeholder="Homer"/>
		<acme:textbox code="author.middleName" path="middleName" placeholder="J."/>
		<acme:textbox code="author.surname" path="surname" placeholder="Simpson"/>
		<acme:textbox code="author.photo" path="photo" placeholder="https://www.jazzguitar.be/images/bio/homer-simpson.jpg"/>
		<acme:textbox code="author.email" path="email" placeholder="homerjsimpson@email.com"/>
		<acme:textbox code="author.phone" path="phone" placeholder="+34 600 1234"/>
		<acme:textbox code="author.address" path="address" placeholder="123 Main St Anytown, Australia"/>
		
		</fieldset>
		
		<fieldset>
    	<legend><spring:message code="author.fieldset.userAccount"/></legend>
		<form:hidden path="userAccount.authorities[0].authority" value="AUTHOR" />

				<form:label path="userAccount.username">
					<spring:message code="author.username" />:
				</form:label>
				<spring:message code="author.username.placeholder"
					var="usernamePlaceholder" />
				<form:input path="userAccount.username"
					placeholder="${usernamePlaceholder}" size="25" />
				<form:errors cssClass="error" path="userAccount.username" />
				<br />
				
				<form:label path="userAccount.password">
					<spring:message code="author.password" />:
				</form:label>
				<spring:message code="author.password.placeholder"
					var="passwordPlaceholder" />
				<form:password path="userAccount.password"
					placeholder="${passwordPlaceholder}" size="25" />
				<form:errors cssClass="error" path="userAccount.password" />
		
		</fieldset>
		<br/>
		
		<input type="submit" name="register" id="register"
		value="<spring:message code="author.save" />" >&nbsp; 
		
		<acme:cancel url="welcome/index.do" code="author.cancel"/>
	</form:form>
	
	
	<script type="text/javascript">
	$("#register").on("click",function(){validatePhone("<spring:message code='author.confirmationPhone'/>","${countryCode}");}); 
</script>