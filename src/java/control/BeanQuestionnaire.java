/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import ul.dateroulette.model.Choix;
import ul.dateroulette.model.Question;
import ul.dateroulette.model.QuestionOuverte;
import ul.dateroulette.model.QuestionQCM;
import ul.dateroulette.model.Questionnaire;
import ul.dateroulette.model.ReponseOuverte;
import ul.dateroulette.model.ReponseQCM;

/**
 *
 * @author Thomas
 */
@ManagedBean
@RequestScoped
public class BeanQuestionnaire {
    @PersistenceContext 
    private EntityManager em;

    @Resource 
    private UserTransaction ut;
    
    public String reponse = "";
    public ArrayList<String> reponsesQcm = new ArrayList<String>();
    public int i = 0;
    public String nomQuest;
    public Questionnaire q;
    public int idQuest;
    /**
     * Creates a new instance of BeanQuestionnaire
     */
    public BeanQuestionnaire() {

    }
    
    public void ajout() {

    }

    public ArrayList<String> getReponsesQcm() {
        return reponsesQcm;
    }

    public void setReponsesQcm(ArrayList<String> s) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        q = em.find(Questionnaire.class, 0 /*id du questionnaire*/);
        QuestionQCM qcm = (QuestionQCM) q.getQuestions().toArray()[i];
        ReponseQCM rq = new ReponseQCM();
        Collection<Choix> cc = new ArrayList<Choix>();
        
        for(int j = 0; j < s.size() ; j++){
            Long id = (long) Integer.parseInt(s.get(i));
            Choix c = em.find(Choix.class, id);
            cc.add(c);
        }
        
        rq.setReponses(cc);
        rq.setQuestion(qcm);
        ut.begin();
        em.merge(rq);
        ut.commit();  
        this.reponsesQcm = s;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String rep) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
       if(rep!=null){
            q = em.find(Questionnaire.class, 0 /*id du questionnaire*/);
            Question qu = (Question) q.getQuestions().toArray()[i];
            if(qu instanceof QuestionOuverte){
                ReponseOuverte ro = new ReponseOuverte();
                ro.setReponse(rep);
                ro.setQuestion((QuestionOuverte) qu);
                ut.begin();
                em.merge(ro);
                ut.commit();             
            }else{ // instanceof QuestionQCM
                Long id = (long) Integer.parseInt(rep);
                Choix c = em.find(Choix.class, id);
                ReponseQCM rq = new ReponseQCM();
                Collection<Choix> cc = new ArrayList<Choix>();
                cc.add(c);
                rq.setReponses(cc);
                rq.setQuestion((QuestionQCM) qu);
                ut.begin();
                em.merge(rq);
                ut.commit();  
            }
            this.reponse = rep;
        }
    }    
    
    public List<Question> getQuestion(){
       Query jQuery = em.createQuery("Select q From Questionnaire q Where q.id = :id");
       HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
      // System.out.println(request.getParameter("id"));
       jQuery.setParameter("id", Integer.parseInt(request.getParameter("id")));
       List<Questionnaire> rList = jQuery.getResultList();
       jQuery = em.createQuery("Select q From Question q Where q.questionnaires = :q");
       jQuery.setParameter("q", rList.get(0));
       List<Question> rListe = jQuery.getResultList();
       return rListe;
    }
    
    public List<Choix> getChoix(int idQuest){
       Query question = em.createQuery("SELECT a FROM Question a WHERE a.id = :id");
       question.setParameter("id", idQuest);
       if(question.getResultList().get(0) instanceof QuestionQCM){
            Query jQuery = em.createQuery("SELECT a FROM Choix a WHERE a.question = :q");
            jQuery.setParameter("q", question.getResultList().get(0));
            //List<Integer> intList = jQuery.getResultList();
            List<Choix> rList = jQuery.getResultList();
       return rList;
       }
       
       return null;
    }

    public int getIdQuest() {
        return idQuest;
    }

    public void setIdQuest(int idQuest) throws IOException {
        this.idQuest = idQuest;

        FacesContext.getCurrentInstance().getExternalContext().redirect("questionnaire.xhtml?id="+idQuest);
    }
    
    public List<Questionnaire> getQuests(){
       Query jQuery = em.createQuery("Select q From Questionnaire q");
       List<Questionnaire> rList = jQuery.getResultList();
       return rList;
    }
    
}
