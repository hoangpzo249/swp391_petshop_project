/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author ADMIN
 */
import java.sql.Timestamp;
import java.util.Base64;

public class Refund {

    private int refundId;
    private int orderId;
    private String bankAccountNumber;
    private String bankName;
    private String accountHolderName;
    private double refundAmount;
    private byte[] proofImage;
    private String refundStatus;
    private Timestamp refundRequestDate;
    private Timestamp refundProcessedDate;

    public Refund() {
    }

    public Refund(int orderId, String bankAccountNumber, String bankName, String accountHolderName, double refundAmount, byte[] proofImage) {
        this.orderId = orderId;
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
        this.accountHolderName = accountHolderName;
        this.refundAmount = refundAmount;
        this.proofImage = proofImage;
        this.refundStatus = "Pending";

    }

    public int getRefundId() {
        return refundId;
    }

    public void setRefundId(int refundId) {
        this.refundId = refundId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public byte[] getProofImage() {
        return proofImage;
    }

    public void setProofImage(byte[] proofImage) {
        this.proofImage = proofImage;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Timestamp getRefundRequestDate() {
        return refundRequestDate;
    }

    public void setRefundRequestDate(Timestamp refundRequestDate) {
        this.refundRequestDate = refundRequestDate;
    }

    public Timestamp getRefundProcessedDate() {
        return refundProcessedDate;
    }

    public void setRefundProcessedDate(Timestamp refundProcessedDate) {
        this.refundProcessedDate = refundProcessedDate;
    }

    public String getBase64ProofImage() {
        return proofImage != null ? Base64.getEncoder().encodeToString(proofImage) : null;
    }
    private String rejectReason;

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    private byte[] proofRefundedImage;

    public byte[] getProofRefundedImage() {
        return proofRefundedImage;
    }

    public void setProofRefundedImage(byte[] proofRefundedImage) {
        this.proofRefundedImage = proofRefundedImage;
    }

    public String getBase64ProofRefundedImage() {
        return proofRefundedImage != null ? Base64.getEncoder().encodeToString(proofRefundedImage) : null;
    }
    private String refundReasonDescription;

    public String getRefundReasonDescription() {
        return refundReasonDescription;
    }

    public void setRefundReasonDescription(String refundReasonDescription) {
        this.refundReasonDescription = refundReasonDescription;
    }

}
