package com.example.me.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.me.utils.RSAKeyLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GoogleTokenValidator {


    public static void validToken(String token) {
        try {
            // Extraemos el 'kid' del token
            String kid = JWT.decode(token).getKeyId();

            // Obtenemos la clave pública correspondiente al 'kid'
            RSAPublicKey publicKey = getPublicKeyForToken(kid);

            // Verificamos la firma del token
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm).build();

        } catch (JWTVerificationException e) {
            System.err.println("Token no válido: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static RSAPublicKey getPublicKeyForToken(String kid) throws JsonProcessingException {

        List<Map<String, Object>> keys = getPublicKeys();

        for (Map<String, Object> key : keys) {
            if (key.get("kid").equals(kid)) {
                // Debes convertir los valores de la clave 'n' y 'e' a un RSAPublicKey
                String n = (String) key.get("n");
                String e = (String) key.get("e");
                // Aquí puedes usar una librería como `RSAPublicKey` para convertir estos valores a una clave pública
                return convertToRSAPublicKey(n, e); // Implementa esta conversión.
            }
        }
        throw new RuntimeException("Clave pública no encontrada.");
    }

    public static RSAPublicKey convertToRSAPublicKey(String n, String e) {
        try {
            // Decodificar Base64 URL-safe (sin relleno)
            byte[] modulusBytes = Base64.getUrlDecoder().decode(n);
            byte[] exponentBytes = Base64.getUrlDecoder().decode(e);

            // Convertir a BigInteger
            BigInteger modulus = new BigInteger(1, modulusBytes);
            BigInteger exponent = new BigInteger(1, exponentBytes);

            // Crear especificación de clave pública RSA
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            // Generar la clave pública
            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

        } catch (Exception ex) {
            throw new RuntimeException("Error al convertir a RSAPublicKey", ex);
        }
    }

    private static List<Map<String, Object>> getPublicKeys() throws JsonProcessingException {

        Map<String, Object> key1 = new HashMap<>();
        key1.put("alg", "RS256");
        key1.put("n", "jb7Wtq9aDMpiXvHGCB5nrfAS2UutDEkSbK16aDtDhbYJhDWhd7vqWhFbnP0C_XkSxsqWJoku69y49EzgabEiUMf0q3X5N0pNvV64krviH2m9uLnyGP5GMdwZpjTXARK9usGgYZGuWhjfgTTvooKDUdqVQYvbrmXlblkM6xjbA8GnShSaOZ4AtMJCjWnaN_UaMD_vAXvOYj4SaefDMSlSoiI46yipFdggfoIV8RDg1jeffyre_8DwOWsGz7b2yQrL7grhYCvoiPrybKmViXqu-17LTIgBw6TDk8EzKdKzm33_LvxU7AKs3XWW_NvZ4WCPwp4gr7uw6RAkdDX_ZAn0TQ");
        key1.put("use", "sig");
        key1.put("e", "AQAB");
        key1.put("kty", "RSA");
        key1.put("kid", "23f7a3583796f97129e5418f9b2136fcc0a96462");

        Map<String, Object> key2 = new HashMap<>();
        key2.put("n", "03Cww27F2O7JxB5Ji9iT9szfKZ4MK-iPzVpQkdLjCuGKfpjaCVAz9zIQ0-7gbZ-8cJRaSLfByWTGMIHRYiX2efdjz1Z9jck0DK9W3mapFrBPvM7AlRni4lPlwUigDd8zxAMDCheqyK3vCOLFW-1xYHt_YGwv8b0dP7rjujarEYlWjeppO_QMNtXdKdT9eZtBEcj_9ms9W0aLdCFNR5AAR3y0kLkKR1H4DW7vncB46rqCJLenhlCbcW0MZ3asqcjqBQ2t9QMRnY83Zf_pNEsCcXlKp4uOQqEvzjAc9ZSr2sOmd_ESZ_3jMlNkCZ4J41TuG-My5illFcW5LajSKvxD3w");
        key2.put("kty", "RSA");
        key2.put("use", "sig");
        key2.put("kid", "07b80a365428525f8bf7cd0846d74a8ee4ef3625");
        key2.put("alg", "RS256");
        key2.put("e", "AQAB");


        return List.of(key1, key2);
    }

}
