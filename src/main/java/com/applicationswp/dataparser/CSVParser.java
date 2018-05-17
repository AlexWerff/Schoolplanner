package com.applicationswp.dataparser;

import com.applicationswp.data.Substitution;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexwerff on 20.02.17.
 */
public final class CSVParser {
    private CSVParser(){

    }

    public static List<Substitution> getSubsitionsFromCSV(String csv){
        List<Substitution> substitutions = new ArrayList<>();
        String[] rows = csv.split("\n");

        for(String row:rows) {
            String[] csvSplit = row.split(",");
            int counter = 0;
            Substitution newSub = new Substitution();
            substitutions.add(newSub);

            String teacher1 = csvSplit[5];
            String teacher2 = csvSplit[6];
            String date= csvSplit[1];
            String room= csvSplit[4];
            String course = csvSplit[7];

            newSub.setDescription(String.format("Vertretung von %s durch %s in Raum %s",teacher1,teacher2,room));
            newSub.setTitle(String.format("Kurs %s",course));
            newSub.setTimeFrom(TimeParser.dateStringToTimestamp(date));
        }
        return substitutions;
    }
}
