<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>New Folder</title>

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





</head>
<link rel='stylesheet' type='text/css' href='style1.css' />

<body>

	<c:choose>
		<c:when test="${(index)==0}">
			<c:out value=" ">
			</c:out>
		</c:when>

		<c:otherwise>

			<div class="text-center">
				<h4>${SelectedID.name}


					<c:out value="${SelectedNewId.name}"></c:out>
				</h4>
			</div>
		</c:otherwise>
	</c:choose>

	<div id=edit>
		<div class="container">

			<form action="NewFolder" method="post">
				<div>
					Name <input type="text" name="FolderName"><br> <br>
					<input type="submit" name="add" value="Create"> <input
						type="hidden" name="id" value="${index}">
				</div>
			</form>

		</div>

	</div>

</body>
</html>