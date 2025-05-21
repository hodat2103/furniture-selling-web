package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.models.Geocode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DistanceService {
    private static final String API_KEY = "cde66758-625c-44b4-b794-ce157063667a";
    private static final String BASE_URL = "https://graphhopper.com/api/1/route";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final GeocodeService geocodeService;

    @Value("${fami.lat}")
    double latFrom; // Vĩ độ điểm gửi

    @Value("${fami.lon}")
    double lonFrom; // Kinh độ điểm gửi

    public double calculateDistance(String address) {
        // Lấy tọa độ của địa chỉ nhận
        Geocode geocodeTo = geocodeService.getCoordinates(address);

        String url = String.format("%s?point=%f,%f&point=%f,%f&vehicle=car&key=%s",
                BASE_URL, latFrom, lonFrom, geocodeTo.getLatTo(), geocodeTo.getLngTo(), API_KEY);

        try {
            // Gọi API
            String response = restTemplate.getForObject(url, String.class);
            // Parse JSON
            JsonNode jsonNode = objectMapper.readTree(response);
            double distance = jsonNode.get("paths").get(0).get("distance").asDouble() / 1000;
            return distance;
        } catch (Exception e) {
            return 0;
        }
    }
}



