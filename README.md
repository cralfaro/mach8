# mach8
mach8 nba sample

What do you need to run the sample?

* Java 8 or higher
* Maven 3.x


How to run it?

From main folder where pom.xml is located run:

`mvn spring-boot:run`

This should start a server running on port 9000 (If its in use you can change it at application.properties)

Now the way to access the endpoints is:

* Detail mode (Will show all player info)

    http://localhost:9000/players/pairs/height/139/detail

    ```json
    {
      "totalPairs" : 2,
      "pairs" : [ [ {
        "firstName" : "Brevin",
        "lastName" : "Knight",
        "heightInches" : 70,
        "heightMeters" : 1.78
      }, {
        "firstName" : "Nate",
        "lastName" : "Robinson",
        "heightInches" : 69,
        "heightMeters" : 1.75
      } ], [ {
        "firstName" : "Mike",
        "lastName" : "Wilks",
        "heightInches" : 70,
        "heightMeters" : 1.78
      }, {
        "firstName" : "Nate",
        "lastName" : "Robinson",
        "heightInches" : 69,
        "heightMeters" : 1.75
      } ] ]
    }
  ```

* Minimize mode (Will show only player name)

    http://localhost:9000/players/pairs/height/139
    
    ```json
    {
      "totalPairs" : 2,
      "pairs" : [ "Brevin Knight - Nate Robinson", "Mike Wilks - Nate Robinson" ]
    }
  ```


