package fontys.ict.kwetter.KwetterAccountService;

import com.google.gson.Gson;
import fontys.ict.kwetter.KwetterAccountService.config.JwtAuthenticationEntryPoint;
import fontys.ict.kwetter.KwetterAccountService.config.JwtRequestFilter;
import fontys.ict.kwetter.KwetterAccountService.config.JwtTokenUtil;
import fontys.ict.kwetter.KwetterAccountService.config.WebSecurityConfiguration;
import fontys.ict.kwetter.KwetterAccountService.controllers.AccountController;
import fontys.ict.kwetter.KwetterAccountService.models.Account;
import fontys.ict.kwetter.KwetterAccountService.repositories.AccountRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = KwetterAccountServiceApplication.class)
@WebMvcTest(AccountController.class)
    class AccountIntegrationTests {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private WebSecurityConfiguration webSecurityConfiguration;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final Gson gson = new Gson();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void contextLoads() {
        assertThat(accountRepository).isNotNull();
        assertThat(webSecurityConfiguration).isNotNull();
        assertThat(jwtAuthenticationEntryPoint).isNotNull();
        assertThat(jwtTokenUtil).isNotNull();

    }

    @Test
    @WithMockUser(username = "admin", roles = "admin")
    public void getAllAccountsAPI() throws Exception {

        Account account1 = new Account(0L,false,"test",null,"bio","location","web",new ArrayList<>(), new ArrayList<>());
        List<Account> allAccounts = new ArrayList<>();
        allAccounts.add(account1);

        given(accountRepository.findAll()).willReturn(allAccounts);
        mvc.perform(MockMvcRequestBuilders
                .get("/account")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = "admin")
    public void getAccountAPI() throws Exception {
        Account account1 = new Account(0L,false,"test",null,"bio","location","web",new ArrayList<>(), new ArrayList<>());

        given(accountRepository.findAccountById(0L)).willReturn(Optional.of(account1));
        mvc.perform(MockMvcRequestBuilders
                .get("/account/0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = "admin")
    public void getAccountByUsernameAPI() throws Exception {
        Account account1 = new Account(0L,false,"test",null,"bio","location","web",new ArrayList<>(), new ArrayList<>());

        given(accountRepository.findAccountByUsername("test")).willReturn(Optional.of(account1));
        mvc.perform(MockMvcRequestBuilders
                .get("/account/getAccountByUsername/test")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = "admin")
    public void searchAccountsAPI() throws Exception {
        Account account1 = new Account(0L,false,"test",null,"bio","location","web",new ArrayList<>(), new ArrayList<>());
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        given(accountRepository.findTop10AccountsByUsernameContains("test")).willReturn(accounts);
        mvc.perform(MockMvcRequestBuilders
                .get("/account/search/test")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = "admin")
    public void createAccountAPI() throws Exception {
        Account account1 = new Account(0L,false,"test",null,"bio","location","web",new ArrayList<>(), new ArrayList<>());
        given(accountRepository.save(any())).willReturn(account1);
        mvc.perform(MockMvcRequestBuilders
                .post("/account").content(gson.toJson(account1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
}
