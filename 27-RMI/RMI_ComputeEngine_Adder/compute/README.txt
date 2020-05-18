kill the running rmiregistry


rmiregistry &


Server:

java -Djava.security.policy=server.policy ComputeEngine




client:
java -Djava.security.policy=client.policy ComputePi localhost 15



