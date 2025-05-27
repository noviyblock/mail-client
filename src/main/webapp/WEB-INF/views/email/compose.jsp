<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<c:set var="pageTitle" value="Compose Email" />

<h2>Compose Email</h2>

<c:if test="${not empty error}">
    <div class="alert error">${error}</div>
</c:if>

<c:if test="${not empty success}">
    <div class="alert success">${success}</div>
</c:if>

<form action="${pageContext.request.contextPath}/email/send" method="post" class="email-form">
    <div class="form-group">
        <label for="recipient">To:</label>
        <input type="email" id="recipient" name="recipient"
               value="${email.recipient}" required>
    </div>

    <div class="form-group">
        <label for="subject">Subject:</label>
        <input type="text" id="subject" name="subject"
               value="${email.subject}" required>
    </div>

    <div class="form-group">
        <label for="content">Message:</label>
        <textarea id="content" name="content" rows="10" required><c:out value="${email.content}"/></textarea>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn">Send</button>
        <a href="${pageContext.request.contextPath}/email/inbox" class="btn btn-cancel">Cancel</a>
    </div>
</form>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>