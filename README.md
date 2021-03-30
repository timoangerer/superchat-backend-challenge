# superchat-backend-challenge

## Run locally

Clone the repository

Change into the `quarkus` folder

    cd quarkus

Create the application package

    mvn package

Start the server and database with docker-compose

    docker-compose -f src/main/docker/docker-compose.yml up

## Example run through of the API

Create a new contact:

    curl --location --request POST 'http://localhost:8080/contacts' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "name": "rick sanchez",
        "email": "rick@schwifty.com"
    }'


List all contacts

    curl --location --request GET 'http://localhost:8080/contacts'


Simulate two messages being sent to the Whatsapp webhook

1.

    curl --location --request POST 'http://localhost:8080/wh/whatsapp' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "msgId": "38747282747382",
        "waId": "wa-100",
        "profileName": "morty smith",
        "text": "Hello",
        "timestamp": "1616859549"
    }'

2.

    curl --location --request POST 'http://localhost:8080/wh/whatsapp' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "msgId": "38747282747341",
        "waId": "wa-100",
        "profileName": "morty smith",
        "text": "Do you offer take-away?",
        "timestamp": "1616859549"
    }'


To replyto the Whatsapp message, create a message containing the `contactId`, `channelId` and the text itself.

The simplest way to get the `contactId` and `channelId` is by listing all messages and copying the values of the last received message:

    curl --location --request GET 'http://localhost:8080/messages'

Send a new message (insert the IDs)

    curl --location --request POST 'http://localhost:8080/messages' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "contactId": "8abb5010-9126-11eb-bb5c-0242ac120003",
        "channelId": "8abc4ce0-9126-11eb-bb5c-0242ac120003",
        "text": "Hey ${name}, yes, we offer take-away. Also, the current BTC price is at: ${bitcoin}"
    }'


You can specify placeholder variables with `${}` i.e to use the `name` placeholder variable you would use `${name}`.

Currently available placeholder variables include customers `${name}` and `${email}`, as well as the current `${bitcoin}` price.

The message fails if a placeholder variable can not be resolved. i.e if the message contains the `${email}` placeholder but there is no email address of the contact stored.