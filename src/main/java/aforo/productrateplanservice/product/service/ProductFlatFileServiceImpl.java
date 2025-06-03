package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.entity.Product;
import aforo.productrateplanservice.product.entity.ProductFlatFile;
import aforo.productrateplanservice.product.enums.ProductType;
import aforo.productrateplanservice.product.repository.ProductFlatFileRepository;
import aforo.productrateplanservice.product.repository.ProductRepository;
import aforo.productrateplanservice.product.request.CreateProductFlatFileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFlatFileServiceImpl implements ProductFlatFileService {

    private final ProductFlatFileRepository productFlatFileRepository;
    private final ProductRepository productRepository;

    private void validateProductType(Product product, ProductType expected) {
        if (product.getProductType() != expected) {
            throw new RuntimeException("Invalid product type. Expected: " + expected + ", but got: " + product.getProductType());
        }
    }

    @Override
    public ProductFlatFile create(CreateProductFlatFileRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        validateProductType(product, ProductType.FlatFile);

        ProductFlatFile entity = ProductFlatFile.builder()
                .product(product)
                .format(request.getFormat())
                .size(request.getSize())
                .deliveryFrequency(request.getDeliveryFrequency())
                .accessMethod(request.getAccessMethod())
                .retentionPolicy(request.getRetentionPolicy())
                .fileNamingConvention(request.getFileNamingConvention())
                .compressionFormat(request.getCompressionFormat())
                .build();

        return productFlatFileRepository.save(entity);
    }

    @Override
    public ProductFlatFile getById(Long id) {
        return productFlatFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flat File Product not found with ID: " + id));
    }

    @Override
    public List<ProductFlatFile> getAll() {
        return productFlatFileRepository.findAll();
    }

    @Override
    public ProductFlatFile update(Long id, CreateProductFlatFileRequest request) {
        ProductFlatFile existing = productFlatFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flat File Product not found with ID: " + id));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        validateProductType(product, ProductType.FlatFile);

        existing.setFormat(request.getFormat());
        existing.setSize(request.getSize());
        existing.setDeliveryFrequency(request.getDeliveryFrequency());
        existing.setAccessMethod(request.getAccessMethod());
        existing.setRetentionPolicy(request.getRetentionPolicy());
        existing.setFileNamingConvention(request.getFileNamingConvention());
        existing.setCompressionFormat(request.getCompressionFormat());

        return productFlatFileRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!productFlatFileRepository.existsById(id)) {
            throw new RuntimeException("Flat File Product not found with ID: " + id);
        }
        productFlatFileRepository.deleteById(id);
    }
}
