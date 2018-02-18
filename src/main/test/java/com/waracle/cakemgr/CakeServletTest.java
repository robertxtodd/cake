package com.waracle.cakemgr;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CakeServletTest {


    @Autowired
    private MockMvc mvc;

    @Test
    public void whenCallingRootContextAsTextHtml_weGetCakeList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/")
                .contentType(MediaType.TEXT_HTML)
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/mainPage.jsp"))
                .andExpect(view().name("mainPage"))
                .andDo(print());
    }

    @Test
    public void whenCallingCakeContextAsTextHtml_weGetCakeList() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/cakes")
                .contentType(MediaType.TEXT_HTML)
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/mainPage.jsp"))
                .andExpect(view().name("mainPage"))
                .andDo(print());
    }

    @Test
    public void whenCallingCakesContextAsApplicationJson_weGetCakeListAsJson() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cakes")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andDo(print());
    }

    @Test
    public void whenCallingCakesContextAsBrowserToAdd_weGetCakeListWithOneCakeAdded() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/add")
                .param("title", "Test Cake")
                .param("desc", "Cake added in test")
                .param("image", "https://i1.wp.com/blog.gregwilson.co.uk/wp-content/uploads/2014/10/Gizza-Job.jpeg?resize=295%2C209")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(MockMvcRequestBuilders.get("/cakes")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)));
    }


    @Test
    public void whenTryingToAddACakeUsingAGetRequestAndTextContentType_weGetA415UnsupportedMediaTypeError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/add")
                .contentType(TEXT_HTML)
                .accept(TEXT_HTML))
                .andExpect(status().isUnsupportedMediaType())
                .andDo(print());
    }

    @Test
    public void whenTryingToAddACakeUsingAGetRequestAndJSONContentType_weGetA400Error() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/add")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}