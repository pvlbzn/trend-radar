package dev.trendradar.app.languages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class LanguageService {
    private static final Logger log = LogManager.getLogger(LanguageService.class);
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public void add(LanguageDTO.Language item) {
        var l = new Language(
                null,
                item.contributors(),
                item.language(),
                item.languageType(),
                item.region(),
                item.year(),
                item.quarter(),
                null,
                null);
        languageRepository.save(l);
    }
}
