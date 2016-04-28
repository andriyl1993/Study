package com.company;

import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static final List<Flat> data =
            FXCollections.observableArrayList(
                    new Flat(1, 40.5, 1, 1, "Type1", "100 years"),
                    new Flat(2, 44.5, 2, 2, "Type1", "10 years"),
                    new Flat(3, 47.5, 1, 2, "Type2", "80 years"),
                    new Flat(4, 70.5, 1, 3, "Type1", "70 years"),
                    new Flat(5, 30.5, 5, 1, "Type2", "70 years")
            );


    public static void startServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("start");
            while (true) {
                Socket connSocket = serverSocket.accept();
                System.out.println("client");
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
                ObjectOutputStream outToClient = new ObjectOutputStream(connSocket.getOutputStream());
                String clientSentence = inFromClient.readLine();
                System.out.println("Recieved = " + clientSentence);
                if (clientSentence.equals("get_start_data")) {
                    String serialize_data = serializeArray(data);
                    outToClient.writeObject(serialize_data);
                }
                else if (clientSentence.equals("add_new_flat")) {
                    outToClient.writeObject("ok"+ (char)13);
                    String response = inFromClient.readLine();
                    Flat flat = Flat.deserializeFlat(response);
                    data.add(flat);
                    outToClient.writeObject("success" + (char) 13);
                }
                else if (clientSentence.equals("search_by_count_rooms")) {
                    System.out.println("search_by_count_rooms121212");
                    outToClient.writeObject("ok"+ (char)13);
                    String response = inFromClient.readLine();
                    if (!response.equals("")) {
                        List<Flat> arr = new ArrayList<Flat>();
                        for (int i = 0; i < data.size(); i++)
                            if (data.get(i).getCountRooms().equals(Integer.parseInt(response)))
                                arr.add(data.get(i));
                        String serialize_data = serializeArray(arr);
                        outToClient.writeObject(serialize_data);
                    }
                }
                else if (clientSentence.equals("search_by_floor_and_square")) {
                    System.out.println("search_by_floor_and_square");
                    outToClient.writeObject("ok"+ (char)13);
                    String response = inFromClient.readLine();
                    System.out.println(response);
                    String[] response_split = response.split("%");
                    System.out.println(response_split[0]);
                    System.out.println(response_split);
                    System.out.println(response_split[1]);
                    if (!response.equals("")) {
                        List<Flat> arr = new ArrayList<Flat>();
                        for (int i = 0; i < data.size(); i++)
                            if (data.get(i).getFlatFloor() > Integer.parseInt(response_split[0]) && data.get(i).getFlatSquare() > Double.parseDouble(response_split[1]))
                                arr.add(data.get(i));
                        String serialize_data = serializeArray(arr);
                        outToClient.writeObject(serialize_data);
                    }
                }
            }
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }


    public static String serializeArray(List<Flat> arr) {
        String res = "[";
        for(int i = 0; i < arr.size(); i++) {
            res += arr.get(i).serializeObj();
            if (i != arr.size() - 1)
                res += ",";
        }
        res += "]" + (char)13;
        System.out.println(res);
        return res;
    }


    public static void main(String[] args) {
        startServer(4444);
    }
}
