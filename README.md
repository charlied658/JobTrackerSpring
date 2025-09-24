# Job Tracker

This is a program developed for use in a water-restoration setting (or any other service-based job such as plumbing, electric, etc.) to help keep track of jobs, customers, and services performed at each job. It is developed in Java using the Spring framework.

Here is an early build of the project with example data (UI is subject to change):
![example1.png](example1.png)
![example2.png](example2.png)

The program reads from a file called `servicelog.json` in `src/main/resources/data`, and uses it to populate the data on screen. To get started, use the file `servicelog.json.example` in the resources directory, just remove the `.example`

## Roadmap
- [X] Initialize Project
- [X] Create page for all active Jobs/Customers
- [X] Create page for each Customer
- [X] Create schedule/calendar view to see services by date
- [ ] Allow users to edit Customer information
- [ ] Allow users to edit/add new services for a Customer
- [ ] Connect data to FireStoreDB to synchronize data across instances