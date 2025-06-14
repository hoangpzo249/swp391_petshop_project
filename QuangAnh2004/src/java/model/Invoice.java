/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author QuangAnh
 */
public class Invoice {
    private int invoiceId;
    private int orderId;
    private Date issuaDate;
    private double totalAmount;
    private double taxAmount;
    private String paymentMethod;

    public Invoice() {
    }

    public Invoice(int invoiceId, int orderId, Date issuaDate, double totalAmount, double taxAmount, String paymentMethod) {
        this.invoiceId = invoiceId;
        this.orderId = orderId;
        this.issuaDate = issuaDate;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
        this.paymentMethod = paymentMethod;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getIssuaDate() {
        return issuaDate;
    }

    public void setIssuaDate(Date issuaDate) {
        this.issuaDate = issuaDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "Invoice{" + "invoiceId=" + invoiceId + ", orderId=" + orderId + ", issuaDate=" + issuaDate + ", totalAmount=" + totalAmount + ", taxAmount=" + taxAmount + ", paymentMethod=" + paymentMethod + '}';
    }

    
}
