package dev.trendradar.app.languages;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table("languages")
record Language(
        @Id UUID id,
        int contributors,
        String language,
        @Column("language_type") String languageType,
        String region,
        int year,
        int quarter,
        @Column("created_at") Date createdAt,
        @Column("updated_at") Date updatedAt
) {
}
