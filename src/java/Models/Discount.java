package Models;

import java.sql.Date;

public class Discount {

    private int discountId;
    private String discountCode;
    private String discountType;
    private Double discountValue;
    private String description;
    private Date validFrom;
    private Date validTo;
    private Double minOrderAmount;
    private Integer maxUsage;
    private int usageCount;
    private boolean isActive;
    private Double maxValue;

    public Discount() {}

    public Discount(int discountId, String discountCode, String discountType, Double discountValue, String description,
                    Date validFrom, Date validTo, Double minOrderAmount, Integer maxUsage, int usageCount,
                    boolean isActive, Double maxValue) {
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

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
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

    public Double getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(Double minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public Integer getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(Integer maxUsage) {
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

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isValidNow() {
        Date today = new Date(System.currentTimeMillis());
        return (validFrom == null || !today.before(validFrom)) &&
               (validTo == null || !today.after(validTo));
    }

    public boolean isUsageAvailable() {
        return maxUsage == null || usageCount < maxUsage;
    }
}
