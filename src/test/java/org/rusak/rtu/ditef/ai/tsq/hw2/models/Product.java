package org.rusak.rtu.ditef.ai.tsq.hw2.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//https://github.com/krmali/SeleniumTest/blob/0a00eb9b4f2f03f90dfce4af0429e48ab1d38e2b/src/main/java/com/AutoPractice_Selenium/Product.java
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Product{
	public int index;
	public String name;
	public int quantity;
	public String color;
	public String size;

	public String getAttributeValueByType(String type){
		return switch (type) {
			case "color" -> this.color;
			case "size" -> this.size;
			default -> null;
		};
	}
}