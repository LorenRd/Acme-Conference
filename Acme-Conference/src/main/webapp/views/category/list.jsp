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

<!-- Listing grid -->
<display:table name="categories" id="row"
	requestURI="category/administrator/list.do" pagesize="5"
	class="displaytag">
<!-- Action links -->

	<display:column>
	<jstl:if test="${cookie['language'].getValue()=='en'}">
	 <jstl:if test="${row.englishName != 'CONFERENCE'}">

		<a href="category/administrator/edit.do?categoryId=${row.id}"> <spring:message code="category.edit" /></a>
			</jstl:if>
		
	</jstl:if>
	<jstl:if test="${cookie['language'].getValue()=='es'}">
	<jstl:if test="${row.spanishName != 'CONFERENCIA'}">
		<a href="category/administrator/edit.do?categoryId=${row.id}"> <spring:message code="category.edit" /></a>
	</jstl:if>
	</jstl:if>
	</display:column>


	<!-- Attributes -->

	<spring:message code="category.name" var="nameHeader" />
	<jstl:if test="${cookie['language'].getValue()=='en'}">
	<display:column property="englishName" title="${nameHeader}" sortable="true" />
	</jstl:if>
	<jstl:if test="${cookie['language'].getValue()=='es'}">
	<display:column property="spanishName" title="${nameHeader}" sortable="true" />
	</jstl:if>
</display:table>
<div>
	<a href="category/administrator/create.do"> <spring:message
			code="category.create" />
	</a>
</div>




