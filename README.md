# spring-batch-bank-app
This repository hosts a basic Spring Batch project for processing bank transactions.

The application reads transaction data from a CSV file, performs minimal processing, and stores the results in a relational database. This project is designed for learning and practicing Spring Batch concepts.

## Getting Started
### Prerequisites

- Java 17
- Maven

### Dependencies

We can use the Spring Initializr to generate a new Spring Boot project
with the following dependencies for our project:
- Spring web
- Spring Batch IO
- Spring DevTools
- Spring Data JPA
- H2 Database
- Lombok


Once we have created the project, we need to configure our application properties to connect to the database. We can do this by adding the following properties in our application.properties file:

```bash
spring.datasource.url=jdbc:h2:mem:springBatchDB
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
```
### Launch Spring Batch job from a REST endpoint 
In a Spring Batch application, you define jobs composed of steps, and these jobs are executed by the JobLauncher when the application starts up by default.

We have a batch application that has one job with one step which processes an excel file.
The job will read a file name from the job parameters, find the file from a folder, and start processing the file.

However, in some scenarios, you might want to disable this automatic execution.

In application.properties, add the following:

```bash
# Disable automatic job execution
spring.batch.job.enabled=false
```

And then, in your application code, you can use a JobLauncher to manually start your batch jobs when needed.

```bash
@RequestMapping("/runit")
    public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis()).toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        return jobExecution.getStatus();
    }
```
This configuration is useful when you want more control over when your batch jobs run.

### Installation

1. Clone the repository:
  ```bash
  git clone https://github.com/HamidIdifi/spring-batch-bank-app.git
  ```
2. Navigate to the project directory:
  ```bash
  cd spring-batch-bank-app
  ```
3. Build the project:
  ```bash
  mvn clean install
  ```
4. Run the application:
  ```bash
  mvn spring-boot:run
  ```

## Usage

The application exposes two endpoints:

- `GET /runit`: to launch the job.
- `GET /analytics`: to get the analytics after job was performed and executed.

..
