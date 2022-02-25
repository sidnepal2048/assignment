/**
 * by Siddhartha Nepal
 */
package assignment.assignment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void happy_path() throws Exception {
        String username ="siddhartha";
        String password ="Siddhartha1!";
        String ipAddress = "24.48.0.1";
        this.mockMvc.perform(get("/sendPayload").param("username", username).
                        param("password",password).param("ipAddress",ipAddress)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Montreal")));
    }

    @Test
    public void username_null_throw_error() throws Exception {
        String password ="Siddhartha1!";
        String ipAddress = "24.48.0.1";
        this.mockMvc.perform(get("/sendPayload").param("username", (String) null).
                        param("password",password).param("ipAddress",ipAddress)).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void password_null_throw_error() throws Exception {
        String username = "Siddhartha";
        String ipAddress = "24.48.0.1";
        this.mockMvc.perform(get("/sendPayload").param("username", username).
                param("password", (String) null).param("ipAddress",ipAddress)).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void ipAddress_null_throw_error() throws Exception {
        String username = "Siddhartha";
        String password ="Siddhartha1!";
        this.mockMvc.perform(get("/sendPayload").param("username", username).
                param("password",password).param("ipAddress", (String) null)).andDo(print()).andExpect(status().isBadRequest());
    }
}
