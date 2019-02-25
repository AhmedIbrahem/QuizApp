package ahmed.fciibrahem.helwan.edu.eg.quizapp;

import android.content.ContentValues;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ahmed.fciibrahem.helwan.edu.eg.quizapp.QuizContract.*;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="QuizApp.db";
    private static final int DATABASE_VERSION=2;
    private static QuizDbHelper instance;

  private   SQLiteDatabase db;

    public QuizDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//code of single tone ***************************************************************


    public static synchronized QuizDbHelper getInstance(Context context)
    {
        if (instance==null)
        {
          instance=  new QuizDbHelper(context.getApplicationContext());

        }
        return instance;

    }
    //********************************************************************************************
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        final String SQL_CREATE_CATEGORY_TABLE=" CREATE TABLE "+
                CategoryTable.Table_Name +" ( "+
                CategoryTable._ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CategoryTable.Coulmn_Name +" TEXT "+" ) ";



        final String SQL_CREATE_QUESTION_TABLE="CREATE TABLE "+
                QuetionTable.Table_Name +" ( "+
                QuetionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                QuetionTable.Column_question + " TEXT, "+
                QuetionTable.Column_Option1 + " TEXT, "+
                QuetionTable.Column_Option2 + " TEXT, "+
                QuetionTable.Column_Option3 + " TEXT, "+
                QuetionTable.Column_Answer_nr + " INTEGER, " +
                QuetionTable.Column_Deffuclty +" TEXT ," +
                QuetionTable.Coulmn_Category_id +" INTEGER ,"+
                " FOREIGN KEY( "+QuetionTable.Coulmn_Category_id +" )REFERENCES "+
                CategoryTable.Table_Name +" ( " +CategoryTable._ID +")"+ " ON DELETE CASCADE "+
                " ) ";

        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillCategoryTable();
        fillquestionTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoryTable.Table_Name);
        db.execSQL("DROP TABLE IF EXISTS " + QuetionTable.Table_Name);
         onCreate(db);

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoryTable()
    {
        Category c=new Category("programing");
        AddCategory(c);

        Category c2=new Category("Math");
        AddCategory(c2);

        Category c3=new Category("Since");
        AddCategory(c3);

        Category c4=new Category("thortical");
        AddCategory(c4);
    }
    private void AddCategory(Category category)
    {
        ContentValues cvc=new ContentValues();
        cvc.put(CategoryTable.Coulmn_Name,category.getName());
        db.insert(CategoryTable.Table_Name,null,cvc);
    }

    private void fillquestionTable()
    {
        Question question1 =new Question("programing,Easy: A is correct","A","B","C",1,Question.Dfecltuy_Esay,Category.programing);
        Addquestion(question1);
        Question question2 =new Question("programing, Meduim : is correct","A","B","C",1,Question.Dfecltuy_Meduiam,Category.programing);
        Addquestion(question2);
        Question question3 =new Question("programing,Hard: A is correct","A","B","C",1,Question.Dfecltuy_Hard,Category.programing);
        Addquestion(question3);
        Question question4 =new Question("math, Hard:  A is correct","A","B","C",1,Question.Dfecltuy_Hard,Category.Math);
        Addquestion(question4);
        Question question5 =new Question("math, Mediam :A is correct","A","B","C",1,Question.Dfecltuy_Meduiam,Category.Math);
        Addquestion(question5);
        Question question6 =new Question("Since,Hard :is correct","A","B","C",1,Question.Dfecltuy_Hard,Category.Since);
        Addquestion(question6);
        Question question7 =new Question("Thoritcal, Hard :A is correct","A","B","C",1,Question.Dfecltuy_Hard,Category.thortical);
        Addquestion(question7);
        Question question8 =new Question("Since,Easy :A is correct","A","B","C",1,Question.Dfecltuy_Esay,Category.Since);
        Addquestion(question8);
        Question question9 =new Question("notExiste,Easy :A is correct","A","B","C",1,Question.Dfecltuy_Esay,5);
        Addquestion(question9);



    }
    private void Addquestion(Question question ){
        ContentValues cv=new ContentValues();
        cv.put(QuetionTable.Column_question, question.getQuestion());
        cv.put(QuetionTable.Column_Option1,question.getOption1());
        cv.put(QuetionTable.Column_Option2,question.getOption2());
        cv.put(QuetionTable.Column_Option3,question.getOption3());
        cv.put(QuetionTable.Column_Answer_nr,question.getAnswernumber());
        cv.put(QuetionTable.Column_Deffuclty,question.getDefuclty());
        cv.put(QuetionTable.Coulmn_Category_id,question.getCategoryId());
        db.insert(QuetionTable.Table_Name,null,cv);
        Log.i("Addquesion", "iam in Add question: ");


    }
    public List<Category> GetAllCategory()
    {
        List<Category> categoryList=new ArrayList<>();
        db=getReadableDatabase();
        Cursor c=db.rawQuery(" SELECT * FROM "+CategoryTable.Table_Name,null);
        if (c.moveToFirst())
        {
            do {
                Category category=new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoryTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoryTable.Coulmn_Name)));
                categoryList.add(category);

            }while (c.moveToNext());


        }


 return categoryList;
    }
    public ArrayList<Question> GetAllQuestions()
    {
        ArrayList<Question> questions=new ArrayList<>();
        db=getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+QuetionTable.Table_Name,null);
        if(c.moveToFirst()){
            do {
                Question question=new Question();
                question.setId(c.getInt(c.getColumnIndex(QuetionTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuetionTable.Column_question)));
                question.setOption1(c.getString(c.getColumnIndex(QuetionTable.Column_Option1)));
                question.setOption2(c.getString(c.getColumnIndex(QuetionTable.Column_Option2)));
                question.setOption3(c.getString(c.getColumnIndex(QuetionTable.Column_Option3)));
                question.setAnswernumber(c.getInt(c.getColumnIndex(QuetionTable.Column_Answer_nr)));
                question.setDefuclty(c.getString(c.getColumnIndex(QuetionTable.Column_Deffuclty)));
                question.setCategoryId(c.getInt(c.getColumnIndex(QuetionTable.Coulmn_Category_id)));

                questions.add(question);
                }while (c.moveToNext());
            }
        c.close();
        return questions;
    }


    public ArrayList<Question> GetSelectQuestionDefculty(int categoryID, String difficlty)
    {

        ArrayList<Question> questions=new ArrayList<>();
        db=getReadableDatabase();
//        String[] selectionArgs=new String[]{difficlty};
//        Cursor c = db.rawQuery("SELECT * FROM "+QuetionTable.Table_Name + " WHERE "+QuetionTable.Column_Deffuclty+ " = ? ",selectionArgs);
       String selection= QuetionTable.Coulmn_Category_id + " = ? "+
               " AND " + QuetionTable.Column_Deffuclty +" = ? ";
       String[] selectionArgs=new String[]{String.valueOf(categoryID),difficlty};
       Cursor c=db.query(QuetionTable.Table_Name,null,selection,selectionArgs,null,null,null );


        if(c.moveToFirst()){
            do {
                Question question=new Question();
                question.setId(c.getInt(c.getColumnIndex(QuetionTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuetionTable.Column_question)));
                question.setOption1(c.getString(c.getColumnIndex(QuetionTable.Column_Option1)));
                question.setOption2(c.getString(c.getColumnIndex(QuetionTable.Column_Option2)));
                question.setOption3(c.getString(c.getColumnIndex(QuetionTable.Column_Option3)));
                question.setAnswernumber(c.getInt(c.getColumnIndex(QuetionTable.Column_Answer_nr)));
                question.setDefuclty(c.getString(c.getColumnIndex(QuetionTable.Column_Deffuclty)));
                question.setCategoryId(c.getInt(c.getColumnIndex(QuetionTable.Coulmn_Category_id)));
                questions.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questions;
    }


}
