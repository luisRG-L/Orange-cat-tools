package com.orangecat.utiles;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            // Crear contexto raíz que sirva el HTML del lexer
            server.createContext("/", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    String htmlContent = "<p>Hola</p>"; // Obtener el HTML dinámico
                    exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                    exchange.sendResponseHeaders(200, htmlContent.getBytes().length);

                    // Enviar el contenido HTML como respuesta
                    OutputStream os = exchange.getResponseBody();
                    os.write(htmlContent.getBytes());
                    os.close();
                }
            });

            // Iniciar el servidor
            server.setExecutor(null); // Usar el ejecutor por defecto
            server.start();
            System.out.println("Server running at http://localhost:8080");

            // Esperar a que el usuario presione una tecla para detener el servidor
            Scanner sc = new Scanner(System.in);
            System.out.println("Press any key to stop the server...");
            sc.next();

            // Detener el servidor
            server.stop(0);
            System.out.println("Server stopped");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
