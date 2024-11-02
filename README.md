# Structure

Package by a feature, then by layer within the feature.

# Commands

## Upload data

To upload data from `resources/data` directory run the following command. 

```shell
mvn spring-boot:run -Dspring-boot.run.arguments=upload
```

It executes `DataUploadCommand` from `trendradar/app/cmd/` directory. Data uploader command is `CommandLineRunner`, therefore part of the Spring application. Such approach allows to inject services into CLI via IoC avoiding a need to write stand-alone database utilities.
