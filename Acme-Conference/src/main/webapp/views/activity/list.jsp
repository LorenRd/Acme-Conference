<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<h3>${conference.title}</h3>


<h3><spring:message code="conference.tutorials" /></h3>
<!-- Listing grid -->

<display:table name="tutorials" id="row" requestURI="activity/administrator/list.do "
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<a href="activity/tutorial/display.do?tutorialId=${row.id}"><spring:message code="conference.display"/></a>
	</display:column>

	<!-- Attributes -->

	<spring:message code="activity.title" var="titleHeader" />
	<display:column property="activityTitle" title="${titleHeader}"
		sortable="true" />
		
	<spring:message code="activity.startDate" var="startDateHeader" />
	<display:column property="startMoment" title="${startDate}"
		sortable="true" />
		
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
		<a href="activity/administrator/tutorial/edit.do?tutorialId=${row.id}"><spring:message code="conference.edit"/></a>
		</display:column>
		<display:column>
		<a href="activity/administrator/tutorial/delete.do?tutorialId=${row.id}"><spring:message code="conference.delete"/></a>
		</display:column>
	</security:authorize>
</display:table>
<security:authorize access="hasRole('ADMIN')">
		<acme:button url="activity/administrator/tutorial/create.do?conferenceId=${conference.id}" code="activity.create"/>
</security:authorize> 
<h3><spring:message code="conference.panels" /></h3>
<!-- Listing grid -->

<display:table name="panels" id="row" requestURI="activity/administrator/list.do "
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<a href="activity/panel/display.do?panelId=${row.id}"><spring:message code="conference.display"/></a>
	</display:column>

	<!-- Attributes -->

	<spring:message code="activity.title" var="titleHeader" />
	<display:column property="activityTitle" title="${titleHeader}"
		sortable="true" />
		
	<spring:message code="activity.startDate" var="startDateHeader" />
	<display:column property="startMoment" title="${startDate}"
		sortable="true" />
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
		<a href="activity/administrator/panel/edit.do?panelId=${row.id}"><spring:message code="conference.edit"/></a>
		</display:column>
		<display:column>
		<a href="activity/administrator/panel/delete.do?panelId=${row.id}"><spring:message code="conference.delete"/></a>
		</display:column>
	</security:authorize>
</display:table>
<security:authorize access="hasRole('ADMIN')">
		<acme:button url="activity/administrator/panel/create.do?conferenceId=${conference.id}" code="activity.create"/>
</security:authorize> 
<h3><spring:message code="conference.presentations" /></h3>
<!-- Listing grid -->

<display:table name="presentations" id="row" requestURI="activity/administrator/list.do "
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<a href="activity/presentation/display.do?presentationId=${row.id}"><spring:message code="conference.display"/></a>
	</display:column>

	<!-- Attributes -->

	<spring:message code="activity.title" var="titleHeader" />
	<display:column property="activityTitle" title="${titleHeader}"
		sortable="true" />
		
	<spring:message code="activity.startDate" var="startDateHeader" />
	<display:column property="startMoment" title="${startDate}"
		sortable="true" />
		
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
		<a href="activity/administrator/presentation/edit.do?presentationId=${row.id}"><spring:message code="conference.edit"/></a>
		</display:column>
		<display:column>
		<a href="activity/administrator/presentation/delete.do?presentationId=${row.id}"><spring:message code="conference.delete"/></a>
		</display:column>
	</security:authorize>
</display:table>
<security:authorize access="hasRole('ADMIN')">
		<acme:button url="activity/administrator/presentation/create.do?conferenceId=${conference.id}" code="activity.create"/>
</security:authorize> 