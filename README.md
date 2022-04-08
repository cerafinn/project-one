# Expense Reimbursement Service

## Description
The application is designed to track and manage reimbursement requests for employees as administered by finance managers. Employees are able to view their own submitted reimbursements as well as submit new reimbursements to the system. Finance managers are able to view all reimbursements for all employees, along with being able to approve or deny pending reimbursements within the system.



Table of Contents |
-------------------|
[Technologies](#Technologies)
[Features](#Features)
[Getting Started](#Getting-Started)
[Screenshots](#Screenshots)
[Tests](#Tests)
[Questions](#Questions)

<br />

## Technologies

* HTML
* CSS
* Bulma
* JavaScript
* SQL
* Java
* Javalin
* Mockito
* JUnit
* JDBC

<br />

## Features

- Users are able to login to the application and directed to the reimbursement page for employees or finance managers depending on the user's role.
-Employees are able to view their own previous reimbursements and submit new reimbursements to the system, as well as upload receipts.
- Managers are able to view all reimbursements regardless of user and are able to approve or deny pending reimbursements.

TO-DO:
- Include dates on the reimbursement table for employees and managers
- Add modal to view the receipt at higher resolution

<br />

## Getting Started

In order to run the project locally, first clone the repository by entering the command within the command line:

```git clone https://github.com/cerafinn/project-one.git```

 The project requires the following envrionment variable to be set up first prior to running the project:

- Within the Java backend, the username, password and url for the database
- For the frontend, the prefix URL may need to be altered depending on where the project is being hosted.

<br />

## Screenshots

![IMG](./assets/images/project-one-screenshot.png)

<br />

## Tests

Tests for the project have been done for the backend. These test are built using JUnit and Mockito, localled within the backend test folder. They can be run upon opening the program in Intellij and running the tests manually, with or without coverage. Currently the tests cover the UserSerivce.

<br />

## Questions

If you have any questions or issues, feel free to reach out. You can find more of my work on [my profile](https://github.com/cerafinn).