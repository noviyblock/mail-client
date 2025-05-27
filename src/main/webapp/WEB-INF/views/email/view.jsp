<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="${email.subject}" />

<div class="email-view">
    <div class="email-header">
        <h2><c:out value="${email.subject}"/></h2>

        <div class="email-meta">
            <div><strong>From:</strong> <c:out value="${email.sender}"/></div>
            <div><strong>To:</strong> <c:out value="${email.recipient}"/></div>
            <div><strong>Date:</strong> <fmt:formatDate value="${email.sentDate}" pattern="dd MMM yyyy HH:mm"/></div>
        </div>
    </div>

    <div class="email-content">
        <pre><c:out value="${email.content}"/></pre>
    </div>

    <div class="email-actions">
        <a href="${pageContext.request.contextPath}/email/compose?replyTo=${email.id}" class="btn">Reply</a>
        <a href="${pageContext.request.contextPath}/email/delete?id=${email.id}"
           class="btn btn-delete"
           onclick="return confirm('Are you sure you want to delete this email?')">Delete</a>
        <a href="${pageContext.request.contextPath}/email/inbox" class="btn">Back to Inbox</a>
    </div>
</div>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>