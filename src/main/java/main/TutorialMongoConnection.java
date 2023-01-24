package main;

import com.google.gson.Gson;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import model.Coffee;
import model.Supplier;
import model.SupplierConverter;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Projections.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class TutorialMongoConnection {


    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");

        MongoDatabase db = mongo.getDatabase("lucia");

        MongoCollection<Document> est = db.getCollection("coffeeSupplier");

        SupplierConverter sc = new SupplierConverter();
        List<Document> suppliers = new ArrayList<>();
//  First option: Get document and print data
//        System.out.println("Get document suppliers");
//        est.find().into(suppliers).forEach(System.out::println);
//
////  Second option: Get document and store it into Supplier with a Converter class
//        System.out.println("Get suppliers with Converter class");
//        est.find().into(suppliers).stream().map(document -> sc.convertDocumentSupplier(document)).forEach(System.out::println);
//
//  Third option: Get document and store it into Supplier with CodecRegistry
        System.out.println("Get suppliers with CodecRegistry");
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder()
                        .automatic(true).build()));

        MongoCollection<Supplier> col = db.getCollection("coffeeSupplier", Supplier.class).withCodecRegistry(pojoCodecRegistry);

        FindIterable<Supplier> suppliers2 = col.find();
        for (Supplier supplier : suppliers2) {
            System.out.println(supplier);
        }
        }

// Fourth option: Gson
//        List<Supplier> suppliersList = new ArrayList<>();
//        List<Document> documents = est.find().into(new ArrayList<>());
//        for (Document supplier : documents) {
//               suppliersList.add(new Gson().fromJson(supplier.toJson(), Supplier.class));
//           }
//        System.out.println(suppliersList);
//        }

// Get coffees (in Suppliers)
//        List<Coffee> coffeesList = new ArrayList<>();
//        List<Document> documents = est.find()
//                .projection(fields(excludeId(), include("coffees")))
//                .into(new ArrayList<>());
//        for (Document document : documents) {
//            List<Document> documentCoffees = (List<Document>) document.get("coffees");
//            for (Document coffee : documentCoffees) {
//                coffeesList.add(new Gson().fromJson(coffee.toJson(), Coffee.class));
//            }
//
//        }
//        System.out.println(coffeesList);
    }


