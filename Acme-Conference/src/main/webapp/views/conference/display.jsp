<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<b><spring:message code="conference.title" /></b>
:
<jstl:out value="${conference.title}" />
<br />
		<jstl:if test="${cookie['language'].getValue()=='en'}">
		<b><spring:message code="conference.category" /></b>:
		<jstl:out value="${conference.category.englishName}"> </jstl:out>
		</jstl:if>
		<jstl:if test="${cookie['language'].getValue()=='es'}">
		<b><spring:message code="conference.category" /></b>:
		<jstl:out value="${conference.category.spanishName}"> </jstl:out>
		</jstl:if>
		<br/>

<b><spring:message code="conference.acronym" /></b>
:
<jstl:out value="${conference.acronym }" />
<br />

<b><spring:message code="conference.venue" /></b>
:
<jstl:out value="${conference.venue }" />
<br />

<b><spring:message code="conference.submissionDeadline" /></b>
:
<jstl:out value="${conference.submissionDeadline }" />
<br />

<b><spring:message code="conference.notificationDeadline" /></b>
:
<jstl:out value="${conference.notificationDeadline }" />
<br />

<b><spring:message code="conference.cameraReadyDeadline" /></b>
:
<jstl:out value="${conference.cameraReadyDeadline }" />
<br />

<b><spring:message code="conference.startDate" /></b>
:
<jstl:out value="${conference.startDate }" />
<br />

<b><spring:message code="conference.endDate" /></b>
:
<jstl:out value="${conference.endDate }" />
<br />

<b><spring:message code="conference.summary" /></b>
:
<jstl:out value="${conference.summary }" />
<br />

<b><spring:message code="conference.fee" /></b>
:
<jstl:out value="${conference.fee }" />
<br />

<jstl:if
	test="${conference.administrator.userAccount.username == pageContext.request.userPrincipal.name}">
	<b><spring:message code="conference.isFinal" /></b>:
		<jstl:out value="${conference.isFinal }" />
	<br />
</jstl:if>

<!-- Administrator -->
<b><spring:message code="conference.administrator" /></b>
:
<a
	href="administrator/display.do?administratorId=${conference.administrator.id}">
	<jstl:out value="${conference.administrator.userAccount.username}" />
</a>
<br />

<h3>
	<spring:message code="conference.activities" />
</h3>

<h3>
	<spring:message code="conference.tutorials" />
</h3>

<!-- Tutorials -->

<display:table name="tutorials" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">

	<!-- Display -->
	<display:column>
		<a href="tutorial/display.do?tutorialId=${row.id}"><spring:message
				code="tutorial.display" /></a>
	</display:column>

	<!-- Attributes -->

	<spring:message code="tutorial.activityTitle" var="activityTitleHeader" />
	<display:column property="activityTitle" title="${activityTitleHeader}" sortable="true" />

	<spring:message code="tutorial.startMoment" var="startMomentHeader" />
	<display:column property="startMoment" title="${startMomentHeader}"
		sortable="true" />

</display:table>

<h3>
	<spring:message code="conference.panels" />
</h3>

<!-- Panels -->

<display:table name="panels" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">

	<!-- Display -->
	<display:column>
		<a href="panel/display.do?panelId=${row.id}"><spring:message
				code="panel.display" /></a>
	</display:column>

	<!-- Attributes -->

	<spring:message code="panel.activityTitle" var="activityTitleHeader" />
	<display:column property="activityTitle" title="${activityTitleHeader}" sortable="true" />

	<spring:message code="panel.startMoment" var="startMomentHeader" />
	<display:column property="startMoment" title="${startMomentHeader}"
		sortable="true" />	

</display:table>

<h3>
	<spring:message code="conference.presentations" />
</h3>

<!-- Presentations -->

<display:table name="presentations" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">

	<!-- Display -->
	<display:column>
		<a href="presentation/display.do?presentationId=${row.id}"><spring:message
				code="presentation.display" /></a>
	</display:column>

	<!-- Attributes -->

	<spring:message code="presentation.activityTitle" var="activityTitleHeader" />
	<display:column property="activityTitle" title="${activityTitleHeader}" sortable="true" />

	<spring:message code="presentation.startMoment" var="startMomentHeader" />
	<display:column property="startMoment" title="${startMomentHeader}"
		sortable="true" />

</display:table>

<!-- Comments -->

<h3>
	<spring:message code="conference.comments" />
</h3>

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
