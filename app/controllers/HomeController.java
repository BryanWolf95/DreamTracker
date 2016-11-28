package controllers;

import play.mvc.*;

import views.html.*;
import com.fasterxml.jackson.databind.JsonNode; 

import models.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    
    //index
    public Result index() {
        return ok(index.render());
    }
    
    
    //chatRoom
    public Result chatRoom(String username) {
        if(username == null || username.trim().equals("")) {
            flash("error", "Please choose a valid username.");
            return redirect(routes.HomeController.index());
        }
        return ok(chatRoom.render(username));
    }
    
    //chatRoomJs
    public Result chatRoomJs(String username) {
        return ok(views.js.chatRoom.render(username));
    }
    
    //chat
    public LegacyWebSocket<JsonNode> chat(final String username) {
        return new LegacyWebSocket<JsonNode>() {
            
            // Called when the Websocket Handshake is done.
            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out){
                
                // Join the chat room.
                try { 
                    ChatRoom.join(username, in, out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

}
