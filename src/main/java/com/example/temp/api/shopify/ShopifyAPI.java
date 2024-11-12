package com.example.temp.api.shopify;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.temp.api.aci.mapper.ApiMapper;
import com.example.temp.api.aci.vo.ApiOrderItemListVO;
import com.example.temp.api.aci.vo.ApiOrderListVO;
import com.example.temp.common.service.ComnService;
import com.example.temp.member.mapper.MemberMapper;
import com.example.temp.member.vo.BlApplyVO;
import com.example.temp.trans.fedex.FedexAPI;
import com.example.temp.trans.shipStation.ShipStationAPI;
import com.example.temp.trans.shipStation.ShipStationMapper;
import com.example.temp.trans.shipStation.ShipStationOrderVO;
import com.google.gson.Gson;

@Service
public class ShopifyAPI {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ShopifyMapper mapper;
	
	@Autowired
	ComnService comnService;
	
	@Autowired
	FedexAPI fedexApi;
	
	@Autowired
	ShipStationAPI shipStationApi;
	
	@Autowired
	ApiMapper apiMapper;
	
	@Autowired
	ShipStationMapper shipStationMapper;
	
	
	public ArrayList<ApiShopifyInfoVO> selectStoreUrl(String orgStation) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStoreUrl(orgStation);
	}
	
	public String shopifyOrderListCntGet(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception{
		String requestUrl = "https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/orders/count.json?status=open&fulfillment_status=unshipped&financial_status=paid";
		String cnts = "";
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestUrl);
			HttpResponse response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				JSONObject jsons = new JSONObject(body);
				cnts = jsons.get("count").toString();
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return cnts;
	}
	
	public String shopifyOrderListGetTest(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
        //String requestUrl = "https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/orders.json?status=any";
        String requestUrl = "https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/orders.json?status=open&fulfillment_status=unshipped&financial_status=paid";
        JSONObject orders = null;
		JSONArray ordersList = null;
		JSONObject ordersInfo = null;
		JSONObject shippingAddress = null;
		JSONArray lineItemsList = null;
		JSONObject lineItemsInfo = null;
		ApiShopifyResultVO temp = new ApiShopifyResultVO();
        
        try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestUrl);
			
			HttpResponse response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				HashMap<String,ArrayList<String>> fulfilInfo = new HashMap<String,ArrayList<String>>();
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				orders = new JSONObject(body);
				ordersList =(JSONArray)orders.get("orders");
			}
        }catch (Exception e) {
			// TODO: handle exception
			temp.setStatus("Exception Error");
			temp.setErrorMsg(e.toString());
			temp.setHawbNo("");
			
		}
        return "";
	}

	public String shopifyOrderListGet(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
		// TODO Auto-generated method stub
		
		String totalCnt = shopifyOrderListCntGet(apiShopifyInfoVO,httpRequest,httpResponse);
		
		String requestUrl = "https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/orders.json?status=open&fulfillment_status=unshipped&financial_status=paid&limit=250";
		//String requestUrl = "https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/orders.json?status=any";
		JSONObject orders = null;
		JSONArray ordersList = null;
		JSONObject ordersInfo = null;
		JSONObject shippingAddress = null;
		JSONArray lineItemsList = null;
		JSONObject lineItemsInfo = null;
		ApiShopifyResultVO temp = new ApiShopifyResultVO();
		
		int ableCnt = 0;
		
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestUrl);
			
			HttpResponse response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				HashMap<String,ArrayList<String>> fulfilInfo = new HashMap<String,ArrayList<String>>();
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				orders = new JSONObject(body);
				ordersList =(JSONArray)orders.get("orders");
				if(ordersList.length()==0) {
					return apiShopifyInfoVO.getUserId()+"의 주문요청건이 없습니다.";
				}
				for(int i = 0; i< ordersList.length(); i++) {
					fulfilInfo = new HashMap<String,ArrayList<String>>();
					boolean stopFlag= false;
					ApiOrderListVO shopifyOrder = new ApiOrderListVO();
					ArrayList<ApiOrderItemListVO> shopifyOrderItem = new ArrayList<ApiOrderItemListVO>();
					ordersInfo = ordersList.getJSONObject(i);
					
					HashMap<String,String> checkCnt = new HashMap<String,String>();

					checkCnt.put("orderNo", ordersInfo.get("order_number").toString());
					checkCnt.put("userId", apiShopifyInfoVO.getUserId());
					int orderCnt= mapper.selectCount(checkCnt);
//					if(!ordersInfo.get("order_number").toString().equals("6537")) {
//						continue;
//					}
					if(orderCnt >0)
						continue;
					ableCnt++;
					String newNno = comnService.selectNNO();
					try{
						shippingAddress = ordersInfo.getJSONObject("shipping_address");
						lineItemsList = ordersInfo.getJSONArray("line_items");
						
						shopifyOrder.setNno(newNno);
						shopifyOrder.setOrgStation(apiShopifyInfoVO.getOrgStation());
						shopifyOrder.setOrderNo(ordersInfo.get("order_number").toString());
						shopifyOrder.setDstnNation(shippingAddress.get("country_code").toString());
						shopifyOrder.setDstnStation(shippingAddress.get("country_code").toString());
						shopifyOrder.setUserId(apiShopifyInfoVO.getUserId());
						shopifyOrder.setOrderType("TAKEIN");
						String[] tempDate = ordersInfo.get("created_at").toString().split("T");
						shopifyOrder.setOrderDate(tempDate[0].replace("-", ""));
						shopifyOrder.setBoxCnt("1");
						double userWta = Double.parseDouble(ordersInfo.get("total_weight").toString());
						userWta = userWta/1000;
						shopifyOrder.setUserWta(Double.toString(userWta));
						shopifyOrder.setWtUnit("KG");
						
						shopifyOrder.setShipperName("ACI");
						shopifyOrder.setShipperZip("90703");
						shopifyOrder.setShipperTel("13109650463");
						shopifyOrder.setShipperHp("");
						shopifyOrder.setShipperCntry("US");
						shopifyOrder.setShipperState("CA");
						shopifyOrder.setShipperCity("Cerritos");
						shopifyOrder.setShipperAddr("14056 Artesia Blvd");
						shopifyOrder.setShipperAddrDetail("");
						
						String _fullName = shippingAddress.get("first_name")+" "+shippingAddress.get("last_name");
						_fullName = _fullName.replaceAll("&", ",");
						_fullName = _fullName.replaceAll("[!@#$%^*().?\":{}|<>]", ""); 


						shopifyOrder.setCneeName(_fullName);
						if(shippingAddress.getString("first_name").toLowerCase().equals("test") && shippingAddress.getString("last_name").toLowerCase().equals("test") ) {
							ableCnt--;
							continue;
						}
						shopifyOrder.setCneeZip(shippingAddress.get("zip").toString());
						shopifyOrder.setCneeTel(shippingAddress.get("phone").toString());
						shopifyOrder.setCneeHp(shippingAddress.get("phone").toString());
						shopifyOrder.setCneeCntry(shippingAddress.get("country_code").toString());
						shopifyOrder.setCneeState(shippingAddress.get("province_code").toString());
						shopifyOrder.setCneeCity(shippingAddress.get("city").toString());
						
						String address = shippingAddress.get("address1").toString();
						if(shippingAddress.get("address2").toString().equals("") || shippingAddress.get("address2").toString() == null || shippingAddress.get("address2").toString().toUpperCase().equals("NULL")) {
							
						}else {
							address += " " + shippingAddress.get("address2").toString();
						}
						shopifyOrder.setCneeAddr(address);
						shopifyOrder.setCneeAddrDetail("");
						
						HashMap<String,Object> transCodeParam = new HashMap<String,Object>(); 
						shopifyOrder.setUserEmail(ordersInfo.get("email").toString());
						transCodeParam.put("userId", apiShopifyInfoVO.getUserId());
						transCodeParam.put("orgStation",apiShopifyInfoVO.getOrgStation());
						transCodeParam.put("dstnNation",shippingAddress.get("country_code").toString());
						String transCode = mapper.selectTransCodeByUserId(transCodeParam);
						
						//transcode는 쿼리에서 id로 해결
						shopifyOrder.setTransCode(transCode);
						shopifyOrder.setCneeEmail(ordersInfo.get("email").toString());
						shopifyOrder.setWUserId((String)httpRequest.getSession().getAttribute("USER_ID"));
						shopifyOrder.setWUserIp((String)httpRequest.getSession().getAttribute("USER_IP"));
						//w_date는 SYSDATE
						
						int itemSubNo = 0;
						for(int itemRoop = 0 ; itemRoop < lineItemsList.length(); itemRoop++) {
							lineItemsInfo = lineItemsList.getJSONObject(itemRoop);
							if(lineItemsInfo.get("fulfillable_quantity").toString().equals("0")) {
								continue;
							}
							if(lineItemsInfo.get("sku").toString().contains("-")) {
								String temp1[] = lineItemsInfo.get("sku").toString().split("-");
								for(int idx1 = 0; idx1 < temp1.length; idx1++) {
									ApiOrderItemListVO tempItem = new ApiOrderItemListVO();
									tempItem.setNno(newNno);
									tempItem.setOrgStation(apiShopifyInfoVO.getOrgStation());
									tempItem.setUserId(apiShopifyInfoVO.getUserId());
									tempItem.setItemCnt(lineItemsInfo.get("quantity").toString());
									tempItem.setUnitValue(lineItemsInfo.get("price").toString());
									tempItem.setBrand(lineItemsInfo.get("vendor").toString());
									
									itemSubNo++;
									tempItem.setSubNo(Integer.toString(itemSubNo));
									tempItem.setItemDetail(lineItemsInfo.get("title").toString()+" other "+temp1.length+" kinds");
									String newSku = temp1[idx1].replaceAll(" ", "");
									tempItem.setCusItemCode(newSku);
									tempItem.setChgCurrency("USD");
									tempItem.setTrkCom(transCode);
									tempItem.setWUserId((String)httpRequest.getSession().getAttribute("USER_ID"));
									tempItem.setWUserIp((String)httpRequest.getSession().getAttribute("USER_IP"));
									shopifyOrderItem.add(tempItem);
								}
							}else {
								ApiOrderItemListVO tempItem = new ApiOrderItemListVO();
								tempItem.setNno(newNno);
								tempItem.setOrgStation(apiShopifyInfoVO.getOrgStation());
								tempItem.setUserId(apiShopifyInfoVO.getUserId());
								tempItem.setItemCnt(lineItemsInfo.get("quantity").toString());
								tempItem.setUnitValue(lineItemsInfo.get("price").toString());
								tempItem.setBrand(lineItemsInfo.get("vendor").toString());
								
								itemSubNo++;
								tempItem.setSubNo(Integer.toString(itemSubNo));
								tempItem.setItemDetail(lineItemsInfo.get("title").toString());
								tempItem.setCusItemCode(lineItemsInfo.get("sku").toString());
								tempItem.setChgCurrency("USD");
								tempItem.setTrkCom(transCode);
								tempItem.setWUserId((String)httpRequest.getSession().getAttribute("USER_ID"));
								tempItem.setWUserIp((String)httpRequest.getSession().getAttribute("USER_IP"));
								shopifyOrderItem.add(tempItem);
							}
						}
						shopifyOrder.setDimUnit("CM");
						//orderlist insert
						shopifyOrder.setSymmetryKey("07412cV8xX8MlRtA9b5lKTy2zXuM4CmO");
						shopifyOrder.encryptData();
						
//						for(int itemIndex=0; itemIndex<lineItemsList.length();itemIndex++) {
//							JSONArray properties = null;
//							lineItemsInfo = lineItemsList.getJSONObject(itemIndex);
//							properties = lineItemsInfo.getJSONArray("properties");
//							for(int propertiesIndex = 0; propertiesIndex<properties.length(); propertiesIndex++) {
//								JSONObject propertiesInfo = null;
//								propertiesInfo = properties.getJSONObject(propertiesIndex);
//								if(propertiesInfo.get("value").equals("Pre-order Item")) {
//									stopFlag = true;
//								}
//								if(stopFlag) {
//									break;
//								}
//							}
//							
//							if(stopFlag) {
//								break;
//							}
//						}
						
						for(int itemIndex=0; itemIndex<shopifyOrderItem.size();itemIndex++) {
							int takeItemCnt = mapper.selectTakeItemCnt(shopifyOrderItem.get(itemIndex).getCusItemCode());
							if(takeItemCnt < Integer.parseInt(shopifyOrderItem.get(itemIndex).getItemCnt())) {
								stopFlag=true;
								break;
							}
						}
						
						if(stopFlag) {
							ableCnt--;
							continue;
						}
						
						mapper.insertApiOrderList(shopifyOrder);
						for(int index = 0; index < shopifyOrderItem.size(); index++) {
							mapper.insertApiOrderItem(shopifyOrderItem.get(index));
						}
						switch(transCode) {
							case "FED":
								double weight = Double.parseDouble(shopifyOrder.getUserWta());
								weight = weight*2.2046;
								if (apiShopifyInfoVO.getUserId().toLowerCase().equals("bmsmileus")) {
									temp = fedexApi.sendFedexApiSmart(httpRequest.getSession().getAttribute("ORG_STATION").toString(), newNno, "API");
								} else {
									if(weight > 9) {
										temp = fedexApi.sendFedexApiGround(httpRequest.getSession().getAttribute("ORG_STATION").toString(), newNno, "API");
									}else {
										temp = fedexApi.sendFedexApiSmart(httpRequest.getSession().getAttribute("ORG_STATION").toString(), newNno, "API");
									}
								}
								/*
								if(weight > 9) {
									temp = fedexApi.sendFedexApiGround(httpRequest.getSession().getAttribute("ORG_STATION").toString(), newNno, "API");
								}else {
									temp = fedexApi.sendFedexApiSmart(httpRequest.getSession().getAttribute("ORG_STATION").toString(),newNno, "API");
								}
								*/
								//if 조건에 따라 여기서 분기
								break;
							case "USP":
								double weightoz = Double.parseDouble(mapper.selectTotalWeight(newNno,"OZ"));
								if(weightoz > 18) {
									transCode = "FEDEX";
									double weightKg = Double.parseDouble(shopifyOrder.getUserWta());
									weightKg = weightKg*2.2046;
									if (apiShopifyInfoVO.getUserId().toLowerCase().equals("bmsmileus")) {
										temp = fedexApi.sendFedexApiSmart(httpRequest.getSession().getAttribute("ORG_STATION").toString(), newNno, "API");
									} else {
										if(weightKg > 9) {
											temp = fedexApi.sendFedexApiGround(httpRequest.getSession().getAttribute("ORG_STATION").toString(), newNno, "API");
										}else {
											temp = fedexApi.sendFedexApiSmart(httpRequest.getSession().getAttribute("ORG_STATION").toString(), newNno, "API");
										}
									}
									/*
									if(weightKg > 9) {
										temp = fedexApi.sendFedexApiGround(httpRequest.getSession().getAttribute("ORG_STATION").toString(), newNno, "API");
									}else {
										temp = fedexApi.sendFedexApiSmart(httpRequest.getSession().getAttribute("ORG_STATION").toString(),newNno, "API");
									}
									*/
								}else {
									temp = shipStationApi.createShipment(newNno);
								}
							default :
								break;
						}
						if(temp.getStatus().equals("ERROR")) {
							ableCnt--;
							fedexApi.deleteOrderListByNno(newNno);
							continue;
						}
						
						if(temp.getHawbNo().equals("")) {
							ableCnt--;
							fedexApi.deleteOrderListByNno(newNno);
							continue;
						}
						
						if(temp.getStatus().equals("DELETE")) {
							ableCnt--;
							fedexApi.deleteOrderListByNno(newNno);
							continue;
						}
						//fedex api
						//orderlist hawb update
						for(int itemRoop = 0 ; itemRoop < lineItemsList.length(); itemRoop++) {
							stopFlag = false;
							JSONObject lineItemsInfoForFul = null;
							lineItemsInfoForFul = lineItemsList.getJSONObject(itemRoop);
							String variantsId = lineItemsInfoForFul.get("variant_id").toString();
							if(lineItemsInfoForFul.get("fulfillable_quantity").toString().equals("0")) {
								continue;
							}
//							if(variantsId.equals("39632159277212")) {
//								
//							}else if(variantsId.equals("")) {
//								
//							}
							String requestUrl2 =  "https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/variants/"+variantsId+".json";
							HttpClient client2 = HttpClientBuilder.create().build();
							HttpGet getRequest2 = new HttpGet(requestUrl2);
							
							HttpResponse response2 = client2.execute(getRequest2);
							JSONObject variant = null;
							JSONObject variantInfo = null;
							
							if (response2.getStatusLine().getStatusCode() == 200) {
								ResponseHandler<String> handler2 = new BasicResponseHandler();
								String body2 = handler2.handleResponse(response2);
								variant = new JSONObject(body2);
								variantInfo = variant.getJSONObject("variant");
								String inventoryItemIds = variantInfo.get("inventory_item_id").toString(); 
								String requestUrl3 =  "https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/inventory_levels.json?inventory_item_ids="+inventoryItemIds;
								HttpClient client3 = HttpClientBuilder.create().build();
								HttpGet getRequest3 = new HttpGet(requestUrl3);
								
								HttpResponse response3 = client3.execute(getRequest3);
								JSONObject inventoryLevels = null;
								JSONArray inventoryLevelsList = null;
								JSONObject inventoryLevelsInfo = null;
								
								
								if (response3.getStatusLine().getStatusCode() == 200) {
									ResponseHandler<String> handler3 = new BasicResponseHandler();
									String body3 = handler3.handleResponse(response3);
									inventoryLevels = new JSONObject(body3);
									inventoryLevelsList =(JSONArray)inventoryLevels.get("inventory_levels");
									
									for(int roopInven = 0; roopInven < inventoryLevelsList.length(); roopInven++) {
										inventoryLevelsInfo = inventoryLevelsList.getJSONObject(roopInven);
										String locationId = inventoryLevelsInfo.get("location_id").toString();
										if(locationId.equals("55260446876")) {
											if(!fulfilInfo.containsKey(locationId)) {
												fulfilInfo.put(locationId, new ArrayList<String>());
											}
											fulfilInfo.get(locationId).add(lineItemsInfoForFul.get("id").toString());
										}
									}
									
								}else {
									temp.setStatus("response3 is error");
									temp.setErrorMsg("Error Status Code : "+response.getStatusLine().getStatusCode()+"Error");
									temp.setHawbNo("");
									stopFlag = true;
									fedexApi.deleteOrderListByNno(newNno);
									System.out.println("response3 is error : " + response.getStatusLine().getStatusCode());
								}
								client3.getConnectionManager().shutdown();
								
							}else {
								temp.setStatus("response2 is error");
								temp.setErrorMsg("Error Status Code : "+response.getStatusLine().getStatusCode()+"Error");
								temp.setHawbNo("");
								stopFlag = true;
								fedexApi.deleteOrderListByNno(newNno);
								System.out.println("response2 is error : " + response.getStatusLine().getStatusCode());
							}
							client2.getConnectionManager().shutdown();
						}
						if(stopFlag) {
							ableCnt--;
							continue;
						}
						
						HttpClient client4 = HttpClientBuilder.create().build(); //Use this instead 
						try {
							for (Entry<String, ArrayList<String>> entry : fulfilInfo.entrySet()) {
								HttpPost request = new HttpPost("https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/orders/"+ordersInfo.get("id").toString()+"/fulfillments.json");
								String json = "{\"fulfillment\":{\"location_id\":"+entry.getKey()+",\"tracking_number\":\""+temp.getHawbNo()+"\",\"tracking_company\":\""+transCode+"\",\"line_items\":[";
								for(int fulfilItemId = 0; fulfilItemId < entry.getValue().size(); fulfilItemId++) {
									if(fulfilItemId!=0) {
										json+=",";
									}
									json+=  "{\"id\":"+ entry.getValue().get(fulfilItemId)+"}";
								}
								json+="]}}";
								StringEntity params =new StringEntity(json);
								request.addHeader("content-type", "application/json");
								request.setEntity(params);
								HttpResponse response4 = client4.execute(request);
								ResponseHandler<String> handler4 = new BasicResponseHandler();
								String body4 = handler4.handleResponse(response4);
								JSONObject fulfillment = null;
								JSONObject fulfilDetailInfo = null;
								fulfillment = new JSONObject(body4);
								fulfilDetailInfo = fulfillment.getJSONObject("fulfillment");
								String fulfillId = fulfilDetailInfo.get("id").toString();
								HashMap<String, Object> shopifyFulfilInfo = new HashMap<String,Object>();
								shopifyFulfilInfo.put("nno", newNno);
								shopifyFulfilInfo.put("userId", checkCnt.get("userId"));
								shopifyFulfilInfo.put("orderNo", checkCnt.get("orderNo"));
								shopifyFulfilInfo.put("hawbNo", temp.getHawbNo());
								shopifyFulfilInfo.put("orderId", ordersInfo.get("id").toString());
								shopifyFulfilInfo.put("fulfilItemId", fulfillId);
								mapper.insertShopifyFulfilInfo(shopifyFulfilInfo);
							}
							client4.getConnectionManager().shutdown();
							//handle response here...
						}catch (Exception ex) {
							ableCnt--;
							fedexApi.deleteOrderListByNno(newNno);
							client4.getConnectionManager().shutdown();
							ex.printStackTrace();
							continue;
							//handle exception here
						} 
						ApiOrderListVO updateShopifyOrder = new ApiOrderListVO();
						updateShopifyOrder.setOrderType("TAKEIN");
						updateShopifyOrder.setNno(newNno);
						updateShopifyOrder.setUserId(shopifyOrder.getUserId());
						updateShopifyOrder.setHawbNo(temp.getHawbNo());
						mapper.updateApiOrderListHawb(updateShopifyOrder);
						//fullpie~ update
						//end???
					}catch (Exception e) {
						ableCnt--;
						// TODO: handle exception
						fedexApi.deleteOrderListByNno(newNno);
						logger.error("Exception", e);
					}
				}
			} else {
				temp.setStatus("response is error");
				temp.setErrorMsg("Error Status Code : "+response.getStatusLine().getStatusCode()+"Error");
				temp.setHawbNo("");
				System.out.println("response is error : " + response.getStatusLine().getStatusCode());
			}
			client.getConnectionManager().shutdown();
		}catch (Exception e) {
			// TODO: handle exception
			temp.setStatus("Exception Error");
			temp.setErrorMsg(e.toString());
			temp.setHawbNo("");
			
		}
		return apiShopifyInfoVO.getUserId()+"의 주문요청건 "+totalCnt+"개 중 "+ableCnt+"건이 적용되었습니다.";
	}
	
	// fedex로 재등록
	public String shopifyOrderListFulfilUpdate(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
		ApiShopifyResultVO temp = new ApiShopifyResultVO();
		String [] targetTmp = httpRequest.getParameterMap().get("datas[]");
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		int ableCnt = 0;
		
		for(int i = 0 ; i < orderNnoList.size(); i ++) {
			ShipStationOrderVO orderInfo = new ShipStationOrderVO();
			orderInfo = shipStationMapper.selectListInfoForShipStation(orderNnoList.get(i));
			try {
				double weight = Double.parseDouble(mapper.selectTotalWeight(orderNnoList.get(i),"KG"));
				weight = weight*2.2046; // 파운드로 변환

				if (orderInfo.getUserId().toLowerCase().equals("bmsmileus")) {
					temp = fedexApi.sendFedexApiSmart(httpRequest.getSession().getAttribute("ORG_STATION").toString(),orderNnoList.get(i), "API");
				} else {
					if(weight > 9) {
						temp = fedexApi.sendFedexApiGround(httpRequest.getSession().getAttribute("ORG_STATION").toString(), orderNnoList.get(i), "API");
					}else {
						temp = fedexApi.sendFedexApiSmart(httpRequest.getSession().getAttribute("ORG_STATION").toString(),orderNnoList.get(i), "API");
					}
				}
				
				if(temp.getStatus().equals("ERROR")) {
					ableCnt--;
					continue;
				}
				
				if(temp.getHawbNo().equals("")) {
					ableCnt--;
					continue;
				}
				
				if (orderInfo.getUserId().toLowerCase().equals("bmsmileus")) {
					LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
					apiHeader.put("Content-Type", "application/json");
					apiHeader.put("X-Shopify-Access-Token", apiShopifyInfoVO.getPassword());
					apiHeader.put("Accept", "application/json");
					ArrayList<HashMap<String,Object>> fulfilInfo = new ArrayList<HashMap<String,Object>>();
					fulfilInfo = mapper.selectFulfillment(orderNnoList.get(i));
					
					for (int roop = 0; roop < fulfilInfo.size(); roop++) {
						
						String orderId = fulfilInfo.get(roop).get("orderId").toString();
						String fulfillId = fulfilInfo.get(roop).get("fulFillId").toString();
						String apiUrl = "https://"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2023-07/orders/"+orderId+"/fulfillments/count.json";
						JSONObject apiReturn = apiGet(apiUrl, apiHeader, httpRequest);
						
						if (apiReturn != null) {
							
							if (apiReturn.optInt("count") > 0) {
								// update fulfillments
								String apiUrl2 = "https://"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2023-07/fulfillments/"+fulfillId+"/update_tracking.json";
								String json = "{\"fulfillment\":{\"tracking_info\":{\"company\":\"FEDEX\",\"number\":\""+temp.getHawbNo()+"\"}}}";
								JSONObject apiReturn2 = apiPost(apiUrl2, apiHeader, httpRequest, json);
								
							} else {
								// create fulfillments
								String apiUrl2 = "https://"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2023-07/fulfillments.json";
								String json = "{\"fulfillment\":{\"tracking_info\":{\"company\":\"FEDEX\",\"number\":\""+temp.getHawbNo()+"\"},\"line_items_by_fulfillment_order\":[{\"fulfillment_order_id\":\""+fulfillId+"\"}]}}";
								JSONObject apiReturn2 = apiPost(apiUrl2, apiHeader, httpRequest, json);
								
							}
						}	
					}
				}
				
				ApiOrderListVO updateShopifyOrder = new ApiOrderListVO();
				updateShopifyOrder.setOrderType("TAKEIN");
				updateShopifyOrder.setUserId(orderInfo.getUserId());
				updateShopifyOrder.setNno(orderNnoList.get(i));
				updateShopifyOrder.setHawbNo(temp.getHawbNo());
				mapper.updateApiOrderListHawb(updateShopifyOrder);
				
				
			}catch (Exception e) {
				logger.error("Exception", e);
				ableCnt--;
				continue;
			}
			ableCnt++;
		}
		return apiShopifyInfoVO.getUserId()+"의 주문요청건 "+orderNnoList.size()+"개 중 "+ableCnt+"건이 적용되었습니다.";
	}
	
	public String shopifyOrderListFulfilUpdateUSPS(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
		// TODO Auto-generated method stub
		
		//String requestUrl = "https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/orders.json?status=any";
		ApiShopifyResultVO temp = new ApiShopifyResultVO();
		String [] targetTmp = httpRequest.getParameterMap().get("datas[]");
		ArrayList<String> orderNnoList = new ArrayList<String> (Arrays.asList(targetTmp));
		int ableCnt = 0;
		
		for(int i = 0 ; i < orderNnoList.size(); i ++) {
			try {
				double weightoz = Double.parseDouble(mapper.selectTotalWeight(orderNnoList.get(i),"OZ"));
				temp = shipStationApi.createShipment(orderNnoList.get(i));
				HashMap<String,Object> fulfilInfo = new HashMap<String,Object>();
				
				fulfilInfo = mapper.selectFulfilInfo(orderNnoList.get(i));
				
				String requestUrl = "https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/orders/"+fulfilInfo.get("orderId")+"/fulfillments/"+fulfilInfo.get("fulfilItemId")+".json";
				HttpClient client = HttpClientBuilder.create().build();
				HttpPut putRequest = new HttpPut(requestUrl);
				
				String json = "{\"fulfillment\":{\"id\":"+fulfilInfo.get("fulfilItemId")+",\"tracking_number\":\""+temp.getHawbNo()+"\",\"tracking_company\":\"USPS\"}}";
				StringEntity params =new StringEntity(json);
				putRequest.addHeader("content-type", "application/json");
				putRequest.setEntity(params);
				HttpResponse response = client.execute(putRequest);
				
				//shipStationApi.voidLabel(orderNnoList.get(i));
				
				ApiOrderListVO updateShopifyOrder = new ApiOrderListVO();
				updateShopifyOrder.setOrderType("TAKEIN");
				updateShopifyOrder.setUserId(fulfilInfo.get("userId").toString());
				updateShopifyOrder.setNno(orderNnoList.get(i));
				updateShopifyOrder.setHawbNo(temp.getHawbNo());
				mapper.updateApiOrderListHawb(updateShopifyOrder);
				
				
			}catch (Exception e) {
				// TODO: handle exception
				ableCnt--;
				continue;
			}
			ableCnt++;
		}
		return apiShopifyInfoVO.getUserId()+"의 주문요청건 "+orderNnoList.size()+"개 중 "+ableCnt+"건이 적용되었습니다.";
	}

	public String shopifyOrderListGetForThread(ApiShopifyInfoVO apiShopifyInfoVO) throws Exception {
		// TODO Auto-generated method stub
		String requestUrl = "https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/orders.json?status=open&fulfillment_status=unshipped&financial_status=paid";
		JSONObject orders = null;
		JSONArray ordersList = null;
		JSONObject ordersInfo = null;
		JSONObject shippingAddress = null;
		JSONArray lineItemsList = null;
		JSONObject lineItemsInfo = null;
		ApiShopifyResultVO temp = new ApiShopifyResultVO();
		
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestUrl);
			
			HttpResponse response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				HashMap<String,ArrayList<String>> fulfilInfo = new HashMap<String,ArrayList<String>>();
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				orders = new JSONObject(body);
				ordersList =(JSONArray)orders.get("orders");
				for(int i = 0; i< ordersList.length(); i++) {
					fulfilInfo = new HashMap<String,ArrayList<String>>();
					String newNno = comnService.selectNNO();
					ApiOrderListVO shopifyOrder = new ApiOrderListVO();
					ArrayList<ApiOrderItemListVO> shopifyOrderItem = new ArrayList<ApiOrderItemListVO>();
					
					ordersInfo = ordersList.getJSONObject(i);
					shippingAddress = ordersInfo.getJSONObject("shipping_address");
					lineItemsList = ordersInfo.getJSONArray("line_items");
					
					shopifyOrder.setNno(newNno);
					shopifyOrder.setOrgStation(apiShopifyInfoVO.getOrgStation());
					shopifyOrder.setOrderNo(ordersInfo.get("order_number").toString());
					shopifyOrder.setDstnNation(shippingAddress.get("country_code").toString());
					shopifyOrder.setDstnStation(shippingAddress.get("country_code").toString());
					shopifyOrder.setUserId(apiShopifyInfoVO.getUserId());
					shopifyOrder.setOrderType("TAKEIN");
					String[] tempDate = ordersInfo.get("created_at").toString().split("T");
					shopifyOrder.setOrderDate(tempDate[0].replace("-", ""));
					shopifyOrder.setBoxCnt("1");
					double userWta = Double.parseDouble(ordersInfo.get("total_weight").toString());
					userWta = userWta/1000;
					shopifyOrder.setUserWta(Double.toString(userWta));
					shopifyOrder.setWtUnit("KG");
					
					shopifyOrder.setShipperName("ACI");
					shopifyOrder.setShipperZip("90703");
					shopifyOrder.setShipperTel("13109650463");
					shopifyOrder.setShipperHp("");
					shopifyOrder.setShipperCntry("US");
					shopifyOrder.setShipperState("CA");
					shopifyOrder.setShipperCity("Cerritos");
					shopifyOrder.setShipperAddr("14056 Artesia Blvd");
					shopifyOrder.setShipperAddrDetail("");
					
					shopifyOrder.setCneeName(shippingAddress.get("first_name")+" "+shippingAddress.get("last_name"));
					shopifyOrder.setCneeZip(shippingAddress.get("zip").toString());
					shopifyOrder.setCneeTel(shippingAddress.get("phone").toString());
					shopifyOrder.setCneeHp(shippingAddress.get("phone").toString());
					shopifyOrder.setCneeCntry(shippingAddress.get("country_code").toString());
					shopifyOrder.setCneeState(shippingAddress.get("province_code").toString());
					shopifyOrder.setCneeCity(shippingAddress.get("city").toString());
					
					String address = shippingAddress.get("address1").toString();
					if(shippingAddress.get("address2").toString().equals("") || shippingAddress.get("address2").toString() == null || shippingAddress.get("address2").toString().toUpperCase().equals("NULL")) {
						
					}else {
						address += " " + shippingAddress.get("address2").toString();
					}
					shopifyOrder.setCneeAddr(address);
					shopifyOrder.setCneeAddrDetail("");
					
					HashMap<String,Object> transCodeParam = new HashMap<String,Object>(); 
					shopifyOrder.setUserEmail(ordersInfo.get("email").toString());
					transCodeParam.put("userId", apiShopifyInfoVO.getUserId());
					transCodeParam.put("orgStation",apiShopifyInfoVO.getOrgStation());
					transCodeParam.put("dstnNation",shippingAddress.get("country_code").toString());
					String transCode = mapper.selectTransCodeByUserId(transCodeParam);
					
					//transcode는 쿼리에서 id로 해결
					shopifyOrder.setTransCode(transCode);
					shopifyOrder.setCneeEmail(ordersInfo.get("email").toString());
					shopifyOrder.setWUserId("Scheduler");
					shopifyOrder.setWUserIp("15.164.92.40");
					//w_date는 SYSDATE
					for(int itemRoop = 0 ; itemRoop < lineItemsList.length(); itemRoop++) {
						lineItemsInfo = lineItemsList.getJSONObject(itemRoop);
						ApiOrderItemListVO tempItem = new ApiOrderItemListVO();
						tempItem.setNno(newNno);
						tempItem.setSubNo(Integer.toString((itemRoop+1)));
						tempItem.setOrgStation(apiShopifyInfoVO.getOrgStation());
						tempItem.setUserId(apiShopifyInfoVO.getUserId());
						tempItem.setItemDetail(lineItemsInfo.get("title").toString());
						tempItem.setItemCnt(lineItemsInfo.get("quantity").toString());
						tempItem.setUnitValue(lineItemsInfo.get("price").toString());
						tempItem.setBrand(lineItemsInfo.get("vendor").toString());
						tempItem.setCusItemCode(lineItemsInfo.get("sku").toString());
						tempItem.setChgCurrency("USD");
						tempItem.setTrkCom(transCode);
						tempItem.setWUserId("Scheduler");
						tempItem.setWUserIp("15.164.92.40");
						shopifyOrderItem.add(tempItem);
					}
					
					//orderlist insert
					shopifyOrder.setSymmetryKey("07412cV8xX8MlRtA9b5lKTy2zXuM4CmO");
					shopifyOrder.encryptData();
					mapper.insertApiOrderList(shopifyOrder);
					for(int index = 0; index < shopifyOrderItem.size(); index++) {
						mapper.insertApiOrderItem(shopifyOrderItem.get(index));
					}
					
					switch(transCode) {
						case "FEDEX":
							double weight = Double.parseDouble(shopifyOrder.getUserWta());
							weight = weight*2.2046;
							if (apiShopifyInfoVO.getUserId().toLowerCase().equals("bmsmileus")) {
								temp = fedexApi.sendFedexApiSmart(apiShopifyInfoVO.getOrgStation(), newNno, "API");
							} else {
								if(weight > 9) {
									temp = fedexApi.sendFedexApiGround(apiShopifyInfoVO.getOrgStation(), newNno, "API");
								}else {
									temp = fedexApi.sendFedexApiSmart(apiShopifyInfoVO.getOrgStation(), newNno, "API");
								}
							}
							/*
							if(weight > 9) {
								temp = fedexApi.sendFedexApiGround(apiShopifyInfoVO.getOrgStation(), newNno, "API");
							}else {
								temp = fedexApi.sendFedexApiSmart(apiShopifyInfoVO.getOrgStation(), newNno, "API");
							}
							*/
							//if 조건에 따라 여기서 분기
							break;
						default :
							break;
					}
					if(temp.getStatus().equals("ERROR")) {
						fedexApi.deleteOrderListByNno(newNno);
					}
					ApiOrderListVO updateShopifyOrder = new ApiOrderListVO();
					
					updateShopifyOrder.setOrderType("TAKEIN");
					updateShopifyOrder.setNno(newNno);
					updateShopifyOrder.setUserId(shopifyOrder.getUserId());
					updateShopifyOrder.setHawbNo(temp.getHawbNo());
					
					mapper.updateApiOrderListHawb(updateShopifyOrder);
					//fedex api
					//orderlist hawb update
					for(int itemRoop = 0 ; itemRoop < lineItemsList.length(); itemRoop++) {
						JSONObject lineItemsInfoForFul = null;
						lineItemsInfoForFul = lineItemsList.getJSONObject(itemRoop);
						String variantsId = lineItemsInfoForFul.get("variant_id").toString();
						
						String requestUrl2 =  "https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/variants/"+variantsId+".json";
						HttpClient client2 = HttpClientBuilder.create().build();
						HttpGet getRequest2 = new HttpGet(requestUrl2);
						
						HttpResponse response2 = client2.execute(getRequest2);
						JSONObject variant = null;
						JSONObject variantInfo = null;
						
						if (response2.getStatusLine().getStatusCode() == 200) {
							ResponseHandler<String> handler2 = new BasicResponseHandler();
							String body2 = handler2.handleResponse(response2);
							variant = new JSONObject(body2);
							variantInfo = variant.getJSONObject("variant");
							String inventoryItemIds = variantInfo.get("inventory_item_id").toString(); 
							String requestUrl3 =  "https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/inventory_levels.json?inventory_item_ids="+inventoryItemIds;
							HttpClient client3 = HttpClientBuilder.create().build();
							HttpGet getRequest3 = new HttpGet(requestUrl3);
							
							HttpResponse response3 = client3.execute(getRequest3);
							JSONObject inventoryLevels = null;
							JSONArray inventoryLevelsList = null;
							JSONObject inventoryLevelsInfo = null;
							
							if (response3.getStatusLine().getStatusCode() == 200) {
								ResponseHandler<String> handler3 = new BasicResponseHandler();
								String body3 = handler3.handleResponse(response3);
								inventoryLevels = new JSONObject(body3);
								inventoryLevelsList =(JSONArray)inventoryLevels.get("inventory_levels");
								
								for(int roopInven = 0; roopInven < inventoryLevelsList.length(); roopInven++) {
									inventoryLevelsInfo = inventoryLevelsList.getJSONObject(roopInven);
									String locationId = inventoryLevelsInfo.get("location_id").toString();
									if(!fulfilInfo.containsKey(locationId)) {
										fulfilInfo.put(locationId, new ArrayList<String>());
									}
									fulfilInfo.get(locationId).add(lineItemsInfoForFul.get("id").toString());
								}
								
							}else {
								temp.setStatus("response3 is error");
								temp.setErrorMsg("Error Status Code : "+response.getStatusLine().getStatusCode()+"Error");
								temp.setHawbNo("");
								System.out.println("response3 is error : " + response.getStatusLine().getStatusCode());
							}
							client3.getConnectionManager().shutdown();
							
						}else {
							temp.setStatus("response2 is error");
							temp.setErrorMsg("Error Status Code : "+response.getStatusLine().getStatusCode()+"Error");
							temp.setHawbNo("");
							System.out.println("response2 is error : " + response.getStatusLine().getStatusCode());
						}
						client2.getConnectionManager().shutdown();
					}
					HttpClient client4 = HttpClientBuilder.create().build(); //Use this instead 
					try {
						for (Entry<String, ArrayList<String>> entry : fulfilInfo.entrySet()) {
				            HttpPost request = new HttpPost("https://"+apiShopifyInfoVO.getApiKey()+":"+apiShopifyInfoVO.getPassword()+"@"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2020-10/orders/"+ordersInfo.get("id").toString()+"/fulfillments.json");
							String json = "{\"fulfillment\":{\"location_id\":"+entry.getKey()+",\"tracking_number\":\""+temp.getHawbNo()+"\",\"tracking_company\":\"fedex\",\"line_items\":[";
							for(int fulfilItemId = 0; fulfilItemId < entry.getValue().size(); fulfilItemId++) {
								if(fulfilItemId!=0) {
									json+=",";
								}
								json+=  "{\"id\":"+ entry.getValue().get(fulfilItemId)+"}";
							}
							json+="]}}";
							StringEntity params =new StringEntity(json);
							request.addHeader("content-type", "application/json");
							request.setEntity(params);
							HttpResponse response4 = client4.execute(request);
							ResponseHandler<String> handler4 = new BasicResponseHandler();
							String body4 = handler4.handleResponse(response4);
				        }
							
						//handle response here...
					}catch (Exception ex) {
						ex.printStackTrace();
					    //handle exception here

					} finally {
					    //Deprecated
						client4.getConnectionManager().shutdown();
					    //httpClient.getConnectionManager().shutdown(); 
					}
					//fullpie~ update
					//end???
					
					
				}
					
					
			} else {
				temp.setStatus("response is error");
				temp.setErrorMsg("Error Status Code : "+response.getStatusLine().getStatusCode()+"Error");
				temp.setHawbNo("");
				System.out.println("response is error : " + response.getStatusLine().getStatusCode());
			}
			client.getConnectionManager().shutdown();
		}catch (Exception e) {
			// TODO: handle exception
			temp.setStatus("Exception Error");
			temp.setErrorMsg(e.toString());
			temp.setHawbNo("");
			
		}
		return "";
	}

	@Transactional
	public String getShopifyOrderList(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest request, HttpServletResponse response) {
		LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
		apiHeader.put("Content-Type", "application/json");
		apiHeader.put("X-Shopify-Access-Token", apiShopifyInfoVO.getPassword());
		apiHeader.put("Accept", "application/json");
		
		int ordersCnt = getShopifyOrderListCnt(apiShopifyInfoVO, request, response);	// shopify 주문 수량체크
		JSONArray ordersList = null;
		JSONObject ordersInfo = null;
		JSONObject shippingAddress = null;
		JSONArray lineItemsList = null;
		JSONObject lineItemsInfo = null;
		ApiShopifyResultVO temp = new ApiShopifyResultVO();
		
		int ableCnt = 0;
		
		if (ordersCnt > 0) {
			try {
				String apiUrl = "https://"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2023-07/orders.json?status=open&fulfillment_status=unshipped&financial_status=paid&limit=250";
				JSONObject orderJson = apiGet(apiUrl, apiHeader, request);
				
				if (orderJson != null) {
					HashMap<String, ArrayList<String>> fulfilInfo = new HashMap<String, ArrayList<String>>();
					ordersList =(JSONArray) orderJson.get("orders");
					
					for (int i = 0; i < ordersList.length(); i++) {
						fulfilInfo = new HashMap<String, ArrayList<String>>();
						HashMap<String, Object> shopItemInfo = new HashMap<String, Object>();
						HashMap<String,String> checkCnt = new HashMap<String,String>();
						ApiOrderListVO shopifyOrder = new ApiOrderListVO();
						ArrayList<ApiOrderItemListVO> shopifyOrderItem = new ArrayList<ApiOrderItemListVO>();
						boolean stopFlag = false;
						
						ordersInfo = ordersList.getJSONObject(i);
						/*
						if (!ordersInfo.get("id").toString().equals("5029737267356")) {
							ableCnt--;
							continue;
						}
						*/
						checkCnt.put("orderNo", ordersInfo.get("order_number").toString());
						checkCnt.put("userId", apiShopifyInfoVO.getUserId());

						int orderCnt = mapper.selectCount(checkCnt);
						
						if (orderCnt > 0) {
							continue;
						}
						ableCnt++;
						
						
						
						String newNno = comnService.selectNNO();
						shopItemInfo.put("nno", newNno);
						shopItemInfo.put("orderId", ordersInfo.optString("id"));
						shopItemInfo.put("orderNo", ordersInfo.optString("order_number"));
						shopItemInfo.put("userId", apiShopifyInfoVO.getUserId());
						
						shippingAddress = ordersInfo.getJSONObject("shipping_address");
						lineItemsList = ordersInfo.getJSONArray("line_items");
						
						// set orderinfo vo
						shopifyOrder.setNno(newNno);
						shopifyOrder.setOrgStation(apiShopifyInfoVO.getOrgStation());
						shopifyOrder.setOrderNo(ordersInfo.get("order_number").toString());
						shopifyOrder.setDstnNation(shippingAddress.get("country_code").toString());
						shopifyOrder.setDstnStation(shippingAddress.get("country_code").toString());
						shopifyOrder.setUserId(apiShopifyInfoVO.getUserId());
						shopifyOrder.setOrderType("TAKEIN");
						String[] tempDate = ordersInfo.get("created_at").toString().split("T");
						shopifyOrder.setOrderDate(tempDate[0].replace("-", ""));
						shopifyOrder.setBoxCnt("1");
						double userWta = Double.parseDouble(ordersInfo.get("total_weight").toString());
						userWta = userWta/1000;
						shopifyOrder.setUserWta(Double.toString(userWta));
						shopifyOrder.setWtUnit("KG");
						
						shopifyOrder.setShipperName("ACI");
						shopifyOrder.setShipperZip("90703");
						shopifyOrder.setShipperTel("13109650463");
						shopifyOrder.setShipperHp("");
						shopifyOrder.setShipperCntry("US");
						shopifyOrder.setShipperState("CA");
						shopifyOrder.setShipperCity("Cerritos");
						shopifyOrder.setShipperAddr("14056 Artesia Blvd");
						shopifyOrder.setShipperAddrDetail("");
						
						String _fullName = shippingAddress.get("first_name")+" "+shippingAddress.get("last_name");
						_fullName = _fullName.replaceAll("&", ",");
						_fullName = _fullName.replaceAll("[!@#$%^*().?\":{}|<>]", ""); 


						shopifyOrder.setCneeName(_fullName);
						if(shippingAddress.getString("first_name").toLowerCase().equals("test") && shippingAddress.getString("last_name").toLowerCase().equals("test") ) {
							ableCnt--;
							continue;
						}
						shopifyOrder.setCneeZip(shippingAddress.get("zip").toString());
						shopifyOrder.setCneeTel(shippingAddress.get("phone").toString());
						shopifyOrder.setCneeHp(shippingAddress.get("phone").toString());
						shopifyOrder.setCneeCntry(shippingAddress.get("country_code").toString());
						shopifyOrder.setCneeState(shippingAddress.get("province_code").toString());
						shopifyOrder.setCneeCity(shippingAddress.get("city").toString());
						
						String address = shippingAddress.get("address1").toString();
						if(shippingAddress.get("address2").toString().equals("") || shippingAddress.get("address2").toString() == null || shippingAddress.get("address2").toString().toUpperCase().equals("NULL")) {
							
						}else {
							address += " " + shippingAddress.get("address2").toString();
						}
						shopifyOrder.setCneeAddr(address);
						shopifyOrder.setCneeAddrDetail("");
						
						HashMap<String,Object> transCodeParam = new HashMap<String,Object>(); 
						shopifyOrder.setUserEmail(ordersInfo.get("email").toString());
						transCodeParam.put("userId", apiShopifyInfoVO.getUserId());
						transCodeParam.put("orgStation",apiShopifyInfoVO.getOrgStation());
						transCodeParam.put("dstnNation",shippingAddress.get("country_code").toString());
						String transCode = mapper.selectTransCodeByUserId(transCodeParam);
						
						shopifyOrder.setTransCode(transCode);
						shopifyOrder.setCneeEmail(ordersInfo.get("email").toString());
						shopifyOrder.setWUserId((String)request.getSession().getAttribute("USER_ID"));
						shopifyOrder.setWUserIp((String)request.getSession().getAttribute("USER_IP"));
						
						int itemSubNo = 0;
						
						// set orderitem vo
						for(int itemRoop = 0 ; itemRoop < lineItemsList.length(); itemRoop++) {
							lineItemsInfo = lineItemsList.getJSONObject(itemRoop);
							shopItemInfo.put("itemId", itemRoop+1);
							if(lineItemsInfo.get("fulfillable_quantity").toString().equals("0")) {
								continue;
							}
							if(lineItemsInfo.get("sku").toString().contains("-")) {
								String temp1[] = lineItemsInfo.get("sku").toString().split("-");
								for(int idx1 = 0; idx1 < temp1.length; idx1++) {
									ApiOrderItemListVO tempItem = new ApiOrderItemListVO();
									tempItem.setNno(newNno);
									tempItem.setOrgStation(apiShopifyInfoVO.getOrgStation());
									tempItem.setUserId(apiShopifyInfoVO.getUserId());
									tempItem.setItemCnt(lineItemsInfo.get("quantity").toString());
									tempItem.setUnitValue(lineItemsInfo.get("price").toString());
									tempItem.setBrand(lineItemsInfo.get("vendor").toString());
									
									itemSubNo++;
									tempItem.setSubNo(Integer.toString(itemSubNo));
									tempItem.setItemDetail(lineItemsInfo.get("title").toString()+" other "+temp1.length+" kinds");
									String newSku = temp1[idx1].replaceAll(" ", "");
									tempItem.setCusItemCode(newSku);
									tempItem.setChgCurrency("USD");
									tempItem.setTrkCom(transCode);
									tempItem.setWUserId((String)request.getSession().getAttribute("USER_ID"));
									tempItem.setWUserIp((String)request.getSession().getAttribute("USER_IP"));
									shopifyOrderItem.add(tempItem);
								}
							} else {
								ApiOrderItemListVO tempItem = new ApiOrderItemListVO();
								tempItem.setNno(newNno);
								tempItem.setOrgStation(apiShopifyInfoVO.getOrgStation());
								tempItem.setUserId(apiShopifyInfoVO.getUserId());
								tempItem.setItemCnt(lineItemsInfo.get("quantity").toString());
								tempItem.setUnitValue(lineItemsInfo.get("price").toString());
								tempItem.setBrand(lineItemsInfo.get("vendor").toString());
								
								itemSubNo++;
								tempItem.setSubNo(Integer.toString(itemSubNo));
								tempItem.setItemDetail(lineItemsInfo.get("title").toString());
								tempItem.setCusItemCode(lineItemsInfo.get("sku").toString());
								tempItem.setChgCurrency("USD");
								tempItem.setTrkCom(transCode);
								tempItem.setWUserId((String)request.getSession().getAttribute("USER_ID"));
								tempItem.setWUserIp((String)request.getSession().getAttribute("USER_IP"));
								shopifyOrderItem.add(tempItem);
							}
						}
						
						shopifyOrder.setDimUnit("CM");
						shopifyOrder.setSymmetryKey("07412cV8xX8MlRtA9b5lKTy2zXuM4CmO");
						shopifyOrder.encryptData();
						
						for(int itemIndex=0; itemIndex<shopifyOrderItem.size();itemIndex++) {
							int takeItemCnt = mapper.selectTakeItemCnt(shopifyOrderItem.get(itemIndex).getCusItemCode());
							if(takeItemCnt < Integer.parseInt(shopifyOrderItem.get(itemIndex).getItemCnt())) {
								stopFlag=true;
								break;
							}
						}
						
						if(stopFlag) {
							ableCnt--;
							continue;
						}
						
						String orderId = ordersInfo.optString("id");
						
						String fulfillapiUrl = "https://"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2023-07/orders/"+orderId+"/fulfillment_orders.json";
						JSONObject fulfillJson = apiGet(fulfillapiUrl, apiHeader, request);
						JSONArray fulfillList = (JSONArray) fulfillJson.get("fulfillment_orders");
						
						JSONObject fulfillInfo = fulfillList.getJSONObject(0);
						JSONArray fulfillItems = (JSONArray) fulfillInfo.get("line_items");
						System.out.println("index " + i);
						System.out.println(fulfillItems);
						
						ArrayList<String> fulfillOrderIdList = new ArrayList<String>();
						for (int fulfillIndex = 0; fulfillIndex < fulfillItems.length(); fulfillIndex++) {
							JSONObject fulfillItemInfo = fulfillItems.getJSONObject(fulfillIndex);
							String fulfillmentOrderId = fulfillItemInfo.get("fulfillment_order_id").toString();
							if (!fulfillOrderIdList.contains(fulfillmentOrderId)) {
								fulfillOrderIdList.add(fulfillmentOrderId);	
							}
						}
						
						for (int fulfillIndex2 = 0; fulfillIndex2 < fulfillOrderIdList.size(); fulfillIndex2++) {
							shopItemInfo.put("fulfillId", fulfillOrderIdList.get(fulfillIndex2));
							mapper.insertShopifyFulfillData(shopItemInfo);
						}
						
						HashMap<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("nno", newNno);
						parameters.put("wUserId", request.getSession().getAttribute("USER_ID"));
						parameters.put("wUserIp", request.getSession().getAttribute("USER_IP"));
						parameters.put("transCode", transCode);
						parameters.put("orderNo", shopifyOrder.getOrderNo());
						parameters.put("userId", shopifyOrder.getUserId());
						BlApplyVO blVo = new BlApplyVO();
						blVo = mapper.selectBlApply(parameters);
						if (!blVo.getStatus().equals("SUCCESS")) {
							ableCnt--;
							continue;
						}
						
						shopifyOrder.setHawbNo(blVo.getHawbNo());
						
						mapper.insertApiOrderList(shopifyOrder);
						for(int index = 0; index < shopifyOrderItem.size(); index++) {
							mapper.insertApiOrderItem(shopifyOrderItem.get(index));
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return apiShopifyInfoVO.getUserId()+"의 주문요청건 "+ordersCnt+"개 중 "+ableCnt+"건이 적용되었습니다.";
	}

	private int getShopifyOrderListCnt(ApiShopifyInfoVO apiShopifyInfoVO, HttpServletRequest request, HttpServletResponse response) {
		String apiUrl = "https://"+apiShopifyInfoVO.getShopifyUrl()+"/admin/api/2023-07/orders/count.json?status=open&fulfillment_status=unshipped&financial_status=paid";
		int cnt = 0;
		
		try {
			
			LinkedHashMap<String, Object> apiHeader = new LinkedHashMap<String, Object>();
			apiHeader.put("Content-Type", "application/json");
			apiHeader.put("X-Shopify-Access-Token", apiShopifyInfoVO.getPassword());
			apiHeader.put("Accept", "application/json");
			
			JSONObject responseData = apiGet(apiUrl, apiHeader, request);
			cnt = responseData.optInt("count");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	public JSONObject apiGet(String url, LinkedHashMap<String, Object> apiHeader, HttpServletRequest request) {
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		JSONObject json = null;
		HashMap<String, Object> apiParams = new HashMap<String, Object>();
		apiParams.put("jsonHeader", "");
		apiParams.put("jsonData", apiHeader.toString());
		
		try {
			URL httpUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setDoOutput(false);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			
			if (apiHeader != null && !apiHeader.isEmpty()) {
				for (String key : apiHeader.keySet()) {
					Object value = apiHeader.get(key);
					conn.setRequestProperty(key, value.toString());
				}
			}
			
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				while ((inputLine = in.readLine()) != null) {
					outResult.append(inputLine);
				}
				
				json = new JSONObject(String.valueOf(outResult.toString()));
			}
			conn.disconnect();
			
			apiParams.put("rtnContents", json.toString());
			apiParams.put("wUserId", request.getSession().getAttribute("USER_ID").toString());
			apiParams.put("wUserIp", request.getSession().getAttribute("USER_IP").toString());
			apiParams.put("connUrl", url);
			apiParams.put("nno", "");
			
			return json;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public JSONObject apiPost(String url, LinkedHashMap<String, Object> apiHeader, HttpServletRequest request, String jsonVal) {
		String inputLine = null;
		StringBuffer outResult = new StringBuffer();
		JSONObject json = null;
		HashMap<String, Object> apiParams = new HashMap<String, Object>();
		apiParams.put("jsonHeader", apiHeader.toString());
		apiParams.put("jsonData", jsonVal);
		
		try {
			URL httpUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			
			if (apiHeader != null && !apiHeader.isEmpty()) {
				for (String key : apiHeader.keySet()) {
					Object value = apiHeader.get(key);
					conn.setRequestProperty(key, value.toString());
				}
			}

			OutputStream os = conn.getOutputStream();
			os.write(jsonVal.getBytes());
			os.flush();
			
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				while ((inputLine = in.readLine()) != null) {
					outResult.append(inputLine);
				}
				
				json = new JSONObject(String.valueOf(outResult.toString()));
			}
			conn.disconnect();
			
			if (json == null) {
				apiParams.put("rtnContents", "");
			} else {
				apiParams.put("rtnContents", json.toString());	
			}
			apiParams.put("wUserId", request.getSession().getAttribute("USER_ID").toString());
			apiParams.put("wUserIp", request.getSession().getAttribute("USER_IP").toString());
			apiParams.put("connUrl", url);
			apiParams.put("nno", "");
			
			return json;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
