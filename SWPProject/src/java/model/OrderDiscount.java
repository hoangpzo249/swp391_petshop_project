/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class OrderDiscount {

    private int orderDiscountId;
    private int orderId;
    private int discountId;

    public OrderDiscount() {
    }

    public OrderDiscount(int orderDiscountId, int orderId, int discountId) {
        this.orderDiscountId = orderDiscountId;
        this.orderId = orderId;
        this.discountId = discountId;
    }

    public int getOrderDiscountId() {
        return orderDiscountId;
    }

    public void setOrderDiscountId(int orderDiscountId) {
        this.orderDiscountId = orderDiscountId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

}
