/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Lenovo
 */
public class Shipper {
    private int shipperId;
    private String shipperAvailability;
    private String shipperNote;

    public Shipper() {
    }

    public Shipper(int shipperId, String shipperAvailability, String shipperNote) {
        this.shipperId = shipperId;
        this.shipperAvailability = shipperAvailability;
        this.shipperNote = shipperNote;
    }

    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
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

    @Override
    public String toString() {
        return "Shipper{" + "shipperId=" + shipperId + ", shipperAvailability=" + shipperAvailability + ", shipperNote=" + shipperNote + '}';
    }
    
    
}
