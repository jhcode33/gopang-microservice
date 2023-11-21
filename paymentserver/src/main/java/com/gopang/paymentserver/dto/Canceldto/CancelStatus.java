package com.gopang.paymentserver.dto.Canceldto;

public class CancelStatus {
    public String merchant_Uid;
    public int cancelAmount;
    public int amount;
    public int remainingBalance;
    public String status;

    public CancelStatus(String merchant_Uid, int cancelAmount, int amount, int remainingBalance, String status) {
        this.merchant_Uid = merchant_Uid;
        this.cancelAmount = cancelAmount;
        this.amount = amount;
        this.remainingBalance = remainingBalance;
        this.status = status;
    }
}
