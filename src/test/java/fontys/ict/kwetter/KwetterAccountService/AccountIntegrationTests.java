package fontys.ict.kwetter.KwetterAccountService;

import com.google.gson.Gson;
import fontys.ict.kwetter.KwetterAccountService.config.JwtAuthenticationEntryPoint;
import fontys.ict.kwetter.KwetterAccountService.config.JwtRequestFilter;
import fontys.ict.kwetter.KwetterAccountService.config.JwtTokenUtil;
import fontys.ict.kwetter.KwetterAccountService.config.WebSecurityConfig;
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
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    @MockBean
    private WebSecurityConfiguration webSecurityConfiguration;
    @MockBean
    private JwtRequestFilter jwtRequestFilter;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private WebSecurityConfig webSecurityConfig;

    private final Gson gson = new Gson();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void contextLoads() {
        assertThat(accountRepository).isNotNull();
        assertThat(webSecurityConfiguration).isNotNull();
        assertThat(webSecurityConfig).isNotNull();
        assertThat(jwtRequestFilter).isNotNull();
        assertThat(jwtAuthenticationEntryPoint).isNotNull();

    }

    @Test
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
    public void createAccountAPI() throws Exception {
        Account account1 = new Account(0L,false,"test",null,"bio","location","web",new ArrayList<>(), new ArrayList<>());
        given(accountRepository.save(account1)).willReturn(account1);
        mvc.perform(MockMvcRequestBuilders
                .post("/account").content(gson.toJson(account1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
}
