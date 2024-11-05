package dev.trendradar.app.languages;

import dev.trendradar.app.common.http.JsonResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
class LanguageController {
    private static final Logger log = LogManager.getLogger(LanguageController.class);
    private final LanguageService languageService;

    LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping(value = "/api/v1/languages")
    ResponseEntity<?> getLanguage(
            @RequestParam(required = false) Optional<String> language,
            @RequestParam(required = false) Optional<String> region,
            @RequestParam(required = false) Optional<String> year,
            @RequestParam(required = false) Optional<String> quarter,
            @RequestParam(required = false) Optional<String> type
    ) {
        var languages = languageService.getLanguages(
                language.orElse(""),
                region.orElse(""),
                year.orElse(""),
                quarter.orElse(""),
                type.orElse(""));

        return new JsonResponse<>()
                .status(HttpStatus.OK)
                .body(languages)
                .success();
    }
}
