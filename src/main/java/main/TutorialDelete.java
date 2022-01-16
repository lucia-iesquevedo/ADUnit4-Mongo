package main;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static com.mongodb.client.model.Filters.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class TutorialDelete {


    public static void main(String[] args) {

        MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323");

        MongoDatabase db = mongo.getDatabase("lucia");
        MongoCollection<Document> est = db.getCollection("coffeeSupplier");

        System.out.println(
                est.deleteOne(eq("street", "street103")).getDeletedCount());

        System.out.println(
                est.deleteMany(eq("street", "whatever")).getDeletedCount());

        System.out.println(
                est.deleteMany(eq("i", 4)).getDeletedCount());

    }
}
