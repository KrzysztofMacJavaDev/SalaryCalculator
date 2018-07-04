<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta charset="UTF-8">
    <script type="text/javascript" src="/resources/static/js/jquery-3.3.1.min.js"></script>
    <link rel="stylesheet" href="/resources/static/css/bootstrap-4.0.0-dist/css/bootstrap.min.css" type="text/css" />

    <style type="text/css">
        container {
            width: 200px;
        }
    </style>    
</head>

<body>
    <div class="container">
        <form id="mainForm" class="needs-validation" method="post" novalidate>
            <div class="form-group">
                <label for="countriesSelectId">Wybierz kraj</label>
                <select name="country" id="countriesSelectId">
                    <c:forEach items="${countries}" var="element">
                        <option name="" value="${element}">
                            ${element}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="dayPriceGrossId">Podaj dzienną kwotę brutto w walucie wybranego kraju </label>
                <input pattern= "^\d*\.?\d{2}$" type="text" id="grossCostId" name="daypricegross" class="form-control">
                <div class="invalid-feedback">Wprowadź liczbę całkowitą albo liczbę we formacie .XX</div>       
                <button id="confirmForm" type="button" class="btn btn-primary">Oblicz</button>			
            </div>        
        </form>
      
        <div class="form-group">
            <label for="resultNetMonthPrice">Miesięczny zarobek w PLN na kontrakcie to</label>
            <input type="text" id="resultNetMonthPrice" class="form-control">
        </div>
    </div>


    <script>
        $("#confirmForm").click(function () {
            validateForm();
        });

        function sendData() {
            var mainForm = $("#mainForm").serialize();
            var testUrl = "http://localhost:8080/netpricesalary";
            $.ajax({
                type: 'post',
                url: testUrl,
                contentType: 'application/x-www-form-urlencoded',
                data: mainForm
            }).done(function (data) {
                $('#resultNetMonthPrice').val(data.amount);
                $('#confirmFirstPart').removeAttr('disabled');
               
            }).fail(function (data) {
            	$('#resultNetMonthPrice').val("");
                $('#confirmFirstPart').removeAttr('disabled');                
            });
        }

        function validateForm() {
            var form = document.getElementById('mainForm');
            if (form.checkValidity() == false) {
                $('#mainForm').addClass('was-validated');
                return false;
            } else {
                if ($("#mainForm").hasClass('was-validated') == true) {
                    $("#mainForm").removeClass('was-validated');
                }
                $('#confirmFirstPart').attr('disabled', 'disabled');
                sendData();
                return true;
            }
        }

    </script>
</body>
</html>