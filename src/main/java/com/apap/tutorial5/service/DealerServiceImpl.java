package com.apap.tutorial5.service;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.repository.DealerDb;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@Transactional
public class DealerServiceImpl implements DealerService {
	@Autowired 
	private DealerDb dealerDb;
	
	@Override 
	public Optional<DealerModel> getDealerDetailById(Long id){
		return dealerDb.findById(id);
		
	}
	
	@Override
	public void addDealer(DealerModel dealer) {
		dealerDb.save(dealer);
	}
	
	@Override
	public void deleteDealer(DealerModel dealer) {
		dealerDb.delete(dealer);
	}

	@Override
	public List<DealerModel> getAllDealer() {
		return dealerDb.findAll();
	
	}

	@Override
	public void updateDealerForm(Long id, String alamat, String noTelp) {
		dealerDb.findById(id).get().setAlamat(alamat);
		dealerDb.findById(id).get().setNoTelp(noTelp);
		
	}
	
	
	
	
	
	
}
