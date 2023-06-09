package ru.job4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.service.AccidentService;

import java.util.List;
import java.util.Optional;
import java.util.Set;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Job4jAccidentsApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccidentControllerTest {

    @MockBean
    private AccidentService accidentService;

    @Autowired
    private MockMvc mockMvc;

    private final List<Accident> accidentList = List.of(
            new Accident(
                    1,
                    "Name1",
                    "Text1",
                    "Address1",
                    new AccidentType(1, "Accident type 1"),
                    Set.of(new Rule(1, "St1"))),
            new Accident(
                    2,
                    "Name2",
                    "Text2",
                    "Address2",
                    new AccidentType(2, "Accident type 2"),
                    Set.of(new Rule(1, "St1"), new Rule(2, "St2"))),
            new Accident(
                    3,
                    "Name3",
                    "Text3",
                    "Address3",
                    new AccidentType(3, "Accident type 3"),
                    Set.of(new Rule(1, "St1"), new Rule(2, "St2"), new Rule(3, "St3"))
            )
    );

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessageWhenCreateAccident() throws Exception {
        mockMvc.perform(post("/saveAccident")
                        .param("name", "name1")
                        .param("text", "text1")
                        .param("address", "address1")
                        .param("typeId", "2")
                        .param("rIds", "1", "2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }

    @Test
    @WithMockUser
    public void whenFindAllAccidents() {
        when(accidentService.findAll()).thenReturn(accidentList);
    }

    @Test
    @WithMockUser
    void whenFindByIdSuccessful() {
        when(accidentService.findById(1))
                .thenReturn(Optional.ofNullable(accidentList.get(1)));
    }

    @Test
    @WithMockUser
    public void updateAccidentAndRedirectIndex() throws Exception {
        mockMvc.perform(post("/updateAccident", 1)
                        .param("name", "name2")
                        .param("text", "text2")
                        .param("address", "address2")
                        .param("accidentType.id", "2")
                        .param("rIds", "1", "2", "3"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }

}
