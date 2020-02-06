
To build the *universes* handler, run (from project root)
```
./mvnw -f backend clean install
``` 

To build the *stars* handler, run (from project root)
```
./mvnw -f backend clean install -Dquarkus.lambda.handler=stars
``` 

