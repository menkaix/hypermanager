package com.menkaix.hypermanager.services;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;
import com.google.auth.oauth2.IdTokenProvider.Option;

@Service
public class GoogleCloudAuthService {

	static Logger log = LoggerFactory.getLogger(GoogleCloudAuthService.class);
	
	

	public String getIdentityToken(String audienceURL) throws IOException {

		GoogleCredentials googleCredentials = GoogleCredentials.getApplicationDefault();

		IdTokenCredentials idTokenCredentials = IdTokenCredentials.newBuilder()
				.setIdTokenProvider((IdTokenProvider) googleCredentials).setTargetAudience(audienceURL)
				// Setting the ID token options.
				.setOptions(Arrays.asList(Option.FORMAT_FULL, Option.LICENSES_TRUE)).build();

		// Get the ID token.
		// Once you've obtained the ID token, you can use it to make an authenticated
		// call to the
		// target audience.
		String idToken = idTokenCredentials.refreshAccessToken().getTokenValue();

		return idToken;
	}

}
