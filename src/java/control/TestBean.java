/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import model.*;

/**
 *
 * @author osswald1u
 */
@ManagedBean
@RequestScoped
public class TestBean {
    @ManagedProperty ( value = "#{test}")
    public String test;
    Questionnaire q;
    //String rep= "bonjour";
    ArrayList<String> rep = new ArrayList<String>();

    /**
     * Creates a new instance of TestBean
     */
    public TestBean() {
        
    }
    
    public String getQuestionnaire() {
            q = new Questionnaire();
            q.setId((long)1);
            q.setNom("Questionnaire");
            QuestionOuverte q1 = new QuestionOuverte();
            QuestionOuverte q2 = new QuestionOuverte();
            QuestionOuverte q3 = new QuestionOuverte();
            QuestionQCM q4 = new QuestionQCM();
            q1.setId((long)1);
            q2.setId((long)2);
            q3.setId((long)3);
            q4.setId((long)4);
            q1.setQuestion("Votre age?");
            q2.setQuestion("Votre taille?");
            q3.setQuestion("Votre ville?");
            q4.setQuestion("Un chiffre?");
            ArrayList<Choix> arrayChoix = new ArrayList<Choix>();
            Choix c1 = new Choix();
            Choix c2 = new Choix();
            Choix c3 = new Choix();
            c1.setReponse("1");
            c2.setReponse("2");
            c3.setReponse("3");
            arrayChoix.add(c1);
            arrayChoix.add(c2);
            arrayChoix.add(c3);
            q4.setChoix(arrayChoix);
            ArrayList<Question> arrayQuestion = new ArrayList<Question>();
            arrayQuestion.add(q1);
            arrayQuestion.add(q2);
            arrayQuestion.add(q3);
            arrayQuestion.add(q4);
            q.setQuestions(arrayQuestion);
            StringBuilder sb = new StringBuilder();
            sb.append("<h:form>");
            for(Question quest: q.getQuestions() ){
                if(quest instanceof QuestionQCM){
                    QuestionQCM que = (QuestionQCM) quest;
                    sb.append("<h:selectOneMenu value=\"#{testBean.reponse}\">");
                    for(Choix cx: que.getChoix()) {
                        sb.append(" <f:selectItem itemValue=\""+cx.getReponse()+"\" itemLabel=\""+cx.getReponse()+"\" />");
                    }
                    sb.append("</h:selectOneMenu>");
                }
                else {
                    
                }
                
                sb.append("<h:commandButton value=\"Submit\" action=\"result\" /></h:form>");
            } 
            
            
            return sb.toString();
            
            
           /* <h:form>
         <h3>Combo Box</h3> 
         <h:selectOneMenu value="#{testBean.}">
            <f:selectItem itemValue="1" itemLabel="Item 1" />
            <f:selectItem itemValue="2" itemLabel="Item 2" />
            <f:selectItem itemValue="3" itemLabel="Item 3" />
            <f:selectItem itemValue="4" itemLabel="Item 4" />
            <f:selectItem itemValue="5" itemLabel="Item 5" />				
         </h:selectOneMenu>	
         <h:commandButton value="Submit" action="result" />
      </h:form> */		
        
    }    
    
    public void setReponse(String s) {
        this.rep.add(s);
    }
    
    public String getReponse() {
        StringBuilder sb = new StringBuilder();
        for(String s: this.rep) {
            sb.append(s);
            sb.append("<br>");
        }
        return sb.toString();
    }
    
    public void setTest(String titre) {
        this.test = titre;
        System.out.println(test);
    }
    
    public String getTest() {
        return this.test;
    }
    
    public void ajout() {
        ReponseOuverte ro = new ReponseOuverte();
        ro.setReponse(this.test);
    }
}
