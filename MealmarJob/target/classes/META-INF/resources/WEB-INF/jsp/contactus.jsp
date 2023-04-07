<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<my:useFunctions />

<html>
	<head>
		<link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet" >
		<link href="webjars/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.standalone.min.css"  rel="stylesheet">
		 <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		<title>Mealmar Website</title>		
	</head>
	<body>
	
<%@ include file="common/navigation.jspf"%>

   <script>
        $(document).ready(function () {
            $("#contactForm").on("submit", function (event) {
                event.preventDefault();
                console.log("Submitting form...");

                var name = $("#name").val();
                var email = $("#email").val();
                var message = $("#message").val();

                $.ajax({
                    url: "/sendMail",
                    type: "GET",
                    data: {
                        name: name,
                        email: email,
                        message: message
                    },
                    success: function (response) {
                    	window.location.href = "/thanksmail";
                    	},
                    error: function (xhr, status, error) {
                        alert("Something went wrong!");
                    }
                });
            });
        });
    </script>
<style>

css
Copy code
	.jumbotron {
		background-size: cover;
		height: 500px;
		display: flex;
		align-items: center;
		color: white;
		text-shadow: 2px 2px #333;
	}

	.card {
		border: none;
		border-radius: 0;
		box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	}


</style>

<div class="jumbotron">
		<div class="container">
			<h1 class="display-4">Your Hospitality Organizer</h1>
			<p class="lead">Hospitoolity provides an app that allows restaurants, hotels, and other hospitality businesses to easily store and access data from their HACCP plans in order to improve food safety and compliance with UK regulations.</p>
		</div>
	</div>







<div class="container my-5">
<h2 class="text-center mb-4">Contact Us</h2>
<div class="row">
<div class="col-md-6">


<form id="contactForm">
    <div class="form-group">
        <label for="name">Name</label>
        <input type="text" class="form-control" id="name" placeholder="Enter your name" required minlength="2" maxlength="50">
        <div class="invalid-feedback">Please enter a valid name.</div>
    </div>
    <div class="form-group">
        <label for="email">Email address</label>
        <input type="email" class="form-control" id="email" placeholder="Enter your email" required>
        <div class="invalid-feedback">Please enter a valid email address.</div>
    </div>
    <div class="form-group">
        <label for="message">Message</label>
        <textarea class="form-control" id="message" rows="5" required minlength="10" maxlength="500"></textarea>
        <div class="invalid-feedback">Please enter a valid message.</div>
    </div>
    <button type="submit" class="btn btn-primary" id="sendButton">Send</button>
</form>





</div>
<div class="col-md-6">

<center><p>Phone: 074 80545488</p>
<p>Email: office@hospitoolity.com</p></center>
</div>
</div>
</div>


<style>
		body {
			padding-bottom: 50px;
			min-height: 100vh;
			position: relative;
		}

		footer {
			position: absolute;
			bottom: 0;
			width: 100%;
			height: 70px;
			background-color: #f8f9fa;
			box-shadow: 0 -2px 10px rgba(0,0,0,0.2);
			display: flex;
			align-items: center;
			justify-content: center;
			font-size: 18px;
			color: #6c757d;
		}
	</style>	


<footer class="bg-dark text-white py-3">
		<div class="container">
			<div class="row">
				<div class="col-md-6">
					<p>© 2023 Hospitoolity</p>
				</div>
				<div class="col-md-6 text-right">
					<p>Privacy Policy | Terms of Service</p>
				</div>
			</div>
		</div>
	</footer>
</html>