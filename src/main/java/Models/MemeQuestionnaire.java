package Models;

import Core.FormInfo;

import java.util.ArrayList;
import java.util.List;

public class MemeQuestionnaire {

    public enum QuestionType {
        YesNo,
        Scale10,
        Agreement
    }

    //Response conversions
    public static final String YES = "Yes";
    public static final String NO = "No";
    public static final String STRONGLY_DISAGREE = "Strongly Disagree";
    public static final String DISAGREE = "Disagree";
    public static final String NEUTRAL = "Neutral";
    public static final String AGREE = "Agree";
    public static final String STRONGLY_AGREE = "Strongly Agree";

    public static List<QuestionType> questions;

    static {
        questions = new ArrayList<>(FormInfo.memeQuestionnaireSize);
        questions.add(0, QuestionType.YesNo);
        questions.add(1, QuestionType.Scale10);
        questions.add(2, QuestionType.Scale10);
        questions.add(3, QuestionType.Agreement);
        questions.add(4, QuestionType.Agreement);
        questions.add(5, QuestionType.Agreement);
        questions.add(6, QuestionType.Agreement);
        questions.add(7, QuestionType.Agreement);
        questions.add(8, QuestionType.Agreement);
        questions.add(9, QuestionType.Agreement);
    }

    public List<String> responses = new ArrayList<>(FormInfo.memeQuestionnaireSize);

    //Utility


}
