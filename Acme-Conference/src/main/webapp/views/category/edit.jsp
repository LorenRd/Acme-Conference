<%--
 * 
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="category/administrator/edit.do"
	modelAttribute="category">

	<form:hidden path="id" />

	<form:hidden path="version" />

	<jstl:if test="${cookie['language'].getValue()=='en'}">
	<form:label path="parentCategory">
		<spring:message code="category.parents" />:
		</form:label>
		<form:select multiple="true" path="parentCategory" >
			<form:options items="${allPossibleParents}" itemValue="id" itemLabel="englishName" />
		</form:select>
		<form:errors cssClass="error" path="parentCategory" />
	</jstl:if>
	
	<jstl:if test="${cookie['language'].getValue()=='es'}">
	<form:label path="parentCategory">
		<spring:message code="category.parents" />:
		</form:label>
		<form:select multiple="true" path="parentCategory" >
			<form:options items="${allPossibleParents}" itemValue="id" itemLabel="spanishName" />
		</form:select>
		<form:errors cssClass="error" path="parentCategory" />
	</jstl:if>

	
	<br />
	<br />
	<form:label path="englishName">
		<spring:message code="category.nameEnglish" />:
	</form:label>
	<form:input path="englishName" />
	<form:errors cssClass="error" path="englishName" />
	
	<br />
	
	<form:label path="spanishName">
		<spring:message code="category.nameSpanish" />:
	</form:label>
	<form:input path="spanishName" />
	<form:errors cssClass="error" path="spanishName" />
	<br />
	<br />

	<spring:message code="category.save" var="saveCategory" />
	<spring:message code="category.delete" var="deleteCategory" />
	<spring:message code="category.confirm.delete"
		var="confirmDeleteCategory" />
	<spring:message code="category.cancel" var="cancelCategory" />

	<input type="submit" id="save" name="save" value="${saveCategory}" />

	<jstl:if test="${category.id != 0}">
		<input type="submit" name="delete" value="${deleteCategory}"
			onclick="return confirm('${confirmDeleteCategory}')" />
	</jstl:if>
	<input type="button" name="cancel" value="${cancelCategory}"
		onclick="javascript: relativeRedir('category/administrator/list.do');" />
	<br />


</form:form>











