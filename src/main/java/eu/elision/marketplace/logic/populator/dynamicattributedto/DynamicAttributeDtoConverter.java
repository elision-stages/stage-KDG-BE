package eu.elision.marketplace.logic.populator.dynamicattributedto;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.logic.populator.Converter;
import eu.elision.marketplace.logic.populator.Populator;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DynamicAttributeDtoConverter extends Converter<DynamicAttribute, DynamicAttributeDto>
{
    protected DynamicAttributeDtoConverter(List<Populator<DynamicAttribute, DynamicAttributeDto>> populators)
    {
        super(populators, DynamicAttributeDto.class);
    }
}
