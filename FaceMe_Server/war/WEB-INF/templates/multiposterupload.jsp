<%@ page import="com.google.appengine.api.users.User" %>
<%
  User user = (User) request.getAttribute("user");
  String authURL = (String) request.getAttribute("authURL");
  String uploadURL = (String) request.getAttribute("uploadURL");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
  "http://www.w3.org/TR/html4/strict.dtd">

<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Multi Poster Upload</title>
</head>
<body>
  <div align="right">
    Hi there, <%= user.getNickname() %> | <a href="<%= authURL %>">Log out</a>
  </div>

  <form action="<%= uploadURL %>" method="POST" enctype="multipart/form-data">
    Poster Name: <input type="text" size="40" name="postername"><br>
    Movie: <input type="text" size="40" name="moviename"><br>
    Classification: <input type="text" size="40" name="classification"><br>
    Description:<br><textarea cols="20" rows="4" name="description"></textarea><br>
    Upload Origin Poster: <input type="file" name="origin"><br>
    Upload thumbnail: <input type="file" name="thumbnail"><br>
    Upload nonface poster: <input type="file" name="nonface"><br>
    ============================================<br>
    Num of Characters:<select name="numoffaces" id="1">
	  <option value="1">1</option>
	  <option value="2">2</option>
	  <option value="3">3</option>
	  <option value="4">4</option>
	</select><br>
	============================================
	<div>
	Upload Character1 Face: <input type="file" name="f1"><br>
	Name: <input type="text" size="40" name="faceName1"><br>
	PositionX: <input type="text" size="40" name="positionX1"><br>
	PositionY: <input type="text" size="40" name="positionY1"><br>
	Width: <input type="text" size="40" name="width1"><br>
	Height: <input type="text" size="40" name="height1"><br>
	Index: <input type="text" size="40" name="index1"><br>
	</div>
	============================================
	<div>
	Upload Character2 Face: <input type="file" name="f2"><br>
	Name: <input type="text" size="40" name="faceName2"><br>
	PositionX: <input type="text" size="40" name="positionX2"><br>
	PositionY: <input type="text" size="40" name="positionY2"><br>
	Width: <input type="text" size="40" name="width2"><br>
	Height: <input type="text" size="40" name="height2"><br>
	Index: <input type="text" size="40" name="index2"><br>
	</div>
	============================================
	<div>
	Upload Character3 Face: <input type="file" name="f3"><br>
	Name: <input type="text" size="40" name="faceName3"><br>
	PositionX: <input type="text" size="40" name="positionX3"><br>
	PositionY: <input type="text" size="40" name="positionY3"><br>
	Width: <input type="text" size="40" name="width3"><br>
	Height: <input type="text" size="40" name="height3"><br>
	Index: <input type="text" size="40" name="index3"><br>
	</div>
	============================================
	<div>
	Upload Character4 Face: <input type="file" name="f4"><br>
	Name: <input type="text" size="40" name="faceName4"><br>
	PositionX: <input type="text" size="40" name="positionX4"><br>
	PositionY: <input type="text" size="40" name="positionY4"><br>
	Width: <input type="text" size="40" name="width4"><br>
	Height: <input type="text" size="40" name="height4"><br>
	Index: <input type="text" size="40" name="index4"><br>
    </div>
    <input type="submit" name="submit" value="Submit">
  </form>

  <hr>
  <a href="/">Cancel</a>
</body>
</html>