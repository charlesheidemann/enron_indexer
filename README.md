# Enron Data Import Porjet #

## Technologies used in the project

* Oracle/Open JDK 1.8 (must be pre-installed).
* Maven 3.3+. (must be pre-installed).
* Elasticsearch 2.4.5 (must be pre-installed).
* [Jest](https://github.com/searchbox-io/Jest/) rest client for ElasticSearch.
* [Jcommander](https://github.com/cbeust/jcommander/) command line parameter parsing.
* [Log4j 2](http://logging.apache.org/log4j/) for logging.
* [JUnit](http://junit.org/junit4/) for unit testing.
* [Mockito](http://mockito.org/) for mocked testing.

## How to Build the Project

This project uses the [Maven](https://maven.apache.org/) build system, you can build the project locally. 
Go to the `./code/enron_indexer` and just typing the following command in the console:

```

mvn clean package

```

## How to Run the Project

Go the `./code/enron_indexer/target` and typing the following command in the console:

```
java -jar enron_indexer.jar -i "file.path"

```

### Usage Options:

```
Usage: <main class> [options]
  Options:
    --elasticsearchHost, -e
      Elasticsearch host
      Default: http://127.0.0.1:9200
    --elasticsearchMaxConnection, -c
      Max HTTP connections to Elasticsearch
      Default: 25
  * --input, -i
      File to input
    --verbose, -v
      Verbose
      Default: false
```

### Logs:

 Import process log is available at `./logs/import.log`