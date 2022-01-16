package main;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Supplier;
import model.SupplierConverter;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

public class TutorialMongoConnection {





    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");

        MongoDatabase db = mongo.getDatabase("lucia");

        MongoCollection<Document> est = db.getCollection("coffeeSupplier");

        SupplierConverter sc = new SupplierConverter();
        List<Document> suppliers = new ArrayList<>();
//  First option: Get document and print data
        System.out.println("Get document suppliers");
        est.find().into(suppliers).forEach(System.out::println);

//  Second option: Get document and store it into Supplier with a Converter class
        System.out.println("Get suppliers with Converter class");
        suppliers.stream().map(document -> sc.convertDocumentSupplier(document)).forEach(System.out::println);

//  Third option: Get document and store it into Supplier with CodecRegistry
        System.out.println("Get suppliers with CodecRegistry");
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder()
                        .automatic(true).build()));

        MongoCollection<Supplier> col = db.getCollection("supplier", Supplier.class).withCodecRegistry(pojoCodecRegistry);

        col.find().into(new ArrayList()).forEach(System.out::println);

    }
}
