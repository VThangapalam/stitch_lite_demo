package com.stitchlite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stitchlite.dao.TokenDAO;
import com.stitchlite.entity.Token;

@Service
public class StoreAccessService {
	@Autowired
	TokenDAO accessTokenDAO;
	public Token getTokenFromStichId(long stitchID) {
		return accessTokenDAO.getTokenFromStichId(stitchID);
	}
	
	
}
