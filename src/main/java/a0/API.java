package a0;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import static spark.Spark.*;
import static spark.Spark.get;

class Event{
    Integer id;
    String time;
    String name;
    public Event(Integer id, String time, String name) {
        this.id = id;
        this.time = time;
        this.name = name;
    }

    String toJsonString() {
        JSONObject jo = new JSONObject();
        jo.put("id", id.toString());
        jo.put("date", time);
        jo.put("name", name);
        return jo.toString();
    }
}

public class API {
    private static List<Event> events = new ArrayList<>();
    private static Integer index = 0;
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static String matchEvents(String date) {
        List<String> rst = new ArrayList<>();
        for (Event e : events) {
            if (e == null) continue;
            if (e.time.contains(date)) rst.add(e.toJsonString());
        }
        return rst.toString();
    }

    public static void main(String[] args) {
        getCurrentTime();
        Event();
        clearEvents();
    }

    public static void getCurrentTime() {
        // {"date" : "2022-11-01 15:00:00"}
        get("/DAI.com/gettime/date",
                (req, res) -> {
                    res.type("application/json");
                    if (!req.queryParams().isEmpty()) {
                        res.status(400);
                        return "{\"error\" : \"invalid request.\"}";
                    }
                    JSONObject jo = new JSONObject();
                    jo.put("date", dtf.format(LocalDateTime.now()));
                    return jo;
                });
        get("/DAI.com/gettime/day",
                (req, res) -> {
                    res.type("application/json");
                    if (!req.queryParams().isEmpty()) {
                        res.status(400);
                        return "{\"error\" : \"invalid request.\"}";
                    }
                    JSONObject jo = new JSONObject();
                    jo.put("day", LocalDateTime.now().getDayOfMonth());
                    return jo;
                });
        get("/DAI.com/gettime/month",
                (req, res) -> {
                    res.type("application/json");
                    if (!req.queryParams().isEmpty()) {
                        res.status(400);
                        return "{\"error\" : \"invalid request.\"}";
                    }
                    JSONObject jo = new JSONObject();
                    jo.put("month", LocalDateTime.now().getMonthValue());
                    return jo;
                });
        get("/DAI.com/gettime/year",
                (req, res) -> {
                    res.type("application/json");
                    if (!req.queryParams().isEmpty()) {
                        res.status(400);
                        return "{\"error\" : \"invalid request.\"}";
                    }
                    JSONObject jo = new JSONObject();
                    jo.put("year", LocalDateTime.now().getYear());
                    return jo;
                });
    }

    public static void Event() {
        get("/DAI.com/event",
                (req, res) -> {
                    res.type("application/json");
                    if (req.queryParams("date") == null) {
                        res.status(400);
                        return "{\"error\" : \"invalid request.\"}";
                    }
                    String date = req.queryParams("date");
                    return matchEvents(date);
                });
        post("/DAI.com/event",
                (req, res) -> {
                    res.type("application/json");
                    String date = req.queryParams("date");
                    if (date == null || req.queryParams("name") == null ) {
                        res.status(400);
                        return "{\"error\" : \"invalid request.\"}";
                    }
                    Event event = new Event(index, date, req.queryParams("name"));
                    index++;
                    events.add(event);
                    return event.toJsonString();
                });
        patch("/DAI.com/event/:eventID",
                (req, res) -> {
                    res.type("application/json");
                    int eventID = Integer.parseInt(req.params("eventID"));
                    if (eventID < 0 || eventID >= index || events.get(eventID) == null) {
                        // event non exist
                        res.status(404);
                        return "{\"error\" : \"event not exists.\"}";
                    }
                    Event event = events.get(eventID);
                    String date = req.queryParams("date");
                    String name = req.queryParams("name");
                    if (date == null && name == null) {
                        res.status(400);
                        return "{\"error\" : \"no input entry.\"}";
                    } else if ((date != null && date.equals(event.time))
                            || (name != null && name.equals(event.name))) {
                        res.status(304);
                        return "{\"error\" : \"event not modified.\"}";
                    }
                    if (name != null) event.name = name;
                    if (date != null) event.time = date;
                    events.add(event.id, event);
                    res.status(200);
                    return event.toJsonString();
                });

        get("/DAI.com/event/:eventID",
                (req, res) -> {
                    res.type("application/json");
                    int eventID = Integer.parseInt(req.params("eventID"));
                    if (eventID < 0 || eventID >= index || events.get(eventID) == null) {
                        // event non exist
                        res.status(404);
                        return "{\"error\" : \"event not exists.\"}";
                    }
                    return events.get(eventID).toJsonString();
                });

        delete("/DAI.com/event/:eventID",
                (req, res) -> {
                    res.type("application/json");
                    int eventID = Integer.parseInt(req.params("eventID"));
                    if (eventID < 0 || eventID >= index || events.get(eventID) == null) {
                        // event non exist
                        res.status(404);
                        return "{\"error\" : \"event not exists.\"}";
                    }
                    events.add(eventID, null);
                    res.status(204);
                    return new JSONObject();
                });
    }
    public static void clearEvents() {
        get("/DAI.com/events/clearAll",
                (req, res) -> {
                    events = new ArrayList<>();
                    index = 0;
                    return "{\"msg\" : \"database cleared.\"}";
                });
    }
}

