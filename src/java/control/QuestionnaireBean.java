/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.*;

/**
 *
 * @author osswald1u
 */
@ManagedBean
@RequestScoped
public class QuestionnaireBean {
    @PersistenceContext 
    private EntityManager em;

    @Resource 
    private UserTransaction ut;
    
    @ManagedProperty ( value = "#{test}")
    public String reponse = "";
    public ArrayList<String> reponsesQcm = new ArrayList<String>();
    public int i = 0;
    public String nomQuest;

    /**
     * Creates a new instance of TestBean
     */
    public QuestionnaireBean() {

    }
    
    /*public String getQuestionnaire() {
       /*     
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
    }  */  
    
    public void ajout() {
    }

    public ArrayList<String> getReponsesQcm() {
        return reponsesQcm;
    }

    public void setReponsesQcm(ArrayList<String> s) {
        System.out.println("Tu réponds à la question qcm " + i);
        i++;
        System.out.println(s);
        this.reponsesQcm = s;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String rep) {
        if(rep!=null){
            Questionnaire q;
            /*FacesContext fc = FacesContext.getCurrentInstance();
            q = em.find(Questionnaire.class, nomQuest);
            Question qu = q.getQuestions().get(i);
            if(qu.getType().equals("ouverte")){
                ReponseOuverte ro = new ReponseOuverte();
                ro.setReponse(rep);
                ro.setQuestion(qu);
                qu.setReponse(ro);
                ut.begin();
                em.merge(ro);
                em.merge(qu);
                ut.commit();
                
            }else{
            
            }
            
            */
            System.out.println("Tu réponds à la question ouverte " + i);
            i++;
            System.out.println(rep);
            this.reponse = rep;
        }

    }
    
    
}
