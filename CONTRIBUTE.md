# Contributing to Project Peak

Thank you for considering contributing to Project Peak! We welcome all contributions, including bug fixes, feature implementations, and documentation improvements.
Here are some guidelines for contributing. 

## Technologies Used

#### Project Peak is built using the following technologies:

* Java
* Spring Boot
* Spring Web
* MySQL
* HTML
* CSS
* Tailwind
* Thymeleaf
* Docker
* Tomcat

## Getting Started
We are using MySQL database, here are a few steps to get MySQL up and running on your computer 

Download MySQL: https://dev.mysql.com/downloads/mysql/ verson: 8.0.30 <br>
Download MySQL Workbench: https://dev.mysql.com/downloads/workbench/ version: 8.0.30

Now, on MySQL Workbench open a new tab and run this script: 

src/mysql/init/1-schema.sql 

Now, you have access to MySQL database, and you can now create a user in Project Peak! <br>
Feel free to try our test client also

When you have downloaded MySQL and runned the script, then you have to modify the fields in application.properties

```
url=jdbc:mysql://localhost:3306/projectPeak
username=root
password=root

```

#### To get started contributing to project Peak, you'll need to:

1. Fork the repository
2. Clone your forked repository to your local machine
3. Create a new branch for your contribution
4. Make changes and commit them to your branch
5. Push your branch to your forked repository
6. Open a pull request

## Pull Requests

#### Before submitting a pull request, please make sure:

* Your code follows our code style guidelines
* Your code passes our unit tests
* You have updated the project documentation as necessary

## Code Style Guidelines

* We follow the Google Java Style Guide for Java code
* We follow the HTML and CSS Style Guide for HTML and CSS code

## Unit Tests

We use JUnit 5 for unit testing. To run the unit tests, run mvn test.

## Reporting Bugs

If you find a bug in Project Peak, please report it by opening an issue in the project's GitHub repository. Be sure to include a detailed description of the bug, steps to reproduce it, and any relevant code or error messages.

## Feature Requests

If you have an idea for a new feature for Project Peak, please open an issue in the project's GitHub repository. Be sure to include a detailed description of the feature, as well as any relevant use cases or code examples.

Thank you for contributing to Project Peak!
