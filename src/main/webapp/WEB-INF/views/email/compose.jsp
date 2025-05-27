<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<div class="container">
    <h2>Compose Email</h2>
    <form action="${pageContext.request.contextPath}/email/send" method="post">
        <div class="form-group">
            <label for="recipient">To:</label>
            <input type="email" id="recipient" name="recipient" required>
        </div>
        <div class="form-group">
            <label for="subject">Subject:</label>
            <input type="text" id="subject" name="subject">
        </div>
        <div class="form-group">
            <label for="content">Message:</label>
            <textarea id="content" name="content" required></textarea>
        </div>
        <button type="submit" class="btn">Send</button>
        <a href="${pageContext.request.contextPath}/email/inbox" class="btn">Cancel</a>
    </form>
</div>
<%@ include file="/WEB-INF/views/shared/footer.jsp" %>