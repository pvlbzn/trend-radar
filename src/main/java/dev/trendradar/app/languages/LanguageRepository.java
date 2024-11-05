package dev.trendradar.app.languages;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/// Spring Data JDBC Language repository.
/// All queries get sorted by `Sort` parameter to not have hardcoded rules such
/// as `OrderByContributorsDesc` for each method.
interface LanguageRepository extends CrudRepository<Language, UUID> {
    /// Method for `language:region:year:quarter:type` query filter.
    List<Language> getLanguagesByLanguageAndRegionAndYearAndQuarterAndLanguageType(
            String language, String region, int year, int quarter, String languageType, Sort sort);

    /// Method for `language:region:year:quarter` query filter.
    List<Language> getLanguagesByLanguageAndRegionAndYearAndQuarter(
            String language, String region, int year, int quarter, Sort sort);

    /// Method for `language:region:year:type` query filter.
    List<Language> getLanguagesByLanguageAndRegionAndYearAndLanguageType(
            String language, String region, int year, String languageType, Sort sort);

    /// Method for `language:region:year` query filter.
    List<Language> getLanguagesByLanguageAndRegionAndYear(
            String language, String region, int year, Sort sort);

    /// Method for `language:region:quarter:type` query filter.
    List<Language> getLanguagesByLanguageAndRegionAndQuarterAndLanguageType(
            String language, String region, int quarter, String languageType, Sort sort);

    /// Method for `language:region:quarter` query filter.
    List<Language> getLanguagesByLanguageAndRegionAndQuarter(
            String language, String region, int quarter, Sort sort);

    /// Method for `language:region:type` query filter.
    List<Language> getLanguagesByLanguageAndRegionAndLanguageType(
            String language, String region, String languageType, Sort sort);

    /// Method for `language:region` query filter.
    List<Language> getLanguagesByLanguageAndRegion(
            String language, String region, Sort sort);

    /// Method for `language:year:quarter:type` query filter.
    List<Language> getLanguagesByLanguageAndYearAndQuarterAndLanguageType(
            String language, int year, int quarter, String languageType, Sort sort);

    /// Method for `language:year:quarter` query filter.
    List<Language> getLanguagesByLanguageAndYearAndQuarter(
            String language, int year, int quarter, Sort sort);

    /// Method for `language:year:type` query filter.
    List<Language> getLanguagesByLanguageAndYearAndLanguageType(
            String language, int year, String languageType, Sort sort);

    /// Method for `language:year` query filter.
    List<Language> getLanguagesByLanguageAndYear(
            String language, int year, Sort sort);

    /// Method for `language:quarter:type` query filter.
    List<Language> getLanguagesByLanguageAndQuarterAndLanguageType(
            String language, int quarter, String languageType, Sort sort);

    /// Method for `language:quarter` query filter.
    List<Language> getLanguagesByLanguageAndQuarter(
            String language, int quarter, Sort sort);

    /// Method for `language:type` query filter.
    List<Language> getLanguagesByLanguageAndLanguageType(
            String language, String languageType, Sort sort);

    /// Method for `language` query filter.
    List<Language> getLanguagesByLanguage(
            String language, Sort sort);

    /// Method for `region:year:quarter:type` query filter.
    List<Language> getLanguagesByRegionAndYearAndQuarterAndLanguageType(
            String region, int year, int quarter, String languageType, Sort sort);

    /// Method for `region:year:quarter` query filter.
    List<Language> getLanguagesByRegionAndYearAndQuarter(
            String region, int year, int quarter, Sort sort);

    /// Method for `region:year:type` query filter.
    List<Language> getLanguagesByRegionAndYearAndLanguageType(
            String region, int year, String languageType, Sort sort);

    /// Method for `region:year` query filter.
    List<Language> getLanguagesByRegionAndYear(
            String region, int year, Sort sort);

    /// Method for `region:quarter:type` query filter.
    List<Language> getLanguagesByRegionAndQuarterAndLanguageType(
            String region, int quarter, String languageType, Sort sort);

    /// Method for `region:quarter` query filter.
    List<Language> getLanguagesByRegionAndQuarter(
            String region, int quarter, Sort sort);

    /// Method for `region:type` query filter.
    List<Language> getLanguagesByRegionAndLanguageType(
            String region, String languageType, Sort sort);

    /// Method for `region` query filter.
    List<Language> getLanguagesByRegion(
            String region, Sort sort);

    /// Method for `year:quarter:type` query filter.
    List<Language> getLanguagesByYearAndQuarterAndLanguageType(
            int year, int quarter, String languageType, Sort sort);

    /// Method for `year:quarter` query filter.
    List<Language> getLanguagesByYearAndQuarter(
            int year, int quarter, Sort sort);

    /// Method for `year:type` query filter.
    List<Language> getLanguagesByYearAndLanguageType(
            int year, String languageType, Sort sort);

    /// Method for `year` query filter.
    List<Language> getLanguagesByYear(
            int year, Sort sort);

    /// Method for `quarter:type` query filter.
    List<Language> getLanguagesByQuarterAndLanguageType(
            int quarter, String languageType, Sort sort);

    /// Method for `quarter` query filter.
    List<Language> getLanguagesByQuarter(
            int quarter, Sort sort);

    /// Method for `type` query filter.
    List<Language> getLanguagesByLanguageType(
            String languageType, Sort sort);

    /// Empty query filter.
    List<Language> getAllByOrderByContributorsDesc();
}
