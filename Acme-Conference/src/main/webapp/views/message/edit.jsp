<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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

<%-- Formulario para la creación de mensajes nuevos, (escritura de un mensaje a un actor) --%>
<jstl:if test="${mensaje.id==0}">

<jstl:if test="${conference}">
	
	<spring:message code="message.conference" />:
	
	<form:select path="conferences">
		<form:options items="${conferences}" itemValue="id" itemLabel="title" />
	</form:select>
	<br>
	<br>
</jstl:if>

<form:form action="${requestURI}" modelAttribute="mensaje">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender" />
	<form:hidden path="moment" />
	
	
	<jstl:if test="${broadcast || conference}">
	<form:hidden path="recipients" />
	
	</jstl:if>
	
	<%-- Mostrado para elegir en el input el destinatario del mensaje, cuando es un mensaje broadcast de un administrador este input no se muestra puesto que el destinatario son todos los actores del sistema--%>
	<jstl:if test="${!broadcast && !conference}">
	<form:label path="recipients">
		<spring:message code="message.recipient.userAccount" />:
	</form:label>
	<form:select multiple="true" path="recipients" >
		<form:options items="${recipients}" itemValue="id" itemLabel="userAccount.username" />
	</form:select>
	<form:errors cssClass="error" path="recipients" />
	<br>
	<br>
	</jstl:if>
	
	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br>
	<br>
	
	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br>
	<br>
	
	<form:label path="topic">
	<spring:message code="message.topic" />:
	</form:label>
	<form:select path="topic" >
	<form:options items="${topics}" />
	</form:select>
	<form:errors cssClass="error" path="topic" />	
	<br>
	<br>
	
	<jstl:if test="${mensaje.id==0}">
	<input type="submit" name="send"
		value="<spring:message code="message.send" />" />&nbsp; 
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="javascript: relativeRedir('/message/actor/list.do');" />
	<br />

</form:form>
</jstl:if>