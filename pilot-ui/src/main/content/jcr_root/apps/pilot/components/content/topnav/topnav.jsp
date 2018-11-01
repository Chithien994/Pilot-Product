<%--
Draws the top navigation

--%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.4.1/css/all.css' integrity='sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz' crossorigin='anonymous'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>  
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<!--confirm-->
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
<%@include file="/libs/foundation/global.jsp"%>
<%

%><%@ page
	import="java.util.Iterator, com.day.text.Text, com.day.cq.wcm.api.PageFilter, com.day.cq.wcm.api.Page, com.day.cq.commons.Doctype, org.apache.commons.lang.StringEscapeUtils"%>
<%
	// get navigation root page Page 
	Page navRootPage = currentPage.getAbsoluteParent(2);

	// check to make sure the page exists 
	if (navRootPage == null && currentPage != null) {
		navRootPage = currentPage;
	}
%>

<cq:includeClientLib categories="pilot-v1-clientlibs"/>
<title><%=StringEscapeUtils.escapeXml(currentPage.getTitle())%></title>
<div class="header">
    <div class="branding">
		<h1 id="site-name"><a href="<%=navRootPage.getPath()%>.html">Pilot Project</a></h1>
    </div>
	<div class="user-tools">
         <ul class="nav">
            <li style="padding-right:16px;"><a style="color:#fff;" href="<%=navRootPage.getPath()%>.html">Home</a></li>
            <%
                if (navRootPage != null) {
                Iterator<Page> children = navRootPage.listChildren(new PageFilter(request));
                while (children.hasNext()) {
                    Page child = children.next();
                    %>
            <li style="padding-right:16px;"><a style="color:#fff;" href="<%=child.getPath()%>.html"><%=StringEscapeUtils.escapeXml(child.getTitle())%></a></li>
                <%
                    }
                }
            %>
        </ul>
    </div>        
</div>
<div class="breadcrumbs">
    <a  style="color:#fff;" href="<%=navRootPage.getPath()%>.html">Home</a>
    <%if(navRootPage.getPath()!=currentPage.getPath()){%>
	<span class="fa fa-angle-right"></span>
    <a style="color:#fff;" href="<%=currentPage.getPath()%>.html"><%=StringEscapeUtils.escapeXml(currentPage.getTitle())%></a>
     <%}%>
</div>