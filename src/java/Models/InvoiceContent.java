/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Lenovo
 */
public class InvoiceContent {

    private int invoiceContentId;
    private int invoiceId;
    private Integer petId;
    private String petName;
    private String petBreed;
    private String petGender;
    private Date petDob;
    private BigDecimal priceAtInvoice;

    public int getInvoiceContentId() {
        return invoiceContentId;
    }

    public void setInvoiceContentId(int invoiceContentId) {
        this.invoiceContentId = invoiceContentId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public Date getPetDob() {
        return petDob;
    }

    public void setPetDob(Date petDob) {
        this.petDob = petDob;
    }

    public BigDecimal getPriceAtInvoice() {
        return priceAtInvoice;
    }

    public void setPriceAtInvoice(BigDecimal priceAtInvoice) {
        this.priceAtInvoice = priceAtInvoice;
    }

    @Override
    public String toString() {
        return "InvoiceContent{" + "invoiceContentId=" + invoiceContentId + ", invoiceId=" + invoiceId + ", petId=" + petId + ", petName=" + petName + ", petBreed=" + petBreed + ", petGender=" + petGender + ", petDob=" + petDob + ", priceAtInvoice=" + priceAtInvoice + '}';
    }

    
}
