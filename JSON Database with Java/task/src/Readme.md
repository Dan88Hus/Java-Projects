In this stage, you will need to improve your client and server by adding the ability to work with files. The server should store (persist) the database as a file on the hard drive, updating it only when setting a new value or deleting one. This functionality is crucial for maintaining data persistence and ensuring that the database state is saved even if the server is restarted.

To handle multiple requests efficiently, you will parallelize the server's work using executors. Each request will be parsed and handled in a separate executor's task, allowing the server to process multiple requests simultaneously. This improvement will significantly enhance the server's performance and scalability, making it capable of handling a higher load.

Implementing synchronization is essential to maintain the integrity of the database when multiple threads access the same file. By using the ReentrantReadWriteLock class, you can allow multiple threads to read the file concurrently while ensuring that only one thread can write to the file at a time. This will prevent data corruption and ensure consistent access to the database.

Additionally, you will implement the ability for the client to read a request from a file. If the -in argument is followed by a file name, the client should read the request from that file. The file will be stored in the /client/data directory. This feature allows the client to directly send pre-formatted JSON requests to the server, bypassing the need to first convert command-line arguments into JSON format and then send that JSON to the server.

One significant advantage of this feature is that it will allow us to store not just strings but also complex JSON objects as values in the future. Writing complex JSON objects directly on the command line can be tedious and error-prone. By using pre-formatted JSON files, we can easily manage and send complex data structures to the server.

Here are the examples of the input file contents:

{"type":"set","key":"name","value":"Sorabh"}

{"type":"get","key":"name"}

{"type":"delete","key":"name"}

For reading client requests from a file, you can get the path using:

path = System.getProperty("user.dir") + "/src/client/data/" + fileName;

Note that like in the previous stage, you should store the database as a JSON object. The keys and values should both be strings.

Example of the contents of a JSON database file:

{
"key1": "some string value",
"key2": "another string value",
"key3": "yet another string value"
}

For working with database file, you can get the path using:

path = System.getProperty("user.dir") + "/src/server/data/db.json";

Objectives

    Persist the Database: The server should store the database on the hard drive in a db.json file, located in the /server/data folder. The database should be updated only after setting a new value or deleting an existing one.

    Parallelize Request Handling: The server should handle multiple requests simultaneously by using executors. Each request should be processed in a separate task, while the main thread waits for incoming requests.

    Implement Synchronization: Ensure that multiple threads can read the database file concurrently, but only one thread can write to the file at a time. This will prevent data corruption and ensure consistent access to the database.

    Read Requests from a File: The client should be able to read a request from a file if the -in argument is followed by a file name. The file will be stored in the /client/data directory.

Example

The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

Starting the server:

> java Main
Server started!

Starting the clients:

> java Main -t get -k name
Client started!
Sent: {"type":"get","key":"name"}
Received: {"response":"ERROR","reason":"No such key"}

> java Main -t set -k name -v "Sorabh Tomar"
Client started!
Sent: {"type":"set","key":"name","value":"Sorabh Tomar"}
Received: {"response":"OK"}

> java Main -t set -k name -v Sorabh
Client started!
Sent: {"type":"set","key":"name","value":"Sorabh"}
Received: {"response":"OK"}

> java Main -t get -k name
Client started!
Sent: {"type":"get","key":"name"}
Received: {"response":"OK","value":"Sorabh"}

> java Main -in testSet.json
Client started!
Sent: {"type":"set","key":"name","value":"Sorabh"}
Received: {"response":"OK"}

> java Main -in testGet.json
Client started!
Sent: {"type":"get","key":"name"}
Received: {"response":"OK","value":"Sorabh"}

> java Main -in testDelete.json
Client started!
Sent: {"type":"delete","key":"name"}
Received: {"response":"OK"}

> java Main -t exit
Client started!
Sent: {"type":"exit"}
Received: {"response":"OK"}