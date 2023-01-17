<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>OCR Processing</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
		<link rel="stylesheet" href="CSS/style.css">
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<script>
			$('#myfile').change(function() {
				var i = $(this).prev('label').clone();
				var file = $('#file-upload')[0].files[0].name;
				$(this).prev('label').text(file);
			});
		</script>
	</head>

	<body>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarToggler"
				aria-controls="navbarToggler" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarToggler">
				<a class="navbar-brand" href="/homePage">OCR</a>
				<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
					<li class="nav-item active"><a class="nav-link" href="/homePage">ホームページ
							<span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="/history">履歴</a></li>
				</ul>
			</div>
		</nav>

		<form class="design-form" action="getDetails" method="post" enctype="multipart/form-data" style="align: center;">
			<input type="file" id="myfile" name="myfile" accept="application/pdf, image/*" multiple>
			<input class="btn btn-primary" type="submit" value="Submit">
			<label><a href="/homePage">Clear</a></label>
		</form>

		<div id="tableDiv" class="table-responsive text-nowrap">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Date</th>
						<th>Issuer</th>
						<th>Telephone</th>
						<th>Amount</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="rec" items="${receipt}">
					    <tr>      
							<td>${rec["date"]}</td>
							<td>${rec["issuer"]}</td>
							<td>${rec["tel"]}</td>
							<td>${rec["amount"]}</td>
					    </tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
</html>