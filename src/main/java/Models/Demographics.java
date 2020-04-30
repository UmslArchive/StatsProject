package Models;

import Core.FormInfo;

import java.util.ArrayList;
import java.util.List;

public class Demographics {

    public static final String ALL_THE_TIME = "All The Time";
    public static final String A_LOT = "A lot";
    public static final String SOMETIMES = "Sometimes";
    public static final String RARELY = "Rarely";
    public static final String NOT_AT_ALL = "Not at all";

    public List<String> responses = new ArrayList<>(FormInfo.demographicSize);
}
