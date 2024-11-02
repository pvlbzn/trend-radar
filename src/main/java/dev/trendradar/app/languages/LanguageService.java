package dev.trendradar.app.languages;

import org.springframework.stereotype.Service;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public void add(int contributors, String language, String languageType, String region, int year, int quarter) {
        var l = new Language(
                null, contributors, language, languageType, region, year, quarter, null, null);
        languageRepository.save(l);
    }
}
