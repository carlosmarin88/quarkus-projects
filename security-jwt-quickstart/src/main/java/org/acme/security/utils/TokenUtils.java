package org.acme.security.utils;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import org.acme.dto.Role;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

public class TokenUtils {

    public static String generateToken(String username, Set<Role> roles, Long duration, String issuer)
            throws Exception {

        String privateKeyLocation = "/privatekey.pem";
        PrivateKey privateKey = readPrivateKey(privateKeyLocation);

        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        long currentTimeInSecs = currentTimeInSecs();

        Set<String> groups = new HashSet<>();
		for (Role role : roles) groups.add(role.toString());

		claimsBuilder.issuer(issuer);
		claimsBuilder.subject(username);
		claimsBuilder.issuedAt(currentTimeInSecs);
		claimsBuilder.expiresAt(currentTimeInSecs + duration);
		claimsBuilder.groups(groups);

		return claimsBuilder.jws().signatureKeyId(privateKeyLocation).sign(privateKey);

        
    }

    private static PrivateKey readPrivateKey(String privateKeyLocation) throws Exception {

        try (InputStream contentIS = TokenUtils.class.getResourceAsStream(privateKeyLocation)) {
            byte[] tmp = new byte[4096];
            int length = contentIS.read(tmp);
            return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));

        }
    }

    private static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {

        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(keySpec);

    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    private static String removeBeginEnd(String pem) {        
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
		pem = pem.replaceAll("-----END (.*)----", "");
		pem = pem.replaceAll("\r\n", "");
		pem = pem.replaceAll("\n", "");
		return pem.trim();
    }

    public static int currentTimeInSecs() {
		long currentTimeMS = System.currentTimeMillis();
		return (int) (currentTimeMS / 1000);
	}
}
