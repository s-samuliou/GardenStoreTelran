# Gardenshop

Gardenshop is a backend part of web application for an online store selling gardening products. It allows users to browse, select, and order products for their garden.

## Installation

1. Clone the repository using the following command:

    ```
    git clone https://github.com/yourusername/gardenshop.git
    ```
2. Change data of your database in application.properties

   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/{database_name}
   spring.datasource.username={datebase_username}
   spring.datasource.password={database_password}
   ```
3. If you wish, you can change the starting data for the database via Liquibase in file with the path:

   ```
   src.main.java.resources.db.changelog
      ```   

4. Navigate to the GardenApp:

    ```
    cd src.main.java.org.garden.com.GardenApp
    ```

5. Install dependencies using Maven:

    ```
    mvn clean install
    ```

6. Run the application:

    ```
    mvn spring-boot:run
    ```

## Usage

1. After successful startup, open a web browser and go to `http://localhost:8080`.
2. Register if you are a new user, or log in if you are already registered.
3. You can also use Postman to check all the necessary methods 


## Technologies

- Java
- Spring Boot
- Spring Security
- Maven
