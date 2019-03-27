# eLibrary-Android-App
This app was developed using Kotlin programming language. Some features are just a prototype which doesn't have a real functionality.

## 10 Use Cases for the Elibrary Application

**1. Create Account:** Unregistered user inputs forename, surname, email address, and password (twice).

**2. Log into Account:** User inputs email address and password. 

**3. Log Out Account:** User terminates his/her session on the application.

**4.	Update User Profile:** User, or staff, or administrator inputs new forename / surname/ email address or new password (twice). Administrator or staff can update user's forename, surname or email address. Administrator or staff can update the user's role.

**5.	Create Item:** Administrator or staff inputs the name, the category and the pages of the book. 

**6.	View Items:** User views a set of items, which includes their name and category.

**7.	View Item:** User views a particular item’s name and category. Extends View Items. 


**8.	Sign Out Item:** User selects a specific item to sign out. The system adds an item to a list of items that the user wishes to sign out. 

**9.	Extend Sign Out:**  User extends the sign out. This action is available only once for each book. 

**10.	Read Book:** User selects the book that he/she signed out and reads it. 

## Database: 

SQLite was used as a storage for the development, which means the application contains the model, DBContact and DBHelper files to provide the SQLite processes.
