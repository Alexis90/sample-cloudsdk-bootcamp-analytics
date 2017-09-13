package com.sap.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryBuilder;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryResult;
import com.sap.cloud.sdk.service.prov.api.operations.Query;
import com.sap.cloud.sdk.service.prov.api.request.QueryRequest;
import com.sap.cloud.sdk.service.prov.api.response.ErrorResponse;
import com.sap.cloud.sdk.service.prov.api.response.QueryResponse;
import com.sap.demo.model.ItemEntity;
import com.sap.demo.model.OrderConsumptionEntity;
import com.sap.demo.model.ProductsEntity;
import com.sap.demo.model.UserConsumptionEntity;

public class AnalyticsService {

	private static Logger logger = LoggerFactory.getLogger(AnalyticsService.class);
	private static final String DESTINATION_NAME = "WebshopQueryEndpoint";
	
	@Query(entity = "UserConsumption", serviceName = "AnalyticsService")
	public QueryResponse getUserConsumption(QueryRequest queryRequest) {
		logger.debug("==> now call backend OData V2 service");		

		QueryResponse queryResponse = null;
		try {

			logger.debug("==> now execute query on Products");

			ODataQueryResult result = ODataQueryBuilder
					.withEntity("/srv.xsodata", "Bills") 
					.select("userId", "totalSum", "currency")
					.build()
					.execute(DESTINATION_NAME);

			logger.debug("==> After calling backend OData V2 service: result: " + result);
			
					
			List<UserConsumptionEntity> userConsumption = result.asList(UserConsumptionEntity.class);
			List<UserConsumptionEntity> aggregatedUserConsumption = new ArrayList<UserConsumptionEntity>();
			for (UserConsumptionEntity uce : userConsumption) {
				if (aggregatedUserConsumption.contains(uce)) {
					UserConsumptionEntity currentEntity = aggregatedUserConsumption.get(aggregatedUserConsumption.indexOf(uce));
					currentEntity.setOverallConsumption(currentEntity.getOverallConsumption()+uce.getOverallConsumption());
				} else {
					aggregatedUserConsumption.add(uce);
				}
			}
			
			queryResponse = QueryResponse.setSuccess().setData(aggregatedUserConsumption).response();
			return queryResponse;

		} catch (IllegalArgumentException | ODataException e) {
			logger.error("==> Eexception calling backend OData V2 service for Query of Bills: " + e.getMessage());
		

			ErrorResponse errorResponse = ErrorResponse.getBuilder()
					.setMessage("There is an error, check the logs for details")
					.setStatusCode(500)
					.setCause(e)
					.response();
			queryResponse = QueryResponse.setError(errorResponse);
		}
		return queryResponse;
		
	} 
	
	@Query(entity = "OrderConsumption", serviceName = "AnalyticsService")
	public QueryResponse getOrderConsumption(QueryRequest queryRequest) {
		logger.debug("==> now call backend OData V2 service");		
		
		QueryResponse queryResponse = null;
		try {

			logger.debug("==> now execute query on Products");
			
			System.out.println("Getting BillItems");
			
			ODataQueryResult resultBillItems = ODataQueryBuilder
					.withEntity("/srv.xsodata", "BillItems") 
					.select("productId", "amount")
					.build()
					.execute(DESTINATION_NAME);

			logger.debug("==> After calling backend OData V2 service: result: " + resultBillItems);
			

			System.out.println("Got BillItems");
			System.out.println("Getting Products");
			
			ODataQueryResult resultProducts = ODataQueryBuilder
					.withEntity("/srv.xsodata", "Products") 
					.select("productId", "availableAmount","price","currency","name")
					.build()
					.execute(DESTINATION_NAME);
			System.out.println("Got Products");
			logger.debug("==> After calling backend OData V2 service: result: " + resultProducts);
			
			System.out.println("Getting Basket");
			ODataQueryResult resultBasket = ODataQueryBuilder
					.withEntity("/srv.xsodata", "Basket") 
					.select("productId", "amount")
					.build()
					.execute(DESTINATION_NAME);
			
			System.out.println("Transferring BillItems into Array");
			List<ItemEntity> billItems = resultBillItems.asList(ItemEntity.class);
			System.out.println("Transferring Products into Array");
			List<ProductsEntity> products = resultProducts.asList(ProductsEntity.class);
			System.out.println("Transferring Basket into Array");
			List<ItemEntity> baskets = resultBasket.asList(ItemEntity.class);
			System.out.println("Creating orderConsumption list for storing results");
			List<OrderConsumptionEntity> orderConsumption = new ArrayList<OrderConsumptionEntity>();
			
			System.out.println("Creating basketList as prediction mechanism");
			List<ItemEntity> basketList = new ArrayList<ItemEntity>();
			
			System.out.println("Aggregating all Basket information to product level");
			// Aggregating basket information for making predictions
			for (ItemEntity ie : baskets) {
				System.out.println("Current BasketItem " + ie.getProductID());
				if (basketList.contains(ie)) {
					System.out.println("BasketItem exists: " + ie.getProductID());
					int iBasketItemIndex = baskets.indexOf(ie);
					System.out.println("Index of existing Item is " + iBasketItemIndex); 
					ItemEntity currentItem = baskets.get(iBasketItemIndex);
					currentItem.setAmount(currentItem.getAmount() + ie.getAmount());
				} else {
					System.out.println("Newly Added BasketItem " + ie.getProductID());
					basketList.add(ie);
				}
			}
			System.out.println("Basket Aggregation done.");
			System.out.println("Start for loop");
			// bie = BasketItemEntity
			for (ItemEntity bie : billItems) {
				System.out.println("Starting new round..");
				System.out.println("Creating new OrderConsumptionEntity");
				OrderConsumptionEntity oce = new OrderConsumptionEntity();
				System.out.println("Setting Product ID " + bie.getProductID());
				oce.setProductID(bie.getProductID());
				
				// Select current product from product list
				int iProductIndex = -1;
				for (int i = 0; i < products.size(); i++) {
					ProductsEntity tpe = products.get(i);
					System.out.println("Current Product: " + tpe.getProductID());
					System.out.println("Current BillItems: " + bie.getProductID());
					if (tpe.getProductID().equals(bie.getProductID())) {
						iProductIndex = i;
						break;
					}
				}
				
				if (iProductIndex < 0) {
					System.out.println("Cannot find Product Information for Product ID " + bie.getProductID());
					continue;
				}
				
				int iAggrgatedBasketListIndex = basketList.indexOf(bie);
				ItemEntity basketItem = null;
				if (iAggrgatedBasketListIndex < 0) {
					System.out.println("No Product with ID " + bie.getProductID() + " in basketList. Creating a dummy.");
					basketItem = new ItemEntity();
					basketItem.setProductID(bie.getProductID());
					basketItem.setAmount(0);
				} else {
					basketItem = basketList.get(iAggrgatedBasketListIndex);
				}
				
				
				ProductsEntity pe = products.get(iProductIndex);
				if (orderConsumption.contains(oce)) {
					System.out.println("Product already exists: " + oce.getProductID());
					OrderConsumptionEntity currentEntity = orderConsumption.get(orderConsumption.indexOf(oce));
					currentEntity.setNumberOrders(currentEntity.getNumberOrders()+1);
					currentEntity.setOverallOrderAmount(currentEntity.getOverallOrderAmount() + bie.getAmount());
					currentEntity.setAverageOrderAmount((currentEntity.getOverallOrderAmount()/Double.parseDouble(Integer.toString(currentEntity.getNumberOrders()))));
					currentEntity.setAveragePrice((currentEntity.getAveragePrice() + pe.getPrice())/2);
					currentEntity.setPredictedAmount(currentEntity.getAvailableAmount() - basketItem.getAmount());
				} else {
					System.out.println("New Product: " + oce.getProductID());
					oce.setOverallOrderAmount(bie.getAmount());
					oce.setAvailableAmount(pe.getAvailableAmount());
					oce.setAverageOrderAmount(Double.parseDouble(Integer.toString(bie.getAmount())));
					oce.setAveragePrice(pe.getPrice());
					oce.setCurrency(pe.getCurrency());
					oce.setPredictedAmount(oce.getAvailableAmount() - basketItem.getAmount());
					oce.setNumberOrders(1);
					oce.setDescription(pe.getDescription());
					orderConsumption.add(oce);
				}
				System.out.println("Finished round.");
			}
			
			queryResponse = QueryResponse.setSuccess().setData(orderConsumption).response();
			return queryResponse;

		} catch (IllegalArgumentException | ODataException e) {
			logger.error("==> Eexception calling backend OData V2 service for Query of Products: " + e.getMessage());
		

			ErrorResponse errorResponse = ErrorResponse.getBuilder()
					.setMessage("There is an error, check the logs for details")
					.setStatusCode(500)
					.setCause(e)
					.response();
			queryResponse = QueryResponse.setError(errorResponse);
		}
		return queryResponse;
		
	} 

}
