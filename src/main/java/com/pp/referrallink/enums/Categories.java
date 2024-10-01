package com.pp.referrallink.enums;

import java.util.HashMap;
import java.util.Map;

public enum Categories {
  FINANCEANDINVESTING("Finance, Banks & Investing", "finance.png"),
  TELECOMANDINTERNET("Telecom & Internet", "internet.png"),
  SHOPPINGANDDEALS("Shopping & Deals", "shopping.png"),
  TRAVEL("Travel", "travel.png"),
  FOODANDDINING("Food & Dining", "food.png"),
  SPORTANDHEALTH("Sport & Health", "sport.png"),
  HOMEANDLIVING("Home & Living", "home.png"),
  PETSANDANIMALS("Pets & Animals", "pet.png"),
  EDUCATIONANDLEARNING("Education & Learning", "education.png"),
  OFFICEANDBUSINESS("Office & Business", "business.png"),
  ENTERTAINMENTANDHOBBIES("Entertainment & Hobbies", "entertainment.png"),
  AUTOMOTIVE("Automotive", "car.png"),
  INSURANCE("Insurance", "insurance.png"),
  KIDSANDTOYS("Kids & Toys", "kids.png");

  private String category;
  private String icon;

  Categories(String category, String icon) {
    this.category = category;
    this.icon = icon;
  }

  private static final Map<String, String> categoryToIconMap = new HashMap<>();

  static {
    for (Categories c : Categories.values()) {
      categoryToIconMap.put(c.getCategory(), c.getIconByCategory());
    }
  }

  public String getCategory() {
    return category;
  }

  public String getIconByCategory() {
    return icon;
  }

  public static String getIconByCategory(String category) {
    return categoryToIconMap.get(category);
  }
}
