# banking-service-practice

## Development
This application will pre-load some data to H2 in-memory database. Please refer the csv file stored in ``resources`` folder.
This application will process multiple requests asynchronously using 20 threads using ``ExecutorService`` and ``Future`` object.
Transactions that requires database access are managed by Spring Boot pre-configured ``@Transactional`` annotation.

## How to run 
1. ``mvn clean install package``
2. ``java -jar -Dspring.profiles.active=dev target/bankingService-0.0.1-SNAPSHOT.jar``
