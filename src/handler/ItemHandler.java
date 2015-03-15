package handler;

import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import data.Inventory;

public class ItemHandler extends DefaultHandler {
private ArrayList<Inventory> inventoryList;
private String curTag = null;
private String curValue = null;
private Inventory inventory;
	
	public ArrayList<Inventory> getInventoryList() {
	return inventoryList;
	}
	
	public void startDocument() {
		inventoryList = new ArrayList<Inventory>();
	}
	//called when a new tag element is found
	public void startElement(String uri, String localName,
	String qualifiedName, Attributes attrs)
	throws SAXException{
		curTag = qualifiedName;
		if (curTag.equals("inventory")) {
			inventory = new Inventory();
		}
	}
	//called when character data is found inside a tagged element
	public void characters(char charAry[], int start, int length)
	throws SAXException
	{
		curValue = new String(charAry,start,length);
	}
	//called when a tagged element ends
	public void endElement(String uri, String localName,String qualifiedName)throws SAXException{
		String endTag = qualifiedName;
		if (endTag.equals("inventory")) {
			inventoryList.add(inventory);
			inventory=null;
		}
		else if (endTag.equals("code")) {
			inventory.setItemCode(curValue);
		}else if (endTag.equals("itemid")) {
			inventory.setItemId(Integer.parseInt(curValue));
		}else if (endTag.equals("itemquantityid")) {
			inventory.setItemQuantityId(Integer.parseInt(curValue));
		}else if (endTag.equals("name")) {
			inventory.setItemName(curValue);
		} else if (endTag.equals("description")) {
			inventory.setDescription(curValue);
		} else if (endTag.equals("type")) {
			inventory.setItemType(curValue);
		} else if (endTag.equals("upccode")) {
			System.out.println("UPC Code: "+curValue);
			inventory.setUpcCode(curValue);
		} else if (endTag.equals("unitprice")) {
			inventory.setUnitPrice(Double.parseDouble(curValue));
		} else if (endTag.equals("tax")) {
			inventory.setTax(Double.parseDouble(curValue));
		} else if (endTag.equals("uom")) {
			inventory.setUom(curValue);
		} else if (endTag.equals("quantity")) {
			inventory.setQuantity(Integer.parseInt(curValue));
		}
		else if (endTag.equals("totalprice")) {
			inventory.setTotalPrice(Double.parseDouble(curValue));
		}
	}
}