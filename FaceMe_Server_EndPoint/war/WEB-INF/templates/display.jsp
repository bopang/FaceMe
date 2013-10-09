<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.demos.mediastore.MediaObject" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
  "http://www.w3.org/TR/html4/strict.dtd">
<%
  MediaObject item = (MediaObject) request.getAttribute("item");
  User user = (User) request.getAttribute("user");
  String authURL = (String) request.getAttribute("authURL");
  String displayURL = (String) request.getAttribute("displayURL");
  String rotation = (String) request.getAttribute("rotation");
  String blobkey = (String) request.getAttribute("blobkey");
%>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>MediaStore Viewer</title>
</head>
<body>
  <div align="right">
    <%
      if (user != null) {
    %>
      Hi there, <%= user.getNickname() %>
    <% } %>

    <a href="<%= authURL %>">
      <% if (user != null) { %>Log out<% } else  { %>Log in<% } %>
    </a>

  <h1 align="center">
    <%= item.getFilename() %>
  </h1>
  <hr>
  <image src="<%= displayURL %>" align="middle"/>
  <a href="/">Back</a>
</body>
</html>