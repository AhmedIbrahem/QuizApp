package ahmed.fciibrahem.helwan.edu.eg.quizapp;



import android.provider.BaseColumns;

public class QuizContract {
    private QuizContract(){}

    public static class CategoryTable implements BaseColumns
    {
        public static final String Table_Name="Category";
        public static final String Coulmn_Name="name";


    }
 public static class QuetionTable implements BaseColumns {
    public    static final String Table_Name="quiz_questions";
     public   static final String Column_question="question";
     public   static final String Column_Option1="option1";
     public   static final String Column_Option2="option2";
     public   static final String Column_Option3="option3";
     public   static final String Column_Answer_nr="answer_nr";
     public  static final String Column_Deffuclty="defucaltyquestion";
     public static final String Coulmn_Category_id="category_id";

    }

}
