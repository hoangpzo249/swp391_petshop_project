<%-- 
    Document   : seller_order_detail
    Created on : 2 Jun 2025, 21:34:21
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .status-badge {
                font-size: 1.1rem;
                padding: 0.5em 0.8em;
            }
            .pet-card {
                transition: transform 0.2s;
            }
            .pet-card:hover {
                transform: translateY(-5px);
            }
        </style>
    </head>
    <body class="container py-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Order #${order.orderId}</h1>
            <span class="badge
                  <c:choose>
                      <c:when test="${order.orderStatus == 'Pending'}">bg-warning</c:when>
                      <c:when test="${order.orderStatus == 'Confirmed'}">bg-primary</c:when>
                      <c:when test="${order.orderStatus == 'Rejected'}">bg-danger</c:when>
                      <c:when test="${order.orderStatus == 'HandedOver'}">bg-success</c:when>
                      <c:otherwise>bg-secondary</c:otherwise>
                  </c:choose>
                  status-badge">
                ${order.orderStatus}
            </span>
        </div>

        <!-- Action Buttons -->
        <div class="card mb-4 border-primary">
            <div class="card-header bg-primary text-white">
                <h2 class="h5 mb-0">Order Actions</h2>
            </div>
            <div class="card-body">
                <form action="updateorderstatus" method="POST" class="d-flex flex-wrap gap-2">
                    <input type="hidden" name="id" value="${order.orderId}">

                    <c:if test="${order.orderStatus == 'Pending'}">
                        <button type="submit" name="status" value="Confirmed" 
                                class="btn btn-success btn-lg px-4">
                            Confirm Order
                        </button>
                        <button type="submit" name="status" value="Rejected" 
                                class="btn btn-danger btn-lg px-4">
                            Reject Order
                        </button>
                    </c:if>

                    <c:if test="${order.orderStatus == 'Confirmed'}">
                        <button type="submit" name="status" value="HandedOver" 
                                class="btn btn-primary btn-lg px-4">
                            Hand Over to Delivery
                        </button>
                    </c:if>

                    <a href="seller_order_view.jsp" class="btn btn-outline-secondary btn-lg px-4 ms-auto">
                        Back to Orders
                    </a>
                </form>
            </div>
        </div>

        <!-- Order Information -->
        <div class="row mb-4">
            <div class="col-md-6">
                <div class="card h-100">
                    <div class="card-header bg-light">
                        <h2 class="h5 mb-0">Customer Information</h2>
                    </div>
                    <div class="card-body">
                        <p><strong>Name:</strong> ${order.customerName}</p>
                        <p><strong>Email:</strong> ${order.customerEmail}</p>
                        <p><strong>Phone:</strong> ${order.customerPhone}</p>
                        <p><strong>Address:</strong> ${order.customerAddress}</p>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card h-100">
                    <div class="card-header bg-light">
                        <h2 class="h5 mb-0">Order Details</h2>
                    </div>
                    <div class="card-body">
                        <p><strong>Order Date:</strong> ${order.orderDate}</p>
                        <p><strong>Payment Method:</strong> ${order.paymentMethod}</p>
                        <p><strong>Payment Status:</strong> 
                            <span class="badge ${order.paymentStatus == 'Paid' ? 'bg-success' : 'bg-warning'}">
                                ${order.paymentStatus}
                            </span>
                        </p>
                        <p><strong>Shipper ID:</strong> 
                            <c:out value="${empty order.shipperId ? 'Not assigned' : order.shipperId}"/>
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Pets in Order -->
        <div class="card mb-4">
            <div class="card-header bg-light">
                <h2 class="h5 mb-0">Pets in this Order</h2>
            </div>
            <div class="card-body">
                <div class="row row-cols-1 row-cols-md-3 g-4">
                    <c:forEach items="${petlist}" var="pet">
                        <div class="col">
                            <div class="card h-100 pet-card">
                                <div class="card-header bg-info text-white">
                                    <h5 class="card-title mb-0">${pet.petName}</h5>
                                </div>
                                <div class="card-body">
                                    <p class="card-text">
                                        <strong>ID:</strong> ${pet.petId}<br>
                                        <strong>Breed:</strong> ${pet.breedId}<br>
                                        <strong>Color:</strong> ${pet.petColor}<br>
                                        <strong>Gender:</strong> ${pet.petGender}<br>
                                        <strong>Price:</strong> $${pet.petPrice}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>