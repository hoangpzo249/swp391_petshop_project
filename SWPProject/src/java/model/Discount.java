/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Admin
 */

//where(i mean the scope,the access modifer) should we use enum?


public class Discount {

    private int discountId; // not require when add
    private String discountCode;
    private String discountType;
    private float disCountValue;
    private String description;
    private Date validFrom;
    private Date validTo;
    private int minOrderAmount;
    private int maxUsage; 
    private int usageCount; // not require when add
    private boolean isActive;

    public Discount(int discountId, String discountCode, String discountType, 
            float disCountValue, String description, Date validFrom, Date validTo, 
            int minOrderAmount, int maxUsage, int usageCount, boolean isActive) {
        this.discountId = discountId;
        this.discountCode = discountCode;
        this.discountType = discountType;
        this.disCountValue = disCountValue;
        this.description = description;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.minOrderAmount = minOrderAmount;
        this.maxUsage = maxUsage;
        this.usageCount = usageCount;
        this.isActive = isActive;
    }

    public Discount() {
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

    public float getDisCountValue() {
        return disCountValue;
    }

    public void setDisCountValue(float disCountValue) {
        this.disCountValue = disCountValue;
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

    public int getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(int minOrderAmount) {
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

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Discount{");
        sb.append("discountId=").append(discountId);
        sb.append(", discountCode=").append(discountCode);
        sb.append(", discountType=").append(discountType);
        sb.append(", disCountValue=").append(disCountValue);
        sb.append(", description=").append(description);
        sb.append(", validFrom=").append(validFrom);
        sb.append(", validTo=").append(validTo);
        sb.append(", minOrderAmount=").append(minOrderAmount);
        sb.append(", maxUsage=").append(maxUsage);
        sb.append(", usageCount=").append(usageCount);
        sb.append(", isActive=").append(isActive);
        sb.append('}');
        return sb.toString();
    }
    
    
}
