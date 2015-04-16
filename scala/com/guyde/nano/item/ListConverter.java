package com.guyde.nano.item;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Multimap;


public class ListConverter{
	public static void put(Multimap map , Object obj1 , Object obj2){
		map.put(obj1, obj2);
	}
	
	public static void addTo(List list,  Object obj ){
		list.add(obj);
	}
	
	public static void addAll(List list,  List col ){
		list.addAll(col);
	}
	

}