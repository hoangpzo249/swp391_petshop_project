/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Timestamp; 

/**
 *
 * @author Lenovo
 */
public class Order {

    private int orderId;
    private Integer accId;
    private Timestamp orderDate;
    private String orderStatus;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private Integer shipperId;
    private String paymentMethod;
    private String paymentStatus;
    private double totalPrice;
    private String rejectionReason;
    private Integer discountId;
    private String petName;
    private Timestamp deliveryDate;

    public Order() {
    }

    public Order(int orderId, Integer accId, Timestamp orderDate, String orderStatus, String customerName, String customerEmail, String customerPhone, String customerAddress, Integer shipperId, String paymentMethod, String paymentStatus, double totalPrice, String rejectionReason, Integer discountId) {
        this.orderId = orderId;
        this.accId = accId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.shipperId = shipperId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.totalPrice = totalPrice;
        this.rejectionReason = rejectionReason;
        this.discountId = discountId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Integer getShipperId() {
        return shipperId;
    }

    public void setShipperId(Integer shipperId) {
        this.shipperId = shipperId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    

    
    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", accId=" + accId + ", orderDate=" + orderDate + ", orderStatus=" + orderStatus + ", customerName=" + customerName + ", customerEmail=" + customerEmail + ", customerPhone=" + customerPhone + ", customerAddress=" + customerAddress + ", shipperId=" + shipperId + ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus + ", totalPrice=" + totalPrice + ", rejectionReason=" + rejectionReason + ", discountId=" + discountId + '}';
    }
 
}
   