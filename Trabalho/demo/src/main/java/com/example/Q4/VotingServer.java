package com.example.Q4;

import com.example.protobuf.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class VotingServer {
    private static final int TCP_PORT = 5000;
    private static final int UDP_PORT = 7000;
    private static final long VOTING_END_TIME = System.currentTimeMillis() + 20000;
    static final Set<String> adminIds = new HashSet<>(Arrays.asList("admin123"));
    static final Map<String, Integer> votes = new ConcurrentHashMap<>();
    static final List<String> candidateList = new CopyOnWriteArrayList<>(Arrays.asList("Candidato 1", "Candidato 2", "Candidato 3"));
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private static DatagramSocket udpSocket;

    static {
        try {
            udpSocket = new DatagramSocket(UDP_PORT);
        } catch (SocketException e) {
            throw new RuntimeException("Erro ao iniciar o socket UDP", e);
        }
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
            System.out.println("Servidor de Votação Iniciado...");

            new Thread(VotingServer::handleMulticast).start();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.submit(new ClientHandler(clientSocket));
            }
        }
    }

    private static void handleMulticast() {
        try {
            InetAddress group = InetAddress.getByName("224.0.0.1");
            while (true) {
                String message = "Nota Informativa: A votação está aberta!";
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, UDP_PORT);
                udpSocket.send(packet);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isVotingOpen() {
        return System.currentTimeMillis() < VOTING_END_TIME;
    }

    public static void calculateResults() {
        int totalVotes = votes.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total de Votos: " + totalVotes);
        candidateList.forEach(candidate -> {
            int voteCount = votes.getOrDefault(candidate, 0);
            double percentage = totalVotes == 0 ? 0 : (voteCount / (double) totalVotes) * 100;
            System.out.printf("Candidato: %s - %d votos (%.2f%%)\n", candidate, voteCount, percentage);
        });
    }
}
