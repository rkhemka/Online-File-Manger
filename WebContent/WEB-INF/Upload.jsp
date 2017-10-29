<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload</title>
</head>
<link rel='stylesheet' type='text/css' href='style1.css' />
<body>
	<div id=edit>
		<div class="container">
			<div id=uploadme>
				<form action="Upload" method="post" enctype="multipart/form-data">

					File: <input type="file" name="uploadSelectedFile" /> <br /> <br>
					<br> <input type="submit" name="upload" value="Upload" /><br>
					<input type="hidden" name="id" value="${index}">
				</form>
			</div>
		</div>

	</div>
</body>
</html>