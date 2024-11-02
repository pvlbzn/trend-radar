package dev.trendradar.app.languages;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface LanguageRepository extends CrudRepository<Language, UUID> {
}
