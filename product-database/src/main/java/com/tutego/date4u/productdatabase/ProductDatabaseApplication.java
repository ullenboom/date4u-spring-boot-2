package com.tutego.date4u.productdatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class ProductDatabaseApplication implements CommandLineRunner {

  @Autowired
  private MongoOperations mongoTemplate;

  @Autowired
  private ProductRepository products;

  public static void main( String[] args ) {
    SpringApplication.run( ProductDatabaseApplication.class, args );
  }

  @Override public void run( String... args ) throws Exception {
    // createNewDocuments();
//    for ( Product product : mongoTemplate.findAll( Product.class ) ) {
//      System.out.println( product );
//    }
//    searchWithCriteria();

    products.findAll().forEach( System.out::println );

  }

  private void searchWithCriteria() {
    Criteria categoryIsThing = Criteria.where( "category" ).is( "Thing" );
    Criteria priceRange = Criteria.where( "price" ).gt( 10_00 ).lt( 20_00 );

    Query query = Query
        .query( categoryIsThing )
        .addCriteria( priceRange )
        .with( Sort.by( "price" ) );

    List<Product> products = mongoTemplate.find( query, Product.class );
    products.forEach( System.out::println );
  }

  private void createNewDocuments() {
    for ( int i = 0; i < 100_000; i++ ) {
      Product product = new Product();
      product.title = "Product " + i;
      product.description = "Description " + i;
      product.category = "Thing";
      product.price = ThreadLocalRandom.current().nextInt( 1000, 100000 );
      product.thumbnail = "https://picsum.photos/200/300?image=" + i;
      mongoTemplate.save( product );
    }
  }
}
