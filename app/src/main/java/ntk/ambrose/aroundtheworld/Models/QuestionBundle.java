package ntk.ambrose.aroundtheworld.Models;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionBundle {

    public class Question {
        private String ansA;
        private String ansB;
        private String ansC;
        private String ansD;

        public static final int ANS_A = 0;
        public static final int ANS_B = 1;
        public static final int ANS_C = 2;
        public static final int ANS_D = 3;

        private int correctAns;

        public Question() {

        }

        public Question(String ansA, String ansB, String ansC, String ansD, int correctCode) {
            setAnsA(ansA);
            setAnsB(ansB);
            setAnsC(ansC);
            setAnsD(ansD);
            correctAns = correctCode;
        }

        public String getAnsA() {
            return ansA;
        }

        public void setAnsA(String ansA) {
            this.ansA = ansA;
        }

        public String getAnsB() {
            return ansB;
        }

        public void setAnsB(String ansB) {
            this.ansB = ansB;
        }

        public String getAnsC() {
            return ansC;
        }

        public void setAnsC(String ansC) {
            this.ansC = ansC;
        }

        public String getAnsD() {
            return ansD;
        }

        public void setAnsD(String ansD) {
            this.ansD = ansD;
        }
    }

    ArrayList<Question> questionArrayList;
    ArrayList<Country> shuffleCountriesList;

    private boolean[][] stateVisited;

    private QuestionBundle() {
        questionArrayList = new ArrayList<>();
        stateVisited = new boolean[WorldMap.getInstance().HEIGHT][WorldMap.getInstance().WIDTH];
        //reset visit state
        for (int i = 0; i < WorldMap.getInstance().HEIGHT; i++) {
            for (int j = 0; j < WorldMap.getInstance().WIDTH; j++) {
                stateVisited[i][j] = false;
            }
        }
    }

    private static QuestionBundle instance;

    public static QuestionBundle getInstance() {
        if (instance == null)
            instance = new QuestionBundle();
        return instance;
    }

    private void generateCountryList(int y, int x) {

        if (y == WorldMap.getInstance().HEIGHT || y == -1) {
            return;
        }
        if (x == -1) {
            x = WorldMap.getInstance().WIDTH - 1;
        } else if (x == WorldMap.getInstance().WIDTH) {
            x = 0;
        }
        if (!stateVisited[y][x]) {
            int countryQuantityInUnit = WorldMap.getInstance().getCell(y, x).getUnit().size();
            ArrayList<Country> tempCountryList = new ArrayList<>();
            for (int i = 0; i < countryQuantityInUnit; i++) {
                tempCountryList.add(WorldMap.getInstance().getCell(y, x).getUnit().get(i));
            }
            Collections.shuffle(tempCountryList);
            shuffleCountriesList.addAll(tempCountryList);
            stateVisited[y][x] = true;
            //Recursion
            generateCountryList(y - 1, x);
            generateCountryList(y - 1, x - 1);
            generateCountryList(y - 1, x + 1);
            generateCountryList(y, x - 1);
            generateCountryList(y, x + 1);
            generateCountryList(y + 1, x);
            generateCountryList(y + 1, x - 1);
            generateCountryList(y + 1, x + 1);
        }
    }

    public void generateQuestionList(int y, int x) {
        questionArrayList = new ArrayList<>();
        shuffleCountriesList=new ArrayList<>();
        generateCountryList(y,x);

    }

}
