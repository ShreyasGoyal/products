import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Checkout {
	
	String product1 = "atv";
	String product2 = "vga";
	String product3 = "mbp";
	String product4 = "ipd";
	
	double product1Price = 109.50;
	double product2Price = 30.00;
	double product3Price = 1399.99;
	double product4Price = 549.99;
	double discountedP4price = 499.99;
	
	int mbpCount;
	int vgaCount;
	
	private Map <String, Double> normalTotal = new HashMap<String, Double>();
	double discountedTotal = 0;
	
	private Map <String, Double> priceMapping = new HashMap<String, Double>();
	
	ArrayList<String> scannedProducts;
	Map<String, Integer> prodCount;
	
	public Checkout() {
		scannedProducts=new ArrayList<String>();
		prodCount = new HashMap<String, Integer>();
		insertPriceMap();
		mbpCount = 0 ;
		vgaCount = 0 ;
	}
	
	public void total() {
		double total;
		for(String key2 : prodCount.keySet()) {
			total = priceMapping.get(key2) * prodCount.get(key2);
			normalTotal.put(key2, total);
		}
		discountApply(scannedProducts, normalTotal);
	}
	
	public void discountApply(ArrayList<String> scannedProds, Map<String, Double> normalTotal) {
		for(String key : prodCount.keySet()) {
			if(key.equals("atv")) {
				if((prodCount.get(key))/3 >= 1){
					normalTotal.put(key, priceMapping.get(key) * (prodCount.get(key)-prodCount.get(key)/3));
				}
			}
			else if(key.equals("ipd")) {
				if(prodCount.get(key)>4) {
					normalTotal.put(key, prodCount.get(key)*discountedP4price);
				}
			}
		}
		
		for(String key1 : prodCount.keySet()) {
			if(key1.equals("mbp")) {
				mbpCount = prodCount.get(key1);
			}
			else if(key1.equals("vga")) {
				vgaCount = prodCount.get(key1);
			}
		}
		
		if(mbpCount>=1) {
			mbp_vga_scheme();
		}
		
		System.out.print("SKUs Scanned: ");
		int j=0;
		for(j=0; j<scannedProducts.size(); j++) {
			System.out.print(scannedProducts.get(j));
			if(j !=scannedProducts.size()-1) {
				System.out.print(", ");
			}
		}
		double total=0;
		for(double value : normalTotal.values())
		{
			total+=value;
		}
		System.out.println();
		System.out.println("Total expected: " + "$"+ total);
		
//		for(String key1 : normalTotal.keySet()) {
//			System.out.println(key1);
//		}
	}
	
	public void mbp_vga_scheme() {
		if(mbpCount>=vgaCount && vgaCount!=0) {
			normalTotal.put("vga", 0.0);
			for(int i=0; i<mbpCount-vgaCount; i++) {
				scannedProducts.add("vga");
			}
		}
		else if(mbpCount<vgaCount) {
			normalTotal.put("vga", (vgaCount-mbpCount)*priceMapping.get("vga"));
		}
		
		if(mbpCount>1 && vgaCount==0) {
			for(int i=0; i<mbpCount; i++) {
				scannedProducts.add("vga");
			}
		}
	}
	
	public void scan(String productName) {
		scannedProducts.add(productName);
		if(!prodCount.containsKey(productName))
	    {
	        prodCount.put(productName, 1);
	    }
		else
	    {
	        int currentCount = prodCount.get(productName);
	        prodCount.put(productName, currentCount+1);
	    }
	}
	
	public void insertPriceMap() {
		priceMapping.put(product1, product1Price);
		priceMapping.put(product2, product2Price);
		priceMapping.put(product3, product3Price);
		priceMapping.put(product4, product4Price);
	}
}
