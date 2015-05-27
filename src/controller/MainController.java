package controller;

import persistence.JPAUtil;

public class MainController {
	public MainController(){
		
	}
	
	public static void initFactory(){
		JPAUtil.initFactory();
	}
	
	public static void closeConnections(){
		JPAUtil.closeEntityManagerFactory();
	}
}
