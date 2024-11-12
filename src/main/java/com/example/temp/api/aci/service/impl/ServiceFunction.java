package com.example.temp.api.aci.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.common.service.ComnService;
import com.example.temp.manager.mapper.ManagerMapper;
import com.example.temp.manager.vo.OrderItemExpOptionVO;
import com.example.temp.manager.vo.OrderItemOptionVO;
import com.example.temp.manager.vo.OrderListExpOptionVO;
import com.example.temp.manager.vo.OrderListOptionVO;
import com.example.temp.manager.vo.ProcedureVO;
import com.example.temp.security.SecurityKeyVO;

import net.aramex.ws.ShippingAPI.v1.ShipmentCreationResponse;

@Service
public class ServiceFunction {
	
	@Autowired
	ManagerMapper mgrMapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	ApiV1ServiceImpl apiV1ServiceImpl;
	
	String YYYYMMDDHHMISS = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[0-9]|1[0-9]|2[0-3])([0-5][0-9])([0-5][0-9])";
	String YYYYMMDD = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])";
	SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
	private SecurityKeyVO originKey = new SecurityKeyVO();
	
	public Map<String, Object> orderJsonColumnChk(JSONObject jObject, String transCodeByRemark) throws Exception {
		OrderListOptionVO expOption = new OrderListOptionVO();
		Map<String, Object> temp = new HashMap<String, Object>();
		expOption = mgrMapper.selectOrderListOption(transCodeByRemark, jObject.getString("Arrival_Nation"));
		if(expOption == null) {
			expOption = mgrMapper.selectOrderListOption(transCodeByRemark, transCodeByRemark);
		}
		String rstVal = "";
		
		if(expOption.getOrgStationYn().equals("on")) 
			if(!jObject.has("Departure_Station"))
				rstVal = rstVal+"Departure_Station, ";

		if(expOption.getDstnNationYn().equals("on"))
			if(!jObject.has("Arrival_Nation"))
				rstVal = rstVal+"Arrival_Nation, ";

		if(expOption.getTransCodeYn().equals("on"))
			if(!jObject.has("Transfer_Company_Code"))
				rstVal = rstVal+"Transfer_Company_Code, ";

		if(expOption.getOrderDateYn().equals("on"))
			if(!jObject.has("Order_Date"))
				rstVal = rstVal+"Order_Date, ";
		
		if(expOption.getShipperNameYn().equals("on"))
			if(!jObject.has("Shipper_name"))
				rstVal = rstVal+"Shipper_name, ";
		
		if(expOption.getShipperCntryYn().equals("on"))
			if(!jObject.has("Shipper_Country"))
				rstVal = rstVal+"Shipper_Country, ";
		
		if(expOption.getShipperStateYn().equals("on"))
			if(!jObject.has("Shipper_State"))
				rstVal = rstVal+"Shipper_State, ";
		
		if(expOption.getShipperCityYn().equals("on"))
			if(!jObject.has("Shipper_City"))
				rstVal = rstVal+"Shipper_City, ";

		if(expOption.getShipperZipYn().equals("on"))
			if(!jObject.has("Shipper_Zip"))
				rstVal = rstVal+"Shipper_Zip, ";

		if(expOption.getShipperAddrYn().equals("on"))
			if(!jObject.has("Shipper_Address"))
				rstVal = rstVal+"Shipper_Address, ";
		
		if(expOption.getShipperAddrDetailYn().equals("on"))
			if(!jObject.has("Shipper_Address_Detail"))
				rstVal = rstVal+"Shipper_Address_Detail, ";
		
//			case "Shipper_Tel":
//				if(expOption.getShipperTelYn().equals("on"))
//					result = true;
//				break;
//			case "Shipper_Hp":
//				if(expOption.getShipperHpYn().equals("on"))
//					result = true;
//				break;
		if(expOption.getUserEmailYn().equals("on"))
			if(!jObject.has("Shipper_Email"))
				rstVal = rstVal+"Shipper_Email, ";

		if(expOption.getCneeNameYn().equals("on"))
			if(!jObject.has("Receiver_Name"))
				rstVal = rstVal+"Receiver_Name, ";

		if(expOption.getCneeCntryYn().equals("on"))
			if(!jObject.has("Receiver_County"))
				rstVal = rstVal+"Receiver_County, ";

		if(expOption.getCneeStateYn().equals("on"))
			if(!jObject.has("Receiver_State"))
				rstVal = rstVal+"Receiver_State, ";
		
		if(expOption.getCneeCityYn().equals("on"))
			if(!jObject.has("Receiver_City"))
				rstVal = rstVal+"Receiver_City, ";

		if(expOption.getCneeDistrictYn().equals("on"))
			if(!jObject.has("Receiver_District"))
				rstVal = rstVal+"Receiver_District, ";
		
		if(expOption.getCneeZipYn().equals("on"))
			if(!jObject.has("Receiver_Zip"))
				rstVal = rstVal+"Receiver_Zip, ";

		if(expOption.getCneeAddrYn().equals("on"))
			if(!jObject.has("Receiver_Address"))
				rstVal = rstVal+"Receiver_Address, ";

		if(expOption.getCneeAddrDetailYn().equals("on"))
			if(!jObject.has("Receiver_Address_Detail"))
				rstVal = rstVal+"Receiver_Address_Detail, ";

		if(expOption.getCneeTelYn().equals("on"))
			if(!jObject.has("Receiver_Tel"))
				rstVal = rstVal+"Receiver_Tel, ";

		if(expOption.getCneeHpYn().equals("on"))
			if(!jObject.has("Receiver_Hp"))
				rstVal = rstVal+"Receiver_Hp, ";

		if(expOption.getCneeEmailYn().equals("on"))
			if(!jObject.has("Receiver_Email"))
				rstVal = rstVal+"Receiver_Email, ";

		if(expOption.getNativeCneeNameYn().equals("on"))
			if(!jObject.has("Native_Receiver_Name"))
				rstVal = rstVal+"Native_Receiver_Name, ";

		if(expOption.getNativeCneeAddrYn().equals("on"))
			if(!jObject.has("Native_Receiver_Address"))
				rstVal = rstVal+"Native_Receiver_Address, ";

		if(expOption.getNativeCneeAddrDetailYn().equals("on"))
			if(!jObject.has("Native_Receiver_Address_Detail"))
				rstVal = rstVal+"Native_Receiver_Address_Detail, ";

		if(expOption.getBoxCntYn().equals("on"))
			if(!jObject.has("Box_Count"))
				rstVal = rstVal+"Box_Count, ";

		if(expOption.getUserWtaYn().equals("on"))
			if(!jObject.has("Actual_Weight"))
				rstVal = rstVal+"Actual_Weight, ";

		if(expOption.getUserWtcYn().equals("on"))
			if(!jObject.has("Volume_Weight"))
				rstVal = rstVal+"Volume_Weight, ";

		if(expOption.getUserLengthYn().equals("on"))
			if(!jObject.has("Volume_Length"))
				rstVal = rstVal+"Volume_Length, ";

		if(expOption.getUserWidthYn().equals("on"))
			if(!jObject.has("Volume_Width"))
				rstVal = rstVal+"Volume_Width, ";

		if(expOption.getUserHeightYn().equals("on"))
			if(!jObject.has("Volume_Height"))
				rstVal = rstVal+"Volume_Height, ";

		if(expOption.getCustomsNoYn().equals("on"))
			if(!jObject.has("Custom_Clearance_ID"))
				rstVal = rstVal+"Custom_Clearance_ID, ";

		if(expOption.getBuySiteYn().equals("on")) {
			if(!jObject.has("BuySite"))
				rstVal = rstVal+"BuySite, ";
		}
			

		if(expOption.getDimUnitYn().equals("on"))
			if(!jObject.has("Size_Unit"))
				rstVal = rstVal+"Size_Unit, ";

		if(expOption.getWtUnitYn().equals("on"))
			if(!jObject.has("Weight_Unit"))
				rstVal = rstVal+"Weight_Unit, ";

		if(expOption.getGetBuyYn().equals("on"))
			if(!jObject.has("Get_Buy"))
				rstVal = rstVal+"Get_Buy, ";

		if(expOption.getMallTypeYn().equals("on"))
			if(!jObject.has("Mall_Type"))
				rstVal = rstVal+"Mall_Type, ";

		if(expOption.getWhReqMsgYn().equals("on"))
			if(!jObject.has("Warehouse_Msg"))
				rstVal = rstVal+"Warehouse_Msg, ";

		if(expOption.getDlvReqMsgYn().equals("on"))
			if(!jObject.has("Delivery_Msg"))
				rstVal = rstVal+"Delivery_Msg, ";

		if(expOption.getShipperReferenceYn().equals("on"))
			if(!jObject.has("Shipper_Reference"))
				rstVal = rstVal+"Shipper_Reference, ";

		if(expOption.getCneeReference1Yn().equals("on"))
			if(!jObject.has("Receiver_Reference1"))
				rstVal = rstVal+"Receiver_Reference1, ";

		if(expOption.getCneeReference2Yn().equals("on"))
			if(!jObject.has("Receiver_Reference2"))
				rstVal = rstVal+"Receiver_Reference2, ";

		if(expOption.getFoodYn().equals("on"))
			if(!jObject.has("Food"))
				rstVal = rstVal+"Food, ";

		if(expOption.getNationalIdDateYn().equals("on"))
			if(!jObject.has("National_Id_Date"))
				rstVal = rstVal+"National_Id_Date, ";

		if(expOption.getNationalIdAuthorityYn().equals("on"))
			if(!jObject.has("National_Id_Authority"))
				rstVal = rstVal+"National_Id_Authority, ";

		if(expOption.getCneeBirthYn().equals("on"))
			if(!jObject.has("Cnee_Birth"))
				rstVal = rstVal+"Cnee_Birth, ";

		if(expOption.getTaxNoYn().equals("on"))
			if(!jObject.has("Tax_No"))
				rstVal = rstVal+"Tax_No, ";
		
		/*
		if(!jObject.has("Exp_Type")) {
			rstVal = rstVal+"Exp_Type, ";
		}
		*/
		
		if(!jObject.has("Exp_Licence_YN")) {
			rstVal = rstVal+"Exp_Licence_YN, ";
		}else {
			
			if (jObject.getString("Exp_Licence_YN").equals("N")) {
				rstVal = "";
			} else if (jObject.getString("Exp_Licence_YN").equals("S")) {
				if(!jObject.has("Exp_Business_Name")) {
					rstVal = rstVal+"Exp_Business_Name, ";
				}
				if(!jObject.has("Exp_Business_Num")) {
					rstVal = rstVal+"Exp_Business_Num, ";
				}
				if(!jObject.has("Exp_Shipper_Code")) {
					rstVal = rstVal+"Exp_Shipper_Code, ";
				}
			} else if (jObject.getString("Exp_Licence_YN").equals("Y")) {
				if(!jObject.has("Exp_Business_Name")) {
					rstVal = rstVal+"Exp_Business_Name, ";
				}
				if(!jObject.has("Exp_Business_Num")) {
					rstVal = rstVal+"Exp_Business_Num, ";
				}
				if(!jObject.has("Exp_Shipper_Code")) {
					rstVal = rstVal+"Exp_Shipper_Code, ";
				}
				if(!jObject.has("Exp_No")) {
					if (!jObject.has("Agency_Business_Name")) {
						rstVal = rstVal+"Agency_Business_Name, ";
					}
				}
			}
		}
		
		
		if (expOption.getTaxIdYn().equals("on")) {
			if (!jObject.has("TaxNumber")) {
				rstVal = rstVal+"TaxNumber, ";
			}
		}
		
		if (expOption.getEoriNoYn().equals("on")) {
			if (!jObject.has("EoriNumber")) {
				rstVal = rstVal+"EoriNumber, ";
			}
		}
		
		if (expOption.getDeclTypeYn().equals("on")) {
			if (!jObject.has("DeclarationType")) {
				rstVal = rstVal+"DeclarationType, ";
			}
		}
		

		if(rstVal.length() != 0) {
			temp.put("Error_Msg","JSON INFO "+rstVal+"is required.");
		}
		
		return temp;
	}
	
	public Map<String, Object> orderJsonValueChk (JSONObject jObject, int jsonIndex, String transCodeByRemark) throws Exception{
		OrderListOptionVO expOption = new OrderListOptionVO();
		expOption = mgrMapper.selectOrderListOption(transCodeByRemark, jObject.getString("Arrival_Nation"));
		if(expOption == null) {
			expOption = mgrMapper.selectOrderListOption(transCodeByRemark, transCodeByRemark);
		}
		Map<String, Object> temp = new HashMap<String, Object>();
		Iterator iter = jObject.keySet().iterator();
		String json = "";
		while (iter.hasNext()) {
			String key = (String) iter.next();
			if("".equals(jObject.get(key)) || jObject.get(key) == null) {
				if(orderJsonExistence(key,expOption)) {
					if(!json.equals("")) {
						json  = json   + ", ";
					}
					json = json + key;
				}
			}
		}
		
		if(json.length() != 0) {
			temp.put("Error_Msg",json+" IS NULL OR EMPTY");
		}
		return temp;
	}
	

	public boolean orderJsonExistence(String key, OrderListOptionVO expOption) {
		boolean result = false;
		
		switch (key) {
			case "UserId":
				result = true;
			break;
			
			case "Departure_Station":
				if(expOption.getOrgStationYn().equals("on"))
					result = true;
				break;
	
			case "Arrival_Nation":
				if(expOption.getDstnNationYn().equals("on"))
					result = true;
				break;
	
			case "":
				break;
	
			case "Transfer_Company_Code":
				if(expOption.getTransCodeYn().equals("on"))
					result = true;
				break;
	
			case "Order_Date":
				if(expOption.getOrderDateYn().equals("on"))
					result = true;
				break;
				
			case "Shipper_name":
				if(expOption.getShipperNameYn().equals("on"))
					result = true;
				break;
				
			case "Shipper_Country":
				if(expOption.getShipperCntryYn().equals("on"))
					result = true;
				break;
				
			case "Shipper_State":
				if(expOption.getShipperStateYn().equals("on"))
					result = true;
				break;
			case "Shipper_City":
				if(expOption.getShipperCityYn().equals("on"))
					result = true;
				break;
			case "Shipper_Zip":
				if(expOption.getShipperZipYn().equals("on"))
					result = true;
				break;
			case "Shipper_Address":
				if(expOption.getShipperAddrYn().equals("on"))
					result = true;
				break;
			case "Shipper_Address_Detail":
				if(expOption.getShipperAddrDetailYn().equals("on"))
					result = true;
				break;
//			case "Shipper_Tel":
//				if(expOption.getShipperTelYn().equals("on"))
//					result = true;
//				break;
//			case "Shipper_Hp":
//				if(expOption.getShipperHpYn().equals("on"))
//					result = true;
//				break;
			case "Shipper_Email":
				if(expOption.getUserEmailYn().equals("on"))
					result = true;
				break;	
			case "Receiver_Name":
				if(expOption.getCneeNameYn().equals("on"))
					result = true;
				break;
			case "Receiver_County":
				if(expOption.getCneeCntryYn().equals("on"))
					result = true;
				break;
			case "Receiver_State":
				if(expOption.getCneeStateYn().equals("on"))
					result = true;
				break;
			case "Receiver_City":
				if(expOption.getCneeCityYn().equals("on"))
					result = true;
				break;
			case "Receiver_District":
				if(expOption.getCneeDistrictYn().equals("on"))
					result = true;
				break;
			case "Receiver_Zip":
				if(expOption.getCneeZipYn().equals("on"))
					result = true;
				break;
			case "Receiver_Address":
				if(expOption.getCneeAddrYn().equals("on"))
					result = true;
				break;
			case "Receiver_Address_Detail":
				if(expOption.getCneeAddrDetailYn().equals("on"))
					result = true;
				break;
			case "Receiver_Tel":
				if(expOption.getCneeTelYn().equals("on"))
					result = true;
				break;
			case "Receiver_Hp":
				if(expOption.getCneeHpYn().equals("on"))
					result = true;
				break;
			case "Receiver_Email":
				if(expOption.getCneeEmailYn().equals("on"))
					result = true;
				break;
			case "Native_Receiver_Name":
				if(expOption.getNativeCneeNameYn().equals("on"))
					result = true;
				break;
	
			case "Native_Receiver_Address":
				if(expOption.getNativeCneeAddrYn().equals("on"))
					result = true;
				break;
	
			case "Native_Receiver_Address_Detail":
				if(expOption.getNativeCneeAddrDetailYn().equals("on"))
					result = true;
				break;
			case "Box_Count":
				if(expOption.getBoxCntYn().equals("on"))
					result = true;
				break;
			case "Actual_Weight":
				if(expOption.getUserWtaYn().equals("on"))
					result = true;
				break;
			case "Volume_Weight":
				if(expOption.getUserWtcYn().equals("on"))
					result = true;
				break;
			case "Volume_Length":
				if(expOption.getUserLengthYn().equals("on"))
					result = true;
				break;
			case "Volume_Width":
				if(expOption.getUserWidthYn().equals("on"))
					result = true;
				break;
			case "Volume_Height":
				if(expOption.getUserHeightYn().equals("on"))
					result = true;
				break;
			case "Custom_Clearance_ID":
				if(expOption.getCustomsNoYn().equals("on"))
					result = true;
				break;
			case "BuySite":
				if(expOption.getBuySiteYn().equals("on"))
					result = true;
				break;
			case "Size_Unit":
				if(expOption.getDimUnitYn().equals("on"))
					result = true;
				break;
			case "Weight_Unit":
				if(expOption.getWtUnitYn().equals("on"))
					result = true;
				break;
			case "Get_Buy":
				if(expOption.getGetBuyYn().equals("on"))
					result = true;
				break;
			case "Mall_Type":
				if(expOption.getMallTypeYn().equals("on"))
					result = true;
				break;
			case "Warehouse_Msg":
				if(expOption.getWhReqMsgYn().equals("on"))
					result = true;
				break;
			case "Delivery_Msg":
				if(expOption.getDlvReqMsgYn().equals("on"))
					result = true;
				break;	
				
			case "Shipper_Reference":
				if(expOption.getShipperReferenceYn().equals("on"))
					result = true;
				break;
				
			case "Cnee_Reference1":
				if(expOption.getCneeReference1Yn().equals("on"))
					result = true;
				break;
				
			case "Cnee_Reference2":
				if(expOption.getCneeReference2Yn().equals("on"))
					result = true;
				break;
				
			case "Food":
				if(expOption.getFoodYn().equals("on"))
					result = true;
				break;
				
			case "National_Id_Date":
				if(expOption.getNationalIdDateYn().equals("on"))
					result = true;
				break;
				
			case "National_Id_Authority":
				if(expOption.getNationalIdAuthorityYn().equals("on"))
					result = true;
				break;
				
			case "Cnee_Birth":
				if(expOption.getCneeBirthYn().equals("on"))
					result = true;
				break;
				
			case "Tax_No":
				if(expOption.getTaxNoYn().equals("on"))
					result = true;
				break;
				
			case "Exp_Licence_YN":
				result = true;
				break;
			case "TaxNumber":
				if(expOption.getTaxIdYn().equals("on"))
					result = true;
				break;
			
			case "EoriNumber":
				if(expOption.getEoriNoYn().equals("on"))
					result = true;
				break;
			
			case "DeclarationType":
				if(expOption.getDeclTypeYn().equals("on"))
					result = true;
				break;
				
			default:
				result = false;
				break;
		}

		return result;
	}
	
	
	public Map<String, Object> itemJsonColumnChk(JSONObject jObject, ApiOrderListVO apiOrderList) throws Exception {
		OrderItemOptionVO expOption = new OrderItemOptionVO();
		Map<String, Object> temp = new HashMap<String, Object>();
		expOption = mgrMapper.selectOrderItemOption(apiOrderList.getTransCode(), apiOrderList.getDstnNation());
		if(expOption == null) {
			expOption = mgrMapper.selectOrderItemOption(apiOrderList.getTransCode(), apiOrderList.getTransCode());
		}
		String rstVal = "";
		
		if(expOption.getBrandYn().equals("on")) 
			if(!jObject.has("Brand"))
				rstVal = rstVal+"Brand, ";

		if(expOption.getChgCurrencyYn().equals("on"))
			if(!jObject.has("Chg_Currency"))
				rstVal = rstVal+"Chg_Currency, ";

		if(expOption.getCusItemCodeYn().equals("on"))
			if(!jObject.has("Customer_Item_Code"))
				rstVal = rstVal+"Customer_Item_Code, ";
		
		if(expOption.getItemDivYn().equals("on"))
			if(!jObject.has("Item_Div"))
				rstVal = rstVal+"Item_Div, ";

		if(expOption.getHsCodeYn().equals("on"))
			if(!jObject.has("Hs_Code"))
				rstVal = rstVal+"Hs_Code, ";
		
		if(expOption.getItemBarcodeYn().equals("on"))
			if(!jObject.has("Item_Barcode"))
				rstVal = rstVal+"Item_Barcode, ";
		
		if(expOption.getItemCntYn().equals("on"))
			if(!jObject.has("Item_Cnt"))
				rstVal = rstVal+"Item_Cnt, ";
		
		if(expOption.getItemDetailYn().equals("on"))
			if(!jObject.has("Item_Detail"))
				rstVal = rstVal+"Item_Detail, ";
		
		if(expOption.getItemExplanYn().equals("on"))
			if(!jObject.has("Item_Explan"))
				rstVal = rstVal+"Item_Explan, ";

		if(expOption.getItemImgUrlYn().equals("on"))
			if(!jObject.has("Item_Img_Url"))
				rstVal = rstVal+"Item_Img_Url, ";

		if(expOption.getItemMeterialYn().equals("on")) {
			if(jObject.has("Item_Material") || jObject.has("Item_Meterial")) {
				
			}else
				rstVal = rstVal+"Item_Material, ";
		}
			
		
		if(expOption.getItemUrlYn().equals("on"))
			if(!jObject.has("Item_Url"))
				rstVal = rstVal+"Item_Url, ";
		
//			case "Shipper_Tel":
//				if(expOption.getShipperTelYn().equals("on"))
//					result = true;
//				break;
//			case "Shipper_Hp":
//				if(expOption.getShipperHpYn().equals("on"))
//					result = true;
//				break;
		if(expOption.getItemUserWtaYn().equals("on"))
			if(!jObject.has("Item_Weight"))
				rstVal = rstVal+"Item_Weight, ";

		if(expOption.getMakeCntryYn().equals("on"))
			if(!jObject.has("Make_Country"))
				rstVal = rstVal+"Make_Country, ";

		if(expOption.getMakeComYn().equals("on"))
			if(!jObject.has("Make_Company"))
				rstVal = rstVal+"Make_Company, ";
		
		if(expOption.getNativeItemDetailYn().equals("on"))
			if(!jObject.has("Native_Item_Detail"))
				rstVal = rstVal+"Native_Item_Detail, ";

		if(expOption.getQtyUnitYn().equals("on"))
			if(!jObject.has("Qty_Unit"))
				rstVal = rstVal+"Qty_Unit, ";
		
		if(expOption.getUnitValueYn().equals("on"))
			if(!jObject.has("Unit_Value"))
				rstVal = rstVal+"Unit_Value, ";
		
		if(expOption.getTrkComYn().equals("on"))
			if(!jObject.has("Trking_Company"))
				rstVal = rstVal+"Trking_Company, ";
		
		if(expOption.getTrkDateYn().equals("on"))
			if(!jObject.has("Trking_Date"))
				rstVal = rstVal+"Trking_Date, ";
		/*
		if(expOption.getTrkNoYn().equals("on"))
			if(!jObject.has("Trking_Number"))
				rstVal = rstVal+"Trking_Number, ";
		*/
		if(!jObject.has("Trking_Number")) {
			rstVal = rstVal+"Trking_Number, ";
		}

		if(rstVal.length() != 0) {
			temp.put("Error_Msg","Goods JSON INFO "+rstVal+"is required.");
		}
		
		return temp;
	}
	
	public Map<String, Object> itemJsonValueChk (JSONObject jObject, int jsonIndex, ApiOrderListVO apiOrderList) throws Exception{
		OrderItemOptionVO expOption = new OrderItemOptionVO();
		expOption = mgrMapper.selectOrderItemOption(apiOrderList.getTransCode(), apiOrderList.getDstnNation());
		if(expOption == null) {
			expOption = mgrMapper.selectOrderItemOption(apiOrderList.getTransCode(), apiOrderList.getTransCode());
		}
		String checkUserId = "dabonea_ems";
		String userId = apiOrderList.getUserId().toLowerCase();
		
		Map<String, Object> temp = new HashMap<String, Object>();
		Iterator iter = jObject.keySet().iterator();
		String json = "";
		while (iter.hasNext()) {
			String key = (String) iter.next();
			
			if(("item_div".equals(key.toLowerCase()) && checkUserId.equals(userId)) ||
					("customer_item_code".equals(key.toLowerCase()) && checkUserId.equals(userId))) {
				continue;
			}
			
			if("".equals(jObject.get(key)) || jObject.get(key) == null) {
				if(itemJsonExistence(key,expOption)) {
					if(!json.equals("")) {
						json  = json   + ", ";
					}
					json = json + key;
				}
			}
		}

		if(json.length() != 0) {
			temp.put("Error_Msg",json+" IS NULL OR EMPTY");
		}
		return temp;
	}
	
	public boolean itemJsonExistence(String key, OrderItemOptionVO expOption) {
		boolean result = false;
		
		switch (key) {
			case "Item_Detail":
				result = true;
				break;
	
			case "Item_Count":
				result = true;
				break;
	
			case "Unit_Value":
				result = true;
				break;
				
			case "Customer_Item_Code":
				if(expOption.getCusItemCodeYn().equals("on"))
					result = true;
				break;
				
			case "Hs_Code":
				if(expOption.getHsCodeYn().equals("on"))
					result = true;
				break;
				
			case "Item_Cnt":
				if(expOption.getItemCntYn().equals("on"))
					result = true;
				break;
				
			case "Brand":
				if(expOption.getBrandYn().equals("on"))
					result = true;
				break;
				
			case "Make_Country":
				if(expOption.getMakeCntryYn().equals("on"))
					result = true;
				break;

			case "Make_Company":
				if(expOption.getMakeComYn().equals("on"))
					result = true;
				break;
				
			case "Item_Div":
				if(expOption.getItemDivYn().equals("on"))
					result = true;
				break;
				
			case "Qty_Unit":
				if(expOption.getQtyUnitYn().equals("on"))
					result = true;
				break;
				
			case "Chg_Currency":
				if(expOption.getChgCurrencyYn().equals("on"))
					result = true;
				break;
				
			case "Item_Weight":
				if(expOption.getItemUserWtaYn().equals("on"))
					result = true;
				break;
				
			case "Item_Url":
				if(expOption.getItemUrlYn().equals("on"))
					result = true;
				break;
				
			case "Item_Img_Url":
				if(expOption.getItemImgUrlYn().equals("on"))
					result = true;
				break;
				
			case "Native_Item_Detail":
				if(expOption.getNativeItemDetailYn().equals("on"))
					result = true;
				break;
				
			case "Item_Explan":
				if(expOption.getItemExplanYn().equals("on"))
					result = true;
				break;
				
			case "Item_Barcode":
				if(expOption.getItemBarcodeYn().equals("on"))
					result = true;
				break;
				
			case "In_Box_Num":
					result = true;
				break;
				
			case "Trking_Number":
				if(expOption.getTrkNoYn().equals("on"))
					result = true;
				break;
				
			case "Trking_Date":
				if(expOption.getTrkDateYn().equals("on"))
					result = true;
				break;
				
			case "Trking_Company":
				if(expOption.getTrkComYn().equals("on"))
					result = true;
				break;
	
			default:
				result = false;
				break;

		}

		return result;
	}

	
	public ApiOrderListVO setApiOrderListValue(ApiOrderListVO apiOrderList, JSONObject jObject, String transCodeByRemark) throws Exception {
		OrderListExpOptionVO expOption = new OrderListExpOptionVO();
		Map<String, Object> temp = new HashMap<String, Object>();
		expOption = mgrMapper.selectExpressListOption(transCodeByRemark, jObject.getString("Arrival_Nation"));
		if(expOption == null) {
			expOption = mgrMapper.selectExpressListOption(transCodeByRemark, transCodeByRemark);
		}
		
		if(expOption.getOrgStationYnExpress().equals("on")) 
			apiOrderList.setOrgStation(jObject.getString("Departure_Station"));

		if(expOption.getDstnNationYnExpress().equals("on")) {
			apiOrderList.setDstnNation(jObject.getString("Arrival_Nation"));
			apiOrderList.setDstnStation(jObject.getString("Arrival_Nation"));
		}
		
		if(jObject.has("Order_Date")) {
			if(Pattern.matches(YYYYMMDD, jObject.getString("Order_Date"))) {
				apiOrderList.setOrderDate(jObject.getString("Order_Date"));
			}else {
				Date time = new Date();
				String time1 = format1.format(time);
				apiOrderList.setOrderDate(time1);
			}
		}else {
			Date time = new Date();
			String time1 = format1.format(time);
			apiOrderList.setOrderDate(time1);
		}
		
		if(expOption.getShipperNameYnExpress().equals("on")) {
			if(jObject.has("Shipper_Name")) {
				apiOrderList.setShipperName(jObject.getString("Shipper_Name"));
			}else {
				apiOrderList.setShipperName("");
			}
		}
		
		if(expOption.getShipperCntryYnExpress().equals("on")){
			if(jObject.has("Shipper_Country")) {
				apiOrderList.setShipperCntry(jObject.getString("Shipper_Country"));
			}else {
				apiOrderList.setShipperCntry("");
			}
		}
			
		
		if(expOption.getShipperStateYnExpress().equals("on")){
			if(jObject.has("Shipper_State")) {
				apiOrderList.setShipperState(jObject.getString("Shipper_State"));
			}else {
				apiOrderList.setShipperState("");
			}
		}
		
		if(expOption.getShipperCityYnExpress().equals("on")) {
			if(jObject.has("Shipper_City")) {
				apiOrderList.setShipperCity(jObject.getString("Shipper_City"));
			}else {
				apiOrderList.setShipperCity("");
			}
		}
			
		if(expOption.getShipperZipYnExpress().equals("on")) {
			if(jObject.has("Shipper_Zip")) {
				apiOrderList.setShipperZip(jObject.getString("Shipper_Zip"));
			}else {
				apiOrderList.setShipperZip("");
			}
		}
			
		if(expOption.getShipperAddrYnExpress().equals("on")) {
			if(jObject.has("Shipper_Address")) {
				apiOrderList.setShipperAddr(jObject.getString("Shipper_Address"));
			}else {
				apiOrderList.setShipperAddr("");
			}
		}
		
		if(expOption.getShipperAddrDetailYnExpress().equals("on")) {
			if(jObject.has("Shipper_Address_Detail")) {
				apiOrderList.setShipperAddrDetail(jObject.getString("Shipper_Address_Detail"));
			}else {
				apiOrderList.setShipperAddrDetail("");
			}
		}
		
		if(expOption.getShipperHpYnExpress().equals("on")) {
			if(jObject.has("Shipper_Hp")) {
				apiOrderList.setShipperHp(jObject.getString("Shipper_Hp"));
			}else {
				apiOrderList.setShipperHp("");
			}
		}
		
		if(expOption.getShipperTelYnExpress().equals("on")) {
			if(jObject.has("Shipper_Tel")) {
				apiOrderList.setShipperTel(jObject.getString("Shipper_Tel"));
			}else {
				apiOrderList.setShipperTel("");
			}
		}
		
		if(expOption.getUserEmailYnExpress().equals("on")) {
			if(jObject.has("Shipper_Email")) {
				apiOrderList.setShipperEmail(jObject.getString("Shipper_Email"));
			}else {
				apiOrderList.setShipperEmail("");
			}
		}

		if(expOption.getCneeNameYnExpress().equals("on")) {
			if(jObject.has("Receiver_Name")) {
				apiOrderList.setCneeName(jObject.getString("Receiver_Name"));
			}else {
				apiOrderList.setCneeName("");
			}
		}

		if(expOption.getCneeCntryYnExpress().equals("on")) {
			if(jObject.has("Receiver_County")) {
				apiOrderList.setCneeCntry(jObject.getString("Receiver_County"));
			}else {
				apiOrderList.setCneeCntry("");
			}
		}

		if(expOption.getCneeStateYnExpress().equals("on")) {
			if(jObject.has("Receiver_State")) {
				apiOrderList.setCneeState(jObject.getString("Receiver_State"));
			}else {
				apiOrderList.setCneeState("");
			}
		}
		
		if(expOption.getCneeCityYnExpress().equals("on")) {
			if(jObject.has("Receiver_City")) {
				apiOrderList.setCneeCity(jObject.getString("Receiver_City"));
			}else {
				apiOrderList.setCneeCity("");
			}
		}

		if(expOption.getCneeDistrictYnExpress().equals("on")) {
			if(jObject.has("Receiver_District")) {
				apiOrderList.setCneeDistrict(jObject.getString("Receiver_District"));
			}else {
				apiOrderList.setCneeDistrict("");
			}
		}
		
		if(expOption.getCneeZipYnExpress().equals("on")) {
			if(jObject.has("Receiver_Zip")) {
				apiOrderList.setCneeZip(jObject.getString("Receiver_Zip"));
			}else {
				apiOrderList.setCneeZip("");
			}
		}

		if(expOption.getCneeAddrYnExpress().equals("on")) {
			if(jObject.has("Receiver_Address")) {
				apiOrderList.setCneeAddr(jObject.getString("Receiver_Address"));
			}else {
				apiOrderList.setCneeAddr("");
			}
		}

		if(expOption.getCneeAddrDetailYnExpress().equals("on")) {
			if(jObject.has("Receiver_Address_Detail")) {
				apiOrderList.setCneeAddrDetail(jObject.getString("Receiver_Address_Detail"));
			}else {
				apiOrderList.setCneeAddrDetail("");
			}
		}
			
		if(expOption.getCneeTelYnExpress().equals("on")) {
			if(jObject.has("Receiver_Tel")) {
				apiOrderList.setCneeTel(jObject.getString("Receiver_Tel"));
			}else {
				apiOrderList.setCneeTel("");
			}
		}

		if(expOption.getCneeHpYnExpress().equals("on")) {
			if(jObject.has("Receiver_Hp")) {
				apiOrderList.setCneeHp(jObject.getString("Receiver_Hp"));
			}else {
				apiOrderList.setCneeHp("");
			}
		}

		if(expOption.getCneeEmailYnExpress().equals("on")) {
			if(jObject.has("Receiver_Email")) {
				apiOrderList.setCneeEmail(jObject.getString("Receiver_Email"));
			}else {
				apiOrderList.setCneeEmail("");
			}
		}
 
		if(expOption.getNativeCneeNameYnExpress().equals("on")) {
			if(jObject.has("Native_Receiver_Name")) {
				apiOrderList.setNativeCneeName(jObject.getString("Native_Receiver_Name"));
			}else {
				apiOrderList.setNativeCneeName("");
			}
		}
			
		if(expOption.getNativeCneeAddrYnExpress().equals("on")) {
			if(jObject.has("Native_Receiver_Address")) {
				apiOrderList.setNativeCneeAddr(jObject.getString("Native_Receiver_Address"));
			}else {
				apiOrderList.setNativeCneeAddr("");
			}
		}

		if(expOption.getNativeCneeAddrDetailYnExpress().equals("on")) {
			if(jObject.has("Native_Receiver_Address_Detail")) {
				apiOrderList.setNativeCneeAddrDetail(jObject.getString("Native_Receiver_Address_Detail"));
			}else {
				apiOrderList.setNativeCneeAddrDetail("");
			}
		}

		if(expOption.getBoxCntYnExpress().equals("on")) {
			if(jObject.has("Box_Count")) {
				apiOrderList.setBoxCnt("1");
				/*
				 * if (jObject.get("Box_Count").toString().equals("0")) {
				 * apiOrderList.setBoxCnt("1"); } else {
				 * apiOrderList.setBoxCnt(jObject.get("Box_Count").toString()); }
				 */
			}else {
				apiOrderList.setBoxCnt("");
			}
		}

		if(expOption.getUserWtaYnExpress().equals("on")) {
			if(jObject.has("Actual_Weight")) {
				apiOrderList.setUserWta(jObject.get("Actual_Weight").toString());
			}else {
				apiOrderList.setUserWta("");
			}
		}

		if(expOption.getUserWtcYnExpress().equals("on")) {
			if(jObject.has("Volume_Weight")) {
				apiOrderList.setUserWtc(jObject.get("Volume_Weight").toString());
			}else {
				apiOrderList.setUserWtc("");
			}
		}

		if(expOption.getUserLengthYnExpress().equals("on")) {
			if(jObject.has("Volume_Length")) {
				apiOrderList.setUserLength(jObject.get("Volume_Length").toString());
			}else {
				apiOrderList.setUserLength("");
			}
		}

		if(expOption.getUserWidthYnExpress().equals("on")) {
			if(jObject.has("Volume_Width")) {
				apiOrderList.setUserWidth(jObject.get("Volume_Width").toString());
			}else {
				apiOrderList.setUserWidth("");
			}
		}

		if(expOption.getUserHeightYnExpress().equals("on")) {
			if(jObject.has("Volume_Height")) {
				apiOrderList.setUserHeight(jObject.get("Volume_Height").toString());
			}else {
				apiOrderList.setUserHeight("");
			}
		}

		if(expOption.getCustomsNoYnExpress().equals("on")) {
			if(jObject.has("Custom_Clearance_ID")) {
				apiOrderList.setCustomsNo(jObject.getString("Custom_Clearance_ID"));
			}else {
				apiOrderList.setCustomsNo("");
			}
		}

		if(expOption.getBuySiteYnExpress().equals("on")) {
			if(jObject.has("BuySite")) {
				apiOrderList.setBuySite(jObject.getString("BuySite"));
			}else {
				apiOrderList.setBuySite("");
			}
		}

		if(expOption.getDimUnitYnExpress().equals("on")) {
			if(jObject.has("Size_Unit")) {
				apiOrderList.setDimUnit(jObject.getString("Size_Unit"));
			}else {
				apiOrderList.setDimUnit("");
			}
		}

		if(expOption.getWtUnitYnExpress().equals("on")) {
			if(jObject.has("Weight_Unit")) {
				apiOrderList.setWtUnit(jObject.getString("Weight_Unit"));
			}else {
				apiOrderList.setWtUnit("");
			}
		}

		if(expOption.getGetBuyYnExpress().equals("on")) {
			if(jObject.has("Get_Buy")) {
				apiOrderList.setGetBuy(jObject.getString("Get_Buy"));
			}else {
				apiOrderList.setGetBuy("");
			}
		}

		if(expOption.getMallTypeYnExpress().equals("on")) {
			if(jObject.has("Mall_Type")) {
				apiOrderList.setMallType(jObject.getString("Mall_Type"));
			}else {
				apiOrderList.setMallType("");
			}
		}

		if(expOption.getWhReqMsgYnExpress().equals("on")) {
			if(jObject.has("Warehouse_Msg")) {
				apiOrderList.setWhReqMsg(jObject.getString("Warehouse_Msg"));
			}else {
				apiOrderList.setWhReqMsg("");
			}
		}

		if(expOption.getDlvReqMsgYnExpress().equals("on")) {
			if(jObject.has("Delivery_Msg")) {
				apiOrderList.setDlvReqMsg(jObject.getString("Delivery_Msg"));
			}else {
				apiOrderList.setDlvReqMsg("");
			}
		}

		if(expOption.getShipperReferenceYnExpress().equals("on")) {
			if(jObject.has("Shipper_Reference")) {
				apiOrderList.setShipperReference(jObject.getString("Shipper_Reference"));
			}else {
				apiOrderList.setShipperReference("");
			}
		}

		if(expOption.getCneeReference1YnExpress().equals("on")) {
			if(jObject.has("Receiver_Reference1")) {
				apiOrderList.setCneeReference1(jObject.getString("Receiver_Reference1"));
			}else {
				apiOrderList.setCneeReference1("");
			}
		}
			

		if(expOption.getCneeReference2YnExpress().equals("on")){
			if(jObject.has("Receiver_Reference2")) {
				apiOrderList.setCneeReference2(jObject.getString("Receiver_Reference2"));
			}else {
				apiOrderList.setCneeReference2("");
			}
		}
			

		if(expOption.getFoodYnExpress().equals("on")) {
			if(jObject.has("Food")) {
				if(jObject.getString("Food").equals("Y")) {
					apiOrderList.setFood("Y");
				}else {
					apiOrderList.setFood("N");
				}
			}else {
				apiOrderList.setFood("");
			}
		}

		if(expOption.getNationalIdDateYnExpress().equals("on")) {
			if(jObject.has("National_Id_Date")) {
				apiOrderList.setNationalIdDate(jObject.getString("National_Id_Date"));
			}else {
				apiOrderList.setNationalIdDate("");
			}
		}

		if(expOption.getNationalIdAuthorityYnExpress().equals("on")) {
			if(jObject.has("National_Id_Authority")) {
				apiOrderList.setNationalIdAuthority(jObject.getString("National_Id_Authority"));
			}else {
				apiOrderList.setNationalIdAuthority("");
			}
		}

		if(expOption.getCneeBirthYnExpress().equals("on")) {
			if(jObject.has("Cnee_Birth")) {
				apiOrderList.setCneeBirth(jObject.getString("Cnee_Birth"));
			}else {
				apiOrderList.setCneeBirth("");
			}
		}
		
		if(expOption.getTaxNoYnExpress().equals("on")) {
			if(jObject.has("Tax_No")) {
				apiOrderList.setTaxNo(jObject.getString("Tax_No"));
			}else {
				apiOrderList.setTaxNo("");
			}
		}
				
		/*
		if(jObject.has("Exp_Licence_YN")) {
			if(jObject.getString("Exp_Licence_YN").equals("S")) {
				apiOrderList.setExpLicenceYn("N");
				apiOrderList.setExpBusinessName(jObject.getString("Exp_Business_Name"));
				apiOrderList.setExpBusinessNum(jObject.getString("Exp_Business_Num"));
				apiOrderList.setExpShipperCode(jObject.getString("Exp_Shipper_Code"));
				apiOrderList.setExpValue("simpleExplicence");
				apiOrderList.setSimpleYn("Y");
				apiOrderList.setAgencyBusinessName("");
			}else if(jObject.getString("Exp_Licence_YN").equals("Y")){
				apiOrderList.setExpLicenceYn(jObject.getString("Exp_Licence_YN"));
				apiOrderList.setExpBusinessName(jObject.getString("Exp_Business_Name"));
				apiOrderList.setExpBusinessNum(jObject.getString("Exp_Business_Num"));
				apiOrderList.setExpShipperCode(jObject.getString("Exp_Shipper_Code"));
				apiOrderList.setSimpleYn("N");
				apiOrderList.setExpValue("registExplicence1");
				apiOrderList.setAgencyBusinessName(jObject.getString("Agency_Business_Name"));
				if(jObject.has("Exp_No")) {
					apiOrderList.setExpNo(jObject.getString("Exp_No"));
					apiOrderList.setExpValue("registExplicence2");
					apiOrderList.setAgencyBusinessName("");
				}
			}else {
				apiOrderList.setExpLicenceYn("");
				apiOrderList.setExpBusinessName("");
				apiOrderList.setExpBusinessNum("");
				apiOrderList.setExpShipperCode("");
				apiOrderList.setSimpleYn("");
				apiOrderList.setExpValue("noExplicence");
				apiOrderList.setAgencyBusinessName("");
			}
		}else {
			apiOrderList.setExpLicenceYn("");
			apiOrderList.setExpBusinessName("");
			apiOrderList.setExpBusinessNum("");
			apiOrderList.setExpShipperCode("");
			apiOrderList.setSimpleYn("");
			apiOrderList.setExpValue("noExplicence");
			apiOrderList.setAgencyBusinessName("");
		}
		*/
		
		if(jObject.has("Payment")) {
			apiOrderList.setPayment(jObject.optString("Payment","DDU"));
		}else {
			apiOrderList.setPayment("DDU");
		}

		
		return apiOrderList;
	}
	
	public ApiOrderItemListVO setApiOrderItemValue(ApiOrderItemListVO apiOrderItem, JSONObject jObject, String transCodeByRemark) throws Exception {
		OrderItemExpOptionVO expOptionItem = new OrderItemExpOptionVO();
		Map<String, Object> temp = new HashMap<String, Object>();
		expOptionItem = mgrMapper.selectExpressItemOption(transCodeByRemark, transCodeByRemark);
		if(expOptionItem == null) {
			expOptionItem = mgrMapper.selectExpressItemOption(transCodeByRemark, transCodeByRemark);
		}
		
		if(expOptionItem.getCusItemCodeYnExpress().equals("on")) {
			if(jObject.getString("Customer_Item_Code").equals("6080371982512")) {
				apiOrderItem.setCusItemCode("A00A001");
			}else {
				apiOrderItem.setCusItemCode(jObject.getString("Customer_Item_Code"));
			}
		}
		
		if(expOptionItem.getHsCodeYnExpress().equals("on")) {
			apiOrderItem.setHsCode(jObject.getString("Hs_Code"));
		}
		
		if(expOptionItem.getItemDetailYnExpress().equals("on")) {
			apiOrderItem.setItemDetail(jObject.getString("Item_Detail"));
		}
		
		if(expOptionItem.getItemCntYnExpress().equals("on")) {
			apiOrderItem.setItemCnt(jObject.get("Item_Cnt").toString());
		}
		
		if(expOptionItem.getUnitValueYnExpress().equals("on")) {
			apiOrderItem.setUnitValue(jObject.getString("Unit_Value"));
		}
		
		if(expOptionItem.getItemWtUnitYnExpress().equals("on")) {
			apiOrderItem.setWtUnit(jObject.getString("Unit_Value"));
		}
		
		if(expOptionItem.getBrandYnExpress().equals("on")) {
			apiOrderItem.setBrand(jObject.getString("Brand"));
		}
		
		if(expOptionItem.getMakeCntryYnExpress().equals("on")) {
			apiOrderItem.setMakeCntry(jObject.getString("Make_Country"));
		}
		
		if(expOptionItem.getMakeComYnExpress().equals("on")) {
			apiOrderItem.setMakeCom(jObject.getString("Make_Company"));
		}
		
		if(expOptionItem.getItemDivYnExpress().equals("on")) {
			apiOrderItem.setItemDiv(jObject.getString("Item_Div"));
		}
		
		if(expOptionItem.getQtyUnitYnExpress().equals("on")) {
			apiOrderItem.setQtyUnit(jObject.getString("Qty_Unit"));
		}
		
		if(expOptionItem.getChgCurrencyYnExpress().equals("on")) {
			apiOrderItem.setChgCurrency(jObject.getString("Chg_Currency"));
		}
		
		if(expOptionItem.getItemUrlYnExpress().equals("on")) {
			apiOrderItem.setItemUrl(jObject.getString("Item_Url"));
		}
		
		if(expOptionItem.getItemImgUrlYnExpress().equals("on")) {
			apiOrderItem.setItemImgUrl(jObject.getString("Item_Img_Url"));
		}
		
		if(expOptionItem.getTrkComYnExpress().equals("on")) {
			apiOrderItem.setTrkCom(jObject.getString("Trking_Company"));
		}
		/*
		if(expOptionItem.getTrkNoYnExpress().equals("on")) {
			apiOrderItem.setTrkNo(jObject.getString("Trking_Number"));
		}
		*/
		if(expOptionItem.getTrkDateYnExpress().equals("on")) {
			apiOrderItem.setTrkDate(jObject.getString("Trking_Date"));
		}
		
		if(expOptionItem.getNativeItemDetailYnExpress().equals("on")) {
			apiOrderItem.setNativeItemDetail(jObject.getString("Native_Item_Detail"));
		}
		
		
		if(jObject.has("Trking_Number")) {
			apiOrderItem.setTrkNo(jObject.optString("Trking_Number"));
		}
		if(jObject.has("Item_Weight")) {
			apiOrderItem.setUserWtaItem(jObject.getString("Item_Weight"));
		}
		if(jObject.has("Item_Explan")){
			apiOrderItem.setItemExplan(jObject.get("Item_Explan").toString());
		}
		if(jObject.has("Item_Barcode")){
			apiOrderItem.setItemBarcode(jObject.get("Item_Barcode").toString());
		}
		if(jObject.has("In_Box_Num")){
			apiOrderItem.setInBoxNum(jObject.get("In_Box_Num").toString());
		}
		return apiOrderItem;
	}
	
	public ApiOrderListVO checkApiOrderListShipperValue(ApiOrderListVO apiOrderList, ApiOrderListVO defaultApiOrderList) throws Exception {
		apiOrderList.setShipperName(defaultApiOrderList.getShipperName());
		apiOrderList.setShipperZip(defaultApiOrderList.getShipperZip());
		apiOrderList.setShipperTel(defaultApiOrderList.getShipperTel());
		apiOrderList.setShipperAddr(defaultApiOrderList.getShipperAddr());
		apiOrderList.setShipperAddrDetail(defaultApiOrderList.getShipperAddrDetail());
		apiOrderList.setShipperHp(defaultApiOrderList.getShipperHp());
		apiOrderList.setShipperEmail(defaultApiOrderList.getShipperEmail());
		
		apiOrderList.shipperEmailDncrypt(originKey.getSymmetryKey());
		apiOrderList.shipperTelDncrypt(originKey.getSymmetryKey());
		apiOrderList.shipperAddrDncrypt(originKey.getSymmetryKey());
		apiOrderList.shipperAddrDetailDncrypt(originKey.getSymmetryKey());
		apiOrderList.shipperHpDncrypt(originKey.getSymmetryKey());
		return apiOrderList;
	}
	
	public String transComChg(ApiOrderListVO apiOrderList, String transCodeByRemark) throws Exception {
		ProcedureVO rstValue = new ProcedureVO();
		HashMap<String,Object> transParameter = new HashMap<String,Object>();
		transParameter.put("nno", apiOrderList.getNno());
		transParameter.put("orgStation", apiOrderList.getOrgStation());
		transParameter.put("dstnNation", apiOrderList.getDstnNation());
		transParameter.put("userId", apiOrderList.getUserId());
		transParameter.put("wta", apiOrderList.getUserWta());
		transParameter.put("wtc", apiOrderList.getUserWtc());
		transParameter.put("wUserId", apiOrderList.getWUserId());
		transParameter.put("wUserIp", apiOrderList.getWUserIp());
		transParameter.put("transCode", transCodeByRemark);
		rstValue  = comnService.selectTransComChangeForVo(transParameter);
		return rstValue.getRstTransCode();
	}
	
	public void settingShipperInfo(ApiOrderListVO apiOrderList, ApiOrderListVO defaultApiOrderList) throws Exception {
		if(apiOrderList.getShipperName().equals("")) {
			apiOrderList = checkApiOrderListShipperValue(apiOrderList, defaultApiOrderList);
		} else if(apiOrderList.getShipperZip().isEmpty()) {
			apiOrderList = checkApiOrderListShipperValue(apiOrderList, defaultApiOrderList);
		} else if(apiOrderList.getShipperTel().isEmpty()) {
			apiOrderList = checkApiOrderListShipperValue(apiOrderList, defaultApiOrderList);					
		} else if(apiOrderList.getShipperAddr().isEmpty()) {
			apiOrderList = checkApiOrderListShipperValue(apiOrderList, defaultApiOrderList);
		} else if(apiOrderList.getShipperAddrDetail().isEmpty()) {
			apiOrderList = checkApiOrderListShipperValue(apiOrderList, defaultApiOrderList);
		} else if(apiOrderList.getShipperHp().isEmpty()) {
			apiOrderList = checkApiOrderListShipperValue(apiOrderList, defaultApiOrderList);
		} else if(apiOrderList.getShipperEmail().isEmpty()) {
			apiOrderList = checkApiOrderListShipperValue(apiOrderList, defaultApiOrderList);
		}
	}

	public Map<String, Object> orderJsonColumnChkV2(JSONObject jObject, String transCodeByRemark) throws Exception {
		OrderListOptionVO expOption = new OrderListOptionVO();
		Map<String, Object> temp = new HashMap<String, Object>();
		expOption = mgrMapper.selectOrderListOption(transCodeByRemark, jObject.getString("Arrival_Nation"));
		if(expOption == null) {
			expOption = mgrMapper.selectOrderListOption(transCodeByRemark, transCodeByRemark);
		}
		String rstVal = "";
		
		if(expOption.getOrgStationYn().equals("on")) 
			if(!jObject.has("Departure_Station"))
				rstVal = rstVal+"Departure_Station, ";

		if(expOption.getDstnNationYn().equals("on"))
			if(!jObject.has("Arrival_Nation"))
				rstVal = rstVal+"Arrival_Nation, ";

		if(expOption.getTransCodeYn().equals("on"))
			if(!jObject.has("Transfer_Company_Code"))
				rstVal = rstVal+"Transfer_Company_Code, ";

		if(expOption.getOrderDateYn().equals("on"))
			if(!jObject.has("Order_Date"))
				rstVal = rstVal+"Order_Date, ";
		
		if(expOption.getShipperNameYn().equals("on"))
			if(!jObject.has("Shipper_name"))
				rstVal = rstVal+"Shipper_name, ";
		
		if(expOption.getShipperCntryYn().equals("on"))
			if(!jObject.has("Shipper_Country"))
				rstVal = rstVal+"Shipper_Country, ";
		
		if(expOption.getShipperStateYn().equals("on"))
			if(!jObject.has("Shipper_State"))
				rstVal = rstVal+"Shipper_State, ";
		
		if(expOption.getShipperCityYn().equals("on"))
			if(!jObject.has("Shipper_City"))
				rstVal = rstVal+"Shipper_City, ";

		if(expOption.getShipperZipYn().equals("on"))
			if(!jObject.has("Shipper_Zip"))
				rstVal = rstVal+"Shipper_Zip, ";

		if(expOption.getShipperAddrYn().equals("on"))
			if(!jObject.has("Shipper_Address"))
				rstVal = rstVal+"Shipper_Address, ";
		
		if(expOption.getShipperAddrDetailYn().equals("on"))
			if(!jObject.has("Shipper_Address_Detail"))
				rstVal = rstVal+"Shipper_Address_Detail, ";

		if(expOption.getUserEmailYn().equals("on"))
			if(!jObject.has("Shipper_Email"))
				rstVal = rstVal+"Shipper_Email, ";
		
		// 2024.06.26 추가
		if(expOption.getShipperTaxTypeYn().equals("on")) {
			if(!jObject.has("Tax_Type")) {
				rstVal = rstVal+"Tax_Type, ";
			}
		}
		
		if(expOption.getShipperTaxNoYn().equals("on")) {
			if(!jObject.has("Tax_Number")) {
				rstVal = rstVal+"Tax_Number, ";
			}
		}
		
		if(expOption.getCneeTaxTypeYn().equals("on")) {
			if(!jObject.has("Receiver_Tax_Type")) {
				rstVal = rstVal+"Receiver_Tax_Type, ";
			}
		}
		
		if(expOption.getCneeTaxNoYn().equals("on")) {
			if(!jObject.has("Receiver_Tax_Number")) {
				rstVal = rstVal+"Receiver_Tax_Number, ";
			}
		}
		
		if(expOption.getCosmeticYn().equals("on")) {
			if(!jObject.has("Cosmetic_Yn")) {
				rstVal = rstVal+"Cosmetic_Yn, ";
			}
		}
		
		if(expOption.getSignYn().equals("on")) {
			if(!jObject.has("Sign_Yn")) {
				rstVal = rstVal+"Sign_Yn, ";
			}
		}
		

		if(expOption.getCneeNameYn().equals("on"))
			if(!jObject.has("Receiver_Name"))
				rstVal = rstVal+"Receiver_Name, ";

		if(expOption.getCneeCntryYn().equals("on"))
			if(!jObject.has("Receiver_County"))
				rstVal = rstVal+"Receiver_County, ";

		if(expOption.getCneeStateYn().equals("on"))
			if(!jObject.has("Receiver_State"))
				rstVal = rstVal+"Receiver_State, ";
		
		if(expOption.getCneeCityYn().equals("on"))
			if(!jObject.has("Receiver_City"))
				rstVal = rstVal+"Receiver_City, ";

		if(expOption.getCneeDistrictYn().equals("on"))
			if(!jObject.has("Receiver_District"))
				rstVal = rstVal+"Receiver_District, ";
		
		// 2024.06.26 추가
		if(expOption.getCneeWardYn().equals("on")) {
			if(!jObject.has("Receiver_Ward")) {
				rstVal = rstVal+"Receiver_Ward, ";
			}
		}
		
		if(expOption.getCneeZipYn().equals("on"))
			if(!jObject.has("Receiver_Zip"))
				rstVal = rstVal+"Receiver_Zip, ";

		if(expOption.getCneeAddrYn().equals("on"))
			if(!jObject.has("Receiver_Address"))
				rstVal = rstVal+"Receiver_Address, ";

		if(expOption.getCneeAddrDetailYn().equals("on"))
			if(!jObject.has("Receiver_Address_Detail"))
				rstVal = rstVal+"Receiver_Address_Detail, ";

		if(expOption.getCneeTelYn().equals("on"))
			if(!jObject.has("Receiver_Tel"))
				rstVal = rstVal+"Receiver_Tel, ";

		if(expOption.getCneeHpYn().equals("on"))
			if(!jObject.has("Receiver_Hp"))
				rstVal = rstVal+"Receiver_Hp, ";

		if(expOption.getCneeEmailYn().equals("on"))
			if(!jObject.has("Receiver_Email"))
				rstVal = rstVal+"Receiver_Email, ";

		if(expOption.getNativeCneeNameYn().equals("on"))
			if(!jObject.has("Native_Receiver_Name"))
				rstVal = rstVal+"Native_Receiver_Name, ";

		if(expOption.getNativeCneeAddrYn().equals("on"))
			if(!jObject.has("Native_Receiver_Address"))
				rstVal = rstVal+"Native_Receiver_Address, ";

		if(expOption.getNativeCneeAddrDetailYn().equals("on"))
			if(!jObject.has("Native_Receiver_Address_Detail"))
				rstVal = rstVal+"Native_Receiver_Address_Detail, ";

		if(expOption.getBoxCntYn().equals("on"))
			if(!jObject.has("Box_Count"))
				rstVal = rstVal+"Box_Count, ";

		if(expOption.getUserWtaYn().equals("on"))
			if(!jObject.has("Actual_Weight"))
				rstVal = rstVal+"Actual_Weight, ";

		if(expOption.getUserWtcYn().equals("on"))
			if(!jObject.has("Volume_Weight"))
				rstVal = rstVal+"Volume_Weight, ";

		if(expOption.getUserLengthYn().equals("on"))
			if(!jObject.has("Volume_Length"))
				rstVal = rstVal+"Volume_Length, ";

		if(expOption.getUserWidthYn().equals("on"))
			if(!jObject.has("Volume_Width"))
				rstVal = rstVal+"Volume_Width, ";

		if(expOption.getUserHeightYn().equals("on"))
			if(!jObject.has("Volume_Height"))
				rstVal = rstVal+"Volume_Height, ";

		if(expOption.getCustomsNoYn().equals("on"))
			if(!jObject.has("Custom_Clearance_ID"))
				rstVal = rstVal+"Custom_Clearance_ID, ";

		if(expOption.getBuySiteYn().equals("on")) {
			if(!jObject.has("BuySite"))
				rstVal = rstVal+"BuySite, ";
		}
			

		if(expOption.getDimUnitYn().equals("on"))
			if(!jObject.has("Size_Unit"))
				rstVal = rstVal+"Size_Unit, ";

		if(expOption.getWtUnitYn().equals("on"))
			if(!jObject.has("Weight_Unit"))
				rstVal = rstVal+"Weight_Unit, ";

		if(expOption.getGetBuyYn().equals("on"))
			if(!jObject.has("Get_Buy"))
				rstVal = rstVal+"Get_Buy, ";

		if(expOption.getMallTypeYn().equals("on"))
			if(!jObject.has("Mall_Type"))
				rstVal = rstVal+"Mall_Type, ";

		if(expOption.getWhReqMsgYn().equals("on"))
			if(!jObject.has("Warehouse_Msg"))
				rstVal = rstVal+"Warehouse_Msg, ";

		if(expOption.getDlvReqMsgYn().equals("on"))
			if(!jObject.has("Delivery_Msg"))
				rstVal = rstVal+"Delivery_Msg, ";

		if(expOption.getShipperReferenceYn().equals("on"))
			if(!jObject.has("Shipper_Reference"))
				rstVal = rstVal+"Shipper_Reference, ";

		if(expOption.getCneeReference1Yn().equals("on"))
			if(!jObject.has("Receiver_Reference1"))
				rstVal = rstVal+"Receiver_Reference1, ";

		if(expOption.getCneeReference2Yn().equals("on"))
			if(!jObject.has("Receiver_Reference2"))
				rstVal = rstVal+"Receiver_Reference2, ";

		if(expOption.getFoodYn().equals("on"))
			if(!jObject.has("Food"))
				rstVal = rstVal+"Food, ";

		if(expOption.getNationalIdDateYn().equals("on"))
			if(!jObject.has("National_Id_Date"))
				rstVal = rstVal+"National_Id_Date, ";

		if(expOption.getNationalIdAuthorityYn().equals("on"))
			if(!jObject.has("National_Id_Authority"))
				rstVal = rstVal+"National_Id_Authority, ";

		if(expOption.getCneeBirthYn().equals("on"))
			if(!jObject.has("Cnee_Birth"))
				rstVal = rstVal+"Cnee_Birth, ";

		if(expOption.getTaxNoYn().equals("on"))
			if(!jObject.has("Tax_No"))
				rstVal = rstVal+"Tax_No, ";
		
		/*
		if(!jObject.has("Exp_Type")) {
			rstVal = rstVal+"Exp_Type, ";
		}
		*/
		
		if(!jObject.has("Exp_Licence_YN")) {
			
			rstVal = rstVal+"Exp_Licence_YN, ";
			
		} else {
			
			if (jObject.optString("Exp_Licence_YN").equals("N")) {
				
				rstVal = "";
				
			} else if (jObject.optString("Exp_Licence_YN").equals("F")) {
				
				if (!jObject.has("Exp_Business_Name")) {
					rstVal = rstVal + "Exp_Business_Name, ";
				}
				
			} else if (jObject.optString("Exp_Licence_YN").equals("S")) {
				
				if (!jObject.has("Exp_Business_Name")) {
					rstVal = rstVal + "Exp_Business_Name, ";
				}
				
			} else if (jObject.optString("Exp_Licence_YN").equals("E")) {
				
				if (!jObject.has("Exp_Business_Name")) {
					rstVal = rstVal + "Exp_Business_Name, ";
				}
				if (!jObject.has("Exp_Business_Num")) {
					rstVal = rstVal + "Exp_Business_Num, ";
				}
				if (!jObject.has("Exp_Business_Representative")) {
					rstVal = rstVal + "Exp_Business_Representative, ";
				}
				if (!jObject.has("Exp_Business_Addr")) {
					rstVal = rstVal + "Exp_Business_Addr, ";
				}
				if (!jObject.has("Exp_Business_Zip")) {
					rstVal = rstVal + "Exp_Business_Zip, ";
				}

				
			}
			
			/*
			if (jObject.getString("Exp_Licence_YN").equals("N")) {
				rstVal = "";
			} else if (jObject.getString("Exp_Licence_YN").equals("S")) {
				if(!jObject.has("Exp_Business_Name")) {
					rstVal = rstVal+"Exp_Business_Name, ";
				}
				if(!jObject.has("Exp_Business_Num")) {
					rstVal = rstVal+"Exp_Business_Num, ";
				}
				if(!jObject.has("Exp_Shipper_Code")) {
					rstVal = rstVal+"Exp_Shipper_Code, ";
				}
			} else if (jObject.getString("Exp_Licence_YN").equals("Y")) {
				if(!jObject.has("Exp_Business_Name")) {
					rstVal = rstVal+"Exp_Business_Name, ";
				}
				if(!jObject.has("Exp_Business_Num")) {
					rstVal = rstVal+"Exp_Business_Num, ";
				}
				if(!jObject.has("Exp_Shipper_Code")) {
					rstVal = rstVal+"Exp_Shipper_Code, ";
				}
				if(!jObject.has("Exp_No")) {
					if (!jObject.has("Agency_Business_Name")) {
						rstVal = rstVal+"Agency_Business_Name, ";
					}
				}
			}
			*/
		}
		
		if (expOption.getDeclTypeYn().equals("on")) {
			if (!jObject.has("Declaration_Type")) {
				rstVal = rstVal+"Declaration_Type, ";
			}
		}
		

		if(rstVal.length() != 0) {
			temp.put("Error_Msg","JSON INFO "+rstVal+"is required.");
		}
		
		return temp;
	}
}