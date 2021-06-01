package fontys.ict.kwetter.KwetterAccountService;

import com.google.gson.Gson;
import fontys.ict.kwetter.KwetterAccountService.config.JwtRequestFilter;
import fontys.ict.kwetter.KwetterAccountService.config.JwtTokenUtil;
import fontys.ict.kwetter.KwetterAccountService.config.WebSecurityConfig;
import fontys.ict.kwetter.KwetterAccountService.controllers.FollowController;
import fontys.ict.kwetter.KwetterAccountService.models.Account;
import fontys.ict.kwetter.KwetterAccountService.models.Follow;
import fontys.ict.kwetter.KwetterAccountService.repositories.AccountRepository;
import fontys.ict.kwetter.KwetterAccountService.repositories.FollowRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.FilterChain;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = KwetterAccountServiceApplication.class)
@WebMvcTest(FollowController.class)
public class FollowIntegrationTests {
/*    @Autowired
    private MockMvc mvc;

    @MockBean
    private FollowRepository followRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AmqpTemplate rabbitTemplate;

    private final Gson gson = new Gson();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void contextLoads() {
        assertThat(followRepository).isNotNull();
        assertThat(accountRepository).isNotNull();
    }

    @Test
    public void getFollowingAPI() throws Exception {
        Account account1 = new Account(0L,false,"test",null,"bio","location","web",new ArrayList<>(), new ArrayList<>());
        Account account2 = new Account(1L,false,"test",null,"bio","location","web",new ArrayList<>(), new ArrayList<>());
        Follow follow = new Follow(0L, account1,account2);
        List<Follow> follows = new ArrayList<>();
        follows.add(follow);
        given(followRepository.getAllByAccount_Id(1L)).willReturn(follows);
        mvc.perform(MockMvcRequestBuilders
                .get("/follow/following/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    public void getFollowersAPI() throws Exception {
        Account account1 = new Account(0L,false,"test",null,"bio","location","web",new ArrayList<>(), new ArrayList<>());
        Account account2 = new Account(1L,false,"test",null,"bio","location","web",new ArrayList<>(), new ArrayList<>());
        Follow follow = new Follow(0L, account1,account2);
        List<Follow> follows = new ArrayList<>();
        follows.add(follow);
        given(followRepository.getAllByFollowingAccount_Id(1L)).willReturn(follows);
        mvc.perform(MockMvcRequestBuilders
                .get("/follow/followers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
    }*/
}
