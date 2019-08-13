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

<display:table name="cameraReadyPapers" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<a href="cameraReadyPaper/author/display.do?cameraReadyPaperId=${row.id}"><spring:message code="cameraReadyPaper.display"/></a>
	</display:column>

	<!-- Attributes -->
	<spring:message code="cameraReadyPaper.submission" var="submissionTickerHeader" />
	<display:column property="submission.ticker" title="${submissionTickerHeader}"
		sortable="true" />
		
	<spring:message code="cameraReadyPaper.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />	
		
	<spring:message code="cameraReadyPaper.author" var="authorHeader" />
	<display:column property="author" title="${authorHeader}"
		sortable="true" />
		
</display:table>

<acme:button url="cameraReadyPaper/author/create.do" code="cameraReadyPaper.create"/>