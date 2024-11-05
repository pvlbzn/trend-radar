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

    public static class Mapper {
        public static Language toDTO(dev.trendradar.app.languages.Language l) {
            return new Language(
                    l.contributors(),
                    l.language(),
                    l.languageType(),
                    l.region(),
                    l.year(),
                    l.quarter()
            );
        }
    }
}
