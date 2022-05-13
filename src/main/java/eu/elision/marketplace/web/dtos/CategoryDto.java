package eu.elision.marketplace.web.dtos;

import java.io.Serializable;
import java.util.Collection;

public record CategoryDto(Long id, String name, Long parentId, Collection<DynamicAttributeDto> characteristics) implements Serializable
{
}
