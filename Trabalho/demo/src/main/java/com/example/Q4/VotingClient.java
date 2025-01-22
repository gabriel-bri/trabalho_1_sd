package com.example.Q4;

import java.io.*;
import java.net.*;

public class VotingClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            out.writeUTF("eleitor123");

            String loginResponse = in.readUTF();
            System.out.println(loginResponse);

            if (!loginResponse.contains("Administrador")) {
                String candidates = in.readUTF();
                System.out.println("Candidatos disponíveis: " + candidates);

                out.writeUTF("Candidato 1");

                String voteResponse = in.readUTF();
                System.out.println(voteResponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (DatagramSocket udpSocket = new DatagramSocket(6000)) {
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                udpSocket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Mensagem recebida via multicast: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
