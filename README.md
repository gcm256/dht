# dht

Run:

```
git clone https://github.com/gcm256/dht.git
cd dht
mvn clean package
docker build -t dht-app .
docker run -it --rm -p 4455:8080 dht-app
docker run -it --rm -p 4466:8080 dht-app
```
