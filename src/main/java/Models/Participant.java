package Models;

import java.util.ArrayList;
import java.util.List;

import Core.FormInfo;

public class Participant {
    public String timestamp;
    public List<MemeQuestionnaire> memeQuestionnaires = new ArrayList<>(FormInfo.numMemes);
    public Demographics demographics = new Demographics();
}
