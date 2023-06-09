package eng.ranker;

import java.util.ArrayList;

import eng.indexer.classes.*;
import eng.util.Preprocessor;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String args[]) {
        Dotenv dotenv = Dotenv.load();
        String connectionString = dotenv.get("CONN_STRING");
        Storage storage = new MongoStorage(connectionString);
        Indexer indexer = new WebIndexer(storage);
        Ranker ranker = new Ranker(indexer);

        // String query = "computer science";
        // String query[] = {"مان" ,"سيتي" ,"ضد" ,"الريال"};
        // String query[] = { "this", "is", "nice" };

        // ArrayList<IndexEntry> entries = new ArrayList<IndexEntry>();
        // for (String word : query) {
        //     entries.add(indexer.retrieve(word));
        // }

        // ArrayList<IndexEntry> filteredEntries = ranker.PhraseFilter(entries);
        // for (IndexEntry entry : filteredEntries) {
        //     System.out.println(entry.getWord());
        //     for (InvertedDocument doc : entry.getInvertedDocuments()) {
        //         System.out.println(doc.getIdentifier());
        //     }
        // }
        String query = "\"computer science\"";
        String[] queryArray = Preprocessor.preprocess(query).toArray(new String[0]);
        System.out.println(ranker.rank(queryArray));
    }
}