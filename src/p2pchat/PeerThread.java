/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2pchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author f_aco
 */
public class PeerThread extends Thread{
    
    private BufferedReader bufferReader;

    public PeerThread(Socket socket) throws IOException{
        this.bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    
    public void run(){
        boolean flag = true;
        while(flag){
            try{
                JsonObject json = Json.createReader(bufferReader).readObject();
                if(json.containsKey("username"))
                    System.out.println("["+json.getString("username")+"]: "+json.getString("message"));
            }catch(Exception e){
                flag = false;
                interrupt();
            }
        }
    }
}
