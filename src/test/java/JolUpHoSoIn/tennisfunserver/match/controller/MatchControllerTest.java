package JolUpHoSoIn.tennisfunserver.match.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import joluphosoin.tennisfunserver.business.data.dto.CourtHoursDto;
import joluphosoin.tennisfunserver.business.data.dto.CourtResDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchRequestDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchRequestResDto;
import joluphosoin.tennisfunserver.match.data.dto.MatchResultResDto;
import joluphosoin.tennisfunserver.match.data.entity.MatchRequest;
import joluphosoin.tennisfunserver.match.data.entity.MatchResult;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.user.data.dto.LoginDto;
import joluphosoin.tennisfunserver.user.data.dto.UserResDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.geo.Point;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.DayOfWeek.*;
import static joluphosoin.tennisfunserver.business.data.entity.Court.CourtType.HARD;
import static joluphosoin.tennisfunserver.match.data.entity.MatchRequest.MatchObjective.FUN;
import static joluphosoin.tennisfunserver.match.data.entity.MatchResult.FeedbackStatus.LIKE;
import static joluphosoin.tennisfunserver.match.data.entity.MatchResult.FeedbackStatus.NOTSELECTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MatchControllerTest {
    @LocalServerPort
    private int port;

    @Value("${test.match.requestId}")
    private String matchRequestId;

    @Value("${test.userId}")
    private String userId;

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

        // Perform login and set session ID in headers
        performLogin();
    }

    private void performLogin() {
        String loginUrl = baseUrl + "/user/login";
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail(email);
        loginDto.setPassword(password);
        HttpEntity<LoginDto> loginRequest = new HttpEntity<>(loginDto, headers);

        ResponseEntity<ApiResult<UserResDto>> loginResponse = restTemplate.exchange(
                loginUrl, HttpMethod.POST, loginRequest, new ParameterizedTypeReference<ApiResult<UserResDto>>() {});
        List<String> cookies = loginResponse.getHeaders().get(HttpHeaders.SET_COOKIE);
        if (cookies != null) {
            for (String cookie : cookies) {
                if (cookie.startsWith("JSESSIONID")) {
                    headers.set("Cookie", cookie.split(";", 2)[0]); // Set only the JSESSIONID
                    break;
                }
            }
        }
    }

    @Test
    @DisplayName("매칭요청 정보 등록")
    @Order(1)
    public void testRegisterMatchRequest() throws Exception {
        // Given
        String url = baseUrl + "/match/request";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date startTime = dateFormat.parse("2024-06-10T10:00:00.000+00:00");
        Date endTime = dateFormat.parse("2024-06-10T14:00:00.000+00:00");

        MatchRequestDto matchRequestDto = new MatchRequestDto(
                startTime,
                endTime,
                FUN,
                true,
                127.051658,
                37.288412,
                3.6,
                new ArrayList<>(),
                30,
                120,
                false,
                null,
                null,
                "반갑습니다"
        );

        HttpEntity<MatchRequestDto> requestEntity = new HttpEntity<>(matchRequestDto, headers);

        // When
        ResponseEntity<ApiResult<MatchRequestResDto>> responseEntity = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<ApiResult<MatchRequestResDto>>() {});

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ApiResult<MatchRequestResDto> result = responseEntity.getBody();
        assertNotNull(result);
        assertNotNull(result.getResult());

        // Validate the response details
        assertEquals(userId, result.getResult().getUserId());
        assertEquals(matchRequestDto.getStartTime(), result.getResult().getStartTime());
        assertEquals(matchRequestDto.getEndTime(), result.getResult().getEndTime());
        assertEquals(matchRequestDto.getObjective(), result.getResult().getObjective());
        assertEquals(matchRequestDto.getIsSingles(), result.getResult().getIsSingles());
        assertEquals(matchRequestDto.getX(), result.getResult().getX());
        assertEquals(matchRequestDto.getY(), result.getResult().getY());
        assertEquals(matchRequestDto.getMaxDistance(), result.getResult().getMaxDistance());
        assertEquals(matchRequestDto.getDislikedCourts(), result.getResult().getDislikedCourts());
        assertEquals(matchRequestDto.getMinTime(), result.getResult().getMinTime());
        assertEquals(matchRequestDto.getMaxTime(), result.getResult().getMaxTime());
        assertEquals(matchRequestDto.getIsReserved(), result.getResult().getIsReserved());
        assertEquals(matchRequestDto.getReservationCourtId(), result.getResult().getReservationCourtId());
        assertEquals(matchRequestDto.getReservationDate(), result.getResult().getReservationDate());
        assertEquals(matchRequestDto.getDescription(), result.getResult().getDescription());
    }
    @Test
    @Order(2)
    @DisplayName("매칭요청 정보 조회")
    public void testGetMatchRequest() throws Exception {
        // Given
        String url = baseUrl + "/match/request";
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date startTime = dateFormat.parse("2024-06-10T10:00:00.000+00:00");
        Date endTime = dateFormat.parse("2024-06-10T14:00:00.000+00:00");
        // Expected response data
        MatchRequestResDto expectedResponse = new MatchRequestResDto(
                matchRequestId,
                userId,
                startTime,
                endTime,
                FUN,
                true,
                127.051658,
                37.288412,
                3.6,
                new ArrayList<>(),
                30,
                120,
                false,
                null,
                null,
                "반갑습니다"
        );

        // When
        ResponseEntity<ApiResult<MatchRequestResDto>> responseEntity = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<ApiResult<MatchRequestResDto>>() {});

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResult<MatchRequestResDto> result = responseEntity.getBody();
        assertNotNull(result);
        assertNotNull(result.getResult());

        // Assert the response using JSON comparison
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);
        String actualJson = objectMapper.writeValueAsString(result.getResult());
        assertEquals(expectedJson, actualJson);
    }

    @Test
    @Order(3)
    @DisplayName("매칭결과 조회")
    public void testGetMatchResult() throws Exception {
        // Given
        String url = baseUrl + "/match/results";
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        UserResDto opponent1 = new UserResDto("663a0b8954ded836e72ea57d", "관주킴", "dev.shhan@gmail.com", 23, "FEMALE", new Point(126.9998913, 37.2661683), 5.0, new ArrayList<>(), 3.5, 36.5, null, true);
        MatchResult.MatchDetails matchDetails1 = new MatchResult.MatchDetails(dateFormat.parse("2024-06-10T10:00:00.000+00:00"), dateFormat.parse("2024-06-10T12:00:00.000+00:00"), new Point(127.02531, 37.274063), "6647034fcb787760b7c01ef6", "HARD", FUN, false);
        CourtResDto court1 = new CourtResDto(
                "6647034fcb787760b7c01ef6",
                new Point(127.0253053, 37.2740633),
                "수원시 수원공고",
                "665b1dac0b8df7460a248bf2",
                HARD,
                "수원공고 1코트",
                List.of(
                        new CourtHoursDto(SUNDAY, "10:00", "22:00"),
                        new CourtHoursDto(MONDAY, "10:00", "22:00"),
                        new CourtHoursDto(TUESDAY, "10:00", "22:00"),
                        new CourtHoursDto(WEDNESDAY, "10:00", "22:00"),
                        new CourtHoursDto(THURSDAY, "10:00", "22:00"),
                        new CourtHoursDto(FRIDAY, "10:00", "22:00"),
                        new CourtHoursDto(SATURDAY, "10:00", "22:00")
                )
        );

        UserResDto opponent2 = new UserResDto("664e01b6381ee6719ca9fa7d", "김영은", "dev.young16@seunglab.dev", 27, "FEMALE", new Point(126.9975653, 37.2902897), 2.0, new ArrayList<>(), 2.0, 36.5, null, true);
        MatchResult.MatchDetails matchDetails2 = new MatchResult.MatchDetails(dateFormat.parse("2024-06-10T10:00:00.000+00:00"), dateFormat.parse("2024-06-10T11:30:00.000+00:00"), new Point(127.01989, 37.285206), "6647030ccb787760b7c01e02", "HARD", FUN, false);

        CourtResDto court2 = new CourtResDto(
                "6647030ccb787760b7c01e02",
                new Point(127.0198875, 37.2852066),
                "수원시 삼일공고",
                "665b1e670b8df7460a248bf4",
                HARD,
                "삼일공고 1코트",
                List.of(
                        new CourtHoursDto(SUNDAY, "10:00", "22:00"),
                        new CourtHoursDto(MONDAY, "10:00", "22:00"),
                        new CourtHoursDto(TUESDAY, "10:00", "22:00"),
                        new CourtHoursDto(WEDNESDAY, "10:00", "22:00"),
                        new CourtHoursDto(THURSDAY, "10:00", "22:00"),
                        new CourtHoursDto(FRIDAY, "10:00", "22:00"),
                        new CourtHoursDto(SATURDAY, "10:00", "22:00")
                )
        );

        List<MatchResultResDto> expectedResponse = List.of(
                new MatchResultResDto("6666b680d97dcb5322d7cf78", opponent1, matchDetails1, court1, NOTSELECTED),
                new MatchResultResDto("66667884ab31d1fc045f142c", opponent2, matchDetails2, court2, NOTSELECTED)
        );

        // When
        ResponseEntity<ApiResult<List<MatchResultResDto>>> responseEntity = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<ApiResult<List<MatchResultResDto>>>() {});

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResult<List<MatchResultResDto>> result = responseEntity.getBody();
        assertNotNull(result);
        assertNotNull(result.getResult());

        // Assert the response using JSON comparison
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);
        String actualJson = objectMapper.writeValueAsString(result.getResult());
        assertEquals(expectedJson, actualJson);
    }





}