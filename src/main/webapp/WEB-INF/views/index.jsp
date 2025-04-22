<%--
  Created by IntelliJ IDEA.
  User: Angel Gael Aviles Gama
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Currency Converter</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
            <h2 class="mb-0">💱 Currency Converter</h2>
        </div>
        <div class="card-body">
            <form action="convert" method="get" class="row g-3">
                <div class="col-md-4">
                    <label class="form-label">Amount</label>
                    <input type="number" step="any" name="amount" required class="form-control"/>
                </div>
                <div class="col-md-4">
                    <label class="form-label">From Currency (e.g. USD)</label>
                    <select name="from" class="form-select" required>
                        <c:forEach var="curr" items="${currencies}">
                            <option value="${curr}" <c:if test="${curr == fromSelected}">selected</c:if>>${curr}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4">
                    <label class="form-label">To Currency (e.g. MXN)</label>
                    <select name="to" class="form-select" required>
                        <c:forEach var="curr" items="${currencies}">
                            <option value="${curr}" <c:if test="${curr == toSelected}">selected</c:if>>${curr}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-12 text-end">
                    <button type="submit" class="btn btn-success">Convert</button>
                </div>
            </form>
        </div>
    </div>

    <c:if test="${not empty result}">
        <div class="alert alert-info mt-4">
            <h4>✅ Result: ${result}</h4>
        </div>
    </c:if>

    <c:if test="${not empty history}">
        <div class="card mt-4">
            <div class="card-header d-flex justify-content-between align-items-center">
                <span>📜 Conversion History</span>
                <form action="clear-history" method="get" class="mb-0">
                    <button type="submit" class="btn btn-sm btn-outline-danger">Clear History</button>
                </form>
            </div>
            <ul class="list-group list-group-flush">
                <c:forEach var="item" items="${history}">
                    <li class="list-group-item d-flex justify-content-between">
                        <span>
                            <strong>${item.amount}</strong> ${item.from} → <strong>${item.result}</strong> ${item.to}
                        </span>
                        <small class="text-muted">${item.formattedTimestamp}</small>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
