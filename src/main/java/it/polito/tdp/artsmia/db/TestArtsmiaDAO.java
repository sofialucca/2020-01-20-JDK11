package it.polito.tdp.artsmia.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Exhibition;

public class TestArtsmiaDAO {

	public static void main(String[] args) {

		ArtsmiaDAO dao = new ArtsmiaDAO();

		System.out.println("Test objects:");
		List<ArtObject> objects = dao.listObjects();
		System.out.println(objects.get(0));
		System.out.println(objects.size());
		
		System.out.println("Test exhibitions:");
		List<Exhibition> exhibitions = dao.listExhibitions();
		System.out.println(exhibitions.get(0));
		System.out.println(exhibitions.size());
		
		Map<Integer,Artist> mappa = new HashMap<>();
		dao.setVertici(mappa, "photographer");
		System.out.println(mappa);

	}

}
