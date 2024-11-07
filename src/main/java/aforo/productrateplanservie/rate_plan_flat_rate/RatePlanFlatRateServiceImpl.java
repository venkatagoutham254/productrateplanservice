package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetails;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.exception.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RatePlanFlatRateServiceImpl implements RatePlanFlatRateService {

    private final RatePlanFlatRateRepository ratePlanFlatRateRepository;
    private final RatePlanRepository ratePlanRepository;
    private final RatePlanFlatRateDetailsRepository ratePlanFlatRateDetailsRepository;
    private final RatePlanFlatRateMapper ratePlanFlatRateMapper;

    public RatePlanFlatRateServiceImpl(final RatePlanFlatRateRepository ratePlanFlatRateRepository,
                                       final RatePlanRepository ratePlanRepository,
                                       final RatePlanFlatRateDetailsRepository ratePlanFlatRateDetailsRepository,
                                       final RatePlanFlatRateMapper ratePlanFlatRateMapper) {
        this.ratePlanFlatRateRepository = ratePlanFlatRateRepository;
        this.ratePlanRepository = ratePlanRepository;
        this.ratePlanFlatRateDetailsRepository = ratePlanFlatRateDetailsRepository;
        this.ratePlanFlatRateMapper = ratePlanFlatRateMapper;
    }

    @Override
    public Page<RatePlanFlatRateDTO> findAll(String filter, Pageable pageable) {
        Page<RatePlanFlatRate> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (NumberFormatException numberFormatException) {
                // Keep null - no parseable input
            }
            page = ratePlanFlatRateRepository.findAllByRatePlanFlatRateId(longFilter, pageable);
        } else {
            page = ratePlanFlatRateRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public RatePlanFlatRateDTO get(Long ratePlanFlatRateId) {
        return ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));
    }

    @Override
    @Transactional
    public Long create(Long ratePlanId, RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(() -> new NotFoundException("RatePlan not found with id: " + ratePlanId));

        RatePlanFlatRate ratePlanFlatRate = new RatePlanFlatRate();
        ratePlanFlatRateMapper.updateRatePlanFlatRate(ratePlanFlatRateDTO, ratePlanFlatRate, ratePlanRepository);

        ratePlanFlatRate.setRatePlan(ratePlan);

        RatePlanFlatRate savedRatePlanFlatRate = ratePlanFlatRateRepository.save(ratePlanFlatRate);

        if (ratePlanFlatRateDTO.getRatePlanFlatRateDetails() != null) {
            for (RatePlanFlatRateDetailsDTO detailsDTO : ratePlanFlatRateDTO.getRatePlanFlatRateDetails()) {
                RatePlanFlatRateDetails details = ratePlanFlatRateMapper.mapToRatePlanFlatRateDetails(detailsDTO);
                details.setRatePlanFlatRate(savedRatePlanFlatRate);
                ratePlanFlatRateDetailsRepository.save(details);
            }
        }

        return savedRatePlanFlatRate.getRatePlanFlatRateId();
    }

    @Override
    @Transactional
    public void update(Long ratePlanFlatRateId, RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));

        ratePlanFlatRateMapper.updateRatePlanFlatRate(ratePlanFlatRateDTO, ratePlanFlatRate, ratePlanRepository);

        RatePlanFlatRate updatedRatePlanFlatRate = ratePlanFlatRateRepository.save(ratePlanFlatRate);

        if (ratePlanFlatRateDTO.getRatePlanFlatRateDetails() != null) {
            ratePlanFlatRateDetailsRepository.deleteAllByRatePlanFlatRate(updatedRatePlanFlatRate);

            for (RatePlanFlatRateDetailsDTO detailsDTO : ratePlanFlatRateDTO.getRatePlanFlatRateDetails()) {
                RatePlanFlatRateDetails details = ratePlanFlatRateMapper.mapToRatePlanFlatRateDetails(detailsDTO);
                details.setRatePlanFlatRate(updatedRatePlanFlatRate);
                ratePlanFlatRateDetailsRepository.save(details);
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long ratePlanFlatRateId) {
        RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));

        ratePlanFlatRateDetailsRepository.deleteAllByRatePlanFlatRate(ratePlanFlatRate);

        ratePlanFlatRateRepository.delete(ratePlanFlatRate);
    }

    @Override
    public ReferencedWarning getReferencedWarning(Long ratePlanFlatRateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final RatePlanFlatRate ratePlanFlatRate = ratePlanFlatRateRepository.findById(ratePlanFlatRateId)
                .orElseThrow(() -> new NotFoundException("RatePlanFlatRate not found with id: " + ratePlanFlatRateId));
        return referencedWarning;
    }

    @Override
    public Page<RatePlanFlatRateDTO> findAllByRatePlanId(Long ratePlanId, Pageable pageable) {
        Page<RatePlanFlatRate> page = ratePlanFlatRateRepository.findAllByRatePlan_RatePlanId(ratePlanId, pageable);
        return new PageImpl<>(page.getContent()
                .stream()
                .map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }


    public Optional<RatePlanFlatRateDTO> findFirstByRatePlanId(Long ratePlanId) {
        Optional<RatePlan> ratePlanOpt = ratePlanRepository.findById(ratePlanId);
        if (ratePlanOpt.isEmpty()) {
            System.out.println("RatePlan not found with id: " + ratePlanId);
            return Optional.empty();
        }
        System.out.println("RatePlan found: " + ratePlanOpt.get());

        Optional<RatePlanFlatRate> ratePlanFlatRateOpt = ratePlanFlatRateRepository.findFirstByRatePlan(ratePlanOpt.get());
        if (ratePlanFlatRateOpt.isEmpty()) {
            System.out.println("RatePlanFlatRate not found for ratePlanId: " + ratePlanId);
            return Optional.empty();
        }
        return ratePlanFlatRateOpt.map(ratePlanFlatRate -> ratePlanFlatRateMapper.updateRatePlanFlatRateDTO(ratePlanFlatRate, new RatePlanFlatRateDTO()));
    }

}
