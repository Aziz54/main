package ul.dateroulette.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@Entity
public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String pseudo;
    private String nom;
    private String prenom;
    /*@Temporal(javax.persistence.TemporalType.DATE)
    private Date ddn;
    private String sexe;
    private String adresse;
    private String mail;
    private String telephone;
    private Boolean estDesinscrit;
    private Boolean estBloque;
    private Boolean estSupprime;*/

    /**
     * 
     * @element-type Galerie
     */
//    @OneToMany(cascade= CascadeType.ALL)
//    private Collection<Galerie>  galeries;
    /**
     * 
     * @element-type Utilisateur
     */
    @OneToMany(cascade= CascadeType.ALL)
    private Collection<Contact>  contacts;
    /**
     * 
     * @element-type Conversation
     */
//    @OneToMany(cascade= CascadeType.ALL)
//    private Collection<Conversation>  conversations;
    /**
     * 
     * @element-type ReponseQCM
     */
//    @OneToMany(cascade= CascadeType.ALL)
//    private Collection<ReponseQCM>  reponsesQCM;
    /**
     * 
     * @element-type ReponseOuverte
     */
//    @OneToMany(cascade= CascadeType.ALL)
//    private Collection<ReponseOuverte>  reponsesOuvertes;
    /**
     * 
     * @element-type SessionChat
     */
//    @OneToMany(cascade= CascadeType.ALL)
//    private Collection<SessionChat>  sessionsChat;
    /**
     * 
     * @element-type Contact
     */
    @OneToOne
    private Image avatar;
//    @OneToOne
//    private Session session;
    @OneToOne
    private SessionChat sessionChat;
    
    public Utilisateur(){
        
    }
    
    public Utilisateur(String pseudo){
        this.pseudo = pseudo;
    }
    
    public String getPseudo() {
        return pseudo;
    }
    
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    
    public String getNom() {
            return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    /*public Date getDdn() {
        return ddn;
    }
    
    public void setDdn(Date ddn) {
        this.ddn = ddn;
    }
    
    public String getSexe() {
        return sexe;
    }
    
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public String getMail() {
        return mail;
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public Boolean getEstDesinscrit() {
        return estDesinscrit;
    }
    
    public void setEstDesinscrit(Boolean estDesinscrit) {
        this.estDesinscrit = estDesinscrit;
    }
    
    public Boolean getEstBloque() {
        return estBloque;
    }
    
    public void setEstBloque(Boolean estBloque) {
        this.estBloque = estBloque;
    }
    
    public Boolean getEstSupprime() {
        return estSupprime;
    }
    
    public void setEstSupprime(Boolean estSupprime) {
        this.estSupprime = estSupprime;
    }
    
    public Collection<Galerie> getGaleries() {
        return galeries;
    }
    
    public void setGaleries(Collection<Galerie> galeries) {
        this.galeries = galeries;
    }*/
    
    public Collection<Contact> getContacts() {
        return contacts;
    }
    
    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
    }
   
    /*public Collection<Conversation> getConversations() {
        return conversations;
    }
    
    public void setConversations(Collection<Conversation> conversations) {
        this.conversations = conversations;
    }
    
    public Collection<ReponseQCM> getReponsesQCM() {
        return reponsesQCM;
    }
    
    public void setReponsesQCM(Collection<ReponseQCM> reponsesQCM) {
        this.reponsesQCM = reponsesQCM;
    }
    
    public Collection<ReponseOuverte> getReponsesOuvertes() {
        return reponsesOuvertes;
    }
    
    public void setReponsesOuvertes(Collection<ReponseOuverte> reponsesOuvertes) {
        this.reponsesOuvertes = reponsesOuvertes;
    }  
    
    public Collection<SessionChat> getSessionsChat() {
        return sessionsChat;
    }
    
    public void setSessionsChat(Collection<SessionChat> sessionsChat) {
        this.sessionsChat = sessionsChat;
    }*/
    
    public Image getAvatar() {
        return avatar;
    }
    
    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }
    
    /*public Session getSession() {
        return session;
    }
    
    public void setSession(Session session) {
        this.session = session;
    }*/

    public SessionChat getSessionChat() {
        return sessionChat;
    }

    public void setSessionChat(SessionChat sessionChat) {
        this.sessionChat = sessionChat;
    }   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pseudo != null ? pseudo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the pseudo fields are not set
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.pseudo == null && other.pseudo != null) || (this.pseudo != null && !this.pseudo.equals(other.pseudo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ul.dateroulette.entity.Utilisateur[ pseudo=" + pseudo + " ]";
    }
    
    
    
    
}