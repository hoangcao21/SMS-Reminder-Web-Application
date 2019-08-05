<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>SMS Reminder - Your truly assistant</title>

        <!-- Main CSS -->
        <link href="${pageContext.request.contextPath}/css/main.css?v=4" rel="stylesheet">
        <!-- Boostrap CSS 4.3.1 -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- Font Awesome CSS 4.7.0 -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- Animate.css CSS 3.7.2 -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.2/animate.min.css">

        <!-- jQuery 3.4.1 -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
        <!-- Popper.js -->
        <script src="https://getbootstrap.com/docs/4.1/assets/js/vendor/popper.min.js" integrity="sha256-98vAGjEDGN79TjHkYWVD4s87rvWkdWLHPs5MC3FvFX4=" crossorigin="anonymous"></script>
        <!-- Boostrap js 4.3.1 -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha256-CjSoeELFOcH0/uxWu6mC/Vlrc1AARqbm/jiiImDGV3s=" crossorigin="anonymous"></script>
        <!-- Bootbox.js 5.2.0 -->
        <script src="${pageContext.request.contextPath}/javascript/bootbox.min.js" type="text/javascript"></script>
        <!-- Custom-made js -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/load-pages.js?v=23"></script>
    </head>

    <body>
        <c:if test="${not empty param.pageName}">
            <script type="text/javascript">
                <c:if test="${param.pageName == 'Edit'}">
                window.onload = loadEditPage('${param.remId}');
                </c:if>

                <c:if test="${param.pageName == 'Saved'}">
                window.onload = loadSavedPage();
                </c:if>
            </script>
        </c:if>

        <c:if test="${empty param.pageName}">
            <script type="text/javascript">
                window.onload = loadMainPage();
            </script>
        </c:if>
    </body>

</html>
