package com.tutego.date4u.productdatabase;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "products" )
public class Product {
  public ObjectId id;
  public String title;
  public String description;
  public String thumbnail;
  public String category;
  public long price;  // price in Cent
  // public Map<String, Map<?, ?>> map;
  // public List<Map<String, Map<?, ?>>> listOfMaps;

  @Override public String toString() {
    return "Product{" +
           "id=" + id +
           ", title='" + title + '\'' +
           ", description='" + description + '\'' +
           ", thumbnail='" + thumbnail + '\'' +
           ", category='" + category + '\'' +
           ", price=" + price +
           '}';
  }
}