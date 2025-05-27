<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Inbox" />

<h2>Inbox</h2>

<c:if test="${not empty success}">
    <div class="alert success">${success}</div>
</c:if>

<div class="action-bar">
    <a href="${pageContext.request.contextPath}/email/compose" class="btn">Compose</a>
</div>

<c:choose>
    <c:when test="${not empty emails}">
        <table class="email-table">
            <thead>
            <tr>
                <th>From</th>
                <th>Subject</th>
                <th>Date</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${emails}" var="email">
                <tr class="${email.read ? '' : 'unread'}">
                    <td><c:out value="${email.sender}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/email/view?id=${email.id}">
                            <c:out value="${email.subject}"/>
                        </a>
                    </td>
                    <td><fmt:formatDate value="${email.sentDate}" pattern="dd MMM yyyy HH:mm"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/email/delete?id=${email.id}"
                           class="btn-delete"
                           onclick="return confirm('Are you sure you want to delete this email?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p class="no-items">No emails in your inbox</p>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>