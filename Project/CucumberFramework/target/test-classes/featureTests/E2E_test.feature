Feature: Automated End2End Tests
Description: The purpose of this feature is to run End2End tests

Scenario: User gets to know the which items are ordered
Given the user is on the sample data webpage
When the user is looking how many distinct items are there in the orders
Then the user sees the distinct items in the orders

Scenario: User gets to know if there are items with order quantity less than 5 units
Given the user is on the sample data webpage
When the user is looking if there are orders with quantity less than 5 units
Then the user sees the orders with quantity less than 5 units

Scenario: User gets to know if there are pencils with order quantity less than 5 units 	
Given the user is on the sample data webpage
When the user is looking if there are orders for pencils with quantity less than 5 units
Then the user sees the orders for pencils with quantity less than 5 units

Scenario: User gets to know the expensive item on the list
Given the user is on the sample data webpage
When the user is looking for the most expensive item in the list
Then the user sees the most expensive item in the list

Scenario: User downloads the order excel file via the link
Given the user is on the sample data webpage
When the user clicks the download excel link
Then the file is downloaded to the system
