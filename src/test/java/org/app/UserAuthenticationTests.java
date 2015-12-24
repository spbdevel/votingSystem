package org.app;


import org.app.srv.TestMessageService;
import org.app.srv.cnfg.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.logging.Logger;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =  {TestConfig.class} )
@WebAppConfiguration
public class UserAuthenticationTests {

    private Logger logger = Logger.getLogger(UserAuthenticationTests.class.getName());


    @Autowired
    private WebApplicationContext context;


    @Autowired
    private TestMessageService testMessageService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/rest").with(testSecurityContext()))
                .build();
    }


    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void getMessageUnauthenticated() {
        String message = testMessageService.getMessage();
        logger.info(message);
    }


    @Test
    @WithMockUser
    public void requestProtectedUrlWithUser() throws Exception {
        mvc
                .perform(get("/rest"))
                        // Ensure we got past Security
                .andExpect(status().isNotFound())
                        // Ensure it appears we are authenticated with user
                .andExpect(authenticated().withUsername("user"));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void requestProtectedUrlWithAdmin() throws Exception {
        mvc
                .perform(get("/rest"))
                        // Ensure we got past Security
                .andExpect(status().isNotFound())
                        // Ensure it appears we are authenticated with user
                .andExpect(authenticated().withUsername("user").withRoles("ADMIN"));
    }


}