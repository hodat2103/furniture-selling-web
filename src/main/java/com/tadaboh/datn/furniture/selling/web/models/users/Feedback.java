package com.tadaboh.datn.furniture.selling.web.models.users;

import com.tadaboh.datn.furniture.selling.web.models.bases.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "feedbacks")
public class Feedback extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating_value")
    private Integer ratingValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
