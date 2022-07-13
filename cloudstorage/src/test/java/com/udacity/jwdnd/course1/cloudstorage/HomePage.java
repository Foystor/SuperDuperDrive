package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePage {
    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "add-note-btn")
    private WebElement addNoteBtn;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "save-note-btn")
    private WebElement saveNoteBtn;

    @FindBy(className = "note-title-list")
    private List<WebElement> noteTitleDisplay;

    @FindBy(className = "note-description-list")
    private List<WebElement> noteDescriptionDisplay;

    @FindBy(className = "edit-note-btn")
    private List<WebElement> editNoteBtn;

    @FindBy(className = "delete-note-btn")
    private List<WebElement> deleteNoteBtn;

    @FindBy(id = "add-credential-btn")
    private WebElement addCredentialBtn;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id = "save-credential-btn")
    private WebElement saveCredentialBtn;

    @FindBy(className = "credential-url-list")
    private List<WebElement> credentialUrlDisplay;

    @FindBy(className = "credential-username-list")
    private List<WebElement> credentialUsernameDisplay;

    @FindBy(className = "credential-password-list")
    private List<WebElement> credentialPasswordDisplay;

    @FindBy(className = "edit-credential-btn")
    private List<WebElement> editCredentialBtn;

    @FindBy(className = "delete-credential-btn")
    private List<WebElement> deleteCredentialBtn;

    @FindBy(id = "logout-button")
    private WebElement logoutBtn;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }

    public void logout() {
        logoutBtn.click();
    }

    public void addNote(String title, String description) {
        noteTab.click();
        addNoteBtn.click();
        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);
        saveNoteBtn.click();
    }

    public List<Note> getNotes() {
        noteTab.click();
        List<Note> notes = new ArrayList<>();
        for (int i = 0; i < noteTitleDisplay.size(); i++) {
            Note note = new Note(null,
                    noteTitleDisplay.get(i).getText(),
                    noteDescriptionDisplay.get(i).getText(),
                    null);

            notes.add(note);
        }
        return notes;
    }

    public void editNote(int index, String title, String description) {
        noteTab.click();
        editNoteBtn.get(index).click();
        noteTitleField.clear();
        noteTitleField.sendKeys(title);
        noteDescriptionField.clear();
        noteDescriptionField.sendKeys(description);
        saveNoteBtn.click();
    }

    public void deleteNote(int index) {
        noteTab.click();
        deleteNoteBtn.get(index).click();
    }

    public void addCredential(String url, String username, String password) {
        credentialTab.click();
        addCredentialBtn.click();
        credentialUrlField.sendKeys(url);
        credentialUsernameField.sendKeys(username);
        credentialPasswordField.sendKeys(password);
        saveCredentialBtn.click();
    }

    public List<Credential> getEncryptedCredentials() {
        credentialTab.click();
        List<Credential> credentials = new ArrayList<>();
        for (int i = 0; i < credentialUrlDisplay.size(); i++) {
            Credential credential = new Credential(null,
                    credentialUrlDisplay.get(i).getText(),
                    credentialUsernameDisplay.get(i).getText(),
                    null,
                    credentialPasswordDisplay.get(i).getText(),
                    null);

            credentials.add(credential);
        }
        return credentials;
    }

    public void editCredential(int index, String url, String username, String password) {
        credentialTab.click();
        editCredentialBtn.get(index).click();
        credentialUrlField.clear();
        credentialUrlField.sendKeys(url);
        credentialUsernameField.clear();
        credentialUsernameField.sendKeys(username);
        credentialPasswordField.clear();
        credentialPasswordField.sendKeys(password);
        saveCredentialBtn.click();
    }

    public void deleteCredential(int index) {
        credentialTab.click();
        deleteCredentialBtn.get(index).click();
    }
}
