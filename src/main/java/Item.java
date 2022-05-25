import java.util.Objects;

public record Item (int productId, String name, double price) {
    public Item {
        //Ensure that object type data is not null (primitives are always not null)
        Objects.requireNonNull(name);
    }
}
