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
public class Shipper {

    private Account shipperAccount;
    private int shipperId;
    private String shipperAvailability;
    private String shipperNote;
    private Timestamp lastDeliveryTime;
    private int currentShippingOrders;

    public Shipper() {
    }

    public Shipper(Account shipperAccount, String shipperAvailability, String shipperNote) {
        this.shipperAccount = shipperAccount;
        this.shipperAvailability = shipperAvailability;
        this.shipperNote = shipperNote;
    }

    public Shipper(Account shipperAccount, String shipperNote, Timestamp lastDeliveryTime, int currentShippingOrders) {
        this.shipperAccount = shipperAccount;
        this.shipperNote = shipperNote;
        this.lastDeliveryTime = lastDeliveryTime;
        this.currentShippingOrders = currentShippingOrders;
    }

    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }
    
    

    public Account getShipperAccount() {
        return shipperAccount;
    }

    public void setShipperAccount(Account shipperAccount) {
        this.shipperAccount = shipperAccount;
    }

    public String getShipperAvailability() {
        return shipperAvailability;
    }

    public void setShipperAvailability(String shipperAvailability) {
        this.shipperAvailability = shipperAvailability;
    }

    public String getShipperNote() {
        return shipperNote;
    }

    public void setShipperNote(String shipperNote) {
        this.shipperNote = shipperNote;
    }

    public Timestamp getLastDeliveryTime() {
        return lastDeliveryTime;
    }

    public void setLastDeliveryTime(Timestamp lastDeliveryTime) {
        this.lastDeliveryTime = lastDeliveryTime;
    }

    public int getCurrentShippingOrders() {
        return currentShippingOrders;
    }

    public void setCurrentShippingOrders(int currentShippingOrders) {
        this.currentShippingOrders = currentShippingOrders;
    }

    @Override
    public String toString() {
        return "Shipper{" + "shipperAvailability=" + shipperAvailability + ", shipperNote=" + shipperNote + '}';
    }

}
