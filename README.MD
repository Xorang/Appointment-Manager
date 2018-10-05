# Appointment Manager
This program was created to meet the requirements of WGU's C195 - Software II course. It is a simple scheduling application backed by a MySQL database.

## Locale
Currently the accepted Locale's are Japanese and English. There is a Locale.getDefault() within the LoginController.java that will automatically determine Locale. You can also manually change the locale in code by uncommenting some of my lines.

## Logging in
Credentials are stored in the database. Currently there is only one user,   username: test     password: test

## Functionality
The program displays the schedule of meetings between consultants and schedules. Meetings are displayed by month by default, with the option to view them by week as well. The program allows adding, editing, and deleting appointment records. It also allows adding, editing, and deleting customer records.

## Reports
The program includes three reports: One displays the number of appointments in each month of each appointment type. A second allows you to select a consultant and display all their appointments. The third allows you to select a customer and display all their appointments.
