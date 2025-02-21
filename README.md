# Wikipedia Google Sheet API

This project is a Spring Boot application that parses championship data from Wikipedia and writes it to a Google Sheet using the Google Sheets API.

## Prerequisites

- Java 11 or higher
- Maven
- JSOUP Library
- Google Sheets API credentials

## Setup

1. **Clone the repository:**

    ```sh
    git clone https://github.com/yourusername/wikipedia_google_api.git
    cd wikipedia_google_api
    ```

2. **Add Google Sheets API credentials:**

    Place your `credentials.json` file in the `src/main/resources` directory.


3. **Install dependencies:**

    ```sh
    mvn clean install
    ```

## Running the Application

To run the application, use the following command:

```sh
mvn spring-boot:run

## References
- [Google Sheets API](https://developers.google.com/sheets/api)
- [JSOUP](https://jsoup.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Wikipedia](https://www.wikipedia.org/)
- [Maven](https://maven.apache.org/)
- [Java](https://www.java.com/)