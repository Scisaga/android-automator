package com.sdyk.android.automator.adapter;

import com.sdyk.android.automator.AndroidDevice;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

/**
 * 钉钉的自动化操作
 */
public class DingdingAdapter extends Adapter {

	/**
	 *
	 * @param androidDevice
	 */
	public DingdingAdapter(AndroidDevice androidDevice) {
		super(androidDevice);
	}

	/**
	 * 导出所有钉钉的联系人，半自动
	 *
	 * @throws InterruptedException 中断异常
	 */
	public void getAllDingdingContacts() throws InterruptedException {

		driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'通讯录')]")).click();
		Thread.sleep(500);

		driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'组织架构')]")).click();
		Thread.sleep(200);

		for (int j = 0; j < 50; j++) {

			List<WebElement> lis = driver.findElementsById("com.alibaba.android.rimet:id/item_contact");

			for (int i = 0; i < lis.size(); i++) {
				lis.get(i).click();
				Thread.sleep(300);
				System.out.println(driver.findElementById("com.alibaba.android.rimet:id/cell_subTitle"));
				Thread.sleep(300);
				driver.findElementByAccessibilityId("返回");
			}

			TouchAction action = new TouchAction(driver)
					.press(PointOption.point(700, 2360))
					.waitAction()
					.moveTo(PointOption.point(700, 200))
					.release();

			action.perform();

			Thread.sleep(400);
		}
	}

	/**
	 * 获取企业信息
	 * @throws InterruptedException 中断异常
	 */
	public void getDingdingCompanyContact() throws InterruptedException {

		driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'通讯录')]")).click();
		Thread.sleep(500);

		driver.findElementByAccessibilityId("企业广场").click();
		Thread.sleep(10000);

		System.out.println(driver.getContext());
	}
}
