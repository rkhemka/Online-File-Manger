<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Register New Users</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
		integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
		crossorigin="anonymous">

		<!-- Latest compiled and minified JavaScript -->
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
			integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
			crossorigin="anonymous"></script>


		<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

		<script>
			$(function() {

				$(".signup").click(function() {
					var row = $(this).closest("tr");

					var a = $("#name1").val();
					var b = $("#name2").val();

					$.ajax({
						url : "CheckUserNameUsingAjax",
						type : "GET",
						data : {
							"name" : a,
							"pass" : b
						},
						success : function(data) {
							if(data=="true"){
								 window.location = "Login";
							}
							else{
							$(".message").text(data)
						}
						}

					});

				});

			});
		</script>
</head>
<body>

	<div class="container">
		<div class="text-center">
			<h1>User Registration</h1>

			<br /> <br /> <br></br>



			<div class="form-group-row">

				<div class="answer">
					UserName <input type="text" name="Newuser" id="name1" /> <br />
					Password <input type="password" name="Newpassword" id="name2"></input><br />
					<br />
					<button class="signup">SignUp</button>
					<h6 class="message"></h6>
				</div>

			</div>

			<br />
			<form action="Login" method="get" class="form-inline">
				<input type="submit" name="login" value="Login Page" />
			</form>

		</div>
	</div>
</body>
</html>