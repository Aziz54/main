/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}
