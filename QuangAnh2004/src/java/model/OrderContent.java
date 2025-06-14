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
    private double priceAtOrder;
    
    private Pet pet; 

    
    public OrderContent() {
    }
    
    public OrderContent(int orderContentId, int orderId, int petId, double priceAtOrder, Pet pet) {
        this.orderContentId = orderContentId;
        this.orderId = orderId;
        this.petId = petId;
        this.priceAtOrder = priceAtOrder;
        this.pet = pet;
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

    public double getPriceAtOrder() {
        return priceAtOrder;
    }

    public void setPriceAtOrder(double priceAtOrder) {
        this.priceAtOrder = priceAtOrder;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "OrderContent{" + "orderContentId=" + orderContentId + ", orderId=" + orderId + ", petId=" + petId + ", priceAtOrder=" + priceAtOrder + ", pet=" + pet + '}';
    }

    
    
}
