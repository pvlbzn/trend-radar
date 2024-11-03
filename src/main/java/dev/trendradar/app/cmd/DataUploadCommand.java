package dev.trendradar.app.cmd;

import dev.trendradar.app.languages.LanguageDTO;
import dev.trendradar.app.languages.LanguageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.InvalidPathException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;


@Component
public class DataUploadCommand implements CommandLineRunner {
    private static final Logger log = LogManager.getLogger(DataUploadCommand.class);

    private final LanguageService languageService;

    public DataUploadCommand(LanguageService languageService) {
        this.languageService = languageService;
    }

    record LanguageDataEntry(int pushers, String language, String type, String region, int year, int quarter) {
        /**
         * Serialize from CSV line into {@code LanguageDataEntry} record.
         * <p>
         * Expected data shape is:
         *
         * <pre>
         * num_pushers,language,language_type,iso2_code,year,quarter
         * </pre>
         *
         * @param row entry to be serialized.
         * @return optional of {@code LanguageDataEntry}. In case if some error occurs during
         * serialization optional will be empty. While working with semi-structured data it should
         * be expected that some fields will be malformed or missing.
         */
        public static Optional<LanguageDataEntry> serializeFrom(String row) {
            var items = row.split(",");

            // Expected item count is 6, if some data point is not valid return empty
            // optional for it.
            if (items.length != 6) {

                return Optional.empty();
            }

            try {
                return Optional.of(new LanguageDataEntry(Integer.parseInt(items[0]), items[1], items[2], items[3], Integer.parseInt(items[4]), Integer.parseInt(items[5])));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
    }

    @Override
    public void run(String... args) {
        if (args.length > 0 && "upload".equals(args[0])) {
            exec();
        }
    }

    private void exec() {
        log.info("Uploading data.");

        long t1 = System.nanoTime();
        var itemsProcessed = forCsv("data/languages.csv", this::uploadToDatabase);
        long elapsed = (System.nanoTime() - t1) / 1_000_000;

        log.info("{} items uploaded in {}ms", itemsProcessed, elapsed);
    }

    // Load `.csv` file from resources and process it.
    private int forCsv(String resourceName, Consumer<LanguageDataEntry> fn) {
        try {
            var resource = new ClassPathResource(resourceName);
            var stream = resource.getInputStream();
            var reader = new BufferedReader(new InputStreamReader(stream));

            var csv = reader.lines()
                    // Skip the header line
                    .skip(1)
                    .map(LanguageDataEntry::serializeFrom)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            // Don't forget to free the resources.
            stream.close();

            var counter = new AtomicInteger();
            csv
                    .stream()
                    .parallel()
                    .forEach(row -> {
                        fn.accept(row);
                        counter.getAndIncrement();
                    });

            return counter.get();
        } catch (IOException | SecurityException | InvalidPathException e) {
            // All exceptions here are terminal, log and exit.
            log.error(e);
            System.exit(1);

            return -1;
        }
    }

    private void uploadToDatabase(LanguageDataEntry entry) {
        var data = new LanguageDTO.Language(
                entry.pushers, entry.language, entry.type, entry.region, entry.year, entry.quarter);
        languageService.add(data);
    }
}
