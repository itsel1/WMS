package com.example.temp.api.cafe24.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cafe24OrdersItemsVo {
	
	public Cafe24OrdersItemsVo() {
		mallId = "";
		shopNo = "";
		itemNo = "";
		orderItemCode = "";
		variantCode = "";
		productNo = "";
		productCode = "";
		customProductCode = "";
		customVariantCode = "";
		engProductName = "";
		optionId = "";
		optionValue = "";
		optionValueDefault = "";
		additionalOptionValue = "";
		additionalOptionValues = "";
		productName = "";
		productNameDefault = "";
		productPrice = "";
		optionPrice = "";
		additionalDiscountPrice = "";
		couponDiscountPrice = "";
		appItemDiscountAmount = "";
		actualPaymentAmount = "";
		quantity = "";
		productTaxType = "";
		taxAmount = "";
		supplierProductName = "";
		supplierTransactionType = "";
		supplierId = "";
		supplierName = "";
		trackingNo = "";
		shippingCode = "";
		claimCode = "";
		claimReasonType = "";
		claimReason = "";
		refundBankName = "";
		refundBankAccountNo = "";
		refundBankAccountHolder = "";
		postExpressFlag = "";
		orderStatus = "";
		requestUndone = "";
		orderStatusAdditionalInfo = "";
		claimQuantity = "";
		statusCode = "";
		statusText = "";
		openMarketStatus = "";
		bundledShippingType = "";
		shippingCompanyId = "";
		shippingCompanyName = "";
		shippingCompanyCode = "";
		productBundle = "";
		productBundleNo = "";
		productBundleName = "";
		productBundleNameDefault = "";
		productBundleType = "";
		wasProductBundle = "";
		originalBundleItemNo = "";
		naverPayOrderId = "";
		naverPayClaimStatus = "";
		individualShippingFee = "";
		shippingFeeType = "";
		shippingFeeTypeText = "";
		shippingPaymentOption = "";
		paymentInfoId = "";
		originalItemNo = "";
		storePickup = "";
		orderedDate = "";
		shippedDate = "";
		deliveredDate = "";
		cancelDate = "";
		returnConfirmedDate = "";
		returnRequestDate = "";
		returnCollectedDate = "";
		cancelRequestDate = "";
		refundDate = "";
		exchangeRequestDate = "";
		exchangeDate = "";
		productMaterial = "";
		productMaterialEng = "";
		clothFabric = "";
		productWeight = "";
		volumeSize = "";
		volumeSizeWeight = "";
		clearanceCategory = "";
		clearanceCategoryInfo = "";
		clearanceCategoryCode = "";
		hsCode = "";
		onePlusNEvent = "";
		originPlace = "";
		originPlaceNo = "";
		madeInCode = "";
		originPlaceValue = "";
		gift = "";
		itemGrantingGift = "";
		subscription = "";
		productBundleList = "";
		marketCancelRequest = "";
		marketCancelRequestQuantity = "";
		marketFailReason = "";
		marketFailReasonGuide = "";
		marketCustomVariantCode = "";
		optionType = "";
		options = "";
		marketDiscountAmount = "";
		orderId = "";
		claimType = "";
		claimStatus = "";
		
		nno = "";
		subNo = "";
		itemDetail = "";					//상품명
		nativeItemDetail = "";			//대상국가 원어 상품명
		itemMeterial = "";				//상품재질
		makeCntry = "";					//제조 국가
		itemCnt  = "";						//상품 개수
		cusItemCode = "";					//상품코드
		brand = "";						//브랜드
		itemImgUrl = ""; 					//상품 이미지 URL
		unitValue = "";					//상품 단가
		itemExplan = "";					//상품설명
		userWta = "";						//실무게
		wtUnit = "";						//무게 단위
	}
	
	private String shopNo;
	private String itemNo;
	private String orderItemCode;
	private String variantCode;
	private String productNo;
	private String productCode;
	private String customProductCode;
	private String customVariantCode;
	private String engProductName;
	private String optionId;
	private String optionValue;
	private String optionValueDefault;
	private String additionalOptionValue;
	private String additionalOptionValues;
	private String productName;
	private String productNameDefault;
	private String productPrice;
	private String optionPrice;
	private String additionalDiscountPrice;
	private String couponDiscountPrice;
	private String appItemDiscountAmount;
	private String actualPaymentAmount;
	private String quantity;
	private String productTaxType;
	private String taxAmount;
	private String supplierProductName;
	private String supplierTransactionType;
	private String supplierId;
	private String supplierName;
	private String trackingNo;
	private String shippingCode;
	private String claimCode;
	private String claimReasonType;
	private String claimReason;
	private String refundBankName;
	private String refundBankAccountNo;
	private String refundBankAccountHolder;
	private String postExpressFlag;
	private String orderStatus;
	private String requestUndone;
	private String orderStatusAdditionalInfo;
	private String claimQuantity;
	private String statusCode;
	private String statusText;
	private String openMarketStatus;
	private String bundledShippingType;
	private String shippingCompanyId;
	private String shippingCompanyName;
	private String shippingCompanyCode;
	private String productBundle;
	private String productBundleNo;
	private String productBundleName;
	private String productBundleNameDefault;
	private String productBundleType;
	private String wasProductBundle;
	private String originalBundleItemNo;
	private String naverPayOrderId;
	private String naverPayClaimStatus;
	private String individualShippingFee;
	private String shippingFeeType;
	private String shippingFeeTypeText;
	private String shippingPaymentOption;
	private String paymentInfoId;
	private String originalItemNo;
	private String storePickup;
	private String orderedDate;
	private String shippedDate;
	private String deliveredDate;
	private String cancelDate;
	private String returnConfirmedDate;
	private String returnRequestDate;
	private String returnCollectedDate;
	private String cancelRequestDate;
	private String refundDate;
	private String exchangeRequestDate;
	private String exchangeDate;
	private String productMaterial;
	private String productMaterialEng;
	private String clothFabric;
	private String productWeight;
	private String volumeSize;
	private String volumeSizeWeight;
	private String clearanceCategory;
	private String clearanceCategoryInfo;
	private String clearanceCategoryCode;
	private String hsCode;
	private String onePlusNEvent;
	private String originPlace;
	private String originPlaceNo;
	private String madeInCode;
	private String originPlaceValue;
	private String gift;
	private String itemGrantingGift;
	private String subscription;
	private String productBundleList;
	private String marketCancelRequest;
	private String marketCancelRequestQuantity;
	private String marketFailReason;
	private String marketFailReasonGuide;
	private String marketCustomVariantCode;
	private String optionType;
	private String options;
	private String marketDiscountAmount;
	private String orderId;
	private String claimType;
	private String claimStatus;
	
	/*추가 컬럼*/
	private String mallId;
	private String nno ;
	private String subNo;
	private String userId;
	private String itemDetail;					//상품명
	private String nativeItemDetail;			//대상국가 원어 상품명
	private String itemMeterial;				//상품재질
	private String makeCntry;					//제조 국가
	private String itemCnt;						//상품 개수
	private String cusItemCode;					//상품코드
	private String brand;						//브랜드
	private String itemImgUrl; 					//상품 이미지 URL
	private String unitValue;					//상품 단가
	private String itemExplan;					//상품설명
	private String userWta;						//실무게
	private String wtUnit;						//무게 단위
	
}
