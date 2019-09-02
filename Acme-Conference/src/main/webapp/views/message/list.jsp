<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ADMIN')">
<a href="message/administrator/createBySubmission.do"><spring:message code="message.bySubmission" /></a>
<br/>
<a href="message/administrator/createByRegistered.do"><spring:message code="message.byRegistered" /></a>
<br/>
<a href="message/administrator/createBySystem.do"><spring:message code="message.bySystem" /></a>
<br/>
<a href="message/administrator/createByAll.do"><spring:message code="message.byAll" /></a>
<br/>
<br/>
</security:authorize>

<spring:message code="message.groupByTopics" />
<br/>

<display:table name="messages" id="row" pagesize="5" requestURI="${requestURI}" 
class="displaytag" keepStatus="true">
	
	<!-- Moment -->
	<spring:message code="message.moment" var="momentHeader" />
	<display:column  property="moment" title="${momentHeader}" />
	
	<!-- Subject -->
	<spring:message code="message.subject" var="subjectHeader" />
	<display:column  property="subject" title="${subjectHeader}" />
	
	<!-- Body -->
	<spring:message code="message.body" var="bodyHeader" />
	<display:column  property="body" title="${bodyHeader}" />
	
	<!-- Topic -->
	<spring:message code="message.topic" var="topicHeader" />
	<display:column  property="topic" title="${topicHeader}" sortable="true" />
	
	<display:column>
		<acme:button url="message/actor/delete.do?messageId=${row.id}" code="message.delete"/>
	</display:column>
	
</display:table>

<br/>

<spring:message code="message.bySenders" />
<br/>

<display:table name="messagesBySender" id="row" pagesize="5" requestURI="${requestURI}" 
class="displaytag" keepStatus="true">
	
	<!-- Moment -->
	<spring:message code="message.moment" var="momentHeader" />
	<display:column  property="moment" title="${momentHeader}" />
	
	<!-- Subject -->
	<spring:message code="message.subject" var="subjectHeader" />
	<display:column  property="subject" title="${subjectHeader}" />
	
	<!-- Body -->
	<spring:message code="message.body" var="bodyHeader" />
	<display:column  property="body" title="${bodyHeader}" />
	
	<!-- Topic -->
	<spring:message code="message.topic" var="topicHeader" />
	<display:column  property="topic" title="${topicHeader}" />
	
	<display:column>
		<acme:button url="message/actor/delete.do?messageId=${row.id}" code="message.delete"/>
	</display:column>
	
</display:table>

<br/>

<spring:message code="message.byRecipients" />
<br/>

<display:table name="messagesByRecipient" id="row" pagesize="5" requestURI="${requestURI}" 
class="displaytag" keepStatus="true">
	
	<!-- Moment -->
	<spring:message code="message.moment" var="momentHeader" />
	<display:column  property="moment" title="${momentHeader}" />
	
	<!-- Subject -->
	<spring:message code="message.subject" var="subjectHeader" />
	<display:column  property="subject" title="${subjectHeader}" />
	
	<!-- Body -->
	<spring:message code="message.body" var="bodyHeader" />
	<display:column  property="body" title="${bodyHeader}" />
	
	<!-- Topic -->
	<spring:message code="message.topic" var="topicHeader" />
	<display:column  property="topic" title="${topicHeader}" />
	
	<display:column>
		<acme:button url="message/actor/delete.do?messageId=${row.id}" code="message.delete"/>
	</display:column>
	
</display:table>

<br/>
<br/>

<acme:button url="message/actor/create.do" code="message.create"/>