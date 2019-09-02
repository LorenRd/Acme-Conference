<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- Listing grid -->

<display:table name="conferenceComments" id="row"
	requestURI="${requestURI }" pagesize="5" class="displaytag">

	<!-- Attributes -->

	<spring:message code="conferenceComment.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="conferenceComment.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="true" />

	<spring:message code="conferenceComment.author" var="authorHeader" />
	<display:column property="author" title="${authorHeader}"
		sortable="true" />

	<spring:message code="conferenceComment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />

</display:table>

<!-- Create comment -->
<acme:button url="conferenceComment/create.do"
	code="conferenceComment.create" />

