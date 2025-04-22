package com.agag.currency.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionRecord {
    private double amount;
    private String from;
    private String to;
    private double result;
    private LocalDateTime timestamp;

    public ConversionRecord(double amount, String from, String to, double result) {
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.result = result;
        this.timestamp = LocalDateTime.now();
    }

    public double getAmount() {
        return amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getResult() {
        return result;
    }

    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }
}
