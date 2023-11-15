<%-- 
    Document   : artistArtwork
    Created on : 23 Oct 2023, 11:58:52
    Author     : dawid
--%>


<%@ include file = "/jspf/header.jspf" %>
<c:set var='view' value='/artistArtworks' scope='session' />

<h2><fmt:message key='artworksby' /> ${selectedArtist.firstname} ${selectedArtist.surname} </h2>

    <c:forEach var="artwork" items="${artistArtworks}" varStatus="iter">
        <img src="${initParam.artworkImagePath}${artwork.imagename}.jpg"
             alt="No cover image found" width="250" height="300">
        <br><br>
        <h1><fmt:message key="${artwork.title}"/></h1>
    </c:forEach>

<%@ include file = "/jspf/footer.jspf" %>



