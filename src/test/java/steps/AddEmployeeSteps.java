package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Ma;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.CommonMethods;
import utils.Constants;
import utils.ExcelReader;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddEmployeeSteps extends CommonMethods {
    @When("user clicks on PIM option")
    public void user_clicks_on_pim_option() {
        //WebElement pimOption = driver.findElement(By.id("menu_pim_viewPimModule"));
        //pimOption.click();
        click(dashboardPage.pimOption);
    }
    @When("user clicks on add employee button")
    public void user_clicks_on_add_employee_button() {
        //WebElement addEmployeeButton = driver.findElement(By.id("menu_pim_addEmployee"));
        //addEmpButton.click();
        click(dashboardPage.addEmployeeButton);
    }
    @When("user enters firstname and lastname")
    public void user_enters_firstname_and_lastname() {
        //WebElement firstNameTextField = driver.findElement(By.id("firstName"));
        //firstNameTextField.sendKeys("Leandro");
        sendText("Leandro", addEmployeePage.firstNameField);
        //WebElement lastNameField = driver.findElement(By.id("lastName"));
        //lastNameTextField.sendKeys("Farewell");
        sendText("Farewell", addEmployeePage.lastNameField);
    }
    @When("user clicks on save button")
    public void user_clicks_on_save_button() {
        //WebElement saveButton = driver.findElement(By.id("btnSave"));
        //saveOption.click();
        click(addEmployeePage.saveButton);
    }
    @Then("employee added successfully")
    public void employee_added_successfully() {
        System.out.println("Employee added successfully");
    }

    @When("user enters {string} and {string} and {string}")
    public void user_enters_and_and(String firstName, String middleName, String lastName) {
        sendText(firstName, addEmployeePage.firstNameField);
        sendText(middleName, addEmployeePage.middleNameField);
        sendText(lastName, addEmployeePage.lastNameField);
    }

    @When("user enters {string} and {string} and {string} in data driven format")
    public void user_enters_and_and_in_data_driven_format(String firstName, String middleName, String lastName) {
        sendText(firstName, addEmployeePage.firstNameField);
        sendText(middleName, addEmployeePage.middleNameField);
        sendText(lastName, addEmployeePage.lastNameField);
    }

    @When("user enters firstname and middlename and lastname and verify employee has added")
    public void user_enters_firstname_and_middlename_and_lastname_and_verify_employee_has_added(io.cucumber.datatable.DataTable dataTable) {
        // We need list of maps to get multiple values from datatable which is coming from feature file
        List<Map<String, String>> employeeNames = dataTable.asMaps();

        for (Map<String, String> employee:employeeNames
             ) {
            // Getting the values against the key in map
            String firstNameValue = employee.get("firstName");
            String middleNameValue = employee.get("middleName");
            String lastNameValue = employee.get("lastName");

            // Filling in names in the field
            sendText(firstNameValue, addEmployeePage.firstNameField);
            sendText(middleNameValue, addEmployeePage.middleNameField);
            sendText(lastNameValue, addEmployeePage.lastNameField);
            click(addEmployeePage.saveButton);
            // After adding one employee, we will add another employee
            // For this, we are checking in add employee button in the loop itself
            click(dashboardPage.addEmployeeButton);
        }
    }

    @When("user adds multiple employees using Excel from {string} and verify it")
    public void user_adds_multiple_employees_using_excel_from_and_verify_it(String sheetName) throws InterruptedException {
        // Here we are getting the data from Excel file using parameters
        List<Map<String, String>> newEmployees = ExcelReader.read(sheetName, Constants.EXCEL_READER_PATH);

        Iterator<Map<String, String>> iterator = newEmployees.iterator();

        // It will check whether we have new element / value or not
        while (iterator.hasNext()){

            // In this map, we have data from every single employee one by one it will give us that data
            Map<String, String> mapNewEmp = iterator.next();
            // We are filling the employee data now using mapNewEmp variable
            // Batch 16, keys that we are passing here should match with the keys in the Excel
            sendText(mapNewEmp.get("firstName"), addEmployeePage.firstNameField);
            sendText(mapNewEmp.get("middleName"), addEmployeePage.middleNameField);
            sendText(mapNewEmp.get("lastName"), addEmployeePage.lastNameField);
            sendText(mapNewEmp.get("photograph"), addEmployeePage.photograph);

            // We can enter username and password only after selecting the checkbox
            if (!addEmployeePage.checkBoxLocator.isSelected()){
                click(addEmployeePage.checkBoxLocator);
            }
            sendText(mapNewEmp.get("username"), addEmployeePage.usernameTextFieldBox);
            sendText(mapNewEmp.get("password"), addEmployeePage.passwordTextFieldBox);
            sendText(mapNewEmp.get("confirmPassword"), addEmployeePage.confirmPasswordBox);

            // Here, we are fetching the employee id from the UI get attribute method
            String empIdValue = addEmployeePage.employeeIdField.getAttribute("value");
            Assert.assertTrue(addEmployeePage.saveButton.isDisplayed());
            click(addEmployeePage.saveButton);
            Thread.sleep(3000);
            // We have to verify that the employee has been added
            click(dashboardPage.empListOption);
            Thread.sleep(3000);
            // Searching the employee using employee ID which we just got
            sendText(empIdValue, employeeSearchPage.idTextField);
            click(employeeSearchPage.searchButton);
            Thread.sleep(3000);
            // Print the value from the table row
            List<WebElement> rowData = driver.findElements(By.xpath("//table[@id='resultTable']/tbody/tr"));
            for (int i=0; i<rowData.size(); i++){
                System.out.println("I am inside the loop");
                // It will return one by one all the data from the row
                String rowText = rowData.get(i).getText();
                // It will print complete row data
                // Output of this will be: empId firstname middlename lastname
                System.out.println(rowText);
                // We have to verify this data coming from Excel

                String expectedData = empIdValue+ " "+mapNewEmp.get("firstName")+" "+mapNewEmp.get("middleName")+" " + mapNewEmp.get("lastName");

                Assert.assertEquals(expectedData, rowText);
                // You can use below code too to verify the data
                //Assert.assertTrue(expectedData.equals(rowText));
            }
            // To add more employees we need to click on add employee button
            click(dashboardPage.addEmployeeButton);
        }
    }
}
