package act.Controller;

import act.View.*;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import act.MainActivity;
import act.Model.*;


public class MainController{
	
	private static MainActivity mainActivity;
	private static MainView mainView;
	private static int questionIndex = 0;
	private static int splitIndex = 0;
	private static int partIndex = 0;
	private static TestBasicInfo basicInfo;
	private static boolean isInstructionShowing = true;
	private static AnswerModel ans;
	private static int[] questionInSplit = new int[ModelConstants.QUESTIONNUM_TOTAL];
	private static int[] splitInPart = new int[ModelConstants.SPLITNUM_TOTAL];
	private static reading readingBrain;
	private static math mathBrain;
	private static int currentStatus;
	private static int testIndex ;
	
private static boolean needUpdatePassage = false;
private static boolean isPartMode = false;	private static boolean isDuringTest = true;private static PrintScore ps = new PrintScore();	public MainController()
	{
		
	}
	public static void setPartIndex(int part){
		partIndex = part;
		for (int i = 0; i < part; i++){
			questionIndex += ModelConstants.QUESTIONNUM_PER_PART[i];
			splitIndex += ModelConstants.SPLITNUM_PER_PART[i];
		}
	}
	public static void setPartMode(boolean mode){
		isPartMode = mode;
	}
	public static boolean getPartMode(){
		return isPartMode;
	}
	public static void init(){
		reset();
		calQuestionIndex();
		calSplitIndex();
		isInstructionShowing = true;
		UpdateBrains(0,0,testIndex);
	}
	private static void calQuestionIndex(){
		for(int i=0;i<ModelConstants.QUESTIONNUM_TOTAL;i++){
			questionInSplit[i] = basicInfo.questionIndexinSplit(i);
		}
	}
	private static void calSplitIndex(){
		for(int i=0;i<ModelConstants.SPLITNUM_TOTAL;i++){
			splitInPart[i] = basicInfo.splitIndexinPart(i);
			//System.out.println(i+","+splitInPart[i]);
		}
	}
	public static void handleNext(){

		needUpdatePassage = false;
		if(partIndex >= basicInfo.getTotalPartNum()){
			handleScore();
			return;
		}
		if(isInstructionShowing){
			isInstructionShowing = false;
			if(partIndex == ModelConstants.MATH){
				
				mainView.showChoiceView();
			}
			else if(partIndex == ModelConstants.ENGLISH){
				mainView.showPassageView();
			}
			else if(partIndex == ModelConstants.READING){
				mainView.showPassageView();
			}
			else if(partIndex == ModelConstants.SCIENCE){
				mainView.showPassageView();
			}
			else if(partIndex == ModelConstants.WRITING){
				mainView.showWritingView();
			}
			mainView.requestUpdate(questionIndex, splitIndex, partIndex);
			mainView.updateNavBar(splitInPart[splitIndex], partIndex);
			mainView.startTimer(partIndex);
			return;
		}
		if(basicInfo.isLastInPart(questionIndex)){
			if(isPartMode) {
				handleScore();
				return;
			}
			if (mainView.isTimeAlive()) return;
			submitThisPart();
			isInstructionShowing = true;
			
			return;
		}
		else if(basicInfo.isLastInSplit(questionIndex)){
			questionIndex++;
			splitIndex++;
			needUpdatePassage = true;
		}
		else
		{
			questionIndex++;
		}
		readingBrain.updateReading(testIndex,splitInPart[splitIndex], partIndex);
		mainView.requestUpdate(questionIndex, splitIndex, partIndex);
		mainView.updateNavBar(splitInPart[splitIndex], partIndex);
	}
	
	public static void handleBef(){
		//System.out.println(questionIndex+","+splitIndex+","+partIndex);
		needUpdatePassage = false;
		if(partIndex > basicInfo.getTotalPartNum() || isInstructionShowing || basicInfo.isFirstInPart(questionIndex)){
			return;
		}
		if(basicInfo.isFirstInSplit(questionIndex)){
			questionIndex--;
			splitIndex--;
			needUpdatePassage = true;
		}
		else{
			questionIndex--;
		}
		readingBrain.updateReading(testIndex,splitInPart[splitIndex], partIndex);
		mainView.requestUpdate(questionIndex, splitIndex, partIndex);
		mainView.updateNavBar(splitInPart[splitIndex], partIndex);
	}
	public static void handleScore(){
		isDuringTest = false;
		mainView.setCountingStatus(false);
		ans.judgeScore();
		mainView.showScoreView();	
	}
	
	public static void handleResume(){
		mainView.resumeTimer();
	}
	public static void handlePause(){
		mainView.pauseTimer();
	}
	public static void handleReturn(){
		mainActivity.showMenuView();
		ans.resetAll();	
	}
	public static void handleSave(){
		String name = ModelConstants.TESTNAME[testIndex];
		JFrame tmp = new JFrame();
		tmp.setAlwaysOnTop(true);
		
		String xx = JOptionPane.showInputDialog(tmp, "Please input file name: \n Eg: daxiang", "report");
		if (xx == null) return;
		name += " " + xx;
		if (name == "" || name == null) name = "report";
		String path = new File(".").getAbsolutePath();
		path = path.substring(0,path.length()-1) + "reports"+File.separator;
		path += name + ".pdf";
		File file = new File(path);
		Object[] options = { "OK", "CANCEL" }; 
		
		if (file.exists()){
			int i = JOptionPane.showOptionDialog(tmp, "The file already exists. Overwrite existing file?", "Warning", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
					null, options, options[0]); 
			if (i == 1) return;
		}
		Object[][][] values = new Object[5][][];
		for (int i = 0; i < 4; i ++){
			values[i] = AnswerModel.getAnsModel(i);
		}
		Object[][] totalScore = AnswerModel.getTotalScore();
		String text = AnswerModel.getText();
		ps.writePDF(ModelConstants.TESTNAME[testIndex], name, values,totalScore, text);
	}
	
	public static void setMainContent(MainView v){
		mainView = v;
	}
	public static void setBasicInfo(TestBasicInfo t){
		basicInfo = t;
		ans.setBasicInfo(t);
	}
	public static void setAnswerModel(AnswerModel a){
		ans = a;
	}
	public static void setAnswer(int questionIndex,int answer){
		ans.setAns(questionIndex, answer);
	}
	public static void setText(String text){
		ans.setText(text);
	}
	public static String getText(){
		return ans.getText();
	}
	public static int getAnswer(int questionIndex){
		return ans.getAnswer(questionIndex);
	}
	public static int questionIndexinSplit(int questionIndexinTotal){
		return questionInSplit[questionIndexinTotal];
	}
	public static int splitIndexinPart(int splitIndexinTotal){
		return splitInPart[splitIndexinTotal];
	}
	public static void setReading(reading r){
		readingBrain = r;
	}
	public static void setMath(math m){
		mathBrain = m;
	}
	public static void UpdateBrains(int splitIndex,int partIndex,int testIndex){
		mathBrain.updateMath(testIndex);
		readingBrain.updateReading(testIndex, splitInPart[splitIndex], partIndex);
	}
	public static void setMainActivity(MainActivity m){
		mainActivity = m;
	}
	public static void setTestIndex(int testIndex){
		MainController.testIndex = testIndex;
	}
	public static void reset(){
		questionIndex = 0;
		splitIndex = 0;
		partIndex = 0;
		isDuringTest = true;
	}
	public static int getPartIndex(){
		return partIndex;
	}
	public static int getSplitIndex(){
		return splitIndex;
	}
	public static int getTestIndex(){
		return testIndex;
	}
	public static int getQuestionIndex(){
		return questionIndex;
	}
	public static void timeIsUp(){
		if(partIndex + 1 >= ModelConstants.PARTNUM_TOTAL){
			handleScore();
		}
		else if(isPartMode){
			handleScore();
		}
		else {
			submitThisPart();
		}
	}
	public static void submitThisPart(){
		if(partIndex == ModelConstants.WRITING){
			handleScore();
			return;
		}
		mainView.initTimer(partIndex);
		mainView.showInstructionView(testIndex,++partIndex);
		mainView.setCountingStatus(false);
		splitIndex = basicInfo.firstSplitInPart(partIndex);
		questionIndex = basicInfo.firstQuestionIndexInSplit(splitIndex);
		UpdateBrains(splitIndex,partIndex,testIndex);
		//System.out.println(questionIndex+","+splitIndex);
		isInstructionShowing = true;
	}
	
	public static boolean notThisPartWriting(){
		
		return isDuringTest &&((partIndex != ModelConstants.WRITING) || (partIndex == ModelConstants.WRITING && isInstructionShowing));
	}
	public static boolean needUpdatePa(){
		return needUpdatePassage | (questionIndexinSplit(questionIndex)==0) | partIndex!=ModelConstants.SCIENCE;
	}
	
};
