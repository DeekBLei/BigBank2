package com.example.project_bigbangk.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;

/**
 * Model created by Vanessa Philips.
 * Object "Transaction" for "regular" transactions.
 */

public class Transaction extends AbstractOrder{

    private final Logger logger = LoggerFactory.getLogger(Transaction.class);

    private double transactionFee;
    private Wallet buyerWallet;
    private Wallet sellerWallet;

    public Transaction(int orderId, Asset asset, double requestedPrice, int numberOfAssets,
                       LocalDateTime date, double transactionFee, Wallet buyerWallet, Wallet sellerWallet) {
        super(orderId, asset, requestedPrice, numberOfAssets, date);
        this.transactionFee = transactionFee;
        this.buyerWallet = buyerWallet;
        this.sellerWallet = sellerWallet;
        logger.info("New transaction using all-arg constructor");
    }

    public Transaction() {
        this(0, null, 0, 0, null, 0, null, null);
        logger.info("New transaction using no-arg constructor");
    }

    public double getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(double transactionFee) {
        this.transactionFee = transactionFee;
    }

    public Wallet getBuyerWallet() {
        return buyerWallet;
    }

    public void setBuyerWallet(Wallet buyerWallet) {
        this.buyerWallet = buyerWallet;
    }

    public Wallet getSellerWallet() {
        return sellerWallet;
    }

    public void setSellerWallet(Wallet sellerWallet) {
        this.sellerWallet = sellerWallet;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Transaction{" +
                "transactionFee=" + transactionFee +
                ", buyerWallet=" + buyerWallet +
                ", sellerWallet=" + sellerWallet +
                '}';
    }
}
