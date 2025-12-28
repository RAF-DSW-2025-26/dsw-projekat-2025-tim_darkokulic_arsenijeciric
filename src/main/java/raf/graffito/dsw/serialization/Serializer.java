package raf.graffito.dsw.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import raf.graffito.dsw.model.Project;

import java.io.File;
import java.io.IOException;

public class Serializer {

    private ObjectMapper objectMapper;

    public Serializer() {
        this.objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(java.awt.Point.class, new PointSerializer());
        module.addSerializer(java.awt.Dimension.class, new DimensionSerializer());
        module.addDeserializer(java.awt.Point.class, new PointDeserializer());
        module.addDeserializer(java.awt.Dimension.class, new DimensionDeserializer());
        objectMapper.registerModule(module);
    }

    public String serialize(Project project) throws IOException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(project);
    }

    public void saveToFile(Project project, String filePath) throws IOException {
        File file = new File(filePath);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, project);
    }

    public Project deserialize(String json) throws IOException {
        return objectMapper.readValue(json, Project.class);
    }

    public Project loadFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        return objectMapper.readValue(file, Project.class);
    }
}

