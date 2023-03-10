package com.uniovi.sdi2223312spring.pageobjects;

import com.uniovi.sdi2223312spring.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PO_PrivateView extends PO_NavView {

    public static void login(WebDriver driver, String username, String password){
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, username, password);
        PO_View.checkElementBy(driver,"text",username);
    }
    public static void logout(WebDriver driver){
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }

    public static void checkElement(WebDriver driver,String checkText){
        List<WebElement> elements = PO_View.checkElementBy(driver,"text",checkText);
        Assertions.assertEquals(checkText,elements.get(0).getText());
    }
    public static void checkElementAndClick(WebDriver driver,String type,String text,int index){
        List<WebElement> elements = checkElementBy(driver,type,text);
        elements.get(index).click();
    }

    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep)
    {
        //Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
        SeleniumUtils.waitSeconds(driver, 5);
        //Seleccionamos el alumnos userOrder
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
        //Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
}
