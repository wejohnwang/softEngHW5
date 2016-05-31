package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class softEngineeringHW5 {
    public static void main(String [] args){
        inputData tmp = new inputData();
        checkOut cO = null;
        
        tmp.inputDate();
        tmp.inputTime();
        tmp.inputPeople();
        switch(tmp.selectMode()){
            case 1:
                cO = new workDayMorningAndLunch(tmp.getAdult(),tmp.getChild(),tmp);
                cO.showMoney();
                break;
            case 2:
                cO = new workDayNight(tmp.getAdult(),tmp.getChild(),tmp);
                cO.showMoney();
                break;
            case 3:
                cO = new weekend(tmp.getAdult(),tmp.getChild(),tmp);
                cO.showMoney();
                break;
            case 4:
                cO = new onSales(tmp.getAdult(),tmp.getChild(),tmp);  //要把產生的inputData帶進去，不然剛剛輸入的值會不見
                cO.showMoney();
                break;
        }
        
    }
}
class inputData{
    private int year;
    private int month;
    private int date;
    private int day;  //星期幾 (1 = 星期天 2 = 星期一..... 7 = 星期六)
    private int whichTime;  //(1:早上、2:中午、3:晚上)
    private int adult;
    private int child;
    private SimpleDateFormat sdf;
    private Scanner sc ;
    
    public void inputDate(){
        boolean inputok = false;     
        sc = new Scanner(System.in);
        while(!inputok){
            System.out.println("請輸入日期(格式 YYYY/MM/DD) : ");
            String[] input = sc.next().split("/");   //將日期用 / 分開
            if(input.length ==3) {
                inputok = setDate(input);  //設定日期
                /*for(int i = 0 ; i < input.length ; i++){
                    System.out.println(input[i]);
                }*/
            }else{
                 System.out.println("日期格式錯誤 請重新輸入");
                 inputok = false;
            }
        }
    }
    public boolean setDate(String [] input){
        String inputdate = input[0]+"/"+input[1]+"/"+input[2];
        sdf = new SimpleDateFormat("yyyy/MM/dd");  //輸入的日期格式
        sdf.setLenient(false);  //給錯格式就要丟例外
        try{
            sdf.parse(inputdate);  //用SimpleDateFormat轉換日期
            year = Integer.parseInt(input[0]);
            month = Integer.parseInt(input[1]);
            date = Integer.parseInt(input[2]);  //設定日期和時間
            Calendar c = Calendar.getInstance();  ///要取得是星期幾
            c.set(year, month-1, date);  //記得month要減一
            day = c.get(Calendar.DAY_OF_WEEK);  //取得星期幾
            return true;
        }catch(ParseException e){
            System.out.println("日期錯誤");
            return false;
        }   
    }
    public void inputTime(){
        boolean inputOk = false;
        NumberFormatException nfe = new NumberFormatException();
        
        while(!inputOk){
            System.out.println("請輸入時間 : (1:早上、2:中午、3:晚上)");
            sc = new Scanner(System.in);
            try{
                whichTime =  sc.nextInt();
                if(whichTime > 3 || whichTime < 0){
                    throw nfe;
                }else{
                    inputOk = !inputOk;
                }
            }catch(Exception e){
                System.out.println("時間只有1.2.3，分別代表不同時段");
            }
        }
    }
    public void inputPeople(){
        NumberFormatException nuEx = new NumberFormatException();
        boolean inputOk = false;
        boolean inputAllPeopleMorehanOne = false;
        while(!inputAllPeopleMorehanOne){
                while(!inputOk){
                System.out.println("請輸入大人人數 : ");
                sc = new Scanner(System.in);  //記得 sc要再回圈裏面new 否則會沿用上一個數字(或是輸入錯誤的數值)
                try{
                    adult =  sc.nextInt();
                    if(adult < 0){
                        throw nuEx;
                    }else{
                        inputOk = !inputOk;
                    }

                }catch(Exception e){
                    System.out.println("人數請輸入正整數");
                }
            }

            inputOk = false;

            while(!inputOk){
                System.out.println("請輸入小孩人數 : ");
                sc = new Scanner(System.in);
                try{
                    child =  sc.nextInt();
                    if(child < 0){
                        throw nuEx;
                    }else{
                        inputOk = true;
                    }

                }catch(Exception e){
                    System.out.println("人數請輸入正整數");
                }
            }
            if(adult + child > 0){
                inputAllPeopleMorehanOne = true;
            }
        }
        
    }
    public int selectMode(){
        int mode = 0;
        char input = 'w';
        boolean inputOK = false;
        while(!inputOK){
            System.out.println("要不要使用目前的促銷模式 : (Y/N)");
            sc = new Scanner(System.in);
            input = sc.next().toUpperCase().charAt(0);  //取得輸入的第一個字元
            if(input != 'Y' && input != 'N'){
                System.out.println("請輸入 Y 或 N ");
            }else{
                inputOK = true;
            }
        }
        
        if(input == 'Y'){  //使用促銷模式
            mode = 4;
        }else if(getDay() == Calendar.SATURDAY || getDay() == Calendar.SUNDAY){  //假日
            mode = 3;
        }else if(getWhichTime() == 3){  ///平日晚上
            mode = 2;
        }else{  //剩下平日早上中午
            mode = 1;
        }
        return mode;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public int getDay() {
        return day;
    }

    public int getWhichTime() {
        return whichTime;
    }

    public int getAdult() {
        return adult;
    }

    public int getChild() {
        return child;
    }
}
abstract class checkOut{
    private int adult;
    private int child;
    
    abstract protected int payMoney();
    abstract protected void showMoney();
    
    public checkOut(int adult,int child){  //設定大人小孩人數
        this.adult = adult;
        this.child = child;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }
    public void setChild(int child) {
        this.child = child;
    }
    public int getAdult() {
        return adult;
    }
    public int getChild() {
        return child;
    }
}
class workDayMorningAndLunch extends checkOut{
    inputData tmp;
    public workDayMorningAndLunch(int adult,int child,inputData tmp){
        super(adult,child);  //記得要先呼叫父類別的建構子(設定大人小孩人數)
        this.tmp = tmp;
    }

    @Override
    protected int payMoney() {
        int money = 0; //要付的總金額;
        money = super.getAdult()*268 + super.getChild()*120;  //大人268 小孩120
        money = (int)(money *1.1)+1 ;  //服務費(1元是因為小數點後面被扣掉，要加回來)
        return money;
    }

    @Override
    protected void showMoney() {
        System.out.println("============================================================");
        System.out.println("=========================發票資訊===========================");
        System.out.println("============================================================");
        System.out.println("消費日期："+ tmp.getYear()+"/"+tmp.getMonth()+"/"+tmp.getDate());
        System.out.println("消費人數："+ (super.getAdult()+super.getChild())+" 大人人數 : "+super.getAdult()+" 小孩人數 : "+super.getChild());
        System.out.println("============================================================");
        System.out.println("平日早上或中午，需支付"+payMoney()+"元");
    }
}
class workDayNight extends checkOut{
    inputData tmp;
    public workDayNight(int adult,int child,inputData tmp){
        super(adult,child);  //記得要先呼叫父類別的建構子(設定大人小孩人數)
        this.tmp = tmp;
    }

    @Override
    protected int payMoney() {
        int money = 0; //要付的總金額;
        money = super.getAdult()*368 + super.getChild()*150;  //大人268 小孩120
        money = (int)(money *1.1)+1 ;  //服務費(1元是因為小數點後面被扣掉，要加回來)
        return money;
    }

    @Override
    protected void showMoney() {
        System.out.println("============================================================");
        System.out.println("=========================發票資訊===========================");
        System.out.println("============================================================");
        System.out.println("消費日期："+ tmp.getYear()+"/"+tmp.getMonth()+"/"+tmp.getDate());
        System.out.println("消費人數："+ (super.getAdult()+super.getChild())+" 大人人數 : "+super.getAdult()+" 小孩人數 : "+super.getChild());
        System.out.println("============================================================");
        System.out.println("平日晚上，需支付"+payMoney()+"元");
    }
}

class weekend extends checkOut{
    inputData tmp;
    public weekend(int adult,int child,inputData tmp){
        super(adult,child);  //記得要先呼叫父類別的建構子(設定大人小孩人數)
        this.tmp = tmp;
    }

    @Override
    protected int payMoney() {
        int money = 0; //要付的總金額;
        money = super.getAdult()*368 + super.getChild()*150;  //大人268 小孩120
        money = (int)(money *1.1)+1 ;  //服務費(1元是因為小數點後面被扣掉，要加回來)
        return money;
    }

    @Override
    protected void showMoney() {
        System.out.println("============================================================");
        System.out.println("=========================發票資訊===========================");
        System.out.println("============================================================");
        System.out.println("消費日期："+ tmp.getYear()+"/"+tmp.getMonth()+"/"+tmp.getDate());
        System.out.println("消費人數："+ (super.getAdult()+super.getChild())+" 大人人數 : "+super.getAdult()+" 小孩人數 : "+super.getChild());
        System.out.println("============================================================");
        System.out.println("假日，需支付"+payMoney()+"元");
    }
}

class onSales extends checkOut{
    inputData tmp;

    public onSales(int adult,int child,inputData inputTmp){
        super(adult,child);  //記得要先呼叫父類別的建構子(設定大人小孩人數)
        this.tmp = inputTmp;
    }

    @Override
    protected int payMoney() {
        int money = 0; //要付的總金額;
        int whichTime = tmp.getWhichTime();  //取得時段 
        int day = tmp.getDay();  //取得星期幾
        int adult = super.getAdult();
        int child = super.getChild();
        
        child = child - (adult/2);  //計算小孩的等值人數(2個大人可以帶一個小孩)
        if(child < 0){
            child = 0;  //小孩人數不要低於0
        }
        
        if(day == Calendar.SATURDAY || day == Calendar.SUNDAY){  //假日
            money = adult*368 + child*150;  
            money = (int)(money *1.1)+1 ;
        }else if(whichTime == 3){  //晚餐價錢
            money = adult*368 + child*150;  //大人268 小孩120
            money = (int)(money *1.1)+1 ;
        }else{  //平日的早上跟中午
            money = adult*268 + child*120;  //大人268 小孩120
            money = (int)(money *1.1)+1 ;  //服務費(1元是因為小數點後面被扣掉，要加回來)
        }
        
        if(super.getAdult() + super.getChild() >= 10){//大於10個人在打95折
            money = (int)(money*0.95);
        }
        return money;
    }

    @Override
    protected void showMoney() {
        System.out.println("============================================================");
        System.out.println("消費日期："+ tmp.getYear()+"/"+tmp.getMonth()+"/"+tmp.getDate());
        System.out.println("消費人數："+ (super.getAdult()+super.getChild())+" 大人人數 : "+super.getAdult()+" 小孩人數 : "+super.getChild());
        System.out.println("============================================================");
        System.out.println("目前促銷方案，需支付"+payMoney()+"元");
    }
}
