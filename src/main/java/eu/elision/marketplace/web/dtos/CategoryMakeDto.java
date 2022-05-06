package eu.elision.marketplace.web.dtos;

import java.io.Serializable;
import java.util.Collection;

public record CategoryMakeDto(String name, long parentId) implements Serializable
{
}
