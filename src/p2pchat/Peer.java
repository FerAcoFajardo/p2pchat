/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2pchat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import javax.json.Json;

/**
 *
 * @author f_aco
 */
public class Peer {
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Enter username and port # for this peer:");
        String[] setupValues = bufferReader.readLine().split(" ");
        System.out.println(setupValues.toString());
        ServerThread serverThread = new ServerThread(setupValues[1]);
        serverThread.start();
        
        new Peer().updateListenToPeers(bufferReader, setupValues[0], serverThread);
    }
    
    public void updateListenToPeers(BufferedReader bufferedReader, String username, ServerThread serverThread) throws Exception{
        System.out.println("Enter (space separated) hostname:port");
        System.out.println("    peers to receive messages from (s to skip):");
        String input = bufferedReader.readLine();
        String[] inputValues = input.split(" ");
        
        if(!input.equals("s"))
            for (String inputValue : inputValues) {
                String[] address = inputValue.split(":");
                Socket socket = null;
                try{
                    socket = new Socket(address[0], Integer.valueOf(address[1]));
                    new PeerThread(socket).start();
                }catch(Exception e){
                    if(socket != null)
                        socket.close();
                    else
                        System.err.println(e.getMessage());
                }
        }
        communicate(bufferedReader, username, serverThread);

        
        

    }
    
    public void communicate(BufferedReader bufferedReader, String username, ServerThread serverThread){
        try{
            System.out.println("You can now communicate (e to exit, c to change)");
            boolean flag = true;
            while(flag){
                String message = bufferedReader.readLine();
                if(message.equalsIgnoreCase("e")){
                    flag = false;
                    break;
                }else if (message.equalsIgnoreCase("c")){
                    updateListenToPeers(bufferedReader, username, serverThread);
                }else{
                    StringWriter stringWriter = new StringWriter();
                    Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder()
                                                    .add("username", username)
                                                    .add("message", message)
                                                    .build());
                    serverThread.sendMessage(stringWriter.toString());
                }
            }
            System.exit(0);
        }catch(Exception e){
            
        }
    }
    
}
