package com.agag.currency.service;

import com.agag.currency.model.ExchangeResponse;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.InputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;


@Service
public class CurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey;

    public CurrencyService() {
        this.apiKey = loadApiKey();
    }

    private String loadApiKey() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.error("❌ config.properties not found in classpath.");
                return null;
            }
            props.load(input);
            return props.getProperty("api.key");
        } catch (IOException e) {
            logger.error("ERROR: Could not load API key from config.properties", e);
            return null;
        }
    }


    public Double convert(double amount, String from, String to) {
        if(Objects.isNull(apiKey)){
            logger.warn("No API key found. Aborting conversion.");
            return null;
        }
        String url = UriComponentsBuilder.fromHttpUrl("https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + from + "/" + to + "/" + amount)
                .toUriString();
        logger.info("Requesting conversion: {}", url);
        ExchangeResponse response = restTemplate.getForObject(url, ExchangeResponse.class);

        if (response != null && "success".equals(response.getResult())) {
            return response.getConversion_result();
        }
        return null;
    }
}
