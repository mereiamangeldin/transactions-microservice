

## Short project description
This microservice responsible for tracking limits for transactions.
Every coming transaction will be checked does it exceeded limit of the current month by given expense category - or not.

Instantly after running project then every day at 05:00, will be sent request to https://www.alphavantage.co/query for obtaining current Currency Rates

In every 1'st day of the month at 00:00, limits for every category with default value 1000$ will be created.

Also you can get report for transactions which exceeded limit using this API

#### This is a project design url: https://www.figma.com/design/G7x9KKl8BmEbf9hw03ZoGO/Transactions-microservice?node-id=0-1&t=ueocBa3ro8JfalDG-1

#### This is a swagger documentation url: http://localhost:8080/docs


## Project installation
#### Step 1: install repository locally
```sh
git clone https://github.com/yourusername/your-repository.git
cd your-repository
```
#### Step 2: Compile the project
```shell
mvn clean package -DskipTests   
```

#### Step 3: Up the docker-compose file
```shell
docker compose up
```
#### Step 4: Open browser and go to address: http://localhost:8080/docs