# Enterprise Computing -assignment

## 1. Distributed Key- value Store Implementation
We have created distributed key value store consisting two replicas, one is master
and another one is slave. As according to the task sheet, we performed CRUD
operations and noted results of benchmarking

## 2. Benchmarking Latency and Staleness

### Latency measurements (Synchronous)
Minimum - 66 ms <br />
Maximum - 572 ms <br />
Average - 128.08 ms <br />
### Latency measurements (Asynchronous)
Minimum - 49 ms <br />
Maximum - 223 ms <br />
Average - 70.67 <br />

The results meet our expectation as latency in case of asynchronous is less than the
latency during synchronous process. The round trip time is more in latter as the
master waits for the confirmation from the slave before replying back to the client.
This is not the case in asynchronous process where master reply back to the client
as soon as it sends the request to slave for updating its data. However, the time
difference is not very much which is also up to our expectations as both of our
master and slave servers are in the same region which is in Europe.


### Staleness Benchmarking(Synchronous)
Minimum - 3 ms <br />
Maximum - 144 ms <br />
Average - 40.34 ms <br />
### Staleness Benchmarking(Asynchronous)
Minimum - 3 ms <br />
Maximum - 52 ms <br />
Average - 42.94 ms <br />

The results in case of staleness also meet our expectations. As both the master
server and the slave server are in the same region, there must be a very small
difference in timestamp when master and slave update their data.
