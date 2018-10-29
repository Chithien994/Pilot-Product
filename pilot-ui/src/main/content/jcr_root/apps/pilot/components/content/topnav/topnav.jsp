<%--
Draws the top navigation

--%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
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
	Page navRootPage = currentPage.getAbsoluteParent(0);

	// check to make sure the page exists 
	if (navRootPage == null && currentPage != null) {
		navRootPage = currentPage;
	}
%>
<nav class="navbar navbar-default navbar-fixed-top" style="background: #1d2124; margin-bottom: 24;">
	<div class="container-fluid">
		<div class="navbar-header">
			<a style="color:#fff;" class="navbar-brand" href="<%=navRootPage.getPath()%>.html">Pilot Project</a>
		</div>
		<ul class="nav">
            <li  style="padding-right:16px;"><a style="color:#fff;" href="<%=navRootPage.getPath()%>.html">Home</a></li>
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
</nav>