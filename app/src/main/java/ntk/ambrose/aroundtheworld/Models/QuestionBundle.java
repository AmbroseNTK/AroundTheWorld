package ntk.ambrose.aroundtheworld.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuestionBundle {

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Question> getQuestionArrayList() {
        return questionArrayList;
    }

    public class Question {

        private Country question;
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

        public Question(Country question,String ansA, String ansB, String ansC, String ansD, int correctCode) {
            setQuestion(question);
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

        public Country getQuestion() {
            return question;
        }

        public void setQuestion(Country question) {
            this.question = question;
        }
    }

    private ArrayList<Question> questionArrayList;
    ArrayList<Country> shuffleCountriesList;

    private boolean[][] stateVisited;

    private int level;

    public static final int LEVEL_EASY=4;
    public static final int LEVEL_MEDIUM=4;
    public static final int LEVEL_HARD = 8;


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
            for(Country tempCountry: tempCountryList){
                if(!isContainCountry(tempCountry,shuffleCountriesList))
                    shuffleCountriesList.add(tempCountry);
            }
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

    private boolean isContainCountry(Country country, ArrayList<Country> list){
        for(Country countryInList: list){
            if(country.getCode().equals(countryInList.getCode()))
                return true;
        }
        return false;
    }

    public void generateQuestionList(int y, int x) {
        questionArrayList = new ArrayList<>();
        shuffleCountriesList=new ArrayList<>();
        for (int i = 0; i < WorldMap.getInstance().HEIGHT; i++) {
            for (int j = 0; j < WorldMap.getInstance().WIDTH; j++) {
                stateVisited[i][j] = false;
            }
        }
        generateCountryList(y,x);
        Random random=new Random();
        for(int i=0;i<shuffleCountriesList.size()-level;i++) {
            ArrayList<String> listAns=new ArrayList<>();
            Country correctCountry = shuffleCountriesList.get(i);
            listAns.add(correctCountry.getCode());
            for (int j = 1; j < 4; j++) {
                int maximum = (i + level > shuffleCountriesList.size()) ? shuffleCountriesList.size() : i + level;
                while (true) {
                    int rand = random.nextInt((maximum - i)) + i;
                    String newAns = shuffleCountriesList.get(rand).getCode();
                    if(!listAns.contains(newAns)) {
                        listAns.add(newAns);
                        break;
                    }
                }
            }
            Collections.shuffle(listAns);
            getQuestionArrayList().add(new Question(correctCountry,
                    listAns.get(0),
                    listAns.get(1),
                    listAns.get(2),
                    listAns.get(3),
                    listAns.indexOf(correctCountry.getCode())));


        }

    }

}
