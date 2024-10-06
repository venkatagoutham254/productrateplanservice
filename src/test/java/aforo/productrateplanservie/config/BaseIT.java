package aforo.productrateplanservie.config;

import aforo.productrateplanservie.ProductrateplanservieApplication;
import aforo.productrateplanservie.currencies.repos.CurrenciesRepository;
import aforo.productrateplanservie.product.repos.ProductRepository;
import aforo.productrateplanservie.rate_plan.repos.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_flat_rate.repos.RatePlanFlatRateRepository;
import aforo.productrateplanservie.rate_plan_flat_rate_details.repos.RatePlanFlatRateDetailsRepository;
import aforo.productrateplanservie.rate_plan_freemium_rate.repos.RatePlanFreemiumRateRepository;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.repos.RatePlanFreemiumRateDetailsRepository;
import aforo.productrateplanservie.rate_plan_subscription_rate.repos.RatePlanSubscriptionRateRepository;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.repos.RatePlanSubscriptionRateDetailsRepository;
import aforo.productrateplanservie.rate_plan_tiered_rate.repos.RatePlanTieredRateRepository;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.repos.RatePlanTieredRateDetailsRepository;
import aforo.productrateplanservie.rate_plan_usage_based.repos.RatePlanUsageBasedRepository;
import aforo.productrateplanservie.rate_plan_usage_based_rates.repos.RatePlanUsageBasedRatesRepository;
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
        classes = ProductrateplanservieApplication.class,
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
