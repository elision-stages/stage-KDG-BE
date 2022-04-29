package eu.elision.marketplace.web.dtos;

import java.io.Serializable;

public record CategoryMakeDto(String name, long parentId) implements Serializable {
}
