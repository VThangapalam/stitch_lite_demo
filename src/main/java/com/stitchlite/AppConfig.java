package com.stitchlite;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.stitchlite.service.StoreProductAPIFactory;
import com.stitchlite.service.StoreProductAPIShopifyImpl;
import com.stitchlite.service.StoreProductAPIVendmpl;
import com.stitchlite.service.StoreProductService;

@Configuration
@ComponentScan(basePackages = {"com.stitchlite"})
public class AppConfig {
 @Bean
 public FactoryBean serviceLocatorFactoryBean() {
    ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
    factoryBean.setServiceLocatorInterface(StoreProductAPIFactory.class);
    return factoryBean;
 }

 @Bean(name = "shopify")
 @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
 public StoreProductAPIShopifyImpl storeProductAPIShopifyImpl() {
    return new StoreProductAPIShopifyImpl();
 }

 @Bean(name = "vend")
 @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
 public StoreProductAPIVendmpl storeProductAPIVendmpl() {
    return new StoreProductAPIVendmpl();
 }
}