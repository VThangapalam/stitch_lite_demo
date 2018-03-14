package com.stitchlite.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.stitchlite.entity.Token;
@Repository
public class TokenDAO {
	 @PersistenceContext
	    private EntityManager em;

	public Token getTokenFromStichId(long stitchID) {
		String query = "select * from access_token where stitchid="+stitchID;
		List<Token> tokens = em.createNativeQuery(query,Token.class).getResultList();
		if(tokens.size()>0) {
			return tokens.get(0);
		}
		
			return null;
	}
	
	
}
