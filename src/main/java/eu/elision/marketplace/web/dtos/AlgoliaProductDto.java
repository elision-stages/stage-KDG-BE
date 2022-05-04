package eu.elision.marketplace.web.dtos;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public final class AlgoliaProductDto {
    private long objectID;
    private String name;
    private String vendor;
    private double price;
    private String description;
    private String image;
    private Map<String, Object> parameters;

    public AlgoliaProductDto() {
        parameters = new LinkedHashMap<>();
    }

    // Makes all the parameters available at the top-level of the object
    @JsonAnyGetter
    public Map<String, Object> getParameters() {
        return parameters;
    }
}
