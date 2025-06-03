package uni.project.online.shop.model;

import java.util.List;

public class TypesForCategory {
    private Long id;
    private String name;
    private String displayName;
    private List<Type> types;

    public TypesForCategory(Type type, List<Type> types) {
        this.id = type.getId();
        this.name = type.getName();
        this.displayName = type.getDisplayName();
        this.types = types;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }
}
