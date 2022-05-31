package com.notonthehighstreet.interview.models;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Catalogue extends HashMap<String, Item> {

    public Catalogue() {
        super();
    }

     public void loadEntriesFromFile(String dataFilePath) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(new FileReader(dataFilePath));
        String[] values;
        int lineCount = 0;

        //iterate through CSV file and parse values into com.notonthehighstreet.interview.models.Item object
        while((values = csvReader.readNext()) != null) {
            //ignore the first line (headers)
            if(lineCount != 0) {
                //create new item object from values extracted from CSV line
                List<String> itemValues = Arrays.asList(values);
                String productId = itemValues.get(0);
                String productName = itemValues.get(1);
                double price = Double.parseDouble(itemValues.get(2).replace("£", ""));
                Item entry = new Item(productId, productName, price);

                this.put(productId, entry);
            }

            lineCount += 1;
        }
    }
}
