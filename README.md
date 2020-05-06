# dht

Run:

```
git clone https://github.com/gcm256/dht.git
cd dht
mvn clean package
docker run -v /Users/kashparida/projects/github_repos/dht/target/dht-0.0.1-SNAPSHOT.war:/usr/local/tomcat/webapps/dht-0.0.1-SNAPSHOT.war -it -p 4455:8080 tomcat:9.0
docker build -t dht-app .
docker run -it --rm -p 4455:8080 dht-app
docker run -it --rm -p 4466:8080 dht-app
```

http://localhost:4455/dht-0.0.1-SNAPSHOT/get?key=zz
http://localhost:4466/dht-0.0.1-SNAPSHOT/get?key=zz

$ curl -H "Content-type: application/json" -XPOST http://localhost:4455/set/key -d "value"
OK
$ curl -H "Accept: application/json" http://localhost:4466/get/key
“value”
