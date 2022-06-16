package eu.elision.marketplace.logic.converter;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.logic.converter.populator.Populator;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Converter to convert dynamic attribute to dynamic attribute dto
 */
@Component
public class DynamicAttributeDtoConverter extends Converter<DynamicAttribute, DynamicAttributeDto>
{
    protected DynamicAttributeDtoConverter(List<Populator<DynamicAttribute, DynamicAttributeDto>> populators) {
        super(populators, DynamicAttributeDto.class);
    }
}
