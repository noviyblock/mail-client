<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<c:set var="pageTitle" value="Error" />

<div class="error-container">
  <h2>Error</h2>

  <div class="alert error">
    <c:out value="${error}"/>
  </div>

  <a href="${pageContext.request.contextPath}/email/inbox" class="btn">Go to Inbox</a>
</div>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>