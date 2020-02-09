## Testing
We use Junit 5 with Mockito for testing. Some tests rely on a running DynamoDB. We use a 
library called [TestContainers](https://www.testcontainers.org/test_framework_integration/junit_5/). See a blog [here](https://www.javacodegeeks.com/2019/01/testing-dynamodb-using-junit5.html) about possible improvements.
TestContainers will start a docker container mocking a real dynamoDB (image `amazon/dynamodb-local`). For details, see `de.asideas.happystars.domain.KGenericContainer`.

To run all tests from with project root, execute 
```
./mvnw clean test -f backend
```

## Build  
To build the Jar handler, run (from project root)
```
./mvnw -f backend clean install
``` 
