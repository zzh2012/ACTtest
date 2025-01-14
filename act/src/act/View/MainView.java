package act.View;

import javax.swing.*;
import act.Controller.MainController;
import act.Model.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class MainView extends JPanel{
	/**
	 This is the main view.
	 */
	private static final long serialVersionUID = 7L;
	private ControlPane navBar = new ControlPane();	//navigation bar on top
	private JLayeredPane mainContent = new JLayeredPane();
	private PassageView passageView = new PassageView();	//content panel,insist of passage and choice
	private ChoiceView choiceView = new ChoiceView();
	private WritingView writingView = new WritingView();
	private InstructionView instructionView = new InstructionView();
	private ScoreListView scoreListView = new ScoreListView();
	private reading readingBrain ;
	private math mathBrain ;
	private static TestBasicInfo basicInfo;
	public MainView()
	{
		this.setPreferredSize(new Dimension(ViewConstants.MAINPANEL_WIDTH, ViewConstants.MAINPANEL_HEIGHT));
		this.setBackground(Color.WHITE);
	}
	
	public void init(){
		MainController.setMainContent(this);
		MainController.setPartMode(false);
		this.removeAll();
		this.revalidate();
		passageView.init();
	    choiceView.init();
	    writingView.init();
	    instructionView.init();
	    navBar.init();
	    initTimer(0);
		mainContentInit();
		mainContent.setBackground(Color.WHITE);
		
		this.setSize(ViewConstants.MAINPANEL_WIDTH, ViewConstants.MAINPANEL_HEIGHT);
		this.setPreferredSize(new Dimension(ViewConstants.MAINPANEL_WIDTH, ViewConstants.MAINPANEL_HEIGHT));
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.add(navBar,BorderLayout.PAGE_START);
        this.add(mainContent,BorderLayout.CENTER);
        this.setVisible(true);
        this.revalidate();
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {  
            
          @Override  
          public void eventDispatched(AWTEvent event) {  
              // TODO Auto-generated method stub  
              if(((KeyEvent)event).getID()==KeyEvent.KEY_PRESSED && MainController.notThisPartWriting()){  
                  switch (((KeyEvent)event).getKeyCode()) {  
                  case KeyEvent.VK_ENTER:  
                       //System.out.println("fuckyou");
                      break;  
                  case KeyEvent.VK_RIGHT:
                	  MainController.handleNext();
                	  break;
                  case KeyEvent.VK_LEFT:
                	  MainController.handleBef();
                	  break;
                  }  
              }  
          }  
      }, AWTEvent.KEY_EVENT_MASK);
        showInstructionView(MainController.getTestIndex(),ModelConstants.ENGLISH);
        //showPassageView();
        
	}
	public void init(int partIndex){
		MainController.setPartIndex(partIndex);
		MainController.setPartMode(true);
		this.removeAll();
		this.revalidate();
		passageView.init();
	    choiceView.init();
	    writingView.init();
	    instructionView.init();
	    navBar.init();
	    initTimer(partIndex);
		mainContentInit();
		mainContent.setBackground(Color.WHITE);
		MainController.setMainContent(this);
		
		this.setSize(ViewConstants.MAINPANEL_WIDTH, ViewConstants.MAINPANEL_HEIGHT);
		this.setPreferredSize(new Dimension(ViewConstants.MAINPANEL_WIDTH, ViewConstants.MAINPANEL_HEIGHT));
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.add(navBar,BorderLayout.PAGE_START);
        this.add(mainContent,BorderLayout.CENTER);
        this.setVisible(true);
        this.revalidate();
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {  
            
          @Override  
          public void eventDispatched(AWTEvent event) {  
              // TODO Auto-generated method stub  
              if(((KeyEvent)event).getID()==KeyEvent.KEY_PRESSED && MainController.notThisPartWriting()){  
                  switch (((KeyEvent)event).getKeyCode()) {  
                  case KeyEvent.VK_ENTER:  
                      break;  
                  case KeyEvent.VK_RIGHT:
                	  MainController.handleNext();
                	  break;
                  case KeyEvent.VK_LEFT:
                	  MainController.handleBef();
                	  break;
                  }  
              }  
          }  
      }, AWTEvent.KEY_EVENT_MASK);
        showInstructionView(MainController.getTestIndex(),partIndex);
        //showPassageView();
        
	}
	public void mainContentInit(){
		mainContent.removeAll();
		mainContent.setSize(ViewConstants.MAINCONTENT_WIDTH, ViewConstants.MAINCONTENT_HEIGHT);
		mainContent.setPreferredSize(new Dimension(ViewConstants.MAINCONTENT_WIDTH, ViewConstants.MAINCONTENT_HEIGHT));
		mainContent.add(passageView, ViewConstants.PASSAGEVIEW_LAYER);
		mainContent.add(choiceView, ViewConstants.CHOICEVIEW_LAYER);
		mainContent.add(writingView, ViewConstants.WRITINGVIEW_LAYER);
		mainContent.add(instructionView, ViewConstants.INSTRUCTIONVIEW_LAYER);
		
	}
	
	public void showChoiceView(){
		choiceView.setVisible(true);
		passageView.setVisible(false);
		writingView.setVisible(false);
		instructionView.setVisible(false);
		mainContent.moveToFront(choiceView);
		
	}
	public void showPassageView(){
		choiceView.setVisible(false);
		passageView.setVisible(true);
		writingView.setVisible(false);
		instructionView.setVisible(false);
		mainContent.moveToFront(passageView);
	}
	public void showWritingView(){
		choiceView.setVisible(false);
		passageView.setVisible(false);
		writingView.setVisible(true);
		instructionView.setVisible(false);
		mainContent.moveToFront(writingView);
	}
	public void showInstructionView(int testIndex,int partIndexToShow){
		//System.out.println("IN");
		choiceView.setVisible(false);
		passageView.setVisible(false);
		writingView.setVisible(false);
		instructionView.requestUpdate(testIndex,partIndexToShow);
		mainContent.remove(instructionView);
		mainContent.add(instructionView, ViewConstants.INSTRUCTIONVIEW_LAYER);
		instructionView.setVisible(true);
		
		mainContent.moveToFront(instructionView);
	}
	public void showScoreView(){
		mainContent.removeAll();
		mainContent.add(scoreListView);
		navBar.scoreMode();
		scoreListView.init();
		scoreListView.setVisible(true);
		
		mainContent.revalidate();
	}
	public void requestUpdate(){
		requestUpdate(MainController.getQuestionIndex(),MainController.getSplitIndex(),MainController.getPartIndex());
	}
	public void requestUpdate(int questionIndex,int splitIndex,int partIndex){
		if(partIndex == ModelConstants.MATH){
			choiceView.requestUpdate(questionIndex, splitIndex, partIndex);
			mainContent.remove(choiceView);
			mainContent.add(choiceView, ViewConstants.CHOICEVIEW_LAYER);
			showChoiceView();
		}
		else if (partIndex != ModelConstants.WRITING){
			passageView.requestUpdate(questionIndex, splitIndex, partIndex);
			mainContent.remove(passageView);
			mainContent.add(passageView, ViewConstants.PASSAGEVIEW_LAYER);
			showPassageView();
		}
		else{
			writingView.requestUpdate(questionIndex, splitIndex, partIndex);
			mainContent.remove(writingView);
			mainContent.add(writingView, ViewConstants.WRITINGVIEW_LAYER);
			showWritingView();
		}
	}
	public void updateNavBar(int splitIndex,int partIndex){
		navBar.requestUpdate(splitIndex, partIndex);
	}
	public void setReadingBrain(reading r){
		readingBrain = r;
		choiceView.setReadingBrain(r);
		passageView.setReadingBrain(r);
		instructionView.setReadingBrain(r);
	}
	public void setMathBrain(math m){
		mathBrain = m;
		choiceView.setMathBrain(m);
		instructionView.setMathBrain(m);
	}
	public void startTimer(int partIndex){
		setCountingStatus(true);
		System.out.println("Timer part"+partIndex+",time:"+ViewConstants.TIME_LIMIT_PER_PART[partIndex]);
		navBar.startTimer(ViewConstants.TIME_LIMIT_PER_PART[partIndex]);
	}
	public void initTimer(int partIndex){
		navBar.initTimer(ViewConstants.TIME_LIMIT_PER_PART[partIndex]);
	}
	public void setCountingStatus(boolean c){
		navBar.setCountingStatus(c);
	}
	public void pauseTimer(){
		navBar.pauseMode();
		navBar.pauseTimer();
	}
	public void resumeTimer(){
		navBar.normalMode();
		navBar.resumeTimer();
	}
	public Boolean isTimeAlive(){
		return navBar.isTimeAlive();
	}
};
