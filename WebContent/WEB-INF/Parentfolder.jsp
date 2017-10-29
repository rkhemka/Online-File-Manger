	
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="ISO-8859-1">
	<title>Display Folders</title>
	
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
		crossorigin="anonymous">
		
	</script>
	
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	
	<script>
	$(function(){
		
		$(".share").click(function(){
	        var row = $(this).closest("tr");

	            var a= $("#name1").val();

	           
	           $.ajax({
	        	   url: "ShareFile",
	        	   type: "GET",
	        	   data :{
	                   "fileid" : row.attr("data-entry-id"),
	        		   "fileSharedWithUser": a
	        		   
	        	   },
	           success: function(data){
	        	 $(".message").text(data) 
	           }
	      
	           
	           });
	        
		
		
		});  
		
	   
	   
	   
	   
	   function delEntry() {
           var row = $(this).closest("tr");
           $.ajax({
        	      url: "DeleteFileFolderUsingAjax",
               data : {
                   "id" : row.attr("data-entry-id")
               },
               success : function() {
                   row.remove();
               }
           });
       }

       $(".delete").click(delEntry);

	   
	   
	   
	   
	   
	   
	   $("#add").click(function(){
	       var row = $(this).closest("tr");
	       var idValue;
	      var name = $("#name").val();
	        $.ajax({
	              url: "AddFolderUsingAjax",
	              data: {
	                  "name": name,
	                   "id" :0
	              },
	              
	              success: function(data){
	                row = $("<tr data-entry-id='" + data["afterId"]+ "'></tr>" );
	                  var fc = $("<td></td>");
	                  var a = $("<a href='EditFolder?id="+data["afterId"]+"'><i class='glyphicon glyphicon-pencil'></i></a> | <a href='DeleteServlet?id="+data["afterId"]+"'><i class='glyphicon glyphicon-trash'></i></a>");
	                  var del = $("<button class='delete'>Delete Using Ajax</button>");
	                  fc.append(a).append(del);
	                  row.append("<td> <a href='SubFolder?id="+data["afterId"]+"'><i class='glyphicon glyphicon-folder-close'></i>" +name+" </a></td>")
	                     .append("<td>"+data["date1"]+"</td>")
	                     .append("<td></td>")
	                     .append(fc);
	                  (del).click(delEntry);
	                  $("#form").before(row);
	              } 
	          });
	   });
	});
	</script>
	
	
	
	</head>
	
	<body>
	
	
		<div class="text-center">
	
	
			<h1>${user}'sFile-Manager</h1>
	
	
		</div>
		<br>
	
		<a class="btn btn-danger pull-right" href="Logout"
			type="btn btn-outline-danger">Logout</a>
	
		<br>
	
		<div class="btn-group">
			<button type="button" class="btn btn-primary dropdown-toggle"
				data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				New <span class="caret"></span>
			</button>
			<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
				<li><a href="NewFolder?id=0"><i
						class="glyphicon glyphicon-folder-open"></i> New folder</a></li>
				<li><a href="Upload?id=0"><i
						class="glyphicon glyphicon-upload"></i> Upload File </a></li>
			</ul>
	
		</div>
		<div></div>
		<br>

	
	<a role="button" class="btn btn-secondary btn-lg btn-block" href="UserFiles">Shared </a>
	
		<br>
		<table class="table table-hover table-inverse">
			<tr>
				<th>Name</th>
				<th>Date</th>
				<th>Size</th>
				<th>Operation</th>
				<th>Share Files With Other User </th>
			</tr>
	
	
			<c:forEach items="${MyFile}" var="entry">
				<tr>
	
					<c:choose>
						<c:when test="${( entry.parent==0)  and(entry.folder)}">
							<tr data-entry-id="${entry.id}" >
								<td><a href="SubFolder?id=${entry.id}"><i
										class="glyphicon glyphicon-folder-close"></i> ${entry.name}</a></td>
								<td><fmt:formatDate pattern="MM/dd/yyyy hh:mm a"
										value="${entry.date }"></fmt:formatDate></td>
								<td><c:choose>
										<c:when test="${entry.folder==true}"></c:when>
										<c:when test="${entry.size<1024}">
	
											<fmt:formatNumber type="number" maxIntegerDigits="4"
												value="${entry.size}" />B
											</c:when>
										<c:when test="${entry.size>=1024}">
											<fmt:formatNumber type="number" maxIntegerDigits="4"
												value="${entry.size/1024}" />KB
											</c:when>
									</c:choose></td>
								<td><a href="EditFolder?id=${entry.id}"><i
										class="glyphicon glyphicon-pencil"></i></a> | <a
									href="DeleteServlet?id=${entry.id}"><i
										class="glyphicon glyphicon-trash"></i></a>
										<button class="delete">Delete Using Ajax</button>
		
		</td>
	<td >
	</td>
							</tr>
	
						</c:when>
	
						<c:when test="${(entry.parent==0) and !(entry.folder) }">
							<tr data-entry-id="${entry.id}" >
								<td><a href="Download?id=${entry.id}">${entry.name}</a></td>
								<td><fmt:formatDate pattern="MM/dd/yyyy hh:mm a"
										value="${entry.date }"></fmt:formatDate></td>
								<td><c:choose>
										<c:when test="${entry.folder==true}"></c:when>
										<c:when test="${entry.size<1024}">
	
											<fmt:formatNumber type="number" maxIntegerDigits="4"
												value="${entry.size}" />B
		                                    </c:when>
										<c:when test="${entry.size>=1024}">
											<fmt:formatNumber type="number" maxIntegerDigits="4"
												value="${entry.size/1024}" />KB
		                                    </c:when>
									</c:choose></td>
								<td><a href="EditFolder?id=${entry.id}"><i
										class="glyphicon glyphicon-pencil"></i> </a> | <a
									href="DeleteServlet?id=${entry.id}"><i
										class="glyphicon glyphicon-trash"></i> </a>
										<button class="delete">Delete Using Ajax</button>
										</td>

<td class ="answers"    >        
    
    
      <div class="answer">
        <input type="text" id ="name1"/> 
    <button class="share">Share</button>
    <h6 class ="message"></h6>
      </div>
    

 
     </td>
							</tr>
						</c:when>
	
					</c:choose>
			</c:forEach>
			
			<tr id="form" data-entry-id="${param.id}">
	            <td><input id="name" type="text" /></td>
	            <td></td>
	            <td></td>
	            <td ><button id="add">
	            <i class="glyphicon glyphicon-folder-open">
	            </i> New folder Using Ajax </button>
	</td>
	
	
	</tr>
	
	

 
	
	
		</table>
	</body>
	</html>
	
