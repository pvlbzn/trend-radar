package dev.trendradar.app.languages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Map.entry;


@Service
public class LanguageService {
    private static final Logger log = LogManager.getLogger(LanguageService.class);
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;

    }

    public void add(int contributors, String language, String type, String region, int year, int quarter) {
        languageRepository.save(new Language(
                null,
                contributors,
                language,
                type,
                region,
                year,
                quarter,
                new Date(),
                new Date()));
    }

    private String generateFilterFingerprint(List<Boolean> fingerprint, List<String> parameters) throws IllegalArgumentException {
        // Fingerprint cardinality must be the same as parameters cardinality.
        if (fingerprint.size() != parameters.size()) {
            throw new IllegalArgumentException(
                    String.format(
                            "The number of fingerprints, %d, does not match the number of parameters, %d.",
                            fingerprint.size(),
                            parameters.size()));
        }

        // Loop over each fingerprint state
        // Match it to parameters
        var filterFingerprint = new StringBuilder();

        for (int i = 0; i < fingerprint.size(); i++) {
            if (fingerprint.get(i)) {
                filterFingerprint
                        .append(parameters.get(i))
                        .append(":");
            }
        }

        var query = filterFingerprint.toString();

        return query.isEmpty() ? "" : query.substring(0, query.length() - 1);
    }

    @Cacheable(value = "languagesCache", key = "T(java.util.Objects).hash(#language, #region, #year, #quarter, #type)")
    public List<LanguageDTO.Language> getLanguages(String language, String region, String year, String quarter, String type) {
        // List of accepted parameters
        var parameters = List.of("language", "region", "year", "quarter", "type");

        // Fingerprint as a boolean vector so that every vector state identifies associated operation, e.g.
        // `1 0 1 0 0` means filter by language, and year
        // `0 1 1 0 1` means filter by region, year and type
        var fingerprint = List.of(
                !language.isEmpty(),
                !region.isEmpty(),
                !year.isEmpty(),
                !quarter.isEmpty(),
                !type.isEmpty());
        // Generate query for the fingerprint. That is, `1 0 1 0 0` becomes `language:year` query.
        var query = generateFilterFingerprint(fingerprint, parameters);

        log.debug("fingerprint: {}", fingerprint);
        log.debug("query: {}", query);

        var nYear = year.isEmpty() ? 0 : Integer.parseInt(year);
        var nQuarter = quarter.isEmpty() ? 0 : Integer.parseInt(quarter);
        var order = Sort.by(Sort.Direction.DESC, "contributors");

        // Exhaustive map of unique fingerprint combinations (2^(N_PARAMS)) for filtering
        Map<String, Supplier<List<Language>>> conditionMap = Map.ofEntries(
                entry("language:region:year:quarter:type", () -> languageRepository
                        .getLanguagesByLanguageAndRegionAndYearAndQuarterAndLanguageType(
                                language, region, nYear, nQuarter, type, order)),
                entry("language:region:year:quarter", () -> languageRepository
                        .getLanguagesByLanguageAndRegionAndYearAndQuarter(
                                language, region, nYear, nQuarter, order)),
                entry("language:region:year:type", () -> languageRepository
                        .getLanguagesByLanguageAndRegionAndYearAndLanguageType(
                                language, region, nYear, type, order)),
                entry("language:region:year", () -> languageRepository
                        .getLanguagesByLanguageAndRegionAndYear(
                                language, region, nYear, order)),
                entry("language:region:quarter:type", () -> languageRepository
                        .getLanguagesByLanguageAndRegionAndQuarterAndLanguageType(
                                language, region, nQuarter, type, order)),
                entry("language:region:quarter", () -> languageRepository
                        .getLanguagesByLanguageAndRegionAndQuarter(
                                language, region, nQuarter, order)),
                entry("language:region:type", () -> languageRepository
                        .getLanguagesByLanguageAndRegionAndLanguageType(
                                language, region, type, order)),
                entry("language:region", () -> languageRepository
                        .getLanguagesByLanguageAndRegion(
                                language, region, order)),
                entry("language:year:quarter:type", () -> languageRepository
                        .getLanguagesByLanguageAndYearAndQuarterAndLanguageType(
                                language, nYear, nQuarter, type, order)),
                entry("language:year:quarter", () -> languageRepository
                        .getLanguagesByLanguageAndYearAndQuarter(
                                language, nYear, nQuarter, order)),
                entry("language:year:type", () -> languageRepository
                        .getLanguagesByLanguageAndYearAndLanguageType(
                                language, nYear, type, order)),
                entry("language:year", () -> languageRepository
                        .getLanguagesByLanguageAndYear(
                                language, nYear, order)),
                entry("language:quarter:type", () -> languageRepository
                        .getLanguagesByLanguageAndQuarterAndLanguageType(
                                language, nQuarter, type, order)),
                entry("language:quarter", () -> languageRepository
                        .getLanguagesByLanguageAndQuarter(
                                language, nQuarter, order)),
                entry("language:type", () -> languageRepository
                        .getLanguagesByLanguageAndLanguageType(
                                language, type, order)),
                entry("language", () -> languageRepository
                        .getLanguagesByLanguage(language, order)),
                entry("region:year:quarter:type", () -> languageRepository
                        .getLanguagesByRegionAndYearAndQuarterAndLanguageType(
                                region, nYear, nQuarter, type, order)),
                entry("region:year:quarter", () -> languageRepository
                        .getLanguagesByRegionAndYearAndQuarter(
                                region, nYear, nQuarter, order)),
                entry("region:year:type", () -> languageRepository
                        .getLanguagesByRegionAndYearAndLanguageType(
                                region, nYear, type, order)),
                entry("region:year", () -> languageRepository
                        .getLanguagesByRegionAndYear(
                                region, nYear, order)),
                entry("region:quarter:type", () -> languageRepository
                        .getLanguagesByRegionAndQuarterAndLanguageType(
                                region, nQuarter, type, order)),
                entry("region:quarter", () -> languageRepository
                        .getLanguagesByRegionAndQuarter(
                                region, nQuarter, order)),
                entry("region:type", () -> languageRepository
                        .getLanguagesByRegionAndLanguageType(
                                region, type, order)),
                entry("region", () -> languageRepository
                        .getLanguagesByRegion(region, order)),
                entry("year:quarter:type", () -> languageRepository
                        .getLanguagesByYearAndQuarterAndLanguageType(
                                nYear, nQuarter, type, order)),
                entry("year:quarter", () -> languageRepository
                        .getLanguagesByYearAndQuarter(
                                nYear, nQuarter, order)),
                entry("year:type", () -> languageRepository
                        .getLanguagesByYearAndLanguageType(
                                nYear, type, order)),
                entry("year", () -> languageRepository
                        .getLanguagesByYear(
                                nYear, order)),
                entry("quarter:type", () -> languageRepository
                        .getLanguagesByQuarterAndLanguageType(
                                nQuarter, type, order)),
                entry("quarter", () -> languageRepository
                        .getLanguagesByQuarter(
                                nQuarter, order)),
                entry("type", () -> languageRepository
                        .getLanguagesByLanguageType(type, order)),
                entry("", languageRepository::getAllByOrderByContributorsDesc)
        );

        var languages = conditionMap
                .getOrDefault(query, languageRepository::getAllByOrderByContributorsDesc)
                .get();

        return languages
                .stream()
                .map(LanguageDTO.Mapper::toDTO)
                .toList();
    }
}
