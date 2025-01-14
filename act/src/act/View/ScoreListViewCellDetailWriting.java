package act.View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import act.Model.AnswerModel;
import act.Model.ModelConstants;
import act.Model.readText;

public class ScoreListViewCellDetailWriting extends JDialog{
 /**
	 * 
	 */
 private static final long serialVersionUID = -6275845889135880243L;
 JPanel maincontent = new JPanel();
 JPanel mainPanel = new JPanel(); 
 PassagePane pa = new PassagePane();
 JScrollPane scrollPane;
 //JPanel centerPanel = new JPanel();
 JEditorPane textPane = new JEditorPane();
 JLabel scoreLabel = new JLabel();
 int w = (int)Math.floor(0.7*ViewConstants.SCORE_LIST_VIEW_DETAIL_WIDTH) ;
 int h = (int)Math.floor(0.7*ViewConstants.SCORE_LIST_VIEW_DETAIL_HEIGHT);
 
 SLVWpane spane = new SLVWpane();
 public ScoreListViewCellDetailWriting(int x,int y){
	 
  setBounds(x,y,ViewConstants.SCORE_LIST_VIEW_DETAIL_WIDTH,ViewConstants.SCORE_LIST_VIEW_DETAIL_HEIGHT);
  this.setVisible(true);
  this.setLayout(new BorderLayout());
  this.add(mainPanel,BorderLayout.CENTER);
  
  mainPanel.setLayout(new FlowLayout());
  mainPanel.add(spane);
  mainPanel.setSize(ViewConstants.SCORE_LIST_VIEW_DETAIL_WIDTH,ViewConstants.SCORE_LIST_VIEW_DETAIL_HEIGHT);
  mainPanel.setPreferredSize(new Dimension(w,h));
  
 }
 
 public void init(){
//	pa.init((int)Math.floor(0.3*ViewConstants.SCORE_LIST_VIEW_DETAIL_WIDTH),
//			 (int)Math.floor(0.3*ViewConstants.SCORE_LIST_VIEW_DETAIL_HEIGHT));
	String s = readText.readWriting();
	spane.init(s);
	spane.setEnabled(false);
 }
 
}


