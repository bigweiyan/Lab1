package com.se.lab;

import java.util.HashMap;
import java.util.Set;

public class VariableMap {
	private HashMap<String, Integer> map;
	
	VariableMap()
	{
		map = new HashMap<String, Integer>();
	}
	
	VariableMap(VariableMap variableMap)
	{
		map = new HashMap<String, Integer>(variableMap.map);
	}
	
	public boolean isEmpty()
	{
		return map.isEmpty();
	}
	
	public Set<HashMap.Entry<String, Integer>>entrySet()
	{
		return map.entrySet();
	}
	
	public Set<String> keySet()
	{
		return map.keySet();
	}
	
	public boolean equals(VariableMap variableMap)
	{
		return variableMap.equals(map);
	}
	
	public boolean equals(HashMap<String, Integer> hashMap)
	{
		return hashMap.equals(map);
	}
	
	public void put(String varName, Integer rank)
	{
		map.put(varName, rank);
	}
	
	public void replace(String varName, Integer rank)
	{
		map.replace(varName, rank);
	}
	
	public Integer get(String varName)
	{
		return map.get(varName);
	}
	
	public boolean containsKey(String varName)
	{
		return map.containsKey(varName);
	}
	
	public void remove(String varName)
	{
		map.remove(varName);
	}
}
