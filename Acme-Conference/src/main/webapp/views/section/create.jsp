
 <%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="section/administrator/create.do?tutorialId=${param['tutorialId']}" modelAttribute="section">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		
		<acme:textbox code="section.sectionTitle" path="sectionTitle"/>
		<br />
		<acme:textbox code="section.sectionSummary" path="sectionSummary"/>
		<br />		
		<acme:textarea code="section.pictures" path="pictures"/>
		<br />		
		<br />
		<acme:submit name="save" code="section.save"/>

		
		<acme:cancel url="welcome/index.do" code="section.cancel"/>
		
</form:form>
