●What were some of the alternative design options considered?  Why did you choose the selected option?

Currently the events are stored as an array list in the server, each action of adding event will insert an event at the end of the array list, while each action of deleting event will set the event in the array list to be null. One alternative design option could be using a hashmap that maps ID of event to an event and a set keeps record of available IDs gathered from deleted events. Each action of adding event will pair one available ID from the set with the event and remove the ID from the set if the set is not empty, otherwise use the smallest unused ID. While each action of deleting event will remove the event from the hashmap and put the ID back to the set.

The former one is chosen because generally add and get operations on array list takes less time than a map and the latter one requires an additional remove action on deleting an event. In addition, it makes more sense that each posted event is assigned an ID in increasing order with their posted time.

Besides, it's also possible to directly keep a set of events and takes all parameters that an event is consists of to enable further delete and modify option. However, putting too much information in an API requrest is not that convenient and elegant compared with other two methods so it's also not considered.



●What changes did you need to make to your tests (if any) to get them to pass.  Why were those changes needed, and do they shed any light on your design?

I wrote a function **startServer** that is called **@BeforeAll** tests and a function **clearServerData** that is called **@AfterEach** test.

**startServer** function is needed to make sure the server is started and actively listening for requests so that an request on given API can be made. **clearServerData** is needed  to make sure all the changes made to the database storing the events are recovered by setting the array list storing events to empty. To achieve this, I wrote an addional API to support delete all events and call this API in the **clearServerData** function.

The changes remind me that in real life design, we have to make sure API are well designed that users always get APIs they needed, though some might not be explicitly required. Also, to simulate a real life database, there should be a way to start, shut down and clear server implemented by us.



●Pick one design principle discussed in class and describe how your design adheres to this principle.

**Single responsibility principle**

Following this principle, modify event and delete event each have different endpoints or methods instead of using the same one. Also following the well-accepted industry standards, for getting events on the given date, GET method is used; for adding an event, POST method is used; for modifying an event, PATCH instead of PUT is used to allow for partial change; for deleting an event, DELETE is used.