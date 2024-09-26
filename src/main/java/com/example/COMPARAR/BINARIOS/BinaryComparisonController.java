package com.example.COMPARAR.BINARIOS;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"https://comparador-binarios-ang.vercel.app"})
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

    @PostMapping("/compare")
    public ResponseEntity<Map<String, Object>> compareBinaries(@RequestBody BinaryRequest request) {
        String binaryA = request.getBinaryA();
        String binaryB = request.getBinaryB();

        // Validación de longitud de los binarios
        if (binaryA.length() != 128 || binaryB.length() != 128) {
            throw new IllegalArgumentException("Both binary patterns must have exactly 128 bits");
        }

        List<Integer> differingPositions = new ArrayList<>();

        // Comparar los bits y guardar las posiciones donde son diferentes
        for (int i = 0; i < 128; i++) {
            if (binaryA.charAt(i) != binaryB.charAt(i)) {
                differingPositions.add(i);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("binaryA", binaryA);
        response.put("binaryB", binaryB);
        response.put("differingPositions", differingPositions);

        return ResponseEntity.ok(response);
    }

    // Método para manejar excepciones globalmente
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Manejo de errores personalizados
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        // Manejo de errores generales
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "An unexpected error occurred. Please try again.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
