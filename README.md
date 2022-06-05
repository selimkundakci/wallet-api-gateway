
# Wallet

This is a basic wallet app that simulates the behaviours of the wallet feature in Getir app. It doesn't have all the features of it but it has the followings;

- Create wallet
- Query wallet
- Create wallet accounts in different currencies
- Topup money to desired account
- Checkout from an account

Normally this Wallet project and team is completely seperated from the Getir app,
so it doesn't have its own user database. ```userId``` and ```phoneNumber``` on Getirs DB
are unique keys on our side. But for this case there is a user entity and registration process.
So there are extra two EP; ```register``` and ```login```.

#### /register

For registration you required fields are ```phoneNumber```, ```email``` and ```password```

#### /login

Once you registered, you will have to login with your email and password.
Login response will be your token.
You will have to use that token for the other requests.

This token needs to be used as Bearer token in Postman but without the Bearer keyword. Postman collection can be found under the postman folder.


## Setup

For postgres and hazelcast you have to run following command

```bash
docker-compose up -d
```

Then you have to run following command in [wallet-api](https://github.com/selimkundakci/wallet-api), [wallet-api-client](https://github.com/selimkundakci/wallet-api-client) and [wallet-api-gateway](https://github.com/selimkundakci/wallet-api-gateway) in order.

```bash
mvn clean install
```

Once you run this command in wallet-api-client, it will create a jar in your local maven repository which allows you to add
wallet-api as a dependency into wallet-api-gateway. (Wanted to make a dockerization to run all in once but couldn't make it work)

After that you can run wallet-api first and then run wallet-api-gateway
