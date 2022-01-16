package main;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import model.Supplier;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class TutorialUpdate {

  public static void main(String[] args) {
    MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323");

    MongoDatabase db = mongo.getDatabase("lucia");
    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    MongoCollection<Supplier> est = db.getCollection("coffeeSupplier", Supplier.class)
            .withCodecRegistry(pojoCodecRegistry);

    System.out.println(est.updateOne(
            eq("country","Spain"),
            set("pcode","111")).getModifiedCount());

    System.out.println(est.updateOne(
        eq("country","Spain"),
        set("coffee.0.price","11.34")).getModifiedCount());

    System.out.println(est.updateOne(
            eq("coffees._id",5000),
            set("coffees.$.cofName","newCoffeeName")).getModifiedCount());

//    System.out.println(est.updateMany(
//            eq("town","Madrid"),
//            set("coffees.$.price","11.34")).getModifiedCount());

    System.out.println(est.updateOne(
            eq("street", "blabla"),
            push("coffees",
                    new Document("_id",5000).append("cofName","pepe").append("price",11.10)))
            .getModifiedCount());

    System.out.println(est.updateMany(
            eq("street", "blabla"),
            pull("coffees",
                    eq("price",10.10)))
            .getModifiedCount());
  }
}
