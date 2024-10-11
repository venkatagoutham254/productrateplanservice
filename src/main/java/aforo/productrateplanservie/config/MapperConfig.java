package aforo.productrateplanservie.config;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import aforo.productrateplanservie.product.ProductMapper;

@Configuration
public class MapperConfig {
    
    @Bean
    public ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }
}

