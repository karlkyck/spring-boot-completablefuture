### Non-blocking with Spring Boot & CompletableFuture

This sample Spring Boot application demonstrates how to use `CompletableFuture` along with the `@Async` annotation to create non-blocking applications with multiple layers.

Bootstrapping of data is achieved with [mongobee](https://github.com/mongobee/mongobee) 

#### Clone
`git clone git@github.com:karlkyck/spring-boot-completablefuture.git`

#### Build
`./gradlew build`

#### Test
`./gradlew test`

#### Run
To run this application you will need an instance of MongoDB running on port 27017.

`./gradlew bootRun`

#### RESTful Endpoints

##### List all users

`http://localhost:8080/api/users`

##### Retrieve individual users

`http://localhost:8080/api/users/590f86d92449343841cc2c3f`
`http://localhost:8080/api/users/590f86d92449343841cc2c40`