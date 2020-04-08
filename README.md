# syncope-cas-saml2-auth

POC sample for SAML2 Authentication with Syncope as `SP` and CAS as `IdP` + `SP` metadata fetching from custom REST endpoint.

## Configure sample domains

```sh
# Add the following rules in your 'hosts' file

127.0.0.1     mycas.com
127.0.0.1     syn-saml2.co
```

## Run CAS

```sh
cd cas-idp
./gradlew clean build; ./gradlew run
```

## Run Syncope

```sh
cd syncope-sp
mvn -P all clean install; cd enduser; mvn -P embedded,all
```

## Run Standalone app converter

```sh
cd sp-metadata-converter
mvn clean install -PskipTests; cd core; mvn -Pstart
```

