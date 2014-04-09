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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import ul.dateroulette.model.Utilisateur;

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
    public List<String> reponsesQcm = new ArrayList<String>();
    public int i = 0;
    public String nomQuest;
    public Questionnaire q;
    public int idQuest;
    public int countRep = 0;
    public String pseudo = null;
    /**
     * Creates a new instance of BeanQuestionnaire
     */
    public BeanQuestionnaire() {

    }
    
    public void ajout() throws IOException {

       HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
       this.pseudo = request.getParameter("pseudo");

        FacesContext.getCurrentInstance().getExternalContext().redirect("liste-questionnaire.xhtml");
    }

    public List<String> getReponsesQcm() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        Utilisateur utilisateurSession = (Utilisateur)em.find(Utilisateur.class,(String)session.getAttribute("pseudoUtilisateur")) ;
        this.pseudo = utilisateurSession.getPseudo();
        
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Questionnaire q = em.find(Questionnaire.class,(long)Integer.parseInt(request.getParameter("id")));
        if(this.countRep >= q.getQuestions().size()){
            this.countRep = 0;
        }
        Question ques = q.getQuestion(this.countRep);

        Query jQuery2 = em.createQuery("Select q.reponsesQCM From Utilisateur q Where q.pseudo = :pseudo");
        jQuery2.setParameter("pseudo", this.pseudo);
        

        List<ReponseQCM> rListq = jQuery2.getResultList();
        ReponseQCM rq = null;
        
        for(ReponseQCM qcm : rListq){
            if(qcm != null && qcm.getQuestion().getId() == ques.getId()){

                rq = qcm;
            }
        }
        this.countRep++;
        List<String> ls = new ArrayList<String>();
        
        if(rq != null){
            for(Choix c : rq.getReponses()){
                ls.add(c.getReponse());
            }
            return ls;
        }
        return reponsesQcm;
    }

    public void setReponsesQcm(List<String> s) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        //Récuperation questionnaire grace au parametre
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        q = em.find(Questionnaire.class,(long)Integer.parseInt(request.getParameter("id")));
        
        //récuperation de la question
        QuestionQCM qcm = (QuestionQCM) q.getQuestions().toArray()[i];

        Query jQuery = em.createQuery("Select q.reponsesQCM From Utilisateur q Where q.pseudo = :pseudo");
        jQuery.setParameter("pseudo", request.getParameter("pseudo"));
        List<ReponseQCM> rList = jQuery.getResultList();
        ReponseQCM rq = null;
        for(ReponseQCM rep : rList){
            if(rep  != null && rep.getQuestion().equals(qcm)){
                rq = rep;
            }
        }
        
        if(rq == null){
            rq = new ReponseQCM();
        }
       
        
        Collection<Choix> cc = new ArrayList<Choix>();
        
        for(int j = 0; j < s.size() ; j++){
            Long id = (long) Integer.parseInt(s.get(j));
            Choix c = em.find(Choix.class, id);
            cc.add(c);
        }
        
        rq.setReponses(cc);
        rq.setQuestion(qcm);
        Utilisateur util = em.find(Utilisateur.class,request.getParameter("pseudo"));
        util.ajouterReponseQCM(rq);
        ut.begin();
        em.merge(util);
        em.merge(rq);
        ut.commit();  
        i++;
        this.reponsesQcm = s;
    }

    public String getReponse() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        Utilisateur utilisateurSession = (Utilisateur)em.find(Utilisateur.class,(String)session.getAttribute("pseudoUtilisateur")) ;
        this.pseudo = utilisateurSession.getPseudo();
        
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Questionnaire q = em.find(Questionnaire.class,(long)Integer.parseInt(request.getParameter("id")));
        if(this.countRep >= q.getQuestions().size()){
            this.countRep = 0;
        }
        Question ques = q.getQuestion(this.countRep);
        Query jQuery = em.createQuery("Select q.reponsesOuvertes From Utilisateur q Where q.pseudo = :pseudo");
        jQuery.setParameter("pseudo", this.pseudo);
        Query jQuery2 = em.createQuery("Select q.reponsesQCM From Utilisateur q Where q.pseudo = :pseudo");
        jQuery2.setParameter("pseudo", this.pseudo);
        
        List<ReponseOuverte> rListo = jQuery.getResultList();

        List<ReponseQCM> rListq = jQuery2.getResultList();

        ReponseOuverte ro = null;
        ReponseQCM rq = null;
        for(ReponseOuverte qo : rListo){
            if(qo != null && qo.getQuestion().getId() == ques.getId()){

                ro = qo;
            }
        }
        
        for(ReponseQCM qcm : rListq){
            if(qcm != null && qcm.getQuestion().getId() == ques.getId()){

                rq = qcm;
            }
        }
        this.countRep++;
        
        if(ro == null){
            return rq.getReponses().toArray()[0].toString();
        }else{
            return ro.getReponse();
        }
    }

    public void setReponse(String rep) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {


        if(rep!=null){
            HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            q = em.find(Questionnaire.class,(long)Integer.parseInt(request.getParameter("id")));

            Question qu = (Question) q.getQuestions().toArray()[i];
            if(qu instanceof QuestionOuverte){
                Query jQuery = em.createQuery("Select q.reponsesOuvertes From Utilisateur q Where q.pseudo = :pseudo");
                jQuery.setParameter("pseudo", request.getParameter("pseudo"));
                List<ReponseOuverte> rList = jQuery.getResultList();
                ReponseOuverte ro = null;
                for(ReponseOuverte reponse : rList){
                    if(reponse  != null && reponse.getQuestion().equals((QuestionOuverte)qu)){
                        ro = reponse;
                    }
                }

                if(ro == null){
                    ro = new ReponseOuverte();
                }
                //ReponseOuverte ro = new ReponseOuverte();
                ro.setReponse(rep);
                ro.setQuestion((QuestionOuverte) qu);
                 Utilisateur util = em.find(Utilisateur.class,request.getParameter("pseudo"));
                util.ajouterReponseOuverte(ro);
                ut.begin();
                em.merge(util);
                em.merge(ro);
                ut.commit();             
            }else{ // instanceof QuestionQCM
                Query jQuery = em.createQuery("Select q.reponsesQCM From Utilisateur q Where q.pseudo = :pseudo");
                jQuery.setParameter("pseudo", request.getParameter("pseudo"));
                List<ReponseQCM> rList = jQuery.getResultList();
                ReponseQCM rq = null;
                for(ReponseQCM reponse : rList){
                    if(reponse  != null && reponse.getQuestion().equals(qu)){
                        rq = reponse;
                    }
                }

                if(rq == null){
                    rq = new ReponseQCM();
                }
                Long id = (long) Integer.parseInt(rep);
                Choix c = em.find(Choix.class, id);
                //ReponseQCM rq = new ReponseQCM();
                Collection<Choix> cc = new ArrayList<Choix>();
                cc.add(c);
                rq.setReponses(cc);
                rq.setQuestion((QuestionQCM) qu);
                 Utilisateur util = em.find(Utilisateur.class,request.getParameter("pseudo"));
                util.ajouterReponseQCM(rq);
                ut.begin();
                em.merge(util);
                em.merge(rq);
                ut.commit();  
            }
            this.reponse = rep;
        }
        i++;
    }    
    
    public List<Question> getQuestion(){

       Query jQuery = em.createQuery("Select q From Questionnaire q Where q.id = :id");
       HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

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
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return Integer.parseInt(request.getParameter("id"));
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
    
    public void rediriger() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("liste-questionnaire.xhtml");
    }
    
    public void redirigerliste(AjaxBehaviorEvent event) throws IOException{

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "liste-questionnaire.xhtml");
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    
    
}
