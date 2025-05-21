package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.models.Geocode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeocodeService {
    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search?q=%s&format=json";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeocodeService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Geocode getCoordinates(String address) {
        String url = String.format(NOMINATIM_URL, address.replace(" ", "+"));

        try {
            // Gọi API
            String response = restTemplate.getForObject(url, String.class);
            JsonNode jsonNode = objectMapper.readTree(response);

            // Lấy tọa độ đầu tiên trong danh sách trả về
            if (jsonNode.isArray() && jsonNode.size() > 0) {
                String latitude = jsonNode.get(0).get("lat").asText();
                String longitude = jsonNode.get(0).get("lon").asText();
                Geocode geocode = new Geocode();
                geocode.setLatTo(Double.parseDouble(latitude));
                geocode.setLngTo(Double.parseDouble(longitude));
                return geocode;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}