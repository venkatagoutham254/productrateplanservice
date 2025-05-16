package aforo.productrateplanservice.product.entity;

import jakarta.persistence.*;
import lombok.*;
import aforo.productrateplanservice.product.enums.FileFormat;
import aforo.productrateplanservice.product.enums.DeliveryFrequency;
import aforo.productrateplanservice.product.enums.AccessMethod;
import aforo.productrateplanservice.product.enums.CompressionFormat;

@Entity
@Table(name = "aforo_product_flatfile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductFlatFile {

    @Id
    private Long productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "productId")
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileFormat format;

    private String size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryFrequency deliveryFrequency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessMethod accessMethod;

    private String retentionPolicy;
    private String fileNamingConvention;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompressionFormat compressionFormat;
}
