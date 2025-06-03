package aforo.productrateplanservice.discount;

import java.util.List;

public interface DiscountService {
    void create(DiscountDTO dto);
    List<DiscountDTO> getAll();
    DiscountDTO getById(Long id);
    void update(Long id, DiscountDTO dto);
    void delete(Long id);
}
