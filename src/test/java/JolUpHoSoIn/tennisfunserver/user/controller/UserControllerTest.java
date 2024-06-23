package JolUpHoSoIn.tennisfunserver.user.controller;

import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.response.ApiResponse;
import joluphosoin.tennisfunserver.user.data.dto.LoginDto;
import joluphosoin.tennisfunserver.user.data.dto.RegistrationDto;
import joluphosoin.tennisfunserver.user.data.dto.UserResDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Value("${test.email}")
    private String email;

    @Value("${test.password}")
    private String password;

    private String baseUrl;
    private RestTemplate restTemplate;
    private HttpHeaders headers;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api";
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("회원 가입 테스트")
    @Order(1)
    public void testRegisterUser() throws Exception {
        // Given
        String url = baseUrl + "/user/register";
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("test@example.com");
        registrationDto.setPassword("Password@1234");
        registrationDto.setName("Test User");
        registrationDto.setNtrp(4.0);
        registrationDto.setBirthDate(LocalDate.of(1990, 1, 1));
        registrationDto.setGender("MALE");

        HttpEntity<RegistrationDto> requestEntity = new HttpEntity<>(registrationDto, headers);

        // When
        ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, ApiResponse.class);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ApiResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("COMMON201", response.getCode());
        assertEquals("Register Success", response.getMessage());
    }

    @Test
    @DisplayName("로그인 테스트")
    @Order(2)
    public void testLoginUser() throws Exception {
        // Given
        String url = baseUrl + "/user/login";
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail(email);
        loginDto.setPassword(password);

        HttpEntity<LoginDto> requestEntity = new HttpEntity<>(loginDto, headers);

        // When
        ResponseEntity<ApiResult<UserResDto>> responseEntity = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<ApiResult<UserResDto>>() {});

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResult<UserResDto> result = responseEntity.getBody();
        assertNotNull(result);
        assertNotNull(result.getResult());
        assertEquals("LOGIN200", result.getCode());
        assertEquals("Login Successful", result.getMessage());
        assertEquals(email, result.getResult().getEmailId());
    }
}