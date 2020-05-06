# dht

In a terminal run:

```
git clone https://github.com/gcm256/dht.git
cd dht
```

Make sure `JAVA_HOME` points to a jdk14 home. May be any java >= 1.8 would work, but my jdk home is jdk14 (Although I am not using any post Java8 features.)   

Run:

```
mvn clean package
docker build -t dht-app .
```
The above will create a docker image called dht-app from a base tomcat9.0 image.

To start container nodes, run one of the following commands (each in a new terminal), running as many as wanted, just changing the host port to a different
number ie 4477, 4488 etc.
```
docker run -it --rm -p 4455:8080 dht-app
docker run -it --rm -p 4466:8080 dht-app
```

1. Adding nodes to group:
To each node, we need to add the host:port of all the other peer nodes. Since they are inside docker network, we can use 
the `docker inspect <container-id> | grep -i ipaddress` to find the docker network ip address assigned to each node.
The port will be 8080 for all, since each container is running the image on 8080.
For each node, run the following api telling it about all its peers:

eg, if my nodes are localhost:4455 and localhost:4466, and they have docker network ip 172.17.0.2 and 172.17.0.3 respectively, 
we tell node1 about node2's (docker) host, port and vice versa. We run:
```
$ curl -H "Accept: application/json" "http://localhost:4455/addNode?host=172.17.0.3&port=8080
$ curl -H "Accept: application/json" "http://localhost:4466/addNode?host=172.17.0.2&port=8080
```

:bulb: **Note:** If we have more than 2 nodes, say node1, node2, node3, we tell node1 about node2, node3; node2 about node1, node3; node3 about node1, node2. 

1. Setting and getting Keys/Values on the DHT cluster:

```
$ curl -H "Content-type: application/json" -XPOST http://localhost:4455/set/key -d "value"
OK
$ curl -H "Accept: application/json" http://localhost:4466/get/key
value
$ curl -H "Accept: application/json" -XPOST http://localhost:4455/set/key101 -d "value101"
$ curl -H "Accept: application/json" -XPOST http://localhost:4455/set/key105 -d "value105"
$ curl -H "Accept: application/json" -XPOST http://localhost:4466/set/key90 -d "value90"
$ curl -H "Accept: application/json" -XPOST http://localhost:4466/set/key92 -d "value92"

$ curl -H "Accept: application/json" http://localhost:4455/get/key101
$ curl -H "Accept: application/json" http://localhost:4466/get/key101
$ curl -H "Accept: application/json" http://localhost:4455/get/key90
$ curl -H "Accept: application/json" http://localhost:4466/get/key90
$ curl -H "Accept: application/json" http://localhost:4455/get/keyfoo
$ curl -H "Accept: application/json" http://localhost:4466/get/keyfoo
```

**TODO:**   
1. Replicate on late joining nodes.
2. Unit tets.
3. JVM resource mgmt for node performance.
