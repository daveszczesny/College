package websocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonReader;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author dawid
 */


@ApplicationScoped
@ServerEndpoint("/users")
public class UserWebSocketServer {

    @Inject
    private UserSessionHandler sessionHandler;
    
    /* On open add session */
    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);
    }

    /* On close remove session */
    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    /* On error log error */
    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(UserWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    /* Method to handle the requests sent from client */
    @OnMessage
    public void handleMessage(String message, Session session) {

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            // Client request "action" is "join"
            if ("join".equals(jsonMessage.getString("action"))) {
                
                // Create new user
                User user = new User();
                user.setUsername(jsonMessage.getString("username"));
                user.setForum(jsonMessage.getString("forum"));

                // Add to session, and add session to specified forum
                sessionHandler.addSession(session);
                sessionHandler.addSessionToForum(session, user);

            }
            /* Update a forum */
            if ("update".equals(jsonMessage.getString("action"))) {
                String forum = jsonMessage.getString("forum");
                String msg = jsonMessage.getString("msg");
                sessionHandler.updateForum(forum, msg);

            }
        }

    }

}
