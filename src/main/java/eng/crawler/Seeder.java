package eng.crawler;

import java.io.BufferedReader;
import java.io.FileReader;

import org.bson.Document;

import io.github.cdimascio.dotenv.Dotenv;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Seeder {
    public static void main(String[] args) throws Exception{
        String fileName = "seed.txt";
        Dotenv dotenv = Dotenv.load();
        String connString = dotenv.get("CONN_STRING");
        MongoClient client = MongoClients.create(connString);
        MongoDatabase db = client.getDatabase("search_engine");
        MongoCollection<Document> seed_set = db.getCollection("seed_set");
        MongoCollection<Document> pop_table = db.getCollection("pop_table");
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while(line != null){
            String url = line;
            Document doc = new Document(){{
                put("url", url);
                put("encounters", 0);
                put("visits", 0);
                put("changes", 0);
                put("time_since_last_visit", 0);
                put("indexed", false);
                put("score", 100);
            }};
            seed_set.insertOne(doc);
            pop_table.insertOne(new Document(){{
                put("url", url);
                put("pop", 1);
            }});
            line = br.readLine();
        }
        br.close();
    }
}
