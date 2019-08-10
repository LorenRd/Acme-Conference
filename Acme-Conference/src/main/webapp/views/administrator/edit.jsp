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



	<form:form action="${actionURI}" modelAttribute="administrator" id="form">

		<form:hidden path="id"/>
	<form:hidden path="version" />
	
	<acme:textbox code="administrator.name" path="name"/>
	
	<acme:textbox code="administrator.middleName" path="middleName"/>
	
	<acme:textbox code="administrator.surname" path="surname"/>
	
	<acme:textbox code="administrator.email" path="email"/>
	
	<acme:textbox code="administrator.phone" path="phone"/>
	
	<acme:textbox code="administrator.address" path="address"/>
	
	<acme:textbox code="administrator.photo" path="photo"/>
	<br />
	<br />
	
	<form:hidden path="userAccount" />

	<input type="submit" name="save" id="save"
	value="<spring:message code="administrator.save" />" >&nbsp; 
		
	<acme:cancel url="welcome/index.do" code="administrator.cancel"/>

	</form:form>