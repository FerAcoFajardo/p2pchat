/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2pchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author f_aco
 */
public class ServerThread extends Thread {
    
    private ServerSocket serverSocket;
    private Set<ServerThreadThread> serverThreadThreads = new HashSet<>();

    public ServerThread(String port) throws IOException{
        serverSocket = new ServerSocket(Integer.valueOf(port));
    
    }
    
    
    public void run(){
        try{
            while(true){
                ServerThreadThread serverThreadThread = new ServerThreadThread(serverSocket.accept(),this);
                serverThreadThreads.add(serverThreadThread);
                serverThreadThread.start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Set<ServerThreadThread> getServerThreadThreads() {
        return serverThreadThreads;
    }

    public void setServerThreadThreads(Set<ServerThreadThread> serverThreadThreads) {
        this.serverThreadThreads = serverThreadThreads;
    }
    
    public void sendMessage(String message){
        try{
            serverThreadThreads.forEach(t -> t.getPrintWriter().println(message));
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    
    
    
}
