package aforo.rateplanservie.config;

import aforo.rateplanservie.RateplanservieApplication;
import aforo.rateplanservie.repos.CurrenciesRepository;
import aforo.rateplanservie.repos.ProductRepository;
import aforo.rateplanservie.repos.RatePlanFlatRateDetailsRepository;
import aforo.rateplanservie.repos.RatePlanFlatRateRepository;
import aforo.rateplanservie.repos.RatePlanFreemiumRateDetailsRepository;
import aforo.rateplanservie.repos.RatePlanFreemiumRateRepository;
import aforo.rateplanservie.repos.RatePlanRepository;
import aforo.rateplanservie.repos.RatePlanSubscriptionRateDetailsRepository;
import aforo.rateplanservie.repos.RatePlanSubscriptionRateRepository;
import aforo.rateplanservie.repos.RatePlanTieredRateDetailsRepository;
import aforo.rateplanservie.repos.RatePlanTieredRateRepository;
import aforo.rateplanservie.repos.RatePlanUsageBasedRatesRepository;
import aforo.rateplanservie.repos.RatePlanUsageBasedRepository;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.util.StreamUtils;
import org.testcontainers.containers.MySQLContainer;


/**
 * Abstract base class to be extended by every IT test. Starts the Spring Boot context with a
 * Datasource connected to the Testcontainers Docker instance. The instance is reused for all tests,
 * with all data wiped out before each test.
 */
@SpringBootTest(
        classes = RateplanservieApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("it")
@Sql("/data/clearAll.sql")
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIT {

    @ServiceConnection
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.0");

    static {
        mySQLContainer.withUrlParam("serverTimezone", "UTC")
                .withReuse(true)
                .start();
    }

    @LocalServerPort
    public int serverPort;

    @Autowired
    public RatePlanUsageBasedRatesRepository ratePlanUsageBasedRatesRepository;

    @Autowired
    public RatePlanUsageBasedRepository ratePlanUsageBasedRepository;

    @Autowired
    public RatePlanTieredRateDetailsRepository ratePlanTieredRateDetailsRepository;

    @Autowired
    public RatePlanTieredRateRepository ratePlanTieredRateRepository;

    @Autowired
    public RatePlanFlatRateDetailsRepository ratePlanFlatRateDetailsRepository;

    @Autowired
    public RatePlanFlatRateRepository ratePlanFlatRateRepository;

    @Autowired
    public RatePlanSubscriptionRateDetailsRepository ratePlanSubscriptionRateDetailsRepository;

    @Autowired
    public RatePlanSubscriptionRateRepository ratePlanSubscriptionRateRepository;

    @Autowired
    public RatePlanFreemiumRateDetailsRepository ratePlanFreemiumRateDetailsRepository;

    @Autowired
    public RatePlanFreemiumRateRepository ratePlanFreemiumRateRepository;

    @Autowired
    public RatePlanRepository ratePlanRepository;

    @Autowired
    public CurrenciesRepository currenciesRepository;

    @Autowired
    public ProductRepository productRepository;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = serverPort;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @SneakyThrows
    public String readResource(final String resourceName) {
        return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), StandardCharsets.UTF_8);
    }

}
