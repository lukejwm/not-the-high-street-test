import com.notonthehighstreet.interview.models.Catalogue;
import com.notonthehighstreet.interview.models.Item;
import com.notonthehighstreet.interview.Checkout;

import com.notonthehighstreet.interview.utils.PromotionalRules;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutTest {
    //Catalogue of items to represent the online store
    private static Catalogue catalogue;
    private static PromotionalRules promotionalRules;
    private static Checkout co;

    @BeforeAll
    static void setup(){
        String fileName = "src/main/resources/products.csv";
        catalogue = new Catalogue();

        try {
            catalogue.loadEntriesFromFile(fileName);
        } catch (IOException | CsvValidationException exp) {
            System.out.println(Arrays.toString(exp.getStackTrace()));
        }
    }

    @BeforeEach
    void resetPromoRulesAndCheckout() {
        promotionalRules = new PromotionalRules();
        co = new Checkout(promotionalRules);
    }

    //Test cases to check that individual and group discounts are applied correctly
    @Test
    void promotionalRulesCanCorrectlyCalculate10PercentDiscount() {
        double mockCurrentTotal = 65.00;

        //discount should be 10 percent of total value
        double expectedDiscount = mockCurrentTotal/10;
        double actualDiscount = promotionalRules.tenPercentDiscountForTotalsOver60GBP(mockCurrentTotal);
        assertEquals(expectedDiscount, actualDiscount);
    }

    @Test
    void promotionalRulesCanCorrectlyCalculateDiscountForMultipleTravelCardHolders() {
        List<Item> mockBasket = new ArrayList<>();
        mockBasket.add(catalogue.get("001"));
        mockBasket.add(catalogue.get("001"));

        double expectedPriceForHolder = 8.50;
        mockBasket = promotionalRules.priceReductionForBasketsWithTwoOrMoreTravelCardHolders(mockBasket);

        for(Item item: mockBasket) {
            double actualPriceForHolder = item.price();
            assertEquals(expectedPriceForHolder, actualPriceForHolder);
        }
    }

    @Test
    void testCase1() {
        String[] itemsToScan = {"001", "002", "003"};

        Double actualPrice = runBasicCheckoutProcess(itemsToScan);
        Double expectedPrice = 66.78;

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void testCase2() {
        String[] itemsToScan = {"001", "003", "001"};

        Double actualPrice = runBasicCheckoutProcess(itemsToScan);
        Double expectedPrice = 36.95;

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void testCase3() {
        String[] itemsToScan = {"001", "002", "001", "003"};
        Double actualPrice = runBasicCheckoutProcess(itemsToScan);
        Double expectedPrice = 73.76;

        assertEquals(expectedPrice, actualPrice);
    }

    private static Double runBasicCheckoutProcess(String[] itemsToScan) {
        for (String productId : itemsToScan) {
            Item item = catalogue.get(productId);
            co.scan(item);
        }

        return co.total();
    }

    @AfterAll
    static void destroyCatalogue() {
        catalogue = null;
    }
}
