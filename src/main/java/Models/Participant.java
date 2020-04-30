package Models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Core.FormInfo;
import Core.Table;
import com.google.api.services.sheets.v4.model.ValueRange;

public class Participant {
    public String timestamp;
    public List<MemeQuestionnaire> memeQuestionnaires = new ArrayList<>(FormInfo.numMemes);
    public Demographics demographics = new Demographics();

    public Participant(final int which, final Table table) {
        timestamp = table.getValue(which, 0);

        //Meme Questionnaires
        String response;
        MemeQuestionnaire memeQuestionnaire;
        for(int i = 0; i < FormInfo.numMemes; ++i) {
            memeQuestionnaire = new MemeQuestionnaire();

            for(int j = 0; j < FormInfo.memeQuestionnaireSize; ++j) {
                response = table.getValue(which, i * FormInfo.memeQuestionnaireSize + j + 2);
                memeQuestionnaire.responses.add(j, response);
            }

            memeQuestionnaires.add(i, memeQuestionnaire);
        }

        //Demographics
        for(int i = 0; i < FormInfo.demographicSize; ++i) {
            response = table.getValue(which, i + FormInfo.demographicOffset);
            demographics.responses.add(i, response);
        }
    }
}
