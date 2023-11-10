# Shopping Cart Checkout Application

Welcome to the Shopping Cart Checkout application. This application was developed using Java 17, Maven, and Spring Boot. It incorporates REST API, Clean Code, SOLID principles, Object-Oriented Programming (OOP), Test-Driven Development (TDD), and Domain-Driven Design (DDD) concepts.

The core objective of the project is to develop the shopping cart in an e-commerce application while adhering to business rules. The shopping cart object allows items to be added, and these items can also include Value Added Services (VAS), such as insurance and assembly services. Depending on the items added, the system provides the user with the most profitable promotions.

## Technologies and Tools

- Java 17
- Spring Boot 3.1.5
- Maven
- RESTful API
- Lombok, MapStruct, ApplicationEventPublisher
- Junit5, Mockito, Spring Test
- Swagger
- Github Actions

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java 17**: You can download and install Java 17 from the official website [here](https://www.oracle.com/java/technologies/javase-downloads.html).

- **Maven**: Maven is used for project management and build automation. You can download it [here](https://maven.apache.org/download.cgi).

## Installation

To get this project up and running on your local machine, follow these steps:


   ```bash
   git clone https://github.com/oguzdursun06/checkout-app.git
   ```
Navigate to the project folder.
   ```bash
   cd project
   ```

At this stage, you can use four different methods:
----
#### Build and run the application using Maven:

   ```bash
mvn clean package
java -jar target/checkout-0.0.1-SNAPSHOT.jar
   ```
***
#### Run the application using Spring Boot:
   ```bash
mvn spring-boot:run
   ```
***
#### Using Dockerfile
To compile the project and create a Docker image, run the following command:
   ```bash
docker build -t checkout-oguzhandursun .
   ```
You can use the following command to run the created Docker image:
   ```bash
docker run -d --name checkout-oguzhandursun -p 8080:8080 checkout-oguzhandursun
   ```
***
#### Using docker compose

   ```bash
docker-compose up -d
   ```
---


## Usage

Once the project is running, access it in your web browser at [http://localhost:8080/api](http://localhost:8080/api). You can interact with the API to perform various actions, such as:
- **POST** &emsp;  /api/v1/cart/items  &emsp;  Add item to cart
- **DELETE** &ensp; /api/v1/cart/items  &ensp;  Remove item from cart
- **POST** &emsp; /api/v1/cart/items/vas-items   &ensp; Add VAS item to item
- **DELETE** &emsp; /api/v1/cart    &emsp; &emsp;  Reset Cart
- **GET** &emsp;/api/v1/cart   &emsp; &emsp; &emsp; &ensp;  Display Cart

For more detailed information and API documentation, you can use the Swagger interface. You can access API documentation through [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html). Swagger UI allows you to view documents that define our APIs, and it also provides a testing environment.


## Contact

If you have any inquiries or feedback regarding the project, feel free to reach out:
- Linkedin: [https://www.linkedin.com/in/oguzhandursun/](https://www.linkedin.com/in/oguzhandursun/)
