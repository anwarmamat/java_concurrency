Akka Cluster Example
====================

This is an example of creating a master distributing work to a set of competing workers. It is highly inspired by this blog post: http://letitcrash.com/post/29044669086/balancing-workload-across-nodes-with-akka-2

Clone the repo and run a seed node (seed nodes are 1337 and 1338):
```bash
./run.sh 1337
```
Additional nodes can be run without specifying a port:
```bash
./run.sh
```
The master on the seed node should start handing out work to workers. If a node is killed it should be registered by the master. If the node running the master is killed a new master should be started on another node.

