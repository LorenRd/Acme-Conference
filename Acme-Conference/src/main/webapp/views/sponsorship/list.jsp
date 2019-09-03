<%--
 * 
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<!-- Listing grid -->

<display:table name="sponsorships" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<!-- Action links -->
	<display:column>
		<a href="sponsorship/sponsor/edit.do?sponsorshipId=${row.id}"> <spring:message
				code="sponsorship.edit" />
		</a>
	</display:column>

	<!-- Attributes -->

	<spring:message code="sponsorship.banner" var="bannerHeader" />
	<display:column title="${bannerHeader}" sortable="true">
		<a href="${row.banner}" target="_blank"> ${row.banner} </a>
	</display:column>

	<spring:message code="sponsorship.targetURL" var="targetURLHeader" />
	<display:column title="${targetURLHeader}" sortable="true">
		<a href="${row.targetURL}" target="_blank"> ${row.targetURL} </a>
	</display:column>

	<spring:message code="sponsorship.conference" var="conferenceHeader" />

	<display:column title="${conferenceHeader}">

		<div>
			<jstl:out value="${row.conference.title}" />
			&nbsp; <a href="conference/display.do?conferenceId=${row.conference.id}">
				${showConference} <spring:message code="sponsorship.conference.display" /></a>
		</div>

	</display:column>

</display:table>

<acme:button url="sponsorship/sponsor/create.do" code="sponsorship.create"/>