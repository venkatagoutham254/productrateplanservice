package aforo.productrateplanservie.service;

import aforo.productrateplanservie.domain.Product;
import aforo.productrateplanservie.domain.RatePlan;
import aforo.productrateplanservie.model.ProductDTO;
import aforo.productrateplanservie.repos.ProductRepository;
import aforo.productrateplanservie.repos.RatePlanRepository;
import aforo.productrateplanservie.util.NotFoundException;
import aforo.productrateplanservie.util.ReferencedWarning;
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
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = productRepository.findAllByProductId(integerFilter, pageable);
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
    public ProductDTO get(final Integer productId) {
        return productRepository.findById(productId)
                .map(product -> productMapper.updateProductDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final ProductDTO productDTO) {
        final Product product = new Product();
        productMapper.updateProduct(productDTO, product);
        return productRepository.save(product).getProductId();
    }

    @Override
    public void update(final Integer productId, final ProductDTO productDTO) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);
        productMapper.updateProduct(productDTO, product);
        productRepository.save(product);
    }

    @Override
    public void delete(final Integer productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final Integer productId) {
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
