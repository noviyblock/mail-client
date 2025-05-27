<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<div class="container">
    <div class="email-view">
        <h2>${email.subject}</h2>
        <div class="email-meta">
            <p><strong>From:</strong> ${email.sender}</p>
            <p><strong>To:</strong> ${email.recipient}</p>
            <p><strong>Date:</strong> ${email.sentDate}</p>
        </div>
        <div class="email-content">
            ${email.content}
        </div>
        <div class="email-actions">
            <a href="${pageContext.request.contextPath}/email/compose?reply=${email.id}" class="btn">Reply</a>
            <form action="${pageContext.request.contextPath}/email/delete" method="post" style="display: inline;">
                <input type="hidden" name="id" value="${email.id}">
                <button type="submit" class="btn" style="background-color: #e74c3c;">Delete</button>
            </form>
            <a href="${pageContext.request.contextPath}/email/inbox" class="btn">Back to Inbox</a>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/shared/footer.jsp" %>