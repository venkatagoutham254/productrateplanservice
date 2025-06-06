package aforo.productrateplanservice.product.request;

import aforo.productrateplanservice.product.enums.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductFlatFileRequest {
    private FileFormat format;
    private String size;
    private DeliveryFrequency deliveryFrequency;
    private AccessMethod accessMethod;
    private String retentionPolicy;
    private String fileNamingConvention;
    private CompressionFormat compressionFormat;
}
