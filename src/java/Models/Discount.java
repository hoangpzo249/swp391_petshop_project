/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Date;

public class Discount {

    private int discountId;
    private String discountCode;
    private String discountType; 
    private double discountValue;
    private String description;
    private Date validFrom;
    private Date validTo;
    private double minOrderAmount;
    private int maxUsage;
    private int usageCount;
    private boolean isActive;
    private double maxValue;

    public Discount() {
    }

    public Discount(int discountId, String discountCode, String discountType, double discountValue, String description, Date validFrom, Date validTo, double minOrderAmount, int maxUsage, int usageCount, boolean isActive, double maxValue) {
        this.discountId = discountId;
        this.discountCode = discountCode;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.description = description;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.minOrderAmount = minOrderAmount;
        this.maxUsage = maxUsage;
        this.usageCount = usageCount;
        this.isActive = isActive;
        this.maxValue = maxValue;
    }
    

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public double getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(double minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public int getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(int maxUsage) {
        this.maxUsage = maxUsage;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
    public boolean isValidNow() {
    Date today = new Date();
    return (validFrom == null || !today.before(validFrom)) &&
           (validTo == null || !today.after(validTo));
}

public boolean isUsageAvailable() {
    return usageCount < maxUsage;
}

}
