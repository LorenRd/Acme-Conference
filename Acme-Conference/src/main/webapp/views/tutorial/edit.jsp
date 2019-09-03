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

<form:form action="activity/administrator/tutorial/edit.do" modelAttribute="tutorial">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
				
		<acme:textbox code="activity.title" path="activityTitle"/>
		<br />		
		<acme:textarea code="activity.speakers" path="speakers"/>
		<br />
		<acme:datebox code="activity.startDate" path="startMoment" placeholder="dd/MM/yyyy HH:mm" />
		<br />
		<acme:textbox code="activity.duration" path="duration" placeholder="0" />
		<br />
		<acme:textarea code="activity.room" path="room" />
		<br />
		<acme:textarea code="activity.summary" path="activitySummary" />
		<br />
		<acme:textarea code="activity.attachments" path="attachments" />
		<br />		
		<br />
		<acme:submit name="save" code="activity.save"/>

		
		<acme:cancel url="welcome/index.do" code="activity.cancel"/>
		
</form:form>
