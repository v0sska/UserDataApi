This project was writed in Java 19.0.1 and SpringBoot 3.2.5. Database is postgreSQL and used docker compose and liquibase for create a db. 
Swagger file is located in resources/static folder. json file for uploading data in db located in resouces. 
You can upload json file for to fill database by multipart file.

Example:

Input file:

     {
    "email" : "adsadslnjadsl@bimba.net",
    "firstName": "Joe",
    "lastName": "Boe",
    "birthDate": "2002-05-04",
    "address": "Volodymyra Velikogo st.",
    "phoneNumber" : "+380668425311"
    }

Output data in response:

      {
          "data": [
              {
                  "id": 8,
                  "email": "adsadslnjadsl@bimba.net",
                  "firstName": "Joe",
                  "lastName": "Boe",
                  "birthDate": "2002-05-04",
                  "address": "Volodymyra Velikogo st.",
                  "phoneNumber": "+380668425311"
              }
          ]
      } 

For creating user must be a requirement filds: email, firstName, lastName, birthDate.
Created user must be older than 18 years old.
In parser if this at least one field is empty object will be ignored.
There is validation by email and birthDate in the task.

