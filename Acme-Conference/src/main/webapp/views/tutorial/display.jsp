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

		<b><spring:message code="tutorial.title" /></b>:
		<jstl:out value="${tutorial.title}"/><br/>
	
		<b><spring:message code="tutorial.speakers" /></b>:
		<br/>
		<ul>
		<jstl:forEach items="${tutorial.speakers}" var="speaker" >
			<jstl:if test="${speaker != null}">
	        	<li><jstl:out value="${speaker}"/></li>
	        </jstl:if>
		</jstl:forEach>
		</ul>
		<br/>
	
		<b><spring:message code="tutorial.schedule" /></b>:
		<jstl:out value="${tutorial.schedule }"/><br/>
	
		<b><spring:message code="tutorial.startMoment" /></b>:
		<jstl:out value="${tutorial.startMoment }"/><br/>
		
		<b><spring:message code="tutorial.duration" /></b>:
		<jstl:out value="${tutorial.duration }"/><br/>
		
		<b><spring:message code="tutorial.room" /></b>:
		<jstl:out value="${tutorial.room }"/><br/>
		
		<b><spring:message code="tutorial.summary" /></b>:
		<jstl:out value="${tutorial.summary }"/><br/>
		
		<b><spring:message code="tutorial.attachments" /></b>:
		<br/>
		<ul>
		<jstl:forEach items="${tutorial.attachments}" var="attachment" >
			<jstl:if test="${attachment != null}">
	        	<li><jstl:out value="${attachment}"/></li>
	        </jstl:if>
		</jstl:forEach>
		</ul>
		<br/>
		
		<!-- Sections -->
		
		<h3><spring:message code="tutorial.sections" /></h3>
		
<display:table name="sections" id="row"
	requestURI="${requestURI }" pagesize="5" class="displaytag">

	<!-- Attributes -->

	<spring:message code="section.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="section.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}"
		sortable="true" />

	<spring:message code="section.pictures" var="picturesHeader" />
	<display:column property="pictures" title="${picturesHeader}"
		sortable="true" />

</display:table>
		
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
<acme:button url="activityComment/create.do"
	code="activityComment.create" />
