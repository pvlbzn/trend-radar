# Trend Radar API

Data is being served by the main API endpoint

```text
GET /api/v1/languages
```

String language, String region, String year, String quarter, String type
Which supports following query string parameters in **any order and in any combination**:

1. `language`: language search, e.g. `Scala`
2. `region`: ISO2 region code, that is a two-character region code such as `BR`
3. `year`: data point year
4. `quarter`: data point quarter
5. `type`: language type, supported types are: `[ prose| programming | data | markup ]`

API supports search by any combination of those 5 parameters, supporting $2^5$ unique queries. To get all the data at
once leave query string empty.

## Examples

For example lets fetch trend of Haskell language in Canada during 3rd quarter of 2021. Here is our query which satisfies
the search. Order of parameters doesn't matter.

```text
GET /api/v1/languages?language=&region=&year=&quarter=
```

Let's fill in the data with concrete search terms.

```text
GET /api/v1/languages?language=Haskell&region=CA&year=2021&quarter=3
```

Response:

```json
{
  "meta": {
    "code": 200
  },
  "data": [
    {
      "contributors": 539,
      "language": "Haskell",
      "languageType": "programming",
      "region": "CA",
      "year": 2021,
      "quarter": 3
    }
  ]
}
```

Now lets get trends for Elixir language in France in 2023:

```text
GET /api/v1/languages?language=Elixir&region=FR&year=2023
```

```json
{
  "meta": {
    "code": 200
  },
  "data": [
    {
      "contributors": 493,
      "language": "Elixir",
      "languageType": "programming",
      "region": "FR",
      "year": 2023,
      "quarter": 4
    },
    {
      "contributors": 346,
      "language": "Elixir",
      "languageType": "programming",
      "region": "FR",
      "year": 2023,
      "quarter": 3
    },
    {
      "contributors": 329,
      "language": "Elixir",
      "languageType": "programming",
      "region": "FR",
      "year": 2023,
      "quarter": 2
    },
    {
      "contributors": 318,
      "language": "Elixir",
      "languageType": "programming",
      "region": "FR",
      "year": 2023,
      "quarter": 1
    }
  ]
}
```

# Application Design & Structure

Package by a feature, then by layer within the feature.

# Performance

Underlying dataset has more than 110,000 records, and querying it might be computationally intensive. Working with data
driven applications requires a high responsiveness of search queries. Trend Radar uses query caching at service level
allowing typical **request to be served under 10ms** after caching.

| Request  | Latency |
|----------|---------|
| Uncached | 40ms    |
| Cached   | 6ms     |

Caching allows to have fast response times ensuring the best user experience on client side while avoiding non-necessary
connections to the database, at the expense of runtime memory.

# Commands

## Upload data

To upload data from `resources/data` directory run the following command.

```shell
mvn spring-boot:run -Dspring-boot.run.arguments=upload
```

It executes `DataUploadCommand` from `trendradar/app/cmd/` directory. Data uploader command is `CommandLineRunner`,
therefore part of the Spring application. Such approach allows to inject services into CLI via IoC avoiding a need to
write stand-alone database utilities.

Data upload runs in parallel via `.parallelStream`, therefore mind thread safety. `languages.csv` dataset contains 110K+
rows sufficiently big to benefit from parallelism overhead.

Parallel stream approach is optimal in terms of performance gain to added code complexity. Performance at expense of
complexity is a non-goal.

| Execution        | TPS   | Runtime (ms) | Change (%) |
|------------------|-------|--------------|------------|
| Sequential       | 1,414 | 77,343       | baseline   | 
| `saveAll`        | 4,642 | 26,061       | 196.7      |
| `parallelStream` | 4,856 | 22,380       | 245.5      |

Notes: i) TPS stands for transactions per second, ii) change computed by percentage change formula, iii) hardware Apple
M1 Pro