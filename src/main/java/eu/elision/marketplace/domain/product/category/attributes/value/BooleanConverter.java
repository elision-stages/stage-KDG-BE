package eu.elision.marketplace.domain.product.category.attributes.value;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanConverter implements
        AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean b) {
        return b.toString();
    }

    @Override
    public Boolean convertToEntityAttribute(String b) {
        return Boolean.valueOf(b);
    }
}