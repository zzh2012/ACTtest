package act.View;

import javax.swing.*;
import java.awt.*;

import act.Controller.MainController;
import act.Model.*;

public class PassageView extends JPanel{

	/**
	 This is on layer of mainContent. 
	 */
	private static final long serialVersionUID = 9L;
	private MainChoicePane mainchoicepane = new MainChoicePane();	//use for minimal the choice panel size
	private PassagePane passagepane = new PassagePane();	//passage panel
	private reading readingBrain;
   	public PassageView()
	{
   		this.setSize(ViewConstants.MAINCONTENT_WIDTH , ViewConstants.MAINCONTENT_HEIGHT );
		this.setLayout(new BorderLayout());
	}
   	
   	public void init(){
   		mainchoicepane.init(ViewConstants.DISPALY_CHOICE_PANE_PART);
   		passagepane.init(ViewConstants.PASSAGEPANE_WIDTH,ViewConstants.PASSAGEPANE_HEIGHT);
   		this.setPreferredSize(new Dimension(ViewConstants.MAINCONTENT_WIDTH, ViewConstants.MAINCONTENT_HEIGHT));
   		this.add(mainchoicepane, BorderLayout.WEST);
		this.add(passagepane, BorderLayout.CENTER);
   	}
   	public void setReadingBrain(reading r){
   		readingBrain = r;
   		mainchoicepane.setReadingBrain(r);
   		passagepane.setReadingBrain(r);
   	}
   	
   	public void requestUpdate(int questionIndex,int splitIndex,int partIndex){
   		if(partIndex != ModelConstants.WRITING){
   			mainchoicepane.requestUpdate(questionIndex, splitIndex, partIndex);
   			if(MainController.needUpdatePa()){
   				passagepane.requestUpdate(questionIndex, splitIndex, partIndex);
   			}
   			
   		}
   	}
};