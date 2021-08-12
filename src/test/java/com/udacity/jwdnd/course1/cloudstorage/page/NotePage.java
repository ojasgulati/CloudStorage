package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class NotePage {

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmit;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNoteTab;

    @FindBy(id = "btn-add-new-node")
    private WebElement btnAddNewNode;

    @FindBy(className = "noteTitleText")
    private WebElement noteTitleText;

    @FindBy(className = "noteDescriptionText")
    private WebElement noteDescriptionText;

    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;

    @FindBy(id = "deleteFileButton")
    private WebElement deleteFileButton;

    private final WebDriver driver;


    public NotePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void createNote(String title, String description){
        navNoteTab.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        btnAddNewNode.click();
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        noteTitle.submit();
    }

    public Note getFirstNote(){
        navNoteTab.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Note note = new Note();
        note.setNoteDescription(noteDescriptionText.getAttribute("innerText"));
        note.setNoteTitle(noteTitleText.getAttribute("innerText"));
        return note;
    }

    public void editNote(String title, String description){
        navNoteTab.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        editNoteButton.click();
        noteTitle.clear();
        noteDescription.clear();
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        noteTitle.submit();
    }

    public void deleteNote(){
        navNoteTab.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        deleteFileButton.click();
    }

    public boolean isNoteTitlePresent(){
        navNoteTab.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver.findElements(By.id("noteTitleText")).isEmpty();
    }

}
