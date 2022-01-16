package main;

import com.mongodb.MongoClientSettings;

import com.mongodb.client.model.InsertManyOptions;
import model.Coffee;
import model.Supplier;
import model.SupplierConverter;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TutorialInsert {



    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323");

        MongoDatabase db = mongo.getDatabase("lucia");

        MongoCollection<Document> est = db.getCollection("coffeeSupplier");

        //First option
        est.insertOne(Document.parse("{ \"street\":\"blabla\",\"country\":\"USA\"}"));
//
//     Second option
       Document d = new Document();
       d.put("street","whatever");
        d.put("town","whatever2");
        d.put("country","Spain");
        d.put("coffees", Arrays.asList(new Document()
                .append("_id", 6000)
                .append("cofName", "coffee3")
                .append("price", 10.1),
                new Document()
                        .append("_id", 7000)
                        .append("cofName", "coffee4")
                        .append("price", 8.1)));

        est.insertOne(d);


        // Third option

        Supplier s = Supplier.builder().street("street103").town("Boston")
                .country("USA").pcode("123212")
                .coffee(Coffee.builder()
                        .idProd(9000)
                        .cofName("Coffee 9")
                        .price(15.00).build())
                .coffee(Coffee.builder()
                        .idProd(9000)
                        .cofName("Coffee 9")
                        .price(15.00).build())
                .build();

        SupplierConverter sc = new SupplierConverter();
        Document d1 = sc.convertSupplierDocument(s);
        est.insertOne(d1);
        s.setSupp_id(d1.getObjectId("_id"));
        System.out.println(s);

        // Insert Many

        List<Document> documents = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            documents.add(new Document("i", i));

//        InsertManyOptions options = new InsertManyOptions();
//        options.ordered(false);
//        est.insertMany(documents, options);
    }
}
