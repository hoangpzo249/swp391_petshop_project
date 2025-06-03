/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Lenovo
 */
public class Cart {
    private int accountId;
    private Integer petId; 
    private Integer productId; 
    private int quantity;
    private double price;

    public Cart() {
    }

    
    public Cart(int accountId, Integer petId, Integer productId, int quantity, double price) {
        this.accountId = accountId;
        this.petId = petId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

   
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cart{" +
               "accountId=" + accountId +
               ", petId=" + petId +
               ", productId=" + productId +
               ", quantity=" + quantity +
               ", price=" + price +
               '}';
    }
}
