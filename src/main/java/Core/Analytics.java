package Core;

import Models.Participant;

import java.util.List;

public class Analytics {

    public static void cleanupData(Table table) {
        cleanAgeResponses(table);
        addHowOftenResponseConversionColumn(table);
    }

    public static Table createMemeQuestionnairePivotTable(final List<Participant> participantList) {
        Table pivotTable = new Table();

        return pivotTable;
    }

    public static Table createDemographicPivotTable(final List<Participant> participantList) {
        Table pivotTable = new Table();

        return pivotTable;
    }

    private static void cleanAgeResponses(Table table) {
        int ageCol =  table.getColumnByFieldName("What is your age?");

        for(int row = 1; row < table.rows(); ++row) {

            String val = table.getValue(row, ageCol);
            switch(val) {
                case "":
                    table.setValue(row, ageCol, "0"); //Average of all non-empty responses
                    break;

                case "19 years":
                    table.setValue(row, ageCol, "19");
                    break;

                case "Shanley Kliethermes":
                    table.setValue(row, ageCol, "20");  //From the mouth of Shanley
                    break;
            }
        }
    }

    private static void addHowOftenResponseConversionColumn(Table table) {
        int conversionColumnIndex = table.addEmptyColumnAt(table.getColumnByFieldName("I am on social media:"), Table.Direction.RIGHT);

        //Add field header
        table.setValue(0, conversionColumnIndex, "Social Media Frequency Score");

        String currentValue, convertedValue;
        for(int row = 1; row < table.rows(); ++row) {
            currentValue = table.getValue(row, conversionColumnIndex - 1);
            convertedValue = Utility.convertResponse(currentValue);
            table.setValue(row, conversionColumnIndex, convertedValue);
        }

    }

}

/**
 * TODO:
 *  * Convert 'how often?' responses to integer values. (complete, not tested)
 * **/