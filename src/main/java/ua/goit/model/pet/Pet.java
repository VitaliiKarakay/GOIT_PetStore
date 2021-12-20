package ua.goit.model.pet;

import java.util.List;

public class Pet {

    private Long id;
    private Category category;
    private String name;
    private List<Tag> tags;
    private String status;
    private List<String> protoUrls;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getProtoUrls() {
        return protoUrls;
    }

    public void setProtoUrls(List<String> protoUrls) {
        this.protoUrls = protoUrls;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", tags=" + tags +
                ", status='" + status + '\'' +
                ", protoUrls=" + protoUrls +
                '}';
    }
}
