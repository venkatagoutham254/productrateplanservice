package aforo.productrateplanservie;
import aforo.productrateplanservie.currencies.CurrenciesResource;
import aforo.productrateplanservie.product.ProductResource;
import aforo.productrateplanservie.rate_plan.RatePlanResource;
import aforo.productrateplanservie.rate_plan_flat_rate.RatePlanFlatRateResource;
import aforo.productrateplanservie.rate_plan_freemium_rate.RatePlanFreemiumRateResource;
import aforo.productrateplanservie.rate_plan_subscription_rate.RatePlanSubscriptionRateResource;
import aforo.productrateplanservie.rate_plan_tiered_rate.RatePlanTieredRateResource;
import aforo.productrateplanservie.rate_plan_usage_based.RatePlanUsageBasedResource;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HomeResource {
	@GetMapping("/")
	public RepresentationModel<?> index() {
		return RepresentationModel.of(null)
				.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductResource.class).getAllProducts(null, null, null, null, null)).withRel("products"))
				.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurrenciesResource.class).getAllCurrenciess(null, null)).withRel("currencieses"))
				.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RatePlanResource.class).getAllRatePlans(null, null)).withRel("ratePlans"))
				.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RatePlanUsageBasedResource.class).getAllRatePlanUsageBaseds(null, null)).withRel("ratePlanUsageBaseds"))
				.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RatePlanTieredRateResource.class).getAllRatePlanTieredRates(null, null)).withRel("ratePlanTieredRates"))
				.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RatePlanFlatRateResource.class).getAllRatePlanFlatRates(null, null)).withRel("ratePlanFlatRates"))
				.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RatePlanSubscriptionRateResource.class).getAllRatePlanSubscriptionRates(null, null)).withRel("ratePlanSubscriptionRates"))
				.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RatePlanFreemiumRateResource.class).getAllRatePlanFreemiumRates(null, null)).withRel("ratePlanFreemiumRates"));
	}

}
