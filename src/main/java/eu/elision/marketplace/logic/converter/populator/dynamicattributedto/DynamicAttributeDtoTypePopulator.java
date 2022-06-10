package eu.elision.marketplace.logic.converter.populator.dynamicattributedto;

import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.logic.converter.populator.Populator;
import eu.elision.marketplace.web.dtos.attributes.DynamicAttributeDto;
import org.springframework.stereotype.Component;

/**
 * Populator for type field of dynamic attribute dto
 */
@Component
public class DynamicAttributeDtoTypePopulator implements Populator<DynamicAttribute, DynamicAttributeDto>
{
    @Override
    public void populate(DynamicAttribute source, DynamicAttributeDto target) {
        target.setType(source.getType());
    }
}
