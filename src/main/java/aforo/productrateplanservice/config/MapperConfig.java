package aforo.productrateplanservice.config;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import aforo.productrateplanservice.product.mapper.ProductMapper;

@Configuration
public class MapperConfig {
    
    @Bean
    public ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }
}

