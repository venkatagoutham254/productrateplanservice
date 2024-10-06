package aforo.productrateplanservie.product.service;

import aforo.productrateplanservie.product.domain.Product;
import aforo.productrateplanservie.product.model.ProductDTO;
import aforo.productrateplanservie.product.repos.ProductRepository;
import aforo.productrateplanservie.rate_plan.domain.RatePlan;
import aforo.productrateplanservie.rate_plan.repos.RatePlanRepository;
import aforo.productrateplanservie.util.NotFoundException;
import aforo.productrateplanservie.util.ReferencedWarning;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final RatePlanRepository ratePlanRepository;

    public ProductServiceImpl(final ProductRepository productRepository,
            final ProductMapper productMapper, final RatePlanRepository ratePlanRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.ratePlanRepository = ratePlanRepository;
    }

    @Override
    public Page<ProductDTO> findAll(final String filter, final Pageable pageable) {
        Page<Product> page;
        if (filter != null) {
            UUID uuidFilter = null;
            try {
                uuidFilter = UUID.fromString(filter);
            } catch (final IllegalArgumentException illegalArgumentException) {
                // keep null - no parseable input
            }
            page = productRepository.findAllByProductId(uuidFilter, pageable);
        } else {
            page = productRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(product -> productMapper.updateProductDTO(product, new ProductDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public ProductDTO get(final UUID productId) {
        return productRepository.findById(productId)
                .map(product -> productMapper.updateProductDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public UUID create(final ProductDTO productDTO) {
        final Product product = new Product();
        productMapper.updateProduct(productDTO, product);
        return productRepository.save(product).getProductId();
    }

    @Override
    public void update(final UUID productId, final ProductDTO productDTO) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);
        productMapper.updateProduct(productDTO, product);
        productRepository.save(product);
    }

    @Override
    public void delete(final UUID productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final UUID productId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);
        final RatePlan productRatePlan = ratePlanRepository.findFirstByProduct(product);
        if (productRatePlan != null) {
            referencedWarning.setKey("product.ratePlan.product.referenced");
            referencedWarning.addParam(productRatePlan.getRatePlanId());
            return referencedWarning;
        }
        return null;
    }

}
