package com.example.COMPARAR.BINARIOS;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BinaryComparisonController {

    // Clase auxiliar para el request
    public static class BinaryRequest {
        private String binaryA;
        private String binaryB;

        public String getBinaryA() {
            return binaryA;
        }

        public void setBinaryA(String binaryA) {
            this.binaryA = binaryA;
        }

        public String getBinaryB() {
            return binaryB;
        }

        public void setBinaryB(String binaryB) {
            this.binaryB = binaryB;
        }
    }

    // Clase auxiliar para la respuesta
    public static class BinaryResponse {
        private String markedBinaryA;
        private String markedBinaryB;

        public BinaryResponse(String markedBinaryA, String markedBinaryB) {
            this.markedBinaryA = markedBinaryA;
            this.markedBinaryB = markedBinaryB;
        }

        public String getMarkedBinaryA() {
            return markedBinaryA;
        }

        public String getMarkedBinaryB() {
            return markedBinaryB;
        }
    }

    // Método principal para comparar los binarios
    @PostMapping("/compare")
    public ResponseEntity<Map<String, Object>> compareBinaries(@RequestBody BinaryRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "https://comparador-binarios-ang.vercel.app");
        headers.add("Access-Control-Allow-Credentials", "true");

        String binaryA = request.getBinaryA();
        String binaryB = request.getBinaryB();
        List<String> validationErrors = new ArrayList<>();

        if (binaryA.length() != 128) {
            validationErrors.add("Binary A has " + binaryA.length() + " bits instead of 128.");
        }

        if (binaryB.length() != 128) {
            validationErrors.add("Binary B has " + binaryB.length() + " bits instead of 128.");
        }

        if (!validationErrors.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errors", validationErrors);
            return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
        }

        List<Integer> differingPositions = new ArrayList<>();

        for (int i = 0; i < 128; i++) {
            if (binaryA.charAt(i) != binaryB.charAt(i)) {
                differingPositions.add(i);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("binaryA", binaryA);
        response.put("binaryB", binaryB);
        response.put("differingPositions", differingPositions);

        if (differingPositions.isEmpty()) {
            response.put("message", "No differences found between the binaries.");
        }

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }


    // Método para manejar solicitudes OPTIONS (Preflight request)
    @RequestMapping(value = "/compare", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptionsRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "POST,OPTIONS");
        headers.add("Access-Control-Allow-Origin", "https://comparador-binarios-ang.vercel.app");
        headers.add("Access-Control-Allow-Methods", "POST,OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Access-Control-Allow-Credentials", "true");

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }


    // Método para manejar excepciones globalmente
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "An unexpected error occurred. Please try again.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
