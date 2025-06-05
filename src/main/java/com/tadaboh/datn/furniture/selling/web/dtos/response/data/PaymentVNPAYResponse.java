package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class PaymentVNPAYResponse {
    private String status;
    private String message;
    private String url;
}
