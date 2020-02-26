# IPAAS Syndesis microservices orchestration

Follow these instructions to see how to build a simple game giveaway notification system by composing a microservices pipeline.
`GamePrice` and `UserPreference` are independent microservices with independent databases, no microservice business logic has been harmed during the experiments!

I am using [minishift](https://www.okd.io/minishift/) as local openshift development cluster; [httpie](https://httpie.org/) as http CLI.

* Deploy [Debezium](https://debezium.io/) (version 0.10) on a local cluster: https://debezium.io/documentation/reference/1.0/operations/openshift.html. You will use `debezium-connect` pod to enable your change data capture.
The database instance used there (`mysql`) is shared for simplicity, but you can create and use 2 separate instances.
* Deploy [Syndesis](https://syndesis.io/) (version 2.0) on a local cluster: https://syndesis.io/quickstart/
* Create `gameprice` and `userpreference` databases
```
# Create databases
oc exec -it $(oc get pods -o custom-columns=NAME:.metadata.name --no-headers -l app=mysql --field-selector status.phase=Running) -- bash -c 'mysql -u root -p$MYSQL_ROOT_PASSWORD -e "CREATE Database gameprice"'
oc exec -it $(oc get pods -o custom-columns=NAME:.metadata.name --no-headers -l app=mysql --field-selector status.phase=Running) -- bash -c 'mysql -u root -p$MYSQL_ROOT_PASSWORD -e "CREATE Database userpreference"'
```
* Create `gameprice` CDC connector
```
# Create CDC connector
oc exec -i -c kafka broker-kafka-0 -- curl -X POST \
    -H "Accept:application/json" \
    -H "Content-Type:application/json" \
    http://debezium-connect-api:8083/connectors -d @- <<'EOF'

{
    "name": "gameprice-connector",
    "config": {
        "connector.class": "io.debezium.connector.mysql.MySqlConnector",
        "tasks.max": "1",
        "database.hostname": "mysql",
        "database.port": "3306",
        "database.user": "debezium",
        "database.password": "dbz",
        "database.server.id": "184054",
        "database.server.name": "dbserver1",
        "database.whitelist": "gameprice",
        "database.history.kafka.bootstrap.servers": "broker-kafka-bootstrap:9092",
        "database.history.kafka.topic": "schema-changes.gameprice"
    }
}
EOF
```
* Deploy `userPreference` microservice pod
```
cd userPreference
mvn clean fabric8:deploy -P openshift
```
* Deploy `gamePrice` microservice pod
```
cd gamePrice
mvn clean fabric8:deploy -P openshift
```
* Create the source and destination connections we'll use for the integration: a `gameprice` CDC (to capture price drop), a `userpreference` API (to list users) and an email service (to send notification).

![image 1](/img/1-connections.png)

* Create an integration to capture the event changes on `GamePrice` domain, load a list of `UserPreference` and send an email to them. Wait for the integration to be up and running on your local openshift.

    * Create a new integration with the debezium connection source: select the `gameprice` table change data topic and the schema change topic
    
    ![image 2](/img/2-debezium-source.png)
    
    * Select a conditional flow as destination source
    
    ![image 3](/img/3-conditional-flow.png)
    
    * Define the steps needed to filter the event promotions (price equals to 0), list users, and send them an email according to a well defined template
    
    ![image 4](/img/4-flow.png)
    
    * Each step may need to map the result coming from the previous step, as for example mapping the `GamePrice` id coming from the change data event into a request parameter expected by the `UserPreference` API
    
    ![image 5](/img/5-data-mapper.png)

* Create `Game`s and `UserPreference`s (host name can be different, checkout yours)
```
http POST http://userpreference-syndesis-services.192.168.42.139.nip.io/userpreference email="pcongius@redhat.com" userId=3 userName="Pasquale@redhat"
http PUT http://gameprice-syndesis-services.192.168.42.139.nip.io/gameprice/ gameId=1 gamePrice=20 gameTitle="Doom"
http PUT http://gameprice-syndesis-services.192.168.42.139.nip.io/gameprice/ gameId=2 gamePrice=29 gameTitle="Doom 2"
http PUT http://gameprice-syndesis-services.192.168.42.139.nip.io/gameprice/ gameId=3 gamePrice=19 gameTitle="Civilization"
...
http POST http://userpreference-syndesis-services.192.168.42.139.nip.io/userpreference/3/game gameId=3 gameTitle=Civilization
```
* Check the `UserPreference` list was updated correctly
```
http GET http://userpreference-syndesis-services.192.168.42.139.nip.io/userpreference/?gameLiked=3

[
    {
        "email": "pcongius@redhat.com",
        "gamesLikedIds": [
            "2",
            "3"
        ],
        "userId": 3,
        "userName": "Pasquale@redhat"
    }
]

```
* Set a `GamePrice` to 0
```
http PUT http://gameprice-syndesis-services.192.168.42.139.nip.io/gameprice/ gameId=3 gamePrice=0 gameTitle="Civilization"
```
* Checkout the email inbox

![image 6](/img/6-email.png)