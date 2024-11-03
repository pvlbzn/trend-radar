package dev.trendradar.app.languages;

public class LanguageDTO {
    public record Language(
            int contributors,
            String language,
            String languageType,
            String region,
            int year,
            int quarter) {
    }
}
