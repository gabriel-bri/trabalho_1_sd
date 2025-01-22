package com.example.Q2;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    public static void main(String[] args) {
        int porta = 5000;

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor aguardando conexões na porta " + porta);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Conexão estabelecida com " + clientSocket.getInetAddress());

                    OutputStream output = clientSocket.getOutputStream();
                    enviarAparelhos(output);
                } catch (IOException e) {
                    System.err.println("Erro ao processar conexão com o cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    private static void enviarAparelhos(OutputStream output) throws IOException {
        // Dados de exemplo: 2 aparelhos
        output.write(2); // Número de aparelhos

        // Primeiro aparelho
        String modelo1 = "TV Samsung";
        String preco1 = "1500.00";
        output.write(modelo1.length());
        output.write(modelo1.getBytes());
        output.write(preco1.length());
        output.write(preco1.getBytes());

        // Segundo aparelho
        String modelo2 = "DVD Player LG";
        String preco2 = "500.00";
        output.write(modelo2.length());
        output.write(modelo2.getBytes());
        output.write(preco2.length());
        output.write(preco2.getBytes());

        output.flush();
    }
}
