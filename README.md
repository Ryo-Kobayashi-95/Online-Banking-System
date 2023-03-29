# Personal Project for CPSC 210

Ryo Kobayashi

Student ID: 60536133

## Online Banking Management System

#### *About the system*
This online banking management system allows users to perform
various tasks related to their banking accounts and transactions
without visiting the branches. Through the system, the users can
create a checking and/or saving account and set a password to log in.
After creating the account, users can deposit & withdraw money,
view their balance, and transfer money between existing accounts.
The saving account has several interest rates (simple or compound)
available depending on their status, and the users can set a savings goal
within a specified time frame. The system calculates how much they need
to save each month to reach that goal and provides a brief recommendation for the users.

#### *Who will use it?*
Anyone can create an account and access the system remotely.

#### *Why this project?*
As a finance student, I wanted to work on a project that is related
to finance, and this banking system project allows me to apply my knowledge.
Also, by creating the system myself, I can cultivate a deeper
understanding of the banking system.

#### *User Stories*:
- As a user, I want to be able to create my account 
- As a user, I want to be able to deposit money into my account
- As a user, I want to be able to withdraw money from my account
- As a user, I want to be able to record my transaction history on my account
- As a user, I want to be able to save all accounts created (with username, password, and chequing & saving balance)
and transaction history associated with each account to file
- As a user, I want to be able to load the list of accounts and transaction history from file

#### *Instructions for Grader*:
- You can generate the first required action related to adding a new account to the account 
list by clicking the "Create" button.
  - After logging into the account, you can perform transactions with the "Deposit" and "Withdraw" buttons.
- You can generate the second required action related to displaying transaction history with options for the user to 
filter the history; show all history, deposit account history, or saving account history. You can select the 
option from the dropdown box and clicking the "View transaction history" button.
- You can locate my visual component by running the application. It appears on the splash screen.
- You can save the state of my application by clicking the "Save" button in the main menu.
- You can reload the state of my application by clicking the "Load" button in the main menu.
