package br.ce.wcaquino.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.remote.DesiredCapabilities;

public class TasksTest {

	public WebDriver acessarAplicacao() throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("chrome");
		cap.setPlatform(Platform.WINDOWS);
		
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		options.merge(cap);
		String huburl = "http://192.168.0.111:4444";
		
		WebDriver driver = new RemoteWebDriver(new URL(huburl), options);
		driver.navigate().to("http://192.168.0.111:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
		  driver.findElement(By.id("addTodo")).click();
		  driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
		  driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
	      driver.findElement(By.id("saveButton")).click();
		  String message = driver.findElement(By.id("message")).getText();
		  Assert.assertEquals("Success!", message);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
		  driver.findElement(By.id("addTodo")).click();
		  driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
		  driver.findElement(By.id("saveButton")).click();
		  String message = driver.findElement(By.id("message")).getText();
		  Assert.assertEquals("Fill the task description", message);
		} finally {
		  driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
		  driver.findElement(By.id("addTodo")).click();
		  driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
		  driver.findElement(By.id("saveButton")).click();
		  String message = driver.findElement(By.id("message")).getText();
		  Assert.assertEquals("Fill the due date", message);
		} finally {
		  driver.quit();
		}
	}
	
	@Test
	public void deveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
		  driver.findElement(By.id("addTodo")).click();
		  driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
		  driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");
	      driver.findElement(By.id("saveButton")).click();
		  String message = driver.findElement(By.id("message")).getText();
		  Assert.assertEquals("Due date must not be in past", message);
		} finally {
			driver.quit();
		}
	}
}
