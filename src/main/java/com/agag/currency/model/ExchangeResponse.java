package com.agag.currency.model;

public class ExchangeResponse {
    private String result;
    private Double conversion_result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Double getConversion_result() {
        return conversion_result;
    }

    public void setConversion_result(Double conversion_result) {
        this.conversion_result = conversion_result;
    }
}
