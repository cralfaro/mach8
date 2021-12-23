package com.mach8.nba;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
class NbaApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	@SuppressWarnings("rawtypes")
	public void checkGetValidPlayersPairs() {
		int totalInches = 139;
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/players/pairs/height/" + totalInches, Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Map<String, Object> body = (Map<String, Object>)entity.getBody();
		if(body != null) {
			then(body).containsKey("totalPairs");
			then(body).containsKey("pairs");
			int totalPairs = (Integer) entity.getBody().get("totalPairs");
			List<String> pairsPlayers = (List<String>) entity.getBody().get("pairs");
			then(totalPairs).isEqualTo(2);
			then(pairsPlayers.size()).isEqualTo(2);
			then(pairsPlayers.get(0)).isEqualTo("Brevin Knight - Nate Robinson");
			then(pairsPlayers.get(1)).isEqualTo("Mike Wilks - Nate Robinson");
		}
		else{
			assert false;
		}
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void checkGetNoMatchesPlayersPairs() {
		int totalInches = 260;
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/players/pairs/height/" + totalInches, Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Map<String, Object> body = (Map<String, Object>)entity.getBody();
		if(body != null) {
			then(body).containsKey("totalPairs");
			then(body).containsKey("message");
			int totalPairs = (Integer) entity.getBody().get("totalPairs");
			then(totalPairs).isEqualTo(0);
			then(body.get("message")).isEqualTo("No matches found");
		}
		else{
			assert false;
		}
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void checkGetValidPlayersPairsDetails() {
		int totalInches = 139;
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/players/pairs/height/" + totalInches + "/detail", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Map<String, Object> body = (Map<String, Object>)entity.getBody();
		if(body != null) {
			then(body).containsKey("totalPairs");
			then(body).containsKey("pairs");
			int totalPairs = (Integer) entity.getBody().get("totalPairs");
			List<List<LinkedHashMap>> pairsPlayers = (List<List<LinkedHashMap>>) entity.getBody().get("pairs");
			then(totalPairs).isEqualTo(2);
			then(pairsPlayers.size()).isEqualTo(2);
			then(pairsPlayers.get(0).get(0).get("firstName")).isEqualTo("Brevin");
			then(pairsPlayers.get(0).get(1).get("firstName")).isEqualTo("Nate");
			then(pairsPlayers.get(0).get(0).get("heightInches")).isEqualTo(70);
			then(pairsPlayers.get(0).get(1).get("heightInches")).isEqualTo(69);

			then(pairsPlayers.get(1).get(0).get("firstName")).isEqualTo("Mike");
			then(pairsPlayers.get(1).get(1).get("firstName")).isEqualTo("Nate");
			then(pairsPlayers.get(1).get(0).get("heightInches")).isEqualTo(70);
			then(pairsPlayers.get(1).get(1).get("heightInches")).isEqualTo(69);

		}
		else{
			assert false;
		}
	}

}
