In this stage, you will enhance your database to store not just strings but any JSON types, including objects, arrays, numbers as values. This improvement will allow for more complex and nested data structures within the database.

Similarly, the key should not just be a single string as it was in the previous stage. The key should instead be in the form of an array, because now the user needs to be able to retrieve specific parts of the JSON value. For example, consider the following JSON structure, where the user wants to get only the surname of person:

{
... ,

    "person": {
        "name": "Adam",
        "surname": "Smith"
    }
    ...
}

To retrieve only the surname of the person, the user should provide the full path to this field in the form of a JSON array: ["person", "surname"]. If the user wants to get the full person object, they should provide ["person"].

The user should also be able to set value against different keys inside JSON objects. For example, it should be possible to set only the surname using the key ["person", "surname"] and any value against a key, including another JSON object.

Moreover, the user should be able to add new values inside other JSON objects. For example, using the key ["person", "age"] and the value 25, the person object should look like this:

{
... ,

    "person": {
        "name": "Adam",
        "surname": "Smith",
        "age": 25

    }
    ...
}

If there are no root objects, the server should create them. For example, if the database does not have a "person1" key but the user sets the value {"id1": 12, "id2": 14} for the key ["person1", "inside1", "inside2"], then the database will have the following structure:

{
... ,
"person1": {
"inside1": {
"inside2" : {
"id1": 12,
"id2": 14
}
}
},
...
}

The deletion of objects should follow the same rules. If a user deletes the object above by the key ["person1", "inside1", "inside2"], then only "inside2" should be deleted, not "inside1" or "person1". See the example below:

{
... ,
"person1": {
"inside1": { }
}

    ...
}

Objectives

    Enhance JSON Storage: Modify your database to store any JSON values, not just strings.
    Nested Key Access: Implement the ability to access and modify nested JSON values using keys in the form of JSON arrays.
    Dynamic Object Creation: Ensure the server can dynamically create root objects if they do not exist when setting new values.
    Selective Deletion: Implement the ability to delete nested JSON objects without affecting their parent objects.

Example

The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

Starting the server:

> java Main
Server started!

There is no need to format JSON in the output.

Starting the clients:

> java Main -t set -k text -v "Hello World!"
Client started!
Sent: {"type":"set","key":"text","value":"Hello World!"}
Received: {"response":"OK"}

> java Main -in setFile.json
Client started!
Sent:
{
"type":"set",
"key":"person",
"value":{
"name":"Elon Musk",
"car":{
"model":"Tesla Roadster",
"year":"2018"
},
"rocket":{
"name":"Falcon 9",
"launches":"87"
}
}
}
Received: {"response":"OK"}

> java Main -in getFile.json
Client started!
Sent: {"type":"get","key":["person","name"]}
Received: {"response":"OK","value":"Elon Musk"}

> java Main -in updateFile.json
Client started!
Sent: {"type":"set","key":["person","rocket","launches"],"value":"88"}
Received: {"response":"OK"}

> java Main -in secondGetFile.json
Client started!
Sent: {"type":"get","key":["person"]}
Received:
{
"response":"OK",
"value":{
"name":"Elon Musk",
"car":{
"model":"Tesla Roadster",
"year":"2018"
},
"rocket":{
"name":"Falcon 9",
"launches":"88"
}
}
}

> java Main -in deleteFile.json
Client started!
Sent: {"type":"delete","key":["person","car","year"]}
Received: {"response":"OK"}

> java Main -in secondGetFile.json
Client started!
Sent: {"type":"get","key":["person"]}
Received:
{
"response":"OK",
"value":{
"name":"Elon Musk",
"car":{
"model":"Tesla Roadster"
},
"rocket":{
"name":"Falcon 9",
"launches":"88"
}
}
}

> java Main -t exit
Client started!
Sent: {"type":"exit"}
Received: {"response":"OK"}