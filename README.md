# Online Banking Management System

Published: Aug 23, 2023

Developer: Ryo Kobayashi

<br/>

This project employs:
- Language: Java
- Platform: IntelliJ IDEA

<br/>

**To run the program:** *src → main → ui → Main.java*

![Alt Text](https://media.giphy.com/media/eFDAUTDYWluxiZt5B3/giphy.gif)

<br/>

## *Abstract*
This online banking management system provides users with the convenience of conducting an extensive range of banking activities pertaining to their accounts and transactions, all within the confines of their digital realm. This platform empowers users to establish both chequing and saving accounts, ensuring each account is fortified with a personalized password to provide seamless and secure access. Subsequent to the successful establishment of their accounts, users are equipped to perform tasks such as fund deposits, withdrawals, and balance inquiries with utmost ease. Distinguished by meticulous attention to detail, the system maintains a robust architecture that adeptly manages and mitigates any potential instances of invalid or unauthorized activities.

## *Target Audience*
The system is designed for universal accessibility, catering to a broad demographic of users who can effortlessly establish an account and engage in various transactions from any remote location.
## *Project Motivation*
Motivated by my background as a finance student, I aspired to embark on a project with intrinsic ties to the financial domain. The endeavour of developing this banking system project affords me the opportunity to tangibly apply and leverage my academic insights. Additionally, it enables me to cultivate a deeper understanding of the inherent intricacies within the domain of banking systems.

## *Users may engage in the following activities*:
- Establish an account, fortified by a concealed four-digit password

![Alt Text](https://media.giphy.com/media/SCqhvk3Hr5EUmHRNpH/giphy.gif)

- Access an existing account through the login procedure
- Deposit funds into either their chequing or saving account
- Withdraw funds exclusively from their chequing account
- Review the balances associated with both their chequing and saving accounts
- Delve into a comprehensive transaction history overview


![Alt Text](https://media.giphy.com/media/3giGDo5HtlecNa5arS/giphy.gif)


## *Instructions*:
- To initiate the primary action of creating a new account, simply access the "Create" button within the main menu with a username and password (must be in four digits) of your choice.
- Once logged in by clicking the 'Login' button, you will seamlessly transition to the account menu, where transactions can be executed through the designated "Deposit" and "Withdraw" buttons. These transactions will be automatically recorded in the account's history.
- To display transaction history, users are provided with filtering options. These include viewing the complete transaction history, as well as focusing specifically on chequing or saving account transactions. By making a selection from the dropdown menu and subsequently clicking the "View transaction history" button, users can conveniently access the desired account's transaction records.

## *Developer Note*:
- Account details and transaction history are stored in a JSON file.
- Account information and transaction history can be updated or removed via the JSON file.
- Each event or transaction is logged and presented in the IntelliJ console upon the application's closure.

![Alt Text](https://media.giphy.com/media/bMpHZ9oBpqphLOALXz/giphy.gif)

### **UML Diagram**
![img.png](img.png)