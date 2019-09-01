<%--
 * dashboard.jsp
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

<!-- Listing grid -->

<security:authorize access="hasRole('ADMIN')">

	<h3>
		<spring:message code="administrator.statistics" />
	</h3>

	<table class="displayStyle">
		<tr>
			<th colspan="5"><spring:message code="administrator.statistics" /></th>
		</tr>

		<tr>
			<th><spring:message code="administrator.metrics" /></th>
			<th><spring:message code="administrator.average" /></th>
			<th><spring:message code="administrator.minimum" /></th>
			<th><spring:message code="administrator.maximum" /></th>
			<th><spring:message code="administrator.std" /></th>
		</tr>

		<tr>
			<td><spring:message code="administrator.submissionPerConference" /></td>
			<td><jstl:out value="${avgSubmissionPerConference }" /></td>
			<td><jstl:out value="${minSubmissionPerConference }" /></td>
			<td><jstl:out value="${maxSubmissionPerConference }" /></td>
			<td><jstl:out value="${stddevSubmissionPerConference }" /></td>
		</tr>

		<tr>
			<td><spring:message code="administrator.registrationPerConference" /></td>
			<td><jstl:out value="${avgRegistrationPerConference }" /></td>
			<td><jstl:out value="${minRegistrationPerConference }" /></td>
			<td><jstl:out value="${maxRegistrationPerConference }" /></td>
			<td><jstl:out value="${stddevRegistrationPerConference }" /></td>
		</tr>

		<tr>
			<td><spring:message
					code="administrator.conferenceFees" /></td>
			<td><jstl:out value="${avgConferenceFees }" /></td>
			<td><jstl:out value="${minConferenceFees }" /></td>
			<td><jstl:out value="${maxConferenceFees }" /></td>
			<td><jstl:out value="${stddevConferenceFees }" /></td>
		</tr>

		<tr>
			<td><spring:message
					code="administrator.daysPerConference" /></td>
			<td><jstl:out value="${avgDaysPerConference }" /></td>
			<td><jstl:out value="${minDaysPerConference }" /></td>
			<td><jstl:out value="${maxDaysPerConference }" /></td>
			<td><jstl:out value="${stddevDaysPerConference }" /></td>
		</tr>
	</table>


</security:authorize>
