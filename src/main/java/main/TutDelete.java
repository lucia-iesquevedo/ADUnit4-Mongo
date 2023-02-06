package main;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Coffee;
import model.Supplier;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

import static com.mongodb.client.model.Updates.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class TutDelete {


    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323");

        MongoDatabase db = mongo.getDatabase("lucia");
        MongoCollection<Document> est = db.getCollection("coffeeSupplier");

//        System.out.println(
//                est.deleteOne(eq("street", "street103")).getDeletedCount());
//
//        System.out.println(
//                est.deleteMany(eq("street", "whatever")).getDeletedCount());
//
//        System.out.println(
//                est.deleteMany(eq("i", 4)).getDeletedCount());



       Document document = est.find(eq("_id",new ObjectId("63dce1aeb7cedd9ea2c8328c"))).first();
        Supplier s = new Gson().fromJson(document.toJson(), Supplier.class);
        Document docCoffee = Document.parse(new Gson().toJson(s.getCoffees().get(0)));

        //Delete one coffee
        est.updateOne(
                eq("coffees.idProd", s.getCoffees().get(0).getIdProd()),
                pull("coffees",docCoffee));


        //Delete all coffees
        List<Document> ldoc=new ArrayList<>();
             est.updateOne(
                eq("_id", new ObjectId("63dce1aeb7cedd9ea2c8328d")),
                set("coffees",ldoc));


    }
}

