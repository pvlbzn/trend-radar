package dev.trendradar.app.languages;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("languages")
public record Language(
        @Id UUID id,
        int contributors,
        String language,
        @Column("language_type") String languageType,
        String region,
        int year,
        int quarter,
        @Column("created_at") Instant createdAt,
        @Column("updated_at") Instant updatedAt
) {
}
