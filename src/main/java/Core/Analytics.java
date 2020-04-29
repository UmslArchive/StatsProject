package Core;

public class Analytics {

    public static void cleanupData(Table table) {
        cleanAgeResponses(table);
    }

    public static Table createMemeQuestionnairePivotTable(final Table table) {
        Table pivotTable = new Table();

        return pivotTable;
    }

    public static Table createDemographicPivotTable(final Table table) {
        Table pivotTable = new Table();

        return pivotTable;
    }

    private static void cleanAgeResponses(Table table) {
        int ageCol =  table.getColumnByFieldName("What is your age?");

        for(int row = 1; row < table.rows(); ++row) {

            String val = table.getValue(row, ageCol);
            switch(val) {
                case "":
                    table.setValue(row, ageCol, "0");
                    break;

                case "19 years":
                    table.setValue(row, ageCol, "19");
                    break;

                case "Shanley Kliethermes":
                    table.setValue(row, ageCol, "20");
                    break;
            }
        }
    }

    private static void addDemographicResponseConversionColumn() {

    }

}

/**
 * TODO:
 *  * Test Table addColumn() functionality.
 *  * Cleanup data inconsistencies in free-response columns (textbox).
 *  * Convert 'how often?' responses to integer values.
 * **/