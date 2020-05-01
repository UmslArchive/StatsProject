package Core;

import Models.MemeQuestionnaire;
import Models.Participant;

import java.text.Normalizer;
import java.util.List;

public class Analytics {
    private static final int HEADER_ROW = 1;

    public static void cleanupData(Table table) {
        cleanAgeResponses(table);
        addHowOftenResponseConversionColumn(table);
    }

    public static Table createMemeQuestionnairePivotTable(final List<Participant> participantList) {
        //Allocate table
        int rows = FormInfo.numParticipants * FormInfo.numMemes * FormInfo.memeQuestionnaireSize + HEADER_ROW;
        int cols = 5; // Participant#, Timestamp, Meme#, Question#, Response
        Table pivotTable = new Table(rows, cols);

        //Set header values
        int row = 0;
        pivotTable.setValue(row, 0, "Participant #");
        pivotTable.setValue(row, 1, "Timestamp");
        pivotTable.setValue(row, 2, "Meme #");
        pivotTable.setValue(row, 3, "Question #");
        pivotTable.setValue(row, 4, "Response");


        row = 1;
        String timestamp, response;
        Participant currentParticipant;
        MemeQuestionnaire currentQuestionnaire;
        for(int participant = 0; participant < FormInfo.numParticipants; ++participant) {

            currentParticipant = participantList.get(participant);

            timestamp = currentParticipant.timestamp;

            for(int meme = 0; meme < FormInfo.numMemes; ++meme) {

                currentQuestionnaire = currentParticipant.memeQuestionnaires.get(meme);

                for(int question = 0; question < FormInfo.memeQuestionnaireSize; ++question) {

                    //Get current response
                    response = currentQuestionnaire.responses.get(question);

                    //Set each column
                    pivotTable.setValue(row, 0, Integer.toString(participant));
                    pivotTable.setValue(row, 1, timestamp);
                    pivotTable.setValue(row, 2, Integer.toString(meme));
                    pivotTable.setValue(row, 3, Integer.toString(question));
                    pivotTable.setValue(row, 4, response);

                    ++row;
                }
            }
        }

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
 *  * Generate pivot tables form list of participants
 * **/