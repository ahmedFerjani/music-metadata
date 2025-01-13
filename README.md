This project is a coding challenge proposed by a recruiter as part of a technical evaluation. It is developed using Spring Boot and demonstrates my approach to solving the problem

## Summary

Build a REST-API for requesting metadata of a music track from an external service storing it in a database. 

The metadata should be fetched via the <a name="_int_vceygjap"></a>API of a public streaming services – for this example try the [Genius API.](https://docs.genius.com/#/getting-started-h1) 

Additional to the track metadata, use the API to retrieve and store the related cover image.

The REST-API built by you should get secured with oAuth2 and GitHub Login 



## Framework Requirements:

Spring Boot: This is the only hard requirement on the technology used. Everything else is your decision. You will find some suggestions here which might serve as hints for you about what could be used.

In case you’re not sure what to choose, feel free to reach out for some directions.

## Code Challenge API Details:

- Write access: There should be a single endpoint to trigger the creation of a track, which takes a string as query parameter
  - Sample: POST http://localhost:8080/codechallenge/createSongs?q= Lady%20Gaga
  - Input is a string, could be an artist name, a song title etc.
  - As soon as the call is invoked, use the [Genius search API](https://docs.genius.com/#/search-h2) to fetch and persist the following metadata for the first 10 songs returned:
    - id
    - title
    - primaryArtistNames
    - releaseDate


Store the search term and the metadata in the DB

- No need to care about updating an already existing record, skipping or giving back an error is enough. 
- Use the songs “id” to perform [another Genius API](https://docs.genius.com/#songs-h2) call to retrieve and store the URLs of the song (Spotify & Youtube URLs) – they are found under the “media” node in the JSON payload returned.
- Return the IDs of the songs persisted in the database, so they can be used to query the newly created API.

- Read access: There should be endpoints to retrieve previously stored data identified by the “id”:
  - single result containing the previously stored metadata
    - Sample metadata: GET http://localhost:8080/codechallenge/song/{id}
  - multiple results, returning the previously stored URLs
    - Sample cover: GET [http://localhost:8080/codechallenge/song/{id}/media ](http://localhost:8080/codechallenge/song/%7bid%7d/media%20)

**Genius API Details:**

- You should use this API to retrieve the metadata of a music track which is queried via a string
- For calling the Genius API you need API credentials. Details can be found here: [https://docs.genius.com/#/getting-started-h1
  ](https://docs.genius.com/#/getting-started-h1)create new credentials here: <https://genius.com/api-clients> 

  Credentials created can be used to retrieve a bearer token that can be used for calling the API and doing searches.
  <https://api.genius.com/oauth/token?client_id=***&client_secret=***&grant_type=client_credentials> 

**Details, Suggestions, etc.**

- **Database**:  
  Choose the database you are most productive with, such as **MSSQL**, **MySQL**, **PostgreSQL**, etc.

- **Database Access Technology**:  
  The technology used to access the database from Java is up to you. You can choose from options like:  
  - Plain **JDBC**  
  - **Spring JDBC Templates**  
  - **Hibernate/JPA**  
  - **MyBatis**  
  - **JOOQ**  

- **REST API Design**:  
  The design of the REST API is entirely up to you. You can use any structure for the following:  
  - **HTTP Methods** (GET/POST/PUT)  
  - **Query Parameters**  
  - **Naming Conventions**  
  - **Endpoint Hierarchy**  

  The suggestions above are just examples; feel free to use something that suits you best.

- **JUnit Tests**:  
  Writing JUnit tests is **not required**, but if you think test cases make your development process more efficient, feel free to add them. However, there is no obligation to include tests.

- **API Demo**:  
  We want to see the API in action! You can use the client of your choice (such as **Postman**, **cURL**, **Httpie**, etc.) to demonstrate the functionality of the API.

- **Swagger/OpenAPI**:  
  Generating a **Swagger** or **OpenAPI** page for testing would be a great addition and is considered a plus.
