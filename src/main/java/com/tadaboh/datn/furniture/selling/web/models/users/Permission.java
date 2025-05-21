package com.tadaboh.datn.furniture.selling.web.models.users;

import com.tadaboh.datn.furniture.selling.web.enums.HttpMethodEnum;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "permissions")
public class Permission   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "end_point")
    private String endPoint;

    @Column(name = "http_method")
    private String httpMethod;
}
