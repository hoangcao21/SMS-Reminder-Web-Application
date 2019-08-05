<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="navig-bar navig-bar--justify-between pd-left pd-right d-flex flex-row">
    <div>
        <h2 class="navig-bar__centered-child">${sessionScope.pageName == 'Main'? 'SMS Reminder' : (sessionScope.pageName == 'Edit'? 'Edit Reminder' : 'Saved Reminder')}</h2>
    </div>

    <div>
        <a href="#" class="btn btn-success navig-bar__centered-child" onclick="${sessionScope.pageName == 'Main'? 'loadSavedPage()' : (sessionScope.pageName == 'Edit'? 'loadSavedPage()' : 'loadMainPage()')}">
            <i class="fa fa-bookmark"></i><strong>${sessionScope.pageName == 'Main'? 'Saved' : (sessionScope.pageName == 'Edit'? 'Saved' : 'Close')}</strong>
        </a>
    </div>
</div>
