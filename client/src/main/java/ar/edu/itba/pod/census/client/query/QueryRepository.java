package ar.edu.itba.pod.census.client.query;

import java.util.List;

import ar.edu.itba.pod.census.api.models.Citizen;

public class QueryRepository {
	
	private QueryRepository() {
		
	}
	
	public static <K, V> Query<K, V> of(String id, List<Citizen> citizens) {
		
		switch (id) {
			case "1": return null; // new Query1<K, V>(citizens);
			case "2": return null; // new Query2<K, V>(citizens);
			case "3": return null; // new Query3<K, V>(citizens);
			case "4": return null; // new Query4<K, V>(citizens);
			case "5": return null; // new Query5<K, V>(citizens);
			default: return null;
		}
	}
}
