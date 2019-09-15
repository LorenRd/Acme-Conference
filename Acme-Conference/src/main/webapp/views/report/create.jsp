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

<form:form action="report/reviewer/create.do" modelAttribute="report">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		<form:label path="submission">
			<spring:message code="report.submission" />
		</form:label>
		<form:select multiple="false" path="submission" >
			<form:options items="${submissions}" itemValue="id" itemLabel="ticker" />
		</form:select>
		<form:errors cssClass="error" path="submission" />
		<br />
		<br />
		<acme:textbox code="report.originalityScore" path="originalityScore"/>
		<br />
		<acme:textbox code="report.qualityScore" path="qualityScore"/>
		<br />
		<acme:textbox code="report.readabilityScore" path="readabilityScore"/>
		<br />
		<spring:message code="report.decision" /> <form:select path="decision" >
			<jstl:if test="${cookie['language'].getValue()=='es'}">
				<option value="ACCEPT"><jstl:out value="ACEPTAR"></jstl:out></option>
				<option value="REJECT"><jstl:out value="RECHAZAR"></jstl:out></option>
				<option value="BORDER-LINE"><jstl:out value="LIMITE"></jstl:out></option>		
			</jstl:if>
			<jstl:if test="${cookie['language'].getValue()=='en'}">
				<option value="ACCEPT"><jstl:out value="ACCEPT"></jstl:out></option>
				<option value="REJECT"><jstl:out value="REJECT"></jstl:out></option>
				<option value="BORDER-LINE"><jstl:out value="BORDER-LINE"></jstl:out></option>	
			</jstl:if>
		</form:select>
		<br />
		<br />
		<acme:textarea code="report.comments" path="comments" />
		<br />		
		<br />
		<acme:submit name="save" code="report.save"/>

		
		<acme:cancel url="welcome/index.do" code="report.cancel"/>
		
</form:form>
