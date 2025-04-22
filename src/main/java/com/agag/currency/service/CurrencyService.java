package com.agag.currency.service;

import com.agag.currency.model.ExchangeResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;


@Service
public class CurrencyService {

    private final RestTemplate restTemplate = new RestTemplate();
    private String apiKey;

    private String loadApiKey() {
        Properties props = new Properties();

        try(FileInputStream input = new FileInputStream("config.properties")){
            props.load(input);
            return props.getProperty("api.key");
        }catch(IOException e){
            System.err.println("ERROR: Could not load API key from config.properties");
            e.printStackTrace();
            return null;
        }
    }

    public Double convert(double amount, String from, String to) {
        if(Objects.isNull(apiKey)){
            System.err.println("No API key found. Aborting conversion.");
            return null;
        }
        String url = UriComponentsBuilder.fromHttpUrl("https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + from + "/" + to + "/" + amount)
                .toUriString();

        ExchangeResponse response = restTemplate.getForObject(url, ExchangeResponse.class);

        if (response != null && "success".equals(response.getResult())) {
            return response.getConversion_result();
        }
        return null;
    }
}
