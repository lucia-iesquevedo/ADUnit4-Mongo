package main;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class TutorialFind {

  public static void main(String[] args) {

    MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");

    MongoDatabase db = mongo.getDatabase("lucia");

    MongoCollection<Document> est = db.getCollection("coffeeSupplier");

//  Basic find
    System.out.println("Find suppliers from Spain");
    est.find(eq("country","Spain")).into(new ArrayList()).forEach(System.out::println);

//  Find with nested columns
    System.out.println("Find suppliers of coffees cheaper than 13 euros");
    est.find(lt("coffees.price",13.00)).into(new ArrayList()).forEach(System.out::println);

// Find with regular expression and or
    System.out.println("Find suppliers from Madrid or with pcode 1* ");
    est.find(or(eq("town","Madrid"),regex("pcode","^1.*"))).into(new ArrayList()).forEach(System.out::println);

//  Find with size
    System.out.println("find suppliers of two different coffees");
    est.find(size("coffees",2)).into(new ArrayList()).forEach(System.out::println);

    //projections
    System.out.println("Projection");
    est.find(eq("country","Spain")).projection(new Document("country",1).append("coffees.cofName",1).append("_id",0)).into(new ArrayList()).forEach(System.out::println);
//

//  Order by
    System.out.println("Sorting");
    est.find().sort(new Document("coffees.price",1)).into(new ArrayList()).forEach(System.out::println);
//
//
//        System.out.println("ordenando y filtrado");
//
//        List<Document> cosas = (List)est.find()
//                .sort(Sorts.ascending("name"))
//                .projection(fields(include("name"), excludeId(),
//                        Projections.elemMatch("cosas",gt("cantidad",1))))
//                .into(new ArrayList());
//
//        cosas.forEach(System.out::println);



  }
}
