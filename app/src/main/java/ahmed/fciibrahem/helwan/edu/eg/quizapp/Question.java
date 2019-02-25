package ahmed.fciibrahem.helwan.edu.eg.quizapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Question  implements Parcelable{
    public static final String Dfecltuy_Esay="esay";
    public static final String Dfecltuy_Meduiam="meduim";
    public static final String Dfecltuy_Hard="hard";

    private  int id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int answernumber;
    private String defuclty;
    private int categoryId;

    public Question(){}

    public Question(String question, String option1, String option2, String option3, int answernumber,String defuclty,int categoryId) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answernumber = answernumber;
        this.defuclty=defuclty;
        this.categoryId=categoryId;
    }

    protected Question(Parcel in) {
         id=in.readInt();
        question = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        answernumber = in.readInt();
        defuclty=in.readString();
        categoryId=in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeInt(answernumber);
        dest.writeString(defuclty);
        dest.writeInt(categoryId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getAnswernumber() {
        return answernumber;
    }

    public void setAnswernumber(int answernumber) {
        this.answernumber = answernumber;
    }

    public String getDefuclty() {
        return defuclty;
    }

    public void setDefuclty(String defuclty) {
        this.defuclty = defuclty;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public static String[] GetAllDefucltyLevel()
    {
        return new String[]
                {
                        Dfecltuy_Esay,
                        Dfecltuy_Meduiam,
                        Dfecltuy_Hard

                };

    }
}
