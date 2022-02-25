/**
 * by Siddhartha Nepal
 */
package assignment.assignment.controller;

import assignment.assignment.model.UserInfo;
import assignment.assignment.service.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.regex.Pattern;

@RestController
public class ApiController {

    @Autowired
    private ApiService apiService;
    @GetMapping(value = "/sendPayload")
    @ApiOperation(value = "Get response", response = UserInfo.class, tags = "validatePayloadAndSendResponse")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request!")})

    public ResponseEntity<UserInfo> validatePayloadAndSendResponse(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String ipAddress) throws JsonProcessingException {

        String regex ="^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d!@#$%^&*()_+]{8,}";
        UserInfo userInfo = new UserInfo();
        if(!StringUtils.hasLength(username)){
            userInfo.setErrorMessage("user cannot null");
            return new ResponseEntity<>(userInfo, HttpStatus.BAD_REQUEST);
        }

        if(!Pattern.matches(regex, password)){
            userInfo.setErrorMessage("Password need to be greater than 8 characters, containing at least" +
                    "1 number, 1 Capitalized letter, 1 special character in this set '_#$%.'");
            return new ResponseEntity<>(userInfo, HttpStatus.BAD_REQUEST);
        }

        if(!StringUtils.hasLength(ipAddress)){
            userInfo.setErrorMessage("ip address cannot be null");
            return new ResponseEntity<>(userInfo, HttpStatus.BAD_REQUEST);
        }
        return apiService.getInformation(username, password, ipAddress);
    }
}
