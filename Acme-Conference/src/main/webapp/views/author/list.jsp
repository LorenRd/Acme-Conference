<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="authors" requestURI="${requestURI}" id="row">
	
	<display:column>
		<a href="author/display.do?authorId=${row.id}"><spring:message code="author.display"/></a>
	</display:column>
	
	<spring:message code="author.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false"/>
	
	<spring:message code="author.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="false"/>
	
	<spring:message code="author.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="false"/>
	
	<spring:message code="author.score" var="scoreHeader" />
	<display:column title="${scoreHeader}" sortable="false">
		<jstl:if test="${row.score != null }">
			<jstl:out value="${row.score }"/>
		</jstl:if>
	</display:column>
</display:table>

<input type="button" value="<spring:message code="author.compute"/>"
onclick="javascript:window.location.replace('administrator/score.do');">