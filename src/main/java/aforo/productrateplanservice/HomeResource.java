package aforo.productrateplanservice;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import aforo.productrateplanservice.product.resource.ProductResource;
import aforo.productrateplanservice.rate_plan.RatePlanResource;
import aforo.productrateplanservice.discount.DiscountController;
import aforo.productrateplanservice.freemium.FreemiumController;
import aforo.productrateplanservice.minimumcommitment.MinimumCommitmentController;
import aforo.productrateplanservice.overagecharges.OverageChargeController;
import aforo.productrateplanservice.resetperiod.ResetPeriodController;
import aforo.productrateplanservice.setupfee.RatePlanSetupFeeController;

@RestController
public class HomeResource {

    @GetMapping("/")
public RepresentationModel<?> index() {
    return RepresentationModel.of(null)
        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductResource.class).getAllProducts()).withRel("products"))
        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RatePlanResource.class).getAll()).withRel("ratePlans"))
        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RatePlanSetupFeeController.class).getAll()).withRel("setupFees"))
        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OverageChargeController.class).getAll()).withRel("overageCharges"))
        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DiscountController.class).getAll()).withRel("discounts"))
        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FreemiumController.class).getAll()).withRel("freemiums"))
        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MinimumCommitmentController.class).getAll()).withRel("minimumCommitments"))
        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ResetPeriodController.class).getAll()).withRel("resetPeriods"));
}
}