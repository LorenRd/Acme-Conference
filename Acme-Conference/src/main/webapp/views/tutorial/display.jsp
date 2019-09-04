<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

		<b><spring:message code="activity.title" /></b>:
		<jstl:out value="${tutorial.activityTitle}"/><br/>

		<b><spring:message code="activity.speakers" /></b>:
		<br/><ul>
		<jstl:forEach items="${tutorial.speakers}" var="speaker" >
			<jstl:if test="${speaker != null}">
	        	<li><jstl:out value="${speaker}"/></li>
	        </jstl:if>
		</jstl:forEach></ul>
				
		<b><spring:message code="activity.schedule" /></b>:
		<jstl:out value="${tutorial.schedule}"/><br/>

		<b><spring:message code="activity.startDate" /></b>:
		<jstl:out value="${tutorial.startMoment}"/><br/>	

		<b><spring:message code="activity.duration" /></b>:
		<jstl:out value="${tutorial.duration}"/><br/>	
				
		<b><spring:message code="activity.room" /></b>:
		<jstl:out value="${tutorial.room}"/><br/>	

		<b><spring:message code="activity.summary" /></b>:
		<jstl:out value="${tutorial.activitySummary}"/><br/>	
		
		<b><spring:message code="activity.attachments" /></b>:
		<br/><ul>
		<jstl:forEach items="${tutorial.attachments}" var="attachment" >
			<jstl:if test="${attachment != null}">
	        	<li><jstl:out value="${attachment}"/></li>
	        </jstl:if>
		</jstl:forEach></ul>
		<!-- Sections -->
		<b><spring:message code="tutorial.sections" /></b>:
		<ul>
		<jstl:forEach items="${sections}" var="section" >
			<jstl:if test="${section != null}">
	        	<li><jstl:out value="${section.sectionTitle}"/></li>
	        	<ul>
	        		<li><jstl:out value="${section.sectionSummary}"/></li>
					<jstl:forEach items="${section.pictures}" var="picture" >
					        	<li><jstl:out value="${picture}"/></li>
					</jstl:forEach>
				</ul>
	        </jstl:if>
		</jstl:forEach></ul>
		<security:authorize access="hasRole('ADMIN')">
				<a href="section/administrator/create.do?tutorialId=${tutorial.id}"><spring:message code="section.create"/></a><br/>
		</security:authorize>

<!-- Comments -->
		
		<h3><spring:message code="tutorial.comments" /></h3>
		
<display:table name="activityComments" id="row"
	requestURI="${requestURI }" pagesize="5" class="displaytag">

	<!-- Attributes -->

	<spring:message code="activityComment.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="activityComment.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="true" />

	<spring:message code="activityComment.author" var="authorHeader" />
	<display:column property="author" title="${authorHeader}"
		sortable="true" />

	<spring:message code="activityComment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />

</display:table>

<!-- Create comment -->
<acme:button url="activityComment/create.do?activityId=${tutorial.id}"
	code="activityComment.create" />