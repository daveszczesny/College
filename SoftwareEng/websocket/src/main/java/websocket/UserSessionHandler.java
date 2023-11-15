/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package websocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.spi.JsonProvider;
import jakarta.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import model.User;

/**
 *
 * @author dawid
 */

@ApplicationScoped
public class UserSessionHandler {

    // Sessions active
    private final Set<Session> sessions = new HashSet<>();

    // key = session id, value = forum name
    // Map Session to forum
    private final HashMap<String, String> sessionForums = new HashMap<>();

    // ArrayList to keep track of conversations in forums
    private final HashMap<String, ArrayList<String>> forumPosts = new HashMap<>();

    /* 
    * Update forum method, inserts forum into forumPosts if it isn't present yet
    * Then retrieves the string array from it
    * Creates json object message containing all strings
    * sends to all sessions connected to the forum
    */
    public void updateForum(String forum, String message) {
        forumPosts.putIfAbsent(forum, new ArrayList<>());
        forumPosts.get(forum).add(message);
        JsonObject obj = createUpdateMessage(forum);
        sendToAllConnectedSessionsInAForum(obj, forum);
    }

    /* Add session to active sessions */
    public void addSession(Session session) {
        sessions.add(session);
    }

    /* Add session to a forum */
    public void addSessionToForum(Session session, User user) {
        sessionForums.put(session.getId(), user.getForum());

        JsonObject obj = createJoinMessage(user);
        sendToSession(session, obj); // send to individual session
    }

    /* Remove session from active sessions */
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    /*
    * Method to cerate update response.
    * Action: "Update", and "Posts" all posts for a forum
    */
    private JsonObject createUpdateMessage(String forum) {

        JsonProvider provider = JsonProvider.provider();

        JsonObjectBuilder message = provider.createObjectBuilder()
                .add("action", "update")
                .add("posts", forumPosts.get(forum).get(forumPosts.get(forum).size() - 1));


        return message.build();

    }

    /* Create Join Response */
    private JsonObject createJoinMessage(User user) {
        JsonProvider provider = JsonProvider.provider();

        JsonObjectBuilder message = provider.createObjectBuilder()
                .add("action", "join")
                .add("username", user.getUsername())
                .add("forum", user.getForum());

        // If posts for a forum exist return posts in an array
        if (forumPosts.containsKey(user.getForum())) {
            JsonArrayBuilder posts = provider.createArrayBuilder(forumPosts.get(user.getForum()));
            message.add("posts", posts);
        } else {
            // if posts for a forum don#t exist reutnr empty array
            JsonArrayBuilder postArr = provider.createArrayBuilder();
            message.add("posts", postArr);
        }

        return message.build();
    }

    
    /* Method to send response to sessions connected to a forum */
    private void sendToAllConnectedSessionsInAForum(JsonObject obj, String forum) {
        for (Session session : sessions) {
            // Check if sessions id is in sessions Forums
            if (sessionForums.get(session.getId()).equals(forum)) {
                sendToSession(session, obj);
            }
        }
    }
    
    /* Method to send a response to a given session */
    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException e) {
        }
    }

}
