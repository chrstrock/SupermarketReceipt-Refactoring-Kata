package dojo.supermarket.model;

import dojo.supermarket.ReceiptPrinter;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SupermarketTest {
    private final SupermarketCatalog catalog = new FakeCatalog();
    private final Teller teller = new Teller(catalog);
    private final Product toothbrush = new Product("toothbrush", ProductUnit.EACH);
    private final Product apples = new Product("apples", ProductUnit.KILO);
    private final ShoppingCart cart = new ShoppingCart();

    // Todo: test all kinds of discounts are applied properly

    @Test
    void tenPercentDiscount() {
        catalog.addProduct(toothbrush, 0.99);
        catalog.addProduct(apples, 1.99);

        teller.addSpecialOffer(SpecialOfferType.TEN_PERCENT_DISCOUNT, toothbrush, 10.0);

        cart.addItemQuantity(apples, 2.5);
        
        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4.975, receipt.getTotalPrice(), 0.01);
        assertEquals(Collections.emptyList(), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem = receipt.getItems().get(0);
        assertEquals(apples, receiptItem.product());
        assertEquals(1.99, receiptItem.price());
        assertEquals(2.5*1.99, receiptItem.totalPrice());
        assertEquals(2.5, receiptItem.quantity());

    }


}
