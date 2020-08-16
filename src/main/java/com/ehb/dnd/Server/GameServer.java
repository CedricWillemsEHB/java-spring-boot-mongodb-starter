package com.ehb.dnd.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private ServerSocket ss;
    private List<ServerSideConnection> players;
    private int numPlayers= 0;

    public GameServer() {
        players = new ArrayList<ServerSideConnection>();
        try {
            ss = new ServerSocket(51734);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");
            while(true) {
                if(ss != null) {
                    Socket s = ss.accept();

                    System.out.println("Player #" + (numPlayers + 1) + " has connected.");
                    ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
                    players.add(ssc);
                    Thread t = new Thread(ssc);
                    t.start();
                    numPlayers++;
                }
            }
        }catch(IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }



    private class ServerSideConnection implements Runnable {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int playerID;
        private int lobyId = -1;
        private List<ServerSideConnection> playersInLoby = new ArrayList<>();
        private int numDungeonmaster;


        public ServerSideConnection(Socket s, int playerID) {
            socket = s;
            this.playerID = playerID;
            try {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                System.out.println("IOException from run() SSC");
            }
        }

        public int getPlayerID() {
            return playerID;
        }

        public int getLobyId() {
            return lobyId;
        }

        public void setLobyId(int lobyId) {
            this.lobyId = lobyId;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            String command = "";
            String tmp = "";
            if(lobyId >= 0){
                System.out.println("playerID = " + playerID + ", lobyId = " + lobyId);
            } else {
                System.out.println("playerID = " + playerID + ", lobyId = null");
            }
            int receiverID= 0;
            boolean firstTime = true;
            try {
                while(true) {
                    if(lobyId >= 0){
                        System.out.println("playerID = " + playerID + ", lobyId = " + lobyId);
                    } else {
                        System.out.println("playerID = " + playerID + ", lobyId = null");
                    }
                    //TODO
                    int condition=dataIn.readInt();
                    switch(condition) {
                        case ServerContract.SHOW_MAP:

                            break;
                        case ServerContract.GO_NORTH:
                            receiverID=0;
                            for(ServerSideConnection player : playersInLoby) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(ServerContract.GO_NORTH);
                                    dataOut.flush();
                                }
                                receiverID++;
                            }
                            break;
                        case ServerContract.GO_SOUTH:
                            receiverID=0;
                            for(ServerSideConnection player : playersInLoby) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(ServerContract.GO_SOUTH);
                                    dataOut.flush();
                                }
                                receiverID++;
                            }
                            break;
                        case ServerContract.GO_EAST:
                            receiverID=0;
                            for(ServerSideConnection player : playersInLoby) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(ServerContract.GO_EAST);
                                    dataOut.flush();
                                }
                                receiverID++;
                            }
                            break;
                        case ServerContract.GO_WEST:
                            receiverID=0;
                            for(ServerSideConnection player : playersInLoby) {
                                if(receiverID!=playerID) {
                                    player.dataOut.writeInt(ServerContract.GO_WEST);
                                    dataOut.flush();
                                }
                                receiverID++;
                            }
                            break;
                        case ServerContract.SET_UP_FIGHT:

                            break;
                        case ServerContract.GET_FROM_FIGHT:
                            break;
                        case ServerContract.MAKE_LOBBY:

                            break;
                        case ServerContract.GET_IN_LOBBY:

                            break;
                    }
                }
            }catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        //Send Int
        public void sendButtonNum(int n) {
            try {
                dataOut.writeInt(n);
                dataOut.flush();
            } catch (IOException ex) {
                System.out.println("IOException from sendButtonNum() SSC");
            }
        }

        //Close cooection
        public void closeConnection() {
            try {
                dataOut.close();
                System.out.println("Connection closed");
            } catch (IOException ex) {
                System.out.println("IOException from closeConnection() SSC");
            }
        }
    }



}
