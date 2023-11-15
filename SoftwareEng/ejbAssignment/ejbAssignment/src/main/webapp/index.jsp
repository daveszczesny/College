<%@ include file = "/jspf/header.jspf" %>

<c:set var='view' value='/index' scope='session' />

<%-- HTML markup starts below --%>


    <div id="welcomeText">
        <p style="font-size: larger"><fmt:message key='greeting'/></p>
        <p><fmt:message key='listofartists' /></p>
    </div>

    
     <c:forEach var="artist" items="${artists}">
         <div>
             <a href="<c:url value='artist?${artist.artistid}'/>">${artist.firstname} ${artist.surname} </a>
             <br>
             <b><fmt:message key='nationality' />: </b> ${artist.nationality} <br>
             <b><fmt:message key='yob'/>: </b>${artist.yob}<br>
             ${artist.biography}<br><br>
         </div>
    </c:forEach>

<%@ include file = "/jspf/footer.jspf" %>