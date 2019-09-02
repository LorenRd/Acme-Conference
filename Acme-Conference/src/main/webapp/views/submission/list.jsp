<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<!-- Grouped list -->
<security:authorize access="hasRole('ADMIN')">

	<form action="submission/administrator/list.do" method="get">

		<input type="radio" name="submissionStatus" value="0" checked>
		<spring:message code="submission.status.all" />
		<input type="radio" name="submissionStatus" value="1">
		<spring:message code="submission.status.accepted" />
		<input type="radio" name="submissionStatus" value="2">
		<spring:message code="submission.status.review" />
		<input type="radio" name="submissionStatus" value="3">
		<spring:message code="submission.status.rejected" />
		<br />
		<spring:message code="submission.status.choose" var="choose" />
		<input type="submit" value="${choose}">
	</form>

</security:authorize>
<br/><br/>

<!-- Listing grid -->

<display:table name="submissions" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<security:authorize access="hasRole('AUTHOR')">
			<a href="submission/author/display.do?submissionId=${row.id}"><spring:message code="submission.display"/></a>
		</security:authorize>
		<security:authorize access="hasRole('ADMIN')">
			<a href="submission/administrator/display.do?submissionId=${row.id}"><spring:message code="submission.display"/></a>
		</security:authorize>
	</display:column>

	<!-- Attributes -->
	<spring:message code="submission.conference" var="conferenceTitleHeader" />
	<display:column property="conference.title" title="${conferenceTitleHeader}"
		sortable="true" />
		
	<spring:message code="submission.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"
		sortable="true" />	
		
	<spring:message code="submission.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="true" />
		
	<spring:message code="submission.status" var="statusHeader" />
	<display:column property="status" title="${statusHeader}"
		sortable="true" />
		
</display:table>


<security:authorize access="hasRole('AUTHOR')">
	<acme:button url="submission/author/create.do" code="submission.create"/>
</security:authorize> 
<security:authorize access="hasRole('ADMIN')">
	<input type="button" name="assignReviewers" value="<spring:message code="administrator.assignReviewers" />" onclick="redirect: location.href = 'submission/administrator/list.do?assignReviewers';" />	
</security:authorize> 
