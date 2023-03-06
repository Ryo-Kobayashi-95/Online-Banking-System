package persistence;

import org.json.JSONObject;

// Citation:
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public interface Writable {
    //EFFECTS: return this as JSON object
    JSONObject toJson();
}
