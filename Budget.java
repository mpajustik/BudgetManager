package budget;

import budget.menu.BuyOption;

import java.util.*;

public class Budget {
    private Double balance = 0.00;
    private Map<BuyOption, List<Item>> purchasesMap;

    public Budget() {
        purchasesMap = createPurchaseMap();
    }

    public void addIncomeToBalance(Double income) {
        balance = balance + income;
    }

    public void reduceBalanceByPrice(Double itemPrice) {
        balance = balance - itemPrice;
    }

    public void addItemToPurchases(Item item, BuyOption option) {
        List<Item> items = purchasesMap.get(option);
        items.add(item);
    }

    public void printAllPurchases() {
        double total = 0;
        System.out.println("\nAll:");
        for (List<Item> items : purchasesMap.values()) {
            for (Item item : items) {
                System.out.println(item.getItemName() + " $" + String.format("%.2f", item.getItemPrice()));
                total = total + item.getItemPrice();
            }
        }
        if (total == 0) {
            System.out.println("The purchase list is empty!");
        } else {
            System.out.println("Total sum: $" + total);
        }

    }

    public void printPurchasesByOption(BuyOption buyOption) {
        double total = 0;
        System.out.println("\n" + buyOption.getOption() + ":");
        List<Item> items = purchasesMap.get(buyOption);
        for (Item item : items) {
            System.out.println(item.getItemName() + " $" + String.format("%.2f", item.getItemPrice()));
            total = total + item.getItemPrice();
        }
        if (total == 0) {
            System.out.println("The purchase list is empty!");
        } else {
            System.out.println("Total sum: $" + total);
        }
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Map<BuyOption, List<Item>> getPurchasesMap() {
        return purchasesMap;
    }


    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();
        for (List<Item> items : purchasesMap.values()) {
            allItems.addAll(items);
        }
        return allItems;
    }

    public List<Item> getItemsByBuyOption(BuyOption buyOption) {
        List<Item> items = purchasesMap.get(buyOption);
        return items;
    }

    public List<Item> getGroupItems() {
        List<Item> groupItems = new ArrayList<>();

        for (Map.Entry<BuyOption, List<Item>> entry : purchasesMap.entrySet()) {
            List<Item> items = entry.getValue();
            double groupPrice = calculateGroupPrice(items);
            BuyOption buyOption = entry.getKey();
            String groupName = buyOption.getOption();
            Item item = new Item(groupName, groupPrice);
            groupItems.add(item);
        }
        return groupItems;
    }

    private double calculateGroupPrice(List<Item> items) {
        double groupPrice = 0;
        for (Item item : items) {
            groupPrice = groupPrice + item.getItemPrice();
        }
        return groupPrice;
    }

    private Map<BuyOption, List<Item>> createPurchaseMap() {
        Map<BuyOption, List<Item>> purchasesMap = new LinkedHashMap<>();
        purchasesMap.put(BuyOption.FOOD, new ArrayList<>());
        purchasesMap.put(BuyOption.CLOTHES, new ArrayList<>());
        purchasesMap.put(BuyOption.ENTERTAINMENT, new ArrayList<>());
        purchasesMap.put(BuyOption.OTHER, new ArrayList<>());
        return purchasesMap;
    }
}