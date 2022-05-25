import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutTest {

    static PromotionalRules promotionalRules = new PromotionalRules();

    @BeforeAll
    static void setup(){
        //loadCatalogue();
    }

    private static Double runBasicCheckoutProcess(Basket basket) {
        Checkout co = new Checkout(promotionalRules);

        for(Item item: basket)
            co.scan(item);

        return co.total();
    }

    @Test
    static void testCase1() {
        Basket basket = new Basket("001", "002", "003");

        Double actualPrice = runBasicCheckoutProcess(basket);
        Double expectedPrice = 66.78;

        assertEquals(actualPrice, expectedPrice);
    }

    @Test
    static void testCase2() {
        Basket basket = new Basket("001", "003", "001");

        Double actualPrice = runBasicCheckoutProcess(basket);
        Double expectedPrice = 36.95;

        assertEquals(actualPrice, expectedPrice);
    }

    @Test
    static void testCase3() {
        //would need to improve how this is achieved!
        Basket basket = new Basket("001", "002", "001", "003");

        Double actualPrice = runBasicCheckoutProcess(basket);
        Double expectedPrice = 73.76;

        assertEquals(actualPrice, expectedPrice);
    }
}
