package Core;

import static Models.MemeQuestionnaire.*;
import static Models.Demographics.*;

public class Analytics {

    private String convertResponse(final String response) {
        switch (response) {
            case NO:
            case STRONGLY_DISAGREE:
            case NOT_AT_ALL:
            case SOMETIMES:
            case RARELY:
                return "0";

            case YES:
            case DISAGREE:
            case A_LOT:
            case ALL_THE_TIME:
                return "1";

            case NEUTRAL:
                return "2";

            case AGREE:
                return "3";

            case STRONGLY_AGREE:
                return "4";

            default:
                return response;
        }
    }

    public static void exportPivotTableSource(String outFilepath) {

    }

}

/**
 * TODO:
 *  * Re-encode how-often-social-media response
 *  * Cleanup data inconsistencies in free-response columns (textbox)
 *
 * **/