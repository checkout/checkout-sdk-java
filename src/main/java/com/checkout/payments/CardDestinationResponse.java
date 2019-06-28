package com.checkout.payments;

public class CardDestinationResponse{
	private String id;
	private String type;
	private String bin;
	private String last4;
	private int expiryMonth;
	private int expiryYear;
	private String scheme;
	private String cardCategory;
	private String cardType;
	private String issuer;
	private String issuerCountry;
	private String productType;
	private String productId;
	private String fingerprint;

	public void setLast4(String last4){
		this.last4 = last4;
	}

	public String getLast4(){
		return last4;
	}

	public void setScheme(String scheme){
		this.scheme = scheme;
	}

	public String getScheme(){
		return scheme;
	}

	public void setBin(String bin){
		this.bin = bin;
	}

	public String getBin(){
		return bin;
	}

	public void setCardCategory(String cardCategory){
		this.cardCategory = cardCategory;
	}

	public String getCardCategory(){
		return cardCategory;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setCardType(String cardType){
		this.cardType = cardType;
	}

	public String getCardType(){
		return cardType;
	}

	public void setExpiryYear(int expiryYear){
		this.expiryYear = expiryYear;
	}

	public int getExpiryYear(){
		return expiryYear;
	}

	public void setIssuer(String issuer){
		this.issuer = issuer;
	}

	public String getIssuer(){
		return issuer;
	}

	public void setIssuerCountry(String issuerCountry){
		this.issuerCountry = issuerCountry;
	}

	public String getIssuerCountry(){
		return issuerCountry;
	}

	public void setExpiryMonth(int expiryMonth){
		this.expiryMonth = expiryMonth;
	}

	public int getExpiryMonth(){
		return expiryMonth;
	}

	public void setProductType(String productType){
		this.productType = productType;
	}

	public String getProductType(){
		return productType;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setFingerprint(String fingerprint){
		this.fingerprint = fingerprint;
	}

	public String getFingerprint(){
		return fingerprint;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

}
