/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class Invoice {
    private int invoiceId;
    private Order order;
    private Timestamp issueDate;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private List<InvoiceContent> invoiceContents;

    public Invoice() {
    }

    public Invoice(int invoiceId, Order order, Timestamp issueDate, BigDecimal totalAmount, String paymentMethod, List<InvoiceContent> invoiceContents) {
        this.invoiceId = invoiceId;
        this.order = order;
        this.issueDate = issueDate;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.invoiceContents = invoiceContents;
    }

    

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<InvoiceContent> getInvoiceContents() {
        return invoiceContents;
    }

    public void setInvoiceContents(List<InvoiceContent> invoiceContents) {
        this.invoiceContents = invoiceContents;
    }
    
    
}
