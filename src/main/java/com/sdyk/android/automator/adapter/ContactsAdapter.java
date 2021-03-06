package com.sdyk.android.automator.adapter;

import com.sdyk.android.automator.AndroidDevice;
import com.sdyk.android.automator.util.ShellUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import one.rewind.util.FileUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ContactsAdapter extends Adapter {

	public ContactsAdapter(AndroidDevice androidDevice) {
		super(androidDevice);
	}

	/**
	 * 清空通讯录
	 * 该方法是利用adb命令中的clear，同时可以以应用程序的包名为参数，修改为重置app的方法
	 */
	public void clearContacts() {
		String commandStr = "adb -s " + udid + " shell pm clear com.android.providers.contacts";
		ShellUtil.exeCmd(commandStr);
	}

	/**
	 * 添加指定姓名和电话的单个联系人
	 * 通过+按钮，输入姓名和电话号码后返回
	 * @param name 姓名
	 * @param number 电话
	 * @throws InterruptedException 操作中断异常
	 */
	public void addContact(String name, String number) throws InterruptedException {

		WebElement addButton = driver.findElementByAccessibilityId("添加新联系人");

		// 点击添加新联系人
		addButton.click();

		Thread.sleep(500);
		driver.findElement(By.xpath("//android.widget.EditText[contains(@text,'姓名')]")).sendKeys(name);
		driver.findElement(By.xpath("//android.widget.EditText[contains(@text,'电话')]")).sendKeys(number);

		// 点击保存
		driver.findElementById("com.android.contacts:id/menu_save").click();
		Thread.sleep(1500);

		// 安卓机器的返回键
		//driver.pressKeyCode(4);
		driver.navigate().back();
		Thread.sleep(500);
	}

	/**
	 * 通过txt文件批量导入联系人
	 * 通过通讯录界面的+按钮，加入联系人后返回，循环添加
	 * @param filePath 通讯录文件
	 * @throws InterruptedException 中断异常
	 */
	public void addContactsFromFile(String filePath) throws InterruptedException {

		// adb 启动 contacts

		String src = FileUtil.readFileByLines(filePath);

		for (String contact : src.split("\\n|\\r\\n")) {

			String[] token = contact.split("\\t");

			WebElement addButton = driver.findElementByAccessibilityId("添加新联系人");
			addButton.click();//点击添加新联系人

			Thread.sleep(500);

			driver.findElement(By.xpath("//android.widget.EditText[contains(@text,'姓名')]")).sendKeys(token[0]);
			driver.findElement(By.xpath("//android.widget.EditText[contains(@text,'电话')]")).sendKeys(token[1]);

			Thread.sleep(500);
			driver.findElementById("com.android.contacts:id/menu_save").click();//点击保存

			Thread.sleep(500);
			//driver.pressKeyCode(4);//安卓机器的返回键

			driver.navigate().back();//安卓机器的返回键
			Thread.sleep(500);

		}
	}

	/**
	 * 删除指定姓名的联系人
	 *
	 * @param name 联系人姓名
	 * @throws InterruptedException 中断异常
	 */
	public void deleteOneContact(String name) throws InterruptedException {

		for (int i = 0; i < 3; i++) {
			List<WebElement> lis = driver.findElements(By.xpath("//android.widget.TextView[contains(@text,name)]"));

			if (lis.size() != 0) {

				driver.findElement(By.xpath("//android.widget.TextView[contains(@text,name)]")).click();
				Thread.sleep(700);

				driver.findElementByAccessibilityId("更多选项").click();
				Thread.sleep(700);

				driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'删除')]")).click();
				Thread.sleep(700);

				driver.findElement(By.xpath("//android.widget.Button[contains(@text,'删除'"));
				Thread.sleep(200);
			}

			TouchAction action = new TouchAction(driver).press(PointOption.point(700, 2360))
					.waitAction()
					.moveTo(PointOption.point(700, 540))
					.release();

			action.perform();

			Thread.sleep(300);
		}
	}
}
