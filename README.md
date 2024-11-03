# Structure

Package by a feature, then by layer within the feature.

# Commands

## Upload data

To upload data from `resources/data` directory run the following command.

```shell
mvn spring-boot:run -Dspring-boot.run.arguments=upload
```

It executes `DataUploadCommand` from `trendradar/app/cmd/` directory. Data uploader command is `CommandLineRunner`, therefore part of the Spring application. Such approach allows to inject services into CLI via IoC avoiding a need to write stand-alone database utilities.

Data upload runs in parallel via `.parallelStream`, therefore mind thread safety. `languages.csv` dataset contains 110K+ rows sufficiently big to benefit from parallelism overhead.

Parallel stream approach is optimal in terms of performance gain to added code complexity. Performance at expense of complexity is a non-goal.

| Execution        | TPS   | Runtime (ms) | Change (%) |
|------------------|-------|--------------|------------|
| Sequential       | 1,414 | 77,343       | baseline   | 
| `saveAll`        | 4,642 | 26,061       | 196.7      |
| `parallelStream` | 4,856 | 22,380       | 245.5      |

Notes: i) TPS stands for transactions per second, ii) change computed by percentage change formula, iii) hardware Apple M1 Pro