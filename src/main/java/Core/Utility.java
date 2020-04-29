package Core;

import static Models.Demographics.*;
import static Models.MemeQuestionnaire.*;

public class Utility {

    private static String convertResponse(final String response) {
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
}
