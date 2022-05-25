import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<Item> catalogue = new ArrayList<>();
        catalogue.add(new Item(100, "Travel Card Holder", 9.25));
        catalogue.add(new Item(200, "Personalised cufflinks", 45.00));
        catalogue.add(new Item(300, "Kids T-shirt", 19.95));

        for (Item item: catalogue) {
            System.out.println(item.toString());
        }
    }
}
