
/**
 * by Siddhartha Nepal
 */

package assignment.assignment.service;

import assignment.assignment.model.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.util.UUID;

@Service
@Slf4j
public class ApiService {
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<UserInfo> getInformation(String username, String password, String ipAddress) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.exchange("http://ip-api.com/json/"+ipAddress+"?fields=country,city",
                                                HttpMethod.GET,
                                                new HttpEntity<>(String.class),
                                                String.class);
        log.info(response.getBody());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        String countryName = root.path("country").asText();
        UserInfo userInfo = new UserInfo();
        if (!countryName.equalsIgnoreCase("canada")){
            userInfo.setErrorMessage("user is not eligible to register");
            return new ResponseEntity<>(userInfo, HttpStatus.BAD_REQUEST);
        }
        String transactionId = UUID.randomUUID().toString();
        String cityName = root.path("city").asText();
        userInfo.setUuid(transactionId);
        userInfo.setUsername(username);
        userInfo.setCity(cityName);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
