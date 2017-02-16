package com.banque.util;

public class Run {

	public static void main(String[] args) throws Exception {
		HibernateSessionFactory hsf = new HibernateSessionFactory();
		System.out.println("open session");
		hsf.getSessionFactory().openSession();
		System.out.println(hsf.getSessionFactory().isClosed());
		hsf.getSessionFactory().close();
		System.out.println("close session");
		System.out.println(hsf.getSessionFactory().isClosed());

	}

}
