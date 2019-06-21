/**
 * 
 */
package stepDefinitions;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;


/**
 * @author Jayesh
 *
 */


public class Steps {
	WebDriver driver;
	String downloadPath = "C:\\Users\\J\\Downloads";
	String expfilename = "SampleData";
	//Array to collect data
	String[][] matrix;

			
		 
		 @Given("^the user is on the sample data webpage$")
		 public void the_user_is_on_the_sample_data_webpage() {
			 System.setProperty("webdriver.chrome.driver","C:\\Test\\Libs\\Drivers\\chromedriver.exe");
			 driver = new ChromeDriver();
			 driver.manage().window().maximize();
			 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			 driver.get("https://www.contextures.com/xlSampleData01.html");
			 
			 
			 
			//count rows
			List<WebElement> Rows = driver.findElements(By.xpath("//table/tbody/tr"));
			int totalRows = Rows.size();
			System.out.println(" Total rows : "+totalRows);
			
			//count columns
			List<WebElement> Columns = driver.findElements(By.xpath("//table/tbody/tr[1]/td"));
			int totalColumns = Columns.size();
			System.out.println(" Total Columns : "+totalColumns);
			
			//Array to collect data re init
			matrix = new String [totalRows-1][totalColumns];
				
			//Extract data
			for(int i=1;i<=totalRows;i++){
				for(int j=1;j<=totalColumns;j++){
					WebElement dataCell = driver.findElement(By.xpath("//table/tbody/tr["+i+"]/td["+j+"]"));
					if(i>1) { matrix[i-2][j-1] = dataCell.getText(); 
					System.out.print(dataCell.getText() + "\t");
					System.out.print(matrix[i-2][j-1] + "\t");
					}
				}
				System.out.println();
			}
					 
		 }

		 @When("^the user is looking how many distinct items are there in the orders$")
		 public void the_user_is_looking_how_many_distinct_items_are_there_in_the_orders() {
			System.out.println("The user is looking for distinct items in the orders");
		 }

		 @Then("^the user sees the distinct items in the orders$")
		 public void the_user_sees_the_distinct_items_in_the_orders() {
			  
		    System.out.println("	distinct items in the orders are:   "); 
			String[] result = getUnique(matrix, 3);
			Stream.of(result).forEach(System.out::println);
		    driver.close();
		 }

		 
		 @When("^the user is looking if there are orders with quantity less than (\\d+) units$")
		 public void the_user_is_looking_if_there_are_orders_with_quantity_less_than_units(int arg1)  {
			System.out.println("The user is looking if there are orders with quantity less than " + arg1  + " units    ");
		 }
		 
		 
		 @Then("^the user sees the orders with quantity less than (\\d+) units$")
		 public void the_user_sees_the_orders_with_quantity_less_than_units(int arg1)  {
			System.out.println("		Orders with quantity less than " + arg1  + " units    ");	
			String[] result = getOrderQtyLessThan(matrix, 4, arg1);
			Stream.of(result).forEach(System.out::println);
		    driver.close();
		 }


		 @When("^the user is looking if there are orders for pencils with quantity less than (\\d+) units$")
		 public void the_user_is_looking_if_there_are_orders_for_pencils_with_quantity_less_than_units(int arg1) {
			 System.out.println("The user is looking if there are orders for pencils with quantity less than " + arg1  + " units    ");
		 }

		 
		 @Then("^the user sees the orders for pencils with quantity less than (\\d+) units$")
		 public void the_user_sees_the_orders_for_pencils_with_quantity_less_than_units(int arg1) {
			System.out.println("	Orders for pencils with quantity less than " + arg1  + " units    ");
			String[] result = getOrderQtyLessThan(matrix, 4, arg1);
//			Stream.of(result).forEach(System.out::println);
			for( int k=0; k < result.length; k++){
				if(result[k].contains("Pencil")) {
					System.out.println("		" + result[k]);	
				}
				
			}

		    driver.close();
		 }


		 @When("^the user is looking for the most expensive item in the list$")
		 public void the_user_is_looking_for_the_most_expensive_item_in_the_list() {
			 System.out.println("The user is looking for the most expensive item in the list:    ");

		 }

		 @Then("^the user sees the most expensive item in the list$")
		 public void the_user_sees_the_most_expensive_item_in_the_list() {
			String result = getMaxValueItem(matrix, 5);    //6th Column is price
			System.out.println("	Most expensive item from the test data is:    ");
			System.out.println("		" + result);

		 }
		 
		 
		 @When("^the user clicks the download excel link$")
		 public void the_user_clicks_the_download_excel_link()  {
			 driver.findElement(By.linkText("Excel sample data workbook")).click();
		 }

		 @Then("^the file is downloaded to the system$")
		 public void the_file_is_downloaded_to_the_system()  {
		 
			 File getLatestFile = getLatestFilefromDir(downloadPath);
			 String fileName = getLatestFile.getName();
			 Assert.assertThat(fileName, containsString(expfilename));
			 driver.quit();

		 }

	
		 /* Support functions */
		 
		 /*Get unique values from the list */
			public static String[] getUnique(String[][] matrix, int column) {
			    Set<String> set = new HashSet<>();
			    for (String[] row : matrix) {
			    	if(row.length >= column) {
			    		set.add(row[column]); //
			    	}
			    }

		    return set.toArray(new String[set.size()]);
			}
		 
			
			 /*Get orders with order quantity less than defined */
			public static String[] getOrderQtyLessThan(String[][] matrix, int column, int qty) {
				    Set<String> set = new HashSet<>();
				    for (String[] row : matrix) {
				    	if(row.length >= column) {
				    		if(qty > Integer.parseInt(row[column])) {
				    			set.add("Region: " + row[column-3] + " Rep: " + row[column-2] + " Item: " + row[column-1] + " Units: " + row[column] ); //
				    		}
				    	}
				    }

				    return set.toArray(new String[set.size()]);
				}
			
		
	
			/* Get the max valued item from the specified column */
			public static String getMaxValueItem(String[][] matrix, int column) {
				double assumedMax = 0;
				String assumedMaxItem = "None";
			    for (String[] row : matrix) {
			    	if(row.length >= column) {
//			    		assumedMax = Double.max(assumedMax, Double.parseDouble(row[column]));
			    		if(Double.parseDouble(row[column]) >assumedMax) {
			    			assumedMax = Double.parseDouble(row[column]);
			    			assumedMaxItem = row[column-2];
			    			
			    		};	
			    	}
			    }

			    return assumedMaxItem + " : priced at " + Double.toString(assumedMax);
			}	 
		 
		 /* Get the latest file from a specific directory*/
			private File getLatestFilefromDir(String dirPath){
			    File dir = new File(dirPath);
			    File[] files = dir.listFiles();
			    if (files == null || files.length == 0) {
			        return null;
			    }
			
			    File lastModifiedFile = files[0];
			    for (int i = 1; i < files.length; i++) {
			       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
			           lastModifiedFile = files[i];
			       }
			    }
			    return lastModifiedFile;
			}
			
	 
}

