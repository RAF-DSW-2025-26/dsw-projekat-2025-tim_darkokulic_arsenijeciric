package raf.graffito.dsw.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.awt.Dimension;
import java.io.IOException;

public class DimensionSerializer extends JsonSerializer<Dimension> {
    @Override
    public void serialize(Dimension dimension, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("width", dimension.width);
        gen.writeNumberField("height", dimension.height);
        gen.writeEndObject();
    }
}

