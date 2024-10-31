package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class AddEmployeePage extends CommonMethods {

    @FindBy(id="firstName")
    public WebElement firstnameLocator;

    @FindBy(id="middleName")
    public WebElement middlenameLocator;

    @FindBy(id="lastName")
    public WebElement lastnameLocator;

    @FindBy(id = "employeeId")
    public WebElement employeeIDField;

    @FindBy(id="btnSave")
    public WebElement saveButton;

    public AddEmployeePage(){
        PageFactory.initElements(driver, this);
    }


}
