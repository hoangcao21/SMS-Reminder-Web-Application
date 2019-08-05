<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="main-box main-box--centered d-flex flex-column animated fadeInRight">
    <!-- Navigation bar -->
    <%@include file="../includes/nav-bar.jsp" %>

    <!-- Main content -->
    <div class="d-flex flex-column form-group ${empty listReminders? "center-error" : ""}">
        <c:if test="${not empty listReminders}">
            <c:forEach var="re" items="${listReminders}">
                <!-- SMS Reminder box -->
                <div class="d-flex flex-column pd-left pd-right sms-reminder-box">
                    <div class="d-flex flex-row reminder-title">
                        <div class="reminder-happen-box">
                            <h6>Reminder happens at
                            </h6>
                            <p>
                                ${re.date}
                            </p>
                        </div>

                        <div>
                            <a href="${pageContext.request.contextPath}/saved?actionToTake=Update&remId=${re.id}" class="btn btn-info">
                                Edit
                            </a>
                        </div>

                        <div class="btn-delete--mr-left">
                            <a onclick='confirmToDelete("${re.id}")' href='#' class="btn btn-danger">
                                Delete
                            </a>
                        </div>
                    </div>

                    <div>
                        <textarea name="shortDes" class="form-control textarea--no-resize" rows="2" maxlength="100"
                                  readonly>${re.shortDes}</textarea>
                    </div>
                </div>
            </c:forEach>
        </c:if>

        <c:if test="${empty listReminders}">

            <div class="pd-left pd-right note">
                <h3>Currently, you don't have any reminders.</h3>
            </div>
        </c:if>
    </div>
</div>
