# twitter-harvester
A small service that harvest tweets meeting certain criteria, as well as a REST api to retrieve them.

## Start it
Run `gradle build`, then `gradle bootRun`. The harvester will start collecting tweets according to the specified 
parameters (minimum follower count, target languages, number of hashtags considered as most used); you can configure 
these values in `application.properties`.

The credentials used can be modified in `twitter4j.properties`.

## Use it
The collected tweets are stored into an in-memory JSON repository. It's actually the `flapdoodle` embedded JSON 
database usually used for tests.

These are the REST api endpoints:
*  `http://localhost:8081/tweets` to get all the harvested tweets
*  `http://localhost:8081/tweets/{{tweet id}}/validate` to validate a certain tweet   
*  `http://localhost:8081/tweets?validated=true` to get just validated tweets
*  `http://localhost:8081/top-hashtags` to get the most used hashtags
