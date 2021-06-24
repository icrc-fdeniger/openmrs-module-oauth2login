package org.openmrs.module;

import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

public class JWTTokenDecoder {
	
	public static void main(String[] args) {
		//token is the entry point
		String token = "eyJ4NXQiOiJNell4TW1Ga09HWXdNV0kwWldObU5EY3hOR1l3WW1NNFpUQTNNV0kyTkRBelpHUXpOR00wWkdSbE5qSmtPREZrWkRSaU9URmtNV0ZoTXpVMlpHVmxOZyIsImtpZCI6Ik16WXhNbUZrT0dZd01XSTBaV05tTkRjeE5HWXdZbU00WlRBM01XSTJOREF6WkdRek5HTTBaR1JsTmpKa09ERmtaRFJpT1RGa01XRmhNelUyWkdWbE5nX1JTMjU2IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJhZG1pbkBjYXJib24uc3VwZXIiLCJhdWQiOiJWbFJPSUtINHU4cVBYeFN1dGN3czF0eTBmQ2NhIiwibmJmIjoxNjI0NTQyMDA4LCJhenAiOiJWbFJPSUtINHU4cVBYeFN1dGN3czF0eTBmQ2NhIiwic2NvcGUiOiJhbV9hcHBsaWNhdGlvbl9zY29wZSBkZWZhdWx0IiwiaXNzIjoiaHR0cHM6XC9cL2d2YS5kY21zYWNjZXNzLm9yZzo5NDQzXC9vYXV0aDJcL3Rva2VuIiwiZXhwIjoxNjI0NTQ1NjA4LCJpYXQiOjE2MjQ1NDIwMDgsImp0aSI6ImEyMGU4ZmQ0LWQ1OGUtNGEwNi05NGU0LTE4YTVjNDk4NDRiYSJ9.B1GSDltBBKeUUEIVoF9rhyuFf-2mNPcnWMY3dsnF6MHZ1rCRimnFUqY_NDS4ZuKVPBC0XvDGl8lFfHAXaWYjYkJuo0u08aThkSapfxPpSD68-egyERYz7g6R24ELgl60-l4wm-nfbJv-1BUodH62ZFa5P41wtXtVYn7JdX_fiurbyR0ackJWQtAxeEt-VtJ26WVSzGy3v80_Of6cTRk7Ve4JPIafCNmkBvH86mhocsRL7Vv0eB9PqKW38pWOLA3yqIY_1hV_t9LyCqU9FA-dTLnQFkCt-h2dxDP-FtKxEpm00HU23FwmFEzElPYPdGDqNyV1jenEPov_wQLeYK6YOQ";
		//public key extracted from wso2carbon.jks
		String publicRSAKey ="-----BEGIN CERTIFICATE-----\n" +
				"MIIDqTCCApGgAwIBAgIEXbABozANBgkqhkiG9w0BAQsFADBkMQswCQYDVQQGEwJV\n" +
				"UzELMAkGA1UECAwCQ0ExFjAUBgNVBAcMDU1vdW50YWluIFZpZXcxDTALBgNVBAoM\n" +
				"BFdTTzIxDTALBgNVBAsMBFdTTzIxEjAQBgNVBAMMCWxvY2FsaG9zdDAeFw0xOTEw\n" +
				"MjMwNzMwNDNaFw0yMjAxMjUwNzMwNDNaMGQxCzAJBgNVBAYTAlVTMQswCQYDVQQI\n" +
				"DAJDQTEWMBQGA1UEBwwNTW91bnRhaW4gVmlldzENMAsGA1UECgwEV1NPMjENMAsG\n" +
				"A1UECwwEV1NPMjESMBAGA1UEAwwJbG9jYWxob3N0MIIBIjANBgkqhkiG9w0BAQEF\n" +
				"AAOCAQ8AMIIBCgKCAQEAxeqoZYbQ/Sr8DOFQ+/qbEbCp6Vzb5hzH7oa3hf2FZxRK\n" +
				"F0H6b8COMzz8+0mvEdYVvb/31jMEL2CIQhkQRol1IruD6nBOmkjuXJSBficklMaJ\n" +
				"ZORhuCrB4roHxzoG19aWmscA0gnfBKo2oGXSjJmnZxIh+2X6syHCfyMZZ00LzDyr\n" +
				"goXWQXyFvCA2ax54s7sKiHOM3P4A9W4QUwmoEi4HQmPgJjIM4eGVPh0GtIANN+BO\n" +
				"Q1KkUI7OzteHCTLu3VjxM0sw8QRayZdhniPF+U9n3fa1mO4KLBsW4mDLjg8R/JuA\n" +
				"GTX/SEEGj0B5HWQAP6myxKFz2xwDaCGvT+rdvkktOwIDAQABo2MwYTAUBgNVHREE\n" +
				"DTALgglsb2NhbGhvc3QwHQYDVR0OBBYEFEDpLB4PDgzsdxD2FV3rVnOr/A0DMB0G\n" +
				"A1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjALBgNVHQ8EBAMCBPAwDQYJKoZI\n" +
				"hvcNAQELBQADggEBAE8H/axAgXjt93HGCYGumULW2lKkgqEvXryP2QkRpbyQSsTY\n" +
				"cL7ZLSVB7MVVHtIsHh8f1C4Xq6Qu8NUrqu5ZLC1pUByaqR2ZIzcj/OWLGYRjSTHS\n" +
				"VmVIq9QqBq1j7r6f3BWqaOIiknmTzEuqIVlOTY0gO+SHdS62vr2FCz4yOrBEulGA\n" +
				"vomsU8sqg4PhFnkhxI4M912Ly+2RgN9L7AkhzK+EzXY1/QtlI/VysNfS6zrHasKz\n" +
				"6CrKKCGqQnBnSvSTyF9OR5KFHnkAwE995IZrcSQicMxsLhTMUHDLQ/gRyy7V/ZpD\n" +
				"MfAWR+5OeQiNAp/bG4fjJoTdoqkul51+2bHHVrU=\n" +
				"-----END CERTIFICATE-----\n";

		SignatureVerifier signatureVerifier=new RsaVerifier(publicRSAKey);
		//with Spring
		JwtAccessTokenConverter tokenConverter=new JwtAccessTokenConverter();
		tokenConverter.setVerifier(signatureVerifier);
		JwtTokenStore tokenStore=new JwtTokenStore(tokenConverter);
		OAuth2AccessToken oAuth2Authentication = tokenStore.readAccessToken(token);
		//warning: sub is not a common key ( user_name is used by spring).
		String user= (String) oAuth2Authentication.getAdditionalInformation().get("sub");
		System.err.println("user in token is "+user);

	}
}
