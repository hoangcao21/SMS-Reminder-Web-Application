<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="main-box main-box--centered d-flex flex-column animated fadeInLeft">
    <!-- Navigation bar -->
    <%@include file="../includes/nav-bar.jsp" %>

    <!-- Main content -->
    <div class="d-flex flex-column form-group">
        <form method="POST" action="${pageContext.request.contextPath}/edit">
            <!-- Reminder ID -->
            <input type="hidden" name="remId" value="${param.remId}" />

            <!-- Short Description -->
            <div class="d-flex flex-column pd-left pd-right content-box">
                <div>
                    <h6>Short description:</h6>
                </div>

                <div>
                    <textarea name="shortDes" class="form-control textarea--no-resize" rows="6" maxlength="100"
                              required>${rem.shortDes}</textarea>
                </div>
            </div>

            <!-- Date -->
            <div class="d-flex flex-column pd-left pd-right content-box">
                <div>
                    <h6>Date:</h6>
                </div>

                <div>
                    <input name="dateAndTime" type="datetime-local" class="form-control" min="${currentDate}"
                           value="${rem.date}" required>
                </div>
            </div>

            <!-- Add button -->
            <div class="d-flex flex-column pd-left pd-right content-box">
                <input type="submit" class="btn btn-info btn-block" value="Update">
            </div>
        </form>

        <!-- Confirmation message -->
        <div class="d-flex flex-column pd-left pd-right content-box">
            <p>${responseMessage}</p>
        </div>
    </div>
</div>

