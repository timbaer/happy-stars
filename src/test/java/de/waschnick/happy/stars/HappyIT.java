package de.waschnick.happy.stars;

import de.waschnick.happy.stars.business.star.entity.StarEntity;
import de.waschnick.happy.stars.business.star.entity.StarRepository;
import de.waschnick.happy.stars.business.universe.entity.UniverseEntity;
import de.waschnick.happy.stars.business.universe.entity.UniverseRepository;
import de.waschnick.happy.stars.controller.StarController;
import de.waschnick.happy.stars.controller.UniverseController;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HappyIT {

    @Autowired
    private UniverseRepository universeRepository;

    @Autowired
    private StarRepository starRepository;

    @Autowired
    private StarController starController;

    @Autowired
    private UniverseController universeController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(starController, universeController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    public void startUp_shouldInitTestData() throws Exception {
        List<UniverseEntity> universes = universeRepository.findAll();
        List<StarEntity> stars = starRepository.findAll();

        assertThat(stars.size(), is(1));
        assertThat(stars.get(0).getUniverse(), not(nullValue()));

        assertThat(universes.size(), is(1));
        assertThat(universes.get(0).getStars().size(), is(1));
    }

    @Test
    public void startUp_shouldInitTestData_shouldBeFound() throws Exception {
        final MvcResult result = mockMvc.perform(get("/api/stars"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String happyIndexVotes = "{'stars':[{'id':1,'name':'start_1','color':'BLUE','universeId':1}]}";
        assertThat(content, is(Matchers.equalTo(excapeJson(happyIndexVotes))));
    }

    private String excapeJson(String pureValue) {
        return pureValue.replaceAll("'","\"");
    }

}