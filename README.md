# Job Tracker

This is a program developed for use in a water-restoration setting (or any other service-based job such as plumbing, electric, etc.) to help keep track of jobs, customers, and services performed at each job. It is developed in Java using the Spring framework, uses MongoDB, and supports basic CRUD operations.

Here is a screenshot of the program in use with example data:
![example1.png](example1.png)

To get started, set up MongoDB on your local machine and create a connection at `localhost:27017/jobtracker`. When the application starts up, navigate to `http://localhost:8080/view/jobs` - you will be able to create jobs and add services which will populate the database automatically.

## Roadmap
- [X] Initialize Project
- [X] Implement MongoDB
- [X] Create page listing all Jobs
- [X] Create detail page for individual Jobs
- [X] Create page for Service Entries by date
- [ ] Implement CRUD operations for Jobs and Services
  - [X] Create new Jobs
  - [X] Add new Services to Jobs
  - [X] Edit Job information
  - [ ] Edit Service information
  - [ ] Delete Jobs (and all sub-services)
  - [ ] Delete Services
- [X] --**Minimum Viable Product**--