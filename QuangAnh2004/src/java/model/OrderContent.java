/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author QuangAnh
 */
public class OrderContent {
    private int orderContentId;
    private int orderId;
    private int petId;

    public OrderContent() {
    }

    public OrderContent(int orderContentId, int orderId, int petId) {
        this.orderContentId = orderContentId;
        this.orderId = orderId;
        this.petId = petId;
    }

    public int getOrderContentId() {
        return orderContentId;
    }

    public void setOrderContentId(int orderContentId) {
        this.orderContentId = orderContentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    @Override
    public String toString() {
        return "OrderContent{" + "orderContentId=" + orderContentId + ", orderId=" + orderId + ", petId=" + petId + '}';
    }
    
}
