### About
This is a [programming exercise](doc/exercise.md) implemented in Kotlin using this [solution](doc/solution.md).

### Install 
#### 1. Directly on your OS:
1. Install [Java JDK](https://adoptopenjdk.net) version 16 or higher (to check, run: `java -version`)
2. Open a command line in the project dir, and run: `./gradlew build`
#### 2. Or on a container:
1. Install a container engine with [Docker Desktop](https://docs.docker.com/get-docker/) or [Rancher Desktop](https://rancherdesktop.io/) (to check run: `docker version`)
2. Open a command line in the project dir, and run:
   ```shell
   docker-compose run --name scores scores
   ./gradlew build
   ```
---
<details>
  <summary>⚠️️ <b>NOTE:</b> <i>If you want to create the image and the container manually...</i></summary>

```shell
docker build -t scores .
docker run --name scores -it -v `pwd`/src:/app/src:ro -v `pwd`/build:/app/build:rw scores sh
```
* To start an exited container: `docker start -ai scores`
* To delete the container: `docker rm scores`
* To delete the image: `docker image rm scores`
* The `src` folder is shared so changes in the source code made on the host OS can be compiled and run in the container
* The `build` folder is shared so all reports and binaries generated from the container are also available in the host OS 
</details>

---
    
### Run
* To run the program with default parameters: `./gradlew run`
* To run the program with specific params: `./gradlew run --args='--impl apdlll.scores.impl.def.ScoresImpl src/test/resources/tournament1'`

### Test
* To run unit tests: `./gradlew test`
  * Reports generated at: `./build/reports/tests/test/`
* To run a specific test: `./gradlew test --tests "*write an empty list of scores to JSON"`    
* To run integration tests: `./gradlew inttest`

### Coverage
* To check tests coverage reports: `./gradlew coverage`
  * Reports generated at: `./build/reports/cover/`

### Style
* To check style of main source, unit tests and scripts code: `./gradlew checkstyle`
  * Reports generated at: `./build/reports/style/`
* To fix style issues automatically: `./gradlew fixstyle`

### Code analysis
* To analyze code smells and complexity: `./gradlew checkcode`
  * Reports generated at: `./build/reports/code/`
* To customize rules: `./gradlew detektGenerateConfig`
  * Config file generated at: `./config/detekt/detekt.yml`

### Dependencies
* To check dependencies updates: `./gradlew deps`
  * Reports generated at: `./build/reports/deps/`

### Vulnerabilities
* To check vulnerabilities in project dependencies: `./gradlew vul`
  * Reports generated at: `./build/reports/vulnerabilities/`

### Doc
* To generate the source code doc: `./gradlew doc`
  * HTML generated at: `./build/sources-doc/index.html`

### Distribution
* To generate the distribution file: `./gradlew assemble`
  * TAR file generated at: `./build/distributions/`
  * To install and run from the distribution files:
    ```shell
    tar xvf scores-1.0.tar 
    cd scores-1.0/bin
    ./scores
    ```
