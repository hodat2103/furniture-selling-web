package com.tadaboh.datn.furniture.selling.web.models.users;

import com.tadaboh.datn.furniture.selling.web.models.bases.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends AuditableEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "code_expiration")
    private LocalDateTime codeExpiration;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;

    @Column(name = "is_active")
    private Boolean isActive;

    @Override
    public Collection<? extends  GrantedAuthority> getAuthorities() {
        String prefixRole = "ROLE_";
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Add roles as authorities

            if (role.getName() != null) {
                authorities.add(new SimpleGrantedAuthority(prefixRole + role.getName().toUpperCase()));
                System.out.println("Added role: " + prefixRole + role.getName().toUpperCase());
            }
        // Add permissins for each role
        if (role.getPermissions() != null) {
            for (Permission permission : role.getPermissions()) {
                if (permission.getName() != null) {
                    authorities.add(new SimpleGrantedAuthority(permission.getName()));
                    System.out.println("Added permission: " + permission.getName());
                } else {
                    System.out.println("Permission name is null for permission: " + permission);
                }
            }
        } else {
            System.out.println("Role permissions are null for role: " + role.getName());
        }

        System.out.println("User authorities: " + authorities);
        return authorities;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
