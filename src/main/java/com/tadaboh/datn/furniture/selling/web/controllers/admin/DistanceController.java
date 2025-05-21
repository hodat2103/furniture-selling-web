package com.tadaboh.datn.furniture.selling.web.controllers.admin;

import com.tadaboh.datn.furniture.selling.web.services.impl.DistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}distances")
@RequiredArgsConstructor
public class DistanceController {
    private final DistanceService distanceService;

    @GetMapping("/{address}")
    public ResponseEntity<Object> getDistances(@PathVariable(name = "address") String address) {
        return ResponseEntity.ok(distanceService.calculateDistance(address)
        );
    }
}
