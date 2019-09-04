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

<form:form action="conference/administrator/edit.do" modelAttribute="conference">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		<acme:textbox code="conference.title" path="title" placeholder="Conference title"/>
		<br />
		<acme:textbox code="conference.acronym" path="acronym"/>
		<br />		
		<acme:textarea code="conference.venue" path="venue"/>
		<br />
		<acme:datebox code="conference.submissionDeadline" path="submissionDeadline" placeholder="dd/MM/yyyy HH:mm" />
		<br />
		<acme:datebox code="conference.notificationDeadline" path="notificationDeadline" placeholder="dd/MM/yyyy HH:mm" />
		<br />
		<acme:datebox code="conference.cameraReadyDeadline" path="cameraReadyDeadline" placeholder="dd/MM/yyyy HH:mm" />
		<br />
		<acme:datebox code="conference.startDate" path="startDate" placeholder="dd/MM/yyyy HH:mm" />
		<br />
		<acme:datebox code="conference.endDate" path="endDate" placeholder="dd/MM/yyyy HH:mm" />
		<br />
		<acme:textarea code="conference.summary" path="summary"/>
		<br />
		<acme:textbox code="conference.fee" path="fee" placeholder="0.0"/>
		<br />		
		
	<jstl:if test="${cookie['language'].getValue()=='en'}">
	<form:label path="category">
		<spring:message code="conference.category" />:
		</form:label>
		<form:select path="category" >
			<form:options items="${categories}" itemValue="id" itemLabel="englishName" />
		</form:select>
		<form:errors cssClass="error" path="category" />
	</jstl:if>
	
	<jstl:if test="${cookie['language'].getValue()=='es'}">
	<form:label path="category">
		<spring:message code="conference.category" />:
		</form:label>
		<form:select multiple="true" path="category" >
			<form:options items="${categories}" itemValue="id" itemLabel="spanishName" />
		</form:select>
		<form:errors cssClass="error" path="category" />
	</jstl:if>
		
		<br />
		<br />
		<acme:submit name="saveDraft" code="conference.saveDraft"/>
		<acme:submit name="saveFinal" code="conference.saveFinal"/>

		
		<acme:cancel url="welcome/index.do" code="conference.cancel"/>
		
</form:form>
