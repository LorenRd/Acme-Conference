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

		<b><spring:message code="panel.activityTitle" /></b>:
		<jstl:out value="${panel.activityTitle}"/><br/>
	
		<b><spring:message code="panel.speakers" /></b>:
		<br/>
		<ul>
		<jstl:forEach items="${panel.speakers}" var="speaker" >
			<jstl:if test="${speaker != null}">
	        	<li><jstl:out value="${speaker}"/></li>
	        </jstl:if>
		</jstl:forEach>
		</ul>
	
		<b><spring:message code="panel.schedule" /></b>:
		<jstl:out value="${panel.schedule }"/><br/>
	
		<b><spring:message code="panel.startMoment" /></b>:
		<jstl:out value="${panel.startMoment }"/><br/>
		
		<b><spring:message code="panel.duration" /></b>:
		<jstl:out value="${panel.duration }"/><br/>
		
		<b><spring:message code="panel.room" /></b>:
		<jstl:out value="${panel.room }"/><br/>
		
		<b><spring:message code="panel.activitySummary" /></b>:
		<jstl:out value="${panel.activitySummary }"/><br/>
		
		<b><spring:message code="panel.attachments" /></b>:
		<br/>
		<ul>
		<jstl:forEach items="${panel.attachments}" var="attachment" >
			<jstl:if test="${attachment != null}">
	        	<li><jstl:out value="${attachment}"/></li>
	        </jstl:if>
		</jstl:forEach>
		</ul>
		
		<!-- Comments -->
		
		<h3><spring:message code="panel.comments" /></h3>
		
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
<acme:button url="activityComment/create.do?activityId=${panel.id}"
	code="activityComment.create" />
