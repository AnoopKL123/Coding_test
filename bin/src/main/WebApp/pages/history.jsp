<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>History</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
		<link rel="stylesheet" href="CSS/style.css">
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
  		<link rel="stylesheet" href="/resources/demos/style.css">
  		<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
  		<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
		<script>
			$(function() {
				var now = new Date();
				var today = new Date(now.setMonth(now.getMonth() + 1));
				var prev = new Date(now.setMonth(now.getMonth() - 1));

			    var day = ("0" + today.getDate()).slice(-2); //　今月の日
			    var month = ("0" + (today.getMonth() + 1)).slice(-2); //　今月

			    var prevMonthDay = ("0" + prev.getDate()).slice(-2); // 前月の日
			    var prevMon = ("0" + (prev.getMonth() + 1)).slice(-2);　// 前月

			    var today = today.getFullYear()+"-"+(month)+"-"+(day);
			    var prevMonth = prev.getFullYear()+"-"+(prevMon)+"-"+(day);
			    
			    $("#fromDate").val(prevMonth);
			    $("#toDate").val(today);

		    	$("#fromDate").datepicker({ dateFormat: 'yy-mm-dd' });
		    	$("#toDate").datepicker({ dateFormat: 'yy-mm-dd' });
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
		
		<h2>履歴</h2>
		<form id="historyForm" action="history" method="post" style="margin-left:600px">
			<label style="margin-right:10px">開始日 : </label>
			<input type="text" id="fromDate" name="fromDate">
			<label style="margin-left:50px;margin-right:10px">終了日 : </label>
			<input type="text" id="toDate" name="toDate">
			<input class="btn btn-primary" type="submit" value="Submit">
		</form>

		<div class="table-responsive text-nowrap">
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
					<c:forEach var="rec" items="${history}">
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