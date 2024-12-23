/**
 * Service for managing WebSocket communication with a blockchain node.
 * Handles connection initialization, sending requests, and processing responses.
 * This service interacts with the blockchain node's WebSocket RPC API
 * to fetch data such as the latest block and other blockchain details.
 *
 * @author cypherfury
 */
package dev.june.juniscan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.june.juniscan.dto.BlockDTO;
import dev.june.juniscan.dto.RpcResponseDTO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Service to manage WebSocket connections with a blockchain node.
 * Handles the initialization, communication, and parsing of messages.
 */
@Service
@Slf4j
public class WebSocketNodeService {

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON parser
    private final String rpcUrl; // URL of the WebSocket RPC endpoint
    private WebSocketSession currentSession; // Active WebSocket session

    /**
     * Constructor to initialize the WebSocketNodeService with the RPC URL.
     * @param rpcUrl the WebSocket URL injected from application properties.
     */
    public WebSocketNodeService(@Value("${rpc.url}") String rpcUrl) {
        this.rpcUrl = rpcUrl;
    }

    /**
     * Initializes the WebSocket connection after the service is fully constructed.
     */
    @PostConstruct
    public void initializeWebSocketConnection() {
        try {
            log.info("Initializing WebSocket connection to {}", rpcUrl);
            StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
            webSocketClient.execute(new NodeWebSocketHandler(), rpcUrl);
        } catch (Exception e) {
            log.error("Error initializing WebSocket connection: {}", e.getMessage());
        }
    }

    /**
     * Sends a request to fetch the latest block from the blockchain node.
     */
    public void fetchLatestBlock() {
        try {
            if (currentSession != null && currentSession.isOpen()) {
                // JSON-RPC request to fetch the latest block
                String request = "{\"id\":1,\"jsonrpc\":\"2.0\",\"method\":\"chain_getBlock\",\"params\":[]}";
                currentSession.sendMessage(new TextMessage(request));
                log.info("Request sent: {}", request);
            } else {
                log.warn("WebSocket session is not open. Unable to send request.");
            }
        } catch (Exception e) {
            log.error("Error sending WebSocket request: {}", e.getMessage());
        }
    }

    /**
     * Inner class to handle WebSocket events like connection, messages, and errors.
     */
    private class NodeWebSocketHandler extends TextWebSocketHandler {

        /**
         * Called when the WebSocket connection is successfully established.
         * Stores the active session.
         */
        @Override
        public void afterConnectionEstablished(@NonNull WebSocketSession session) {
            log.info("WebSocket connection established!");
            currentSession = session;
        }

        /**
         * Called when a message is received from the WebSocket.
         * Parses the message and logs the relevant data.
         */
        @Override
        protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) {
            String payload = message.getPayload();
            log.info("Message received: {}", payload);

            try {
                // Parse the JSON message into the RpcResponseDTO object
                RpcResponseDTO response = objectMapper.readValue(payload, RpcResponseDTO.class);
                log.info("Parsed Response: {}", response);

                // Extract block details from the parsed response
                BlockDTO block = response.getResult().getBlock();
                log.info("Parsed Block: {}", block);

                // Log justifications, if available
                log.info("Justifications: {}", response.getResult().getJustifications());
            } catch (Exception e) {
                log.error("Error parsing JSON: {}", e.getMessage());
            }
        }

        /**
         * Called when a WebSocket transport error occurs.
         */
        @Override
        public void handleTransportError(@NonNull WebSocketSession session, Throwable exception) {
            log.error("WebSocket transport error: {}", exception.getMessage());
        }

        /**
         * Called when the WebSocket connection is closed.
         * Clears the current session.
         */
        @Override
        public void afterConnectionClosed(@NonNull WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
            log.info("WebSocket connection closed: {}", status.getReason());
            currentSession = null;
        }
    }

}
